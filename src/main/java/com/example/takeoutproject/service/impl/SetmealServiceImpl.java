package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.Setmeal;
import com.example.takeoutproject.mapper.DishMapper;
import com.example.takeoutproject.mapper.SetmealMapper;
import com.example.takeoutproject.service.DishService;
import com.example.takeoutproject.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
