package com.pingan.training;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String orderId;
    private List<SalesInfo> salesList;
    private String userId;

    public Order() {}
    public Order(String orderId,String userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public boolean belongs(String user){
        return this.userId.equals(user);
    }
}
