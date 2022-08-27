package com.example.takeoutproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.takeoutproject.dto.SetmealDto;
import com.example.takeoutproject.entity.Category;
import com.example.takeoutproject.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * add new meal set, save meal set and dish relationship
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * delete setmeal and coresponding dishes data
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
