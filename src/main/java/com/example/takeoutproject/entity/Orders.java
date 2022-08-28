package com.example.takeoutproject.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * order
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // order number
    private String number;

    //订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
    private Integer status;


    // user id
    private Long userId;

    // address id
    private Long addressBookId;


    // order time
    private LocalDateTime orderTime;


    // checkout time
    private LocalDateTime checkoutTime;


    //支付方式 1微信，2支付宝
    private Integer payMethod;


    //实收金额
    private BigDecimal amount;

    //备注
    private String remark;

    //用户名
    private String userName;

    //手机号
    private String phone;

    //地址
    private String address;

    //收货人
    private String consignee;
}
