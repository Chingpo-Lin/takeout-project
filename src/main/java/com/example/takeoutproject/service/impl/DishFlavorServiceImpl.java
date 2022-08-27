package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.DishFlavor;
import com.example.takeoutproject.mapper.DishFlavorMapper;
import com.example.takeoutproject.mapper.DishMapper;
import com.example.takeoutproject.service.DishFlavorService;
import com.example.takeoutproject.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
