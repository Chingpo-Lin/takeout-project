package com.example.takeoutproject.dto;

import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.DishFlavor;
import com.example.takeoutproject.entity.Setmeal;
import com.example.takeoutproject.entity.SetmealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
