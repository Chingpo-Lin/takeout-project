package com.example.takeoutproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeoutproject.dto.DishDto;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Dish;
import com.example.takeoutproject.service.CategoryService;
import com.example.takeoutproject.service.DishFlavorService;
import com.example.takeoutproject.service.DishService;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * add new dish
     * @return
     */
    @PostMapping
    public JsonData save(@RequestBody DishDto dishDto) {
        log.info("add new dish: {}", dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return JsonData.buildSuccess(null, "add successfully");
    }

    /**
     * dish paging
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public JsonData page(int page, int pageSize, String name) {
        // paging constructor
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // condition constructor
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        // add filter condition
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        // page
        dishService.page(pageInfo, queryWrapper);

        // object copy, and solely handle records because there is no category name
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();

        // use stream to set a list of dishdto
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return JsonData.buildSuccess(dishDtoPage);
    }

    /**
     * check dish info and flavor info by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public JsonData get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return JsonData.buildSuccess(dishDto);
    }

    /**
     * update dishes
     * @return
     */
    @PutMapping
    public JsonData update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        return JsonData.buildSuccess(null, "successfully add");
    }
}
