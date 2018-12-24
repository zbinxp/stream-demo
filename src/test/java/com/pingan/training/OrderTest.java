package com.pingan.training;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    private List<Order> orderList;

    @Before
    public void setup() {
        orderList = createOrdersForTest();
    }

    private List<Order> createOrdersForTest() {
        Order[] orders = {
            new Order("S0001","A"),
            new Order("S0002","A"),
            new Order("S0003","B"),
            new Order("S0004","C")
        };
        orders[0].setSalesList(Arrays.asList(
            new SalesInfo("iphone",1, new BigDecimal(8888))
        ));
        orders[1].setSalesList(Arrays.asList(
            new SalesInfo("huaweiM1",2, new BigDecimal(2999)),
            new SalesInfo("xiaomi",3, new BigDecimal(1999))
        ));
        orders[2].setSalesList(Arrays.asList(
            new SalesInfo("vivoX3",4, new BigDecimal(3888)),
            new SalesInfo("iphone",1, new BigDecimal(8800))
        ));
        orders[3].setSalesList(Arrays.asList(
            new SalesInfo("NokiaN9",2, new BigDecimal(3788)),
            new SalesInfo("xiaomi",1, new BigDecimal(1888))
        ));

        return Arrays.asList(orders);
    }

    @Test
    public void countOrdersBelongsToUserA() {
        long count = orderList.stream()
                .filter(order->order.belongs("A"))
                .count();
        assertEquals(2,count);
    }

    @Test
    public void calcOrderAmountBelongsToUserA() {
        BigDecimal amount = orderList.stream()
                .filter(order->order.belongs("A"))
                .flatMap(order->order.getSalesList().stream())
                .map(si -> si.getPrice().multiply(new BigDecimal(si.getQty())))
                .reduce(BigDecimal.ZERO,(acc,e) -> acc.add(e));
        assertEquals(new BigDecimal(20883),amount);
    }

    @Test
    public void collectProduct() {
        List<String> products = orderList.stream()
                .flatMap(order->order.getSalesList().stream())
                .map(si->si.getProduct())
                .distinct()
                .collect(ArrayList::new,
                        (list,e)-> list.add(e),
                        (a,b)->a.addAll(b));
        System.out.println(products);
        assertEquals(5,products.size());
    }

    @Test
    public void collectProductUsingCollector() {
        List<String> products = orderList.stream()
                .flatMap(order->order.getSalesList().stream())
                .map(si->si.getProduct())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(products);
        assertEquals(5,products.size());
    }

    private BigDecimal calcAmount(SalesInfo si) {
        if (si == null || si.getPrice() == null) return BigDecimal.ZERO;
        return si.getPrice().multiply(new BigDecimal(si.getQty()));
    }

    @Test
    public void calcOrderAmountBelongsToUserAUsingMethodReference() {
        BigDecimal amount = orderList.stream()
                .filter(order->order.belongs("A"))
                .flatMap(order->order.getSalesList().stream())
                .map(this::calcAmount)
                .reduce(BigDecimal.ZERO,(acc,e) -> acc.add(e));
        assertEquals(new BigDecimal(20883),amount);
    }

    @Test
    public void debugUsingPeek() {
        List<String> products = orderList.stream()
                .flatMap(order->order.getSalesList().stream())
                .map(si->si.getProduct())
                .distinct()
                .peek(System.out::println)
                .collect(Collectors.toList());

        assertEquals(5,products.size());
    }
}
