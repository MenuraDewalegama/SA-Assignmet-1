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
package lk.sliit.code4.osgi.orderDetail;

import lk.sliit.code4.osgi.orderDetail.entity.OrderDetail;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailServicePublishImpl implements OrderDetailServicePublish{

    private static OrderDetailServicePublishImpl orderDetailPublishImpl = null;
    LinkedList<OrderDetail> orderDetailLinkedList = new LinkedList<>();

    private OrderDetailServicePublishImpl() {
    }

    public static OrderDetailServicePublishImpl getInstance() {
        return (orderDetailPublishImpl == null)
                ? orderDetailPublishImpl = new OrderDetailServicePublishImpl()
                : orderDetailPublishImpl;
    }


    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) {
        return this.orderDetailLinkedList.add(orderDetail);
    }

    @Override
    public boolean updateOrderDetail(OrderDetail orderDetail) {

        boolean result = false;
        if (orderDetail != null) {
            try {
                OrderDetail orderDetailResult = this.orderDetailLinkedList
                        .stream()
                        .filter(orderDetailDB -> orderDetailDB.getId() == orderDetail.getId())
                        .collect(Collectors.toList())
                        .get(0);

                if (this.orderDetailLinkedList.remove(orderDetailResult)) {
                    result = this.orderDetailLinkedList.add(orderDetail);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return result;
    }

    @Override
    public boolean deleteOrderDetail(int orderDetailId) {
        return this.orderDetailLinkedList.removeIf(orderDetailDB -> orderDetailDB.getOrderId() == orderDetailId);
    }

    @Override
    public OrderDetail getOrderDetail(int orderDetailId) {
        List<OrderDetail> collect = this.orderDetailLinkedList
                .stream()
                .filter(orderDetailDB -> orderDetailDB.getId() == orderDetailId)
                .collect(Collectors.toList());
        return collect.get(0);
    }

    @Override
    public List<OrderDetail> getOrderDetails(int orderId) {
        return this.orderDetailLinkedList
                .stream()
                .filter(orderDetailDB -> orderDetailDB.getOrderId() == orderId)
                .collect(Collectors.toList());
    }

//    @Override
//    public void checkForOrderDetail(int orderId) {
//        this.orderDetailLinkedList
//                .stream()
//                .filter(orderDetailDB -> orderDetailDB.getOrderId() == orderId)
//                .collect(Collectors.toList());
//    }

}
