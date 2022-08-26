package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Employee;
import com.example.takeoutproject.mapper.CategoryMapper;
import com.example.takeoutproject.mapper.EmployeeMapper;
import com.example.takeoutproject.service.CategoryService;
import com.example.takeoutproject.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
