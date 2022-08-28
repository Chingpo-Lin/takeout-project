package com.example.takeoutproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeoutproject.entity.AddressBook;
import com.example.takeoutproject.entity.ShoppingCart;
import com.example.takeoutproject.mapper.AddressBookMapper;
import com.example.takeoutproject.mapper.ShoppingCartMapper;
import com.example.takeoutproject.service.AddressBookService;
import com.example.takeoutproject.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
