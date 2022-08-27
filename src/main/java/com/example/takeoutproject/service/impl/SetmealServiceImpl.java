package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.dto.SetmealDto;
import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.Setmeal;
import com.example.takeoutproject.entity.SetmealDish;
import com.example.takeoutproject.exception.CustomException;
import com.example.takeoutproject.mapper.DishMapper;
import com.example.takeoutproject.mapper.SetmealMapper;
import com.example.takeoutproject.service.DishService;
import com.example.takeoutproject.service.SetmealDishService;
import com.example.takeoutproject.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * add new meal set, save meal set and dish relationship
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // save meal set basic info use insert
        this.save(setmealDto);

        // save meal set and dish relation
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish: setmealDishList) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishList);
    }

    /**
     * delete setmeal and coresponding dishes data
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // read setmeal status, make sure it can be delete
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);
        // if not, throw exception
        if (count > 0) { // means setmeal is on sell, cannot delete
            throw new CustomException("setmeal is on sell, cannot delete");
        }
        // if can delete, delete setmeal data first
        this.removeByIds(ids);

        // delete data in relative table
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
