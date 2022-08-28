package com.example.takeoutproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * order detail
 */
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // name
    private String name;

    // order id
    private Long orderId;


    // dish id
    private Long dishId;


    //setmeal id
    private Long setmealId;


    // flavor
    private String dishFlavor;


    // number
    private Integer number;

    // price
    private BigDecimal amount;

    // img
    private String image;
}
