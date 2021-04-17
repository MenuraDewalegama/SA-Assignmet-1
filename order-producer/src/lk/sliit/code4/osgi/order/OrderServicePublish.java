package lk.sliit.code4.osgi.order;/*
@author : Dhanusha Perera
@since : 16/04/2021
*/

import lk.sliit.code4.osgi.order.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderServicePublish {

    int placeOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(Order order);

    List<Order> searchOrderByDate(Date orderDate);
}
