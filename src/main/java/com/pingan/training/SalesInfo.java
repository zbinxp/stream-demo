package com.pingan.training;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesInfo {
    private String product;
    private int qty;
    private BigDecimal price;

    public SalesInfo(){}
    public SalesInfo(String product,int qty,BigDecimal price) {
        this.product = product;
        this.qty = qty;
        this.price = price;
    }
}
