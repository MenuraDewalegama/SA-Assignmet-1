package lk.sliit.code4.osgi.order;

import lk.sliit.code4.osgi.order.entity.Order;
import org.osgi.framework.BundleContext;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public interface OrderServicePublish {

    void setContext(BundleContext context);

    int placeOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(Order order);

    boolean deleteOrderByOrderId(int orderId);

    List<Order> searchOrderByDate(Date orderDate);

    Order findOrder(int orderId);

    List<Order> findOrders();

    boolean isContains(int orderId);

    boolean isEmpty();

    boolean updateCustomerIdOfTheOrder(int orderId, int newCustomerId);

    LinkedList<Order> getBackUp();

    void setBackUp(LinkedList<Order> linkList);
}
