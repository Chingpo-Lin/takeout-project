package com.example.takeoutproject.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * shopping cart
 */
@Data
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // name
    private String name;

    // user id
    private Long userId;

    // dish id
    private Long dishId;

    //setmeal id
    private Long setmealId;

    // flavor
    private String dishFlavor;

    // count
    private Integer number;

    // price
    private BigDecimal amount;

    //img
    private String image;

    private LocalDateTime createTime;
}
