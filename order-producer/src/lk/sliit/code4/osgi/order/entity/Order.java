package lk.sliit.code4.osgi.order.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private int customer;
    private Date orderedDate;

    public Order() {
    }

    public Order(int id, int customer, Date orderedDate) {
        this.id = id;
        this.customer = customer;
        this.orderedDate = orderedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderedDate=" + new SimpleDateFormat("dd/MM/yyyy").format(orderedDate) +
                '}';
    }
}
