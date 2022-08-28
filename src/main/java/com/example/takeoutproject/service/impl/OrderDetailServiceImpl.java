package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.OrderDetail;
import com.example.takeoutproject.entity.Orders;
import com.example.takeoutproject.mapper.OrderDetailMapper;
import com.example.takeoutproject.mapper.OrdersMapper;
import com.example.takeoutproject.service.OrderDetailService;
import com.example.takeoutproject.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
