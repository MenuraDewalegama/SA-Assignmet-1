/**
 * MIT License
 * <p>
 * Copyright (c) 2021 Dhanusha Perera
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author : Dhanusha Perera
 * @since : 16/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 16/04/2021
 */
package lk.sliit.code4.osgi.order;

import lk.sliit.code4.osgi.order.entity.Order;
import lk.sliit.code4.osgi.orderDetail.OrderDetailServicePublish;
import lk.sliit.code4.osgi.orderDetail.OrderDetailServicePublishImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class OrderServicePublishImpl implements OrderServicePublish{
    private static OrderServicePublishImpl orderServicePublishImpl = null;
    int counter = 0;
    LinkedList<Order> orderLinkedList = new LinkedList<>();

    private final OrderDetailServicePublish orderDetailPublish = OrderDetailServicePublishImpl.getInstance();

    private OrderServicePublishImpl() {
    }

    public static OrderServicePublishImpl getInstance() {
        return (orderServicePublishImpl == null)
                ? orderServicePublishImpl = new OrderServicePublishImpl()
                : orderServicePublishImpl;
    }

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

    @Override
    public boolean deleteOrder(Order order) {
        boolean result = false;
        if (!(this.orderDetailPublish.getOrderDetails(order.getId()).size() > 0)) {
            result = this.orderLinkedList.remove(order);
        }
        return result;
    }


    @Override
    public ArrayList<Order> searchOrderByDate(Date orderDate) {
        ArrayList<Order> collect = this.orderLinkedList.stream()
                .filter(orderDB -> orderDB.getOrderedDate() == orderDate)
                .collect(Collectors.toCollection(ArrayList::new));
        return collect;
    }
}
