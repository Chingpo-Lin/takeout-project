package com.example.takeoutproject.filter;

import com.alibaba.fastjson.JSON;
import com.example.takeoutproject.util.BaseContext;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. get request uri
        String requestURI = request.getRequestURI();

        // urls that not need to filter
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        // 2. if need filter
        boolean check = check(urls, requestURI);

        // 3. if not need, just do filter
        if (check) {
            log.info("not need to check {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 4.1 check employee login status, if login, dofilter
        if (request.getSession().getAttribute("employee") != null) {
            Long empId = (Long)request.getSession().getAttribute("employee");
            log.info("employee already logins with id: {}", empId);

            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(request.getSession().getAttribute("user"));
        // 4.2 check user login status, if login, dofilter
        if (request.getSession().getAttribute("user") != null) {
            Long userId = (Long)request.getSession().getAttribute("user");
            log.info("user already logins with id: {}", userId);

            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }

        log.info("user need login {}");
        // if not login return not login result
        response.getWriter().write(JSON.toJSONString(JsonData.buildError("NOTLOGIN")));
        return;
    }

    /**
     * check if need filter
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
//        return true;
        System.out.println(requestURI);
        for (String url: urls) {
            boolean match = ANT_PATH_MATCHER.match(url, requestURI);
            if (match) return true;
        }
        return false;
    }
}
