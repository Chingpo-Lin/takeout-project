package com.example.takeoutproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.service.CategoryService;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * category controller
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * save new category
     * @param category
     * @return
     */
    @PostMapping
    public JsonData save(@RequestBody Category category) {
        log.info("category: {}", category);
        categoryService.save(category);
        return JsonData.buildSuccess(category, "add category successfully");
    }

    /**
     * category paging
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public JsonData page(int page, int pageSize) {

        // paging constructor
        Page<Category> pageInfo = new Page<>(page, pageSize);

        // condition filter
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return JsonData.buildSuccess(pageInfo);
    }

    /**
     * delete category by id
     * @param ids
     * @return
     */
    @DeleteMapping
    public JsonData delete(Long ids) {
        log.info("delete category with id: {}", ids);
        categoryService.remove(ids);
        return JsonData.buildSuccess(null, "delete successfully");
    }
}
