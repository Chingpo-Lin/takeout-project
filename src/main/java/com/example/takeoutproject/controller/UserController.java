package com.example.takeoutproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.takeoutproject.entity.User;
import com.example.takeoutproject.service.UserService;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * send msg code (default 123456)
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public JsonData sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        String code = "123456";
        if (StringUtils.isNotEmpty(phone)) {

            // save code 123456 into redis
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
//            session.setAttribute(phone, code);
            return JsonData.buildSuccess("send successfully");
        }
        return JsonData.buildError("user not found");
    }

    /**
     * user login
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public JsonData login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        
        String code = map.get("code").toString();
        String phone = map.get("phone").toString();

        // get code from session
//        Object codeInSession = session.getAttribute(phone);

        // get code from redis
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        // if code is correct
        if(codeInSession != null && codeInSession.equals(code)){
            // login successfully
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            System.out.println("enter here");
            User user = userService.getOne(queryWrapper);
            System.out.println("enter here2");
            if(user == null){
                // check if new user
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
                System.out.println("enter here3");
            }
            session.setAttribute("user",user.getId());

            // delete code from redis after login successfully
            redisTemplate.delete(phone);

            return JsonData.buildSuccess(user);
        }
        return JsonData.buildError("login fail");
    }
}
