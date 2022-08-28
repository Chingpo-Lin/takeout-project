package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.Employee;
import com.example.takeoutproject.entity.User;
import com.example.takeoutproject.mapper.EmployeeMapper;
import com.example.takeoutproject.mapper.UserMapper;
import com.example.takeoutproject.service.EmployeeService;
import com.example.takeoutproject.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
