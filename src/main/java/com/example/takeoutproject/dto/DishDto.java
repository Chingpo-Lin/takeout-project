package com.example.takeoutproject.dto;

import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
