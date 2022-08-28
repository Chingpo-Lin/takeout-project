package com.example.takeoutproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.takeoutproject.entity.ShoppingCart;
import com.example.takeoutproject.service.ShoppingCartService;
import com.example.takeoutproject.util.BaseContext;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * add to cart
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public JsonData add(@RequestBody ShoppingCart shoppingCart) {
        log.info("shopping cart: {}", shoppingCart);

        // set user id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // check if current dish is inside cart (if yes, plus count, if no, add new)
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            // means add dish to cart
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // means add setmeal to cart
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if (cart != null) {
            // exists in cart
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartService.updateById(cart);
        } else {
            // not exists in cart
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            System.out.println(shoppingCart);
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }
        return JsonData.buildSuccess(cart);
    }

    /**
     * check cart
     * @return
     */
    @GetMapping("/list")
    public JsonData list() {
        log.info("check cart...");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return JsonData.buildSuccess(list);
    }

    /**
     * clear shopping cart
     * @return
     */
    @DeleteMapping("/clean")
    public JsonData clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(queryWrapper);
        return JsonData.buildSuccess(null, "successfully clear");
    }

    /**
     * sub shopping cart
     */
    @PostMapping("/sub")
    public JsonData sub(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        // check if current dish is inside cart (if yes, plus count, if no, add new)
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            // means add dish to cart
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // means add setmeal to cart
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if (cart.getNumber() <= 1) {
            // remove from cart
            shoppingCartService.removeById(cart);
        } else {
            // minus 1 from cart
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartService.updateById(cart);
        }
        return JsonData.buildSuccess(cart);
    }
}
