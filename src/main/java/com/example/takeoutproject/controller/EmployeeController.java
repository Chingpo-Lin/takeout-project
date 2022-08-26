package com.example.takeoutproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeoutproject.entity.Employee;
import com.example.takeoutproject.service.EmployeeService;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * employee login
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public JsonData login(HttpServletRequest request, @RequestBody Employee employee) {

        // 1. use MD5 algorithm to encode
        String encodePassword = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        // 2. search database by username
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee searchEmployee = employeeService.getOne(queryWrapper);

        // 3. if can't search
        if (searchEmployee == null) {
            return JsonData.buildError("login fail: can't find username");
        }

        // 4. compare password
        if (!encodePassword.equals(searchEmployee.getPassword())) {
            return JsonData.buildError("login fail: username and password not match");
        }

        // 5. check employee status
        if (searchEmployee.getStatus() == 0) {
            return JsonData.buildError("employee account is banned");
        }

        // 6. put id into session and return success
        request.getSession().setAttribute("employee", searchEmployee.getId());

        return JsonData.buildSuccess(searchEmployee);
    }

    /**
     * employee logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public JsonData logout(HttpServletRequest request) {
        // clear session id
        request.getSession().removeAttribute("employee");

        return JsonData.buildSuccess("successful logout");
    }

    /**
     *
     * @param employee
     * @return
     */
    @PostMapping
    public JsonData save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("add employee: {}", employee.toString());

        // initial password, need md5
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long)request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return JsonData.buildSuccess();
    }

    /**
     * paging for employee
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public JsonData page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        /// paging constructor
        Page pageInfo = new Page(page, pageSize);

        // condition constructor
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        if (name != null && name.length() != 0) {
            queryWrapper.like(Employee::getName, name);
        }
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // execute paging
        employeeService.page(pageInfo, queryWrapper);

        return JsonData.buildSuccess(pageInfo);
    }

    /**
     * renew employee info by id
     * @param employee
     * @return
     */
    @PutMapping
    public JsonData update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return JsonData.buildSuccess();
    }
}
