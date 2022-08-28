package com.example.takeoutproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeoutproject.dto.SetmealDto;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Setmeal;
import com.example.takeoutproject.service.CategoryService;
import com.example.takeoutproject.service.SetmealDishService;
import com.example.takeoutproject.service.SetmealService;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * set meal controller
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * add new set meal
     * @param setmealDto
     * @return
     */
    @PostMapping
    public JsonData save(@RequestBody SetmealDto setmealDto) {
        log.info("meal set info: {}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return JsonData.buildSuccess(null, "successfully add set meal");
    }

    @GetMapping("/page")
    public JsonData page(int page, int pageSize, String name) {
        // paging constructor
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        // condition
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = new ArrayList<>();

        for (Setmeal setmeal: records) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            Long categoryId = setmeal.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            list.add(setmealDto);
        }

        dtoPage.setRecords(list);
        return JsonData.buildSuccess(dtoPage);
    }

    /**
     * delete set meals
     * @param ids
     * @return
     */
    @DeleteMapping
    public JsonData delete(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        setmealService.removeWithDish(ids);
        return JsonData.buildSuccess(null, "delete successfully");
    }

    /**
     * list of setmeal
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public JsonData list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return JsonData.buildSuccess(list);
    }
}
