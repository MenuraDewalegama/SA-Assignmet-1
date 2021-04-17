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
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @author : Dhanusha Perera
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 * @since : 17/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 17/04/2021
 */
package lk.sliit.code4.osgi.user.service;

import lk.sliit.code4.osgi.customer.CustomerServicePublish;
import lk.sliit.code4.osgi.customer.entity.Customer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Scanner;

public class CustomerUserService implements SuperUserService {

    BundleContext context;

    CustomerServicePublish customerServicePublish;
    ServiceReference customerServiceReference;

    Customer customer;
    Scanner scanner = new Scanner(System.in);
    String userInput;

    @Override
    public void setBundleContext(BundleContext context) {
        this.context = context;
        initServiceReferences();
    }

    private void initServiceReferences() {
        this.customerServiceReference = context.getServiceReference(CustomerServicePublish.class.getName());
        this.customerServicePublish = (CustomerServicePublish) context.getService(this.customerServiceReference);
    }

    @Override
    public void add() {
        this.customer = new Customer();
        getCustomerNameFromUser();
        getCustomerPhoneNoFromUser();

        if (this.customerServicePublish.addCustomer(this.customer)) {
            System.out.println("Customer added successfully..!");
            viewAll();
        } else {
            System.err.println("Customer is not added..!");
        }
    }

    @Override
    public void update() {
        this.customer = new Customer();
        if (!isCustomerDBEmpty()) {
            getCustomerIdFromUser();
            getCustomerNameFromUser();
            getCustomerPhoneNoFromUser();

            if (this.customerServicePublish.updateCustomer(this.customer)) {
                System.out.println("Customer updated successfully..!");
                viewAll();
            } else {
                System.err.println("Customer is not updated..!");
            }
        } else {
            System.out.println("No customers are found in the DB, try; adding one instead.");
        }
    }

    @Override
    public void delete() {
        initNewCustomer();
        if (!isCustomerDBEmpty()) {
            getCustomerIdFromUser();

            if (this.customerServicePublish.deleteCustomer(this.customer.getId())) {
                System.out.println("Customer deleted successfully..!");
                viewAll();
            } else {
                System.err.println("Customer is NOT deleted successfully..!");
            }
        } else {
            System.out.println("No customers are found in the DB.");
        }
    }

    @Override
    public void view() {
        initNewCustomer();
        if (!isCustomerDBEmpty()) {
            getCustomerIdFromUser();

            this.customer = this.customerServicePublish.findCustomer(this.customer.getId());
            if (this.customer != null) {
                System.out.println("*** Customer details ***");
                this.customer.toString();
            } else {
                System.err.println("Customer is NOT found..!");
            }
        } else {
            System.out.println("No customers are found in the DB.");
        }
    }

    @Override
    public void viewAll() {
        if (!isCustomerDBEmpty()) {
            System.out.println("*** Customer details ***");
            this.customerServicePublish.findCustomers().forEach(System.out::println);
        } else {
            System.out.println("No customers are found in the DB.");
        }
    }

    private void initNewCustomer() {
        this.customer = new Customer();
    }

    private boolean isCustomerDBEmpty() {
        return this.customerServicePublish.isEmpty();
    }

    private void getCustomerIdFromUser() {
        boolean isEligible = true;
        do {
            System.out.println("Enter Customer ID: ");
            this.userInput = scanner.nextLine();

            if (!this.userInput.matches("\\d*")) {
                System.err.println("Customer ID should only be an integer");
            } else {
                if (!this.customerServicePublish.isContain(Integer.parseInt(this.userInput))) {
                    System.err.println("No customer found for the given ID.");
                    isEligible = false;
                } else {
                    isEligible = true;
                }
            }


        } while ((!this.userInput.matches("\\d*")) && !isEligible);

        this.customer.setId(Integer.parseInt(this.userInput));
    }

    private void getCustomerNameFromUser() {

        do {
            System.out.println("Enter Customer Name: ");
            this.userInput = scanner.nextLine();

            if (!this.userInput.matches("^[A-Za-z]*+[\\w .]*$")) {
                System.err.println("Customer name should be only letters (space and underscore valid).");
            }

        } while (!this.userInput.matches("^[A-Za-z]*+[\\w .]*$"));

        this.customer.setName(this.userInput);
    }

    private void getCustomerPhoneNoFromUser() {
        do {
            System.out.println("Enter Customer Phone No: ");
            this.userInput = scanner.nextLine();

            if (!this.userInput.matches("\\d{10}$")) {
                System.err.println("Customer phone no should be only 10 digit number (without any special characters).");
            }

        } while (!this.userInput.matches("\\d{10}$"));

        this.customer.setPhoneNo(this.userInput);
    }
}
