package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.Setmeal;
import com.example.takeoutproject.entity.SetmealDish;
import com.example.takeoutproject.mapper.SetmealDishMapper;
import com.example.takeoutproject.mapper.SetmealMapper;
import com.example.takeoutproject.service.SetmealDishService;
import com.example.takeoutproject.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
