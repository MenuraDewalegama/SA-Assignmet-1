package lk.sliit.code4.osgi.orderDetail;

import lk.sliit.code4.osgi.orderDetail.entity.OrderDetail;

import java.util.LinkedList;
import java.util.List;

public interface OrderDetailServicePublish {
    boolean addOrderDetail(OrderDetail orderDetail);

    boolean updateOrderDetail(OrderDetail orderDetail);

    boolean deleteOrderDetail(int orderDetailId);

    boolean deleteOrderDetailByOrderId(int orderId);

    OrderDetail getOrderDetail(int orderDetailId);

    List<OrderDetail> getOrderDetails(int orderId);

    LinkedList<OrderDetail> getBackUp();

    void setBackUp(LinkedList<OrderDetail> orderDetailLinkedList);

}
