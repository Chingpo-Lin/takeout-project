package com.example.takeoutproject.controller;

import com.example.takeoutproject.entity.Orders;
import com.example.takeoutproject.service.OrdersService;
import com.example.takeoutproject.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    /**
     * user order
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public JsonData submit(@RequestBody Orders orders) {
        log.info("order info: {}", orders);
        ordersService.submit(orders);
        return JsonData.buildSuccess(null, "order successfully");
    }
}
