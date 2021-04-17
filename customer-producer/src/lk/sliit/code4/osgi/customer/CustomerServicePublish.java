package lk.sliit.code4.osgi.customer;/*
@author : Dhanusha Perera
@since : 15/04/2021
*/

import lk.sliit.code4.osgi.customer.entity.Customer;
import org.osgi.framework.BundleContext;

import java.util.List;

public interface CustomerServicePublish {

    boolean addCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(int customerId);

    Customer findCustomer(int customerId);

    List<Customer> findCustomers();

    boolean isContain(int customerId);

    boolean isEmpty();
}
