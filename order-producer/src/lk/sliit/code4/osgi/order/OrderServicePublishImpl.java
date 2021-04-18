package lk.sliit.code4.osgi.order;

import lk.sliit.code4.osgi.order.entity.Order;
import lk.sliit.code4.osgi.orderDetail.OrderDetailServicePublish;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServicePublishImpl implements OrderServicePublish {
    private static OrderServicePublishImpl orderServicePublishImpl = null;
    BundleContext context;
    //    private final OrderDetailServicePublish orderDetailPublish = OrderDetailServicePublishImpl.getInstance();
    /* counter is used to manage the order ids. */
    int counter = 0;
    /* Orders are stored here. */
    LinkedList<Order> orderLinkedList = new LinkedList<>();
    private OrderDetailServicePublish orderDetailPublish;

    /* default constructor. */
    private OrderServicePublishImpl() {
    }

    /**
     * Expose the class instance.
     * make the class singleton.
     */
    public static OrderServicePublishImpl getInstance() {
        return (orderServicePublishImpl == null)
                ? orderServicePublishImpl = new OrderServicePublishImpl()
                : orderServicePublishImpl;
    }


    /**
     * setter for bundle context.
     */
    @Override
    public void setContext(BundleContext context) {
        this.context = context;
        initServiceReferences();
    }

    private void initServiceReferences() {
        ServiceReference serviceReference =
                this.context.getServiceReference(OrderDetailServicePublish.class.getName());
        orderDetailPublish = (OrderDetailServicePublish) this.context.getService(serviceReference);
    }

    /**
     * add a new order.
     */
    @Override
    public int placeOrder(Order order) {
        int result = 0;
        if (order != null) {
            order.setId(++counter);
            if (this.orderLinkedList.add(order)) {
                result = order.getId();
            }
        }

        return result;
    }

    /**
     * update an order using Order object.
     */
    @Override
    public boolean updateOrder(Order order) {
        boolean result = false;
        if (order != null) {
            int orderIndex = this.orderLinkedList.indexOf(order);
            Order removedOrder = this.orderLinkedList.remove(orderIndex);

            if (removedOrder != null) {
                this.orderLinkedList.add(orderIndex, order);
                result = true;
            }
        }
        return result;
    }

    /**
     * delete an order using Order object.
     */
    @Override
    public boolean deleteOrder(Order order) {
        boolean result = false;
        if (!(this.orderDetailPublish.getOrderDetails(order.getId()).size() > 0)) {
            result = this.orderLinkedList.remove(order);
        }
        return result;
    }


    /**
     * get all the orders as a list, according to the given date.
     *
     * @return ArrayList<Order>
     */
    @Override
    public ArrayList<Order> searchOrderByDate(Date orderDate) {
        ArrayList<Order> collect = this.orderLinkedList.stream()
                .filter(orderDB -> orderDB.getOrderedDate() == orderDate)
                .collect(Collectors.toCollection(ArrayList::new));
        return collect;
    }

    /**
     * find and get the order using order id.
     *
     * @return order : if found,
     * otherwise @throws : IndexOutOfBoundsException
     */
    @Override
    public Order findOrder(int orderId) {
        return this.orderLinkedList
                .stream()
                .filter(orderDB -> orderDB.getId() == orderId)
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public List<Order> findOrders() {
        return this.orderLinkedList.stream().collect(Collectors.toList());
    }

    /**
     * checks whether an order is contained in the linklist or not.
     *
     * @return true :  if an order contains for the given orderId,
     * otherwise false.
     */
    @Override
    public boolean isContains(int orderId) {
        boolean result = false;
        try {
            Order order = findOrder(orderId);
            if (order != null) {
                result = true;
            }
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * check the orderLinkedList is empty or not.
     *
     * @return true : if it is empty,
     * otherwise false.
     */
    @Override
    public boolean isEmpty() {
        return this.orderLinkedList.isEmpty();
    }

    /**
     * update the customer id of an order.
     *
     * @returns true : if successful,
     * otherwise false.
     */
    @Override
    public boolean updateCustomerIdOfTheOrder(int orderId, int newCustomerId) {
        boolean result = false;
        try {
            Order order = findOrder(orderId);
            /* update the customer id */
            order.setCustomer(newCustomerId);
            result = updateOrder(order);
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
        }

        return result;
    }

    /**
     * delete an order using order id.
     *
     * @returns true : if successful,
     * otherwise false.
     */
    @Override
    public boolean deleteOrderByOrderId(int orderId) {
        boolean result = false;
        try {
            result = this.orderLinkedList.removeIf(order -> order.getId() == orderId);
        } catch (Exception e) {
//            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * returns the orderLinkedList
     *
     * @return orderLinkedList
     */
    @Override
    public LinkedList<Order> getBackUp() {
        return this.orderLinkedList;
    }

    /**
     * set the backup linklist.
     */
    @Override
    public void setBackUp(LinkedList<Order> linkList) {
        this.orderLinkedList = linkList;
    }


}
