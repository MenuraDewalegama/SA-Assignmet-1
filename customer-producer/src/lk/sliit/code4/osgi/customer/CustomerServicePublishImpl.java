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
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @since : 15/04/2021
 * @since : 15/04/2021
 * @since : 15/04/2021
 * @since : 15/04/2021
 * @since : 15/04/2021
 * @since : 15/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 15/04/2021
 */
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
                        .filter(customerDB -> customerDB.getId() == customerDB.getId())
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
