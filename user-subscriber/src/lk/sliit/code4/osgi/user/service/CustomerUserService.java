package lk.sliit.code4.osgi.user.service;

import lk.sliit.code4.osgi.customer.CustomerServicePublish;
import lk.sliit.code4.osgi.customer.entity.Customer;
import lk.sliit.code4.osgi.user.constant.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Scanner;

public class CustomerUserService implements SuperUserService {

    private static CustomerUserService customerUserService = null;

    BundleContext context;

    CustomerServicePublish customerServicePublish;
    ServiceReference customerServiceReference;

    Customer customer;
    Scanner scanner = new Scanner(System.in);
    String userInput;

    private CustomerUserService() {

    }

    public static CustomerUserService getInstance() {
        return (customerUserService == null)
                ? customerUserService = new CustomerUserService()
                : customerUserService;
    }

    @Override
    public void setBundleContext(BundleContext context) {
        this.context = context;
        initServiceReferences();
    }

    private void initServiceReferences() {
        this.customerServiceReference = context.getServiceReference(CustomerServicePublish.class.getName());
        this.customerServicePublish = (CustomerServicePublish) context.getService(this.customerServiceReference);
    }

    /**
     * add a new customer.
     * prompts the to enter required details.
     */
    @Override
    public void add() {
        System.out.println(Dividers.ADD_CUSTOMER);

        initNewCustomer();
        getCustomerNameFromUser();
        getCustomerPhoneNoFromUser();

        if (this.customerServicePublish.addCustomer(this.customer)) {
            System.out.println(Common.NEXT_LINE + EntityNames.CUSTOMER + SuccessfulMessages.ADDED_SUCCESSFUL);
            viewAll();
        } else {
            System.err.println(Common.NEXT_LINE + EntityNames.CUSTOMER + FailedMessages.ADDED_UNSUCCESSFUL);
        }
    }

    /**
     * update an existing customer using customer id.
     * prompts the to enter required details.
     */
    @Override
    public void update() {
        System.out.println(Dividers.UPDATE_CUSTOMER);
        initNewCustomer();

        if (!isCustomerDBEmpty()) {
            getCustomerIdFromUser();
            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                getCustomerNameFromUser();
                getCustomerPhoneNoFromUser();

                if (this.customerServicePublish.updateCustomer(this.customer)) {
                    System.out.println(Common.NEXT_LINE + EntityNames.CUSTOMER + SuccessfulMessages.UPDATED_SUCCESSFUL);

                    /* view all the customers. */
                    viewAll();
                } else {
                    System.err.println(Common.NEXT_LINE + EntityNames.CUSTOMER + FailedMessages.UPDATED_UNSUCCESSFUL);
                }
            }
        } else {
            System.err.println(Common.NO_CUSTOMERS_FOUND_IN_DB);
        }
    }

    /**
     * delete a given customer using customer id.
     * prompts the to enter required details.
     */
    @Override
    public void delete() {
        System.out.println(Dividers.DELETE_CUSTOMER);

        initNewCustomer();
        if (!isCustomerDBEmpty()) {
            getCustomerIdFromUser();

            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                if (this.customerServicePublish.deleteCustomer(this.customer.getId())) {
                    System.out.println(Common.NEXT_LINE + EntityNames.CUSTOMER + SuccessfulMessages.DELETED_SUCCESSFUL);

                    /* view all the customers. */
                    viewAll();
                } else {
                    System.err.println(Common.NEXT_LINE + EntityNames.CUSTOMER + FailedMessages.DELETED_UNSUCCESSFUL);
                }
            }
        } else {
            System.err.println(Common.NO_CUSTOMERS_FOUND_IN_DB);
        }
    }

    /**
     * prints a given customer in the terminal using customer id.
     */
    @Override
    public void view() {
//        System.out.println("********** VIEW CUSTOMER **********\n");

        initNewCustomer();
        if (!isCustomerDBEmpty()) {
            getCustomerIdFromUser();

            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                this.customer = this.customerServicePublish.findCustomer(this.customer.getId());
                if (this.customer != null) {
                    System.out.println(Common.NEXT_LINE + Dividers.CUSTOMER);
                    System.out.println(this.customer.toString());
                } else {
                    /* prompt no customer found */
                    System.err.println(Common.NO_MATCHING_RECORD_FOUND);
                }
            }
        } else {
            System.err.println(Common.NO_CUSTOMERS_FOUND_IN_DB);
        }
    }

    /**
     * prints all the customers in the terminal.
     */
    @Override
    public void viewAll() {
//        System.out.println("********** VIEW ALL CUSTOMERS **********");

        if (!isCustomerDBEmpty()) {
            System.out.println(Common.NEXT_LINE + Dividers.CUSTOMER);
            this.customerServicePublish.findCustomers().forEach(System.out::println);
        } else {
            System.err.println(Common.NO_CUSTOMERS_FOUND_IN_DB);
            this.customerServicePublish.addCustomer(new Customer(0, "Dhanusha Perera", "0751234567"));
            this.customerServicePublish.addCustomer(new Customer(0, "Sachintha De Zoysa", "0112933445"));
        }
    }

    /**
     * creates a new customer instance and assign it to customer variable.
     */
    private void initNewCustomer() {
        this.customer = new Customer();
    }

    /**
     * check the customer db is empty or not.
     * if it is empty @returns true,
     * otherwise false.
     */
    private boolean isCustomerDBEmpty() {
        return this.customerServicePublish.isEmpty();
    }

    /**
     * gets the customer id from the user as an input.
     */
    private void getCustomerIdFromUser() {
        boolean isEligible = true;
        do {
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_CUSTOMER_ID);
            System.out.println(Instructions.TO_EXIT_ENTER_MINUS_99);
            this.userInput = scanner.nextLine();

            if (this.userInput.equals(Common.EXIT_MINUS_99)) {
                return;
            } else if (!this.userInput.matches("^\\d{1,}$")) {
                System.err.println(EntityProperties.CUSTOMER_ID + ValidationPrompts.ONLY_BE_INTEGER);
            } else {
                if (!this.customerServicePublish.isContain(Integer.parseInt(this.userInput))) {
                    /*  No customer found for the given ID. */
                    System.err.println(Common.NO_MATCHING_RECORD_FOUND);
                    isEligible = false;
                } else {
                    isEligible = true;
                }
            }


        } while ((!this.userInput.matches("^\\d{1,}$")) || !isEligible);

        this.customer.setId(Integer.parseInt(this.userInput));
    }

    /**
     * gets the customer name from the user as an input.
     */
    private void getCustomerNameFromUser() {

        do {
            /* "\nEnter Customer Name: " */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_CUSTOMER_NAME);
            this.userInput = scanner.nextLine();

            if (!this.userInput.matches("^[A-Za-z]*+[\\w .]*$")) {
                /* "Customer name should be only contain letters (space and underscore valid)." */
                System.err.println(ValidationPrompts.CUSTOMER_NAME_INVALID);
            }

        } while (!this.userInput.matches("^[A-Za-z]*+[\\w .]*$"));

        this.customer.setName(this.userInput);
    }

    /**
     * gets the customer phone number from the user as an input.
     */
    private void getCustomerPhoneNoFromUser() {
        do {
            /* "\nEnter Customer Phone No: " */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_CUSTOMER_PHONE);
            this.userInput = scanner.nextLine();

            if (!this.userInput.matches("\\d{10}$")) {
                /* "Customer phone no should be only 10 digit number (without any special characters)." */
                System.err.println(ValidationPrompts.CUSTOMER_PHONE_NO_INVALID);
            }

        } while (!this.userInput.matches("\\d{10}$"));

        this.customer.setPhoneNo(this.userInput);
    }
}
