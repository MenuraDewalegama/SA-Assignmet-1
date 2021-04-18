package lk.sliit.code4.osgi.orderDetail;

import lk.sliit.code4.osgi.orderDetail.entity.OrderDetail;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailServicePublishImpl implements OrderDetailServicePublish {

    private static OrderDetailServicePublishImpl orderDetailPublishImpl = null;
    int counter = 0;
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
        if (orderDetail != null) {
            orderDetail.setId(++counter);
        }
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
    public boolean deleteOrderDetailByOrderId(int orderId) {
        boolean result = false;
        try {
            result = this.orderDetailLinkedList.removeIf(orderDetailDB -> orderDetailDB.getOrderId() == orderId);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return result;
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


    /** returns the orderDetailLinkedList
     * @return orderDetailLinkedList
     * */
    @Override
    public LinkedList<OrderDetail> getBackUp() {
        return this.orderDetailLinkedList;
    }

    /** setter for orderDetailLinkedList */
    @Override
    public void setBackUp(LinkedList<OrderDetail> orderDetailLinkedList) {
        this.orderDetailLinkedList = orderDetailLinkedList;
    }
}
