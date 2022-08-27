package com.example.takeoutproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.takeoutproject.dto.DishDto;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Dish;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    // search dish info and dish flavor info by id
    public DishDto getByIdWithFlavor(Long id);

    // update dish
    public void updateWithFlavor(DishDto dishDto);
}
