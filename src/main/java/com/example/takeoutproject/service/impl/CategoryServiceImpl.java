package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.Employee;
import com.example.takeoutproject.entity.Setmeal;
import com.example.takeoutproject.exception.CustomException;
import com.example.takeoutproject.mapper.CategoryMapper;
import com.example.takeoutproject.mapper.EmployeeMapper;
import com.example.takeoutproject.service.CategoryService;
import com.example.takeoutproject.service.DishService;
import com.example.takeoutproject.service.EmployeeService;
import com.example.takeoutproject.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * delte category by id
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // check if current category connect with dish, if yes, throw exception
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            // throw custom exception
            throw new CustomException("there is dish under current category, delete fail");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // check if current category connect with Setmeal, if yes, throw exception
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            // throw custom exception
            throw new CustomException("there is meal set under current category, delete fail");
        }

        // normal delete
        super.removeById(id);
    }
}
