/*
@author : Dhanusha Perera
@since : 16/04/2021
*/
package lk.sliit.code4.osgi.orderDetail;

import lk.sliit.code4.osgi.orderDetail.entity.OrderDetail;

import java.util.List;

public interface OrderDetailServicePublish {
    boolean addOrderDetail(OrderDetail orderDetail);

    boolean updateOrderDetail(OrderDetail orderDetail);

    boolean deleteOrderDetail(int orderDetailId);

    OrderDetail getOrderDetail(int orderDetailId);

    List<OrderDetail> getOrderDetails(int orderId);
}
