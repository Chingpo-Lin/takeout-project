package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.*;
import com.example.takeoutproject.exception.CustomException;
import com.example.takeoutproject.mapper.AddressBookMapper;
import com.example.takeoutproject.mapper.OrdersMapper;
import com.example.takeoutproject.service.*;
import com.example.takeoutproject.util.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * user order
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        // get current user id
        Long userId = BaseContext.getCurrentId();

        // check current user shopping cart
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<ShoppingCart>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(queryWrapper);

        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("shopping cart is empty, can't order");
        }

        // read user data
        User user = userService.getById(userId);

        // read address data
        System.out.println("order is: " + orders);
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        System.out.println("address is: " + addressBook);
        if (addressBook == null) {
            throw new CustomException("address incorrect, cannot place order");
        }

        AtomicInteger amount = new AtomicInteger(0);

        long orderId = IdWorker.getId();

        List<OrderDetail> orderDetailList = shoppingCartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        // insert one data to order table
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2); // 2 means in way
        orders.setAmount(new BigDecimal(amount.get()));// total amount
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee()); // user name for receipt
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        this.save(orders);

        // insert data into orderDetail table
        orderDetailService.saveBatch(orderDetailList);

        // clear shopping cart
        shoppingCartService.remove(queryWrapper);
    }
}
