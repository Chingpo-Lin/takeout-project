package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.AddressBook;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.Setmeal;
import com.example.takeoutproject.exception.CustomException;
import com.example.takeoutproject.mapper.AddressBookMapper;
import com.example.takeoutproject.mapper.CategoryMapper;
import com.example.takeoutproject.service.AddressBookService;
import com.example.takeoutproject.service.CategoryService;
import com.example.takeoutproject.service.DishService;
import com.example.takeoutproject.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
