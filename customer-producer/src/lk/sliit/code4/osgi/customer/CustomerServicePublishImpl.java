package lk.sliit.code4.osgi.customer;

import lk.sliit.code4.osgi.customer.entity.Customer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerServicePublishImpl implements CustomerServicePublish {

    int counter = 0;
    LinkedList<Customer> customerLinkedList = new LinkedList<>();


    @Override
    public boolean addCustomer(Customer customer) {
        boolean result = false;
        if (customer != null) {
            customer.setId(++counter);
            result = this.customerLinkedList.add(customer);
        }
        return result;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        boolean result = false;
        if (customer != null) {
            try {
                Customer customerResult = this.customerLinkedList
                        .stream()
                        .filter(customerDB -> customerDB.getId() == customer.getId())
                        .collect(Collectors.toList())
                        .get(0);

                if (this.customerLinkedList.remove(customerResult)) {
                    result = this.customerLinkedList.add(customer);
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
    public boolean deleteCustomer(int customerId) {
        return this.customerLinkedList.removeIf(customerDB -> customerDB.getId() == customerId);
    }

    @Override
    public Customer findCustomer(int customerId) {
        return this.customerLinkedList
                .stream()
                .filter(customerDB -> customerDB.getId() == customerId)
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public List<Customer> findCustomers() {
        return this.customerLinkedList.stream().collect(Collectors.toList());
    }

    @Override
    public boolean isContain(int customerId) {
        boolean result = false;
        try {
            Customer customer = findCustomer(customerId);
            if (customer != null) {
                result = true;
            }
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.customerLinkedList.isEmpty();
    }
}
