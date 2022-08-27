package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.dto.DishDto;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.DishFlavor;
import com.example.takeoutproject.mapper.CategoryMapper;
import com.example.takeoutproject.mapper.DishMapper;
import com.example.takeoutproject.service.CategoryService;
import com.example.takeoutproject.service.DishFlavorService;
import com.example.takeoutproject.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * save dishes and save flavor
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // save to dish table
        this.save(dishDto);

        Long dishId = dishDto.getId(); // dish id
        List<DishFlavor> flavorList = dishDto.getFlavors();
//        flavorList.stream().map((item) -> {
//            item.setDishId(dishId);
//            return item;
//        }).collect(Collectors.toList());
        for (DishFlavor df: flavorList) {
            df.setDishId(dishId);
        }
        // save to dish flavor table
        dishFlavorService.saveBatch(flavorList);
    }

    /**
     * search dish info and dish flavor info by id
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        // get dish info
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        // get flavor info of current dish
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavorList = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavorList);

        return dishDto;
    }

    /**
     *
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // update dish basic info
        this.updateById(dishDto);

        // clear dish flavor (delete)
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // add updated flavor (insert) dish id is null inside flavor list so we need to add manually
        List<DishFlavor> flavorList = dishDto.getFlavors();
        Long dishId = dishDto.getId();
        for (DishFlavor df: flavorList) {
            df.setDishId(dishId);
        }
        dishFlavorService.saveBatch(flavorList);
    }
}
