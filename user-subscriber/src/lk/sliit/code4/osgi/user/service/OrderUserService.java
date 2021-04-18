package lk.sliit.code4.osgi.user.service;

import lk.sliit.code4.osgi.customer.CustomerServicePublish;
import lk.sliit.code4.osgi.item.ItemServicePublish;
import lk.sliit.code4.osgi.item.entity.Item;
import lk.sliit.code4.osgi.order.OrderServicePublish;
import lk.sliit.code4.osgi.order.entity.Order;
import lk.sliit.code4.osgi.orderDetail.OrderDetailServicePublish;
import lk.sliit.code4.osgi.orderDetail.entity.OrderDetail;
import lk.sliit.code4.osgi.user.constant.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class OrderUserService implements SuperUserService {

    private static OrderUserService orderUserService = null;

    BundleContext context;

    ServiceReference itemServiceReference;
    ItemServicePublish itemServicePublish;

    ServiceReference customerServiceReference;
    CustomerServicePublish customerServicePublish;

    OrderServicePublish orderServicePublish;
    ServiceReference orderServiceReference;


    ServiceReference orderDetailServiceReference;
    OrderDetailServicePublish orderDetailServicePublish;

    /* user services */
    SuperUserService itemUserService = ItemUserService.getInstance();
    SuperUserService customerUserService = CustomerUserService.getInstance();

    Order order;
    OrderDetail orderDetail;
    Scanner scanner = new Scanner(System.in);
    String userInput;

    int userInputOrderId;

    /* default constructor */
    private OrderUserService() {
    }

    /**
     * expose the OrderUserService instance to outside. Single design pattern is used here.
     */
    public static OrderUserService getInstance() {
        return (orderUserService == null)
                ? orderUserService = new OrderUserService()
                : orderUserService;
    }

    /**
     * set the bundle context.
     */
    @Override
    public void setBundleContext(BundleContext context) {
        this.context = context;
        initServiceReferences();
    }

    /**
     * initialize the service references.
     */
    private void initServiceReferences() {
        /* init customer service */
        this.customerServiceReference = context.getServiceReference(CustomerServicePublish.class.getName());
        this.customerServicePublish = (CustomerServicePublish) context.getService(this.customerServiceReference);

        /* init item service */
        this.itemServiceReference = context.getServiceReference(ItemServicePublish.class.getName());
        this.itemServicePublish = (ItemServicePublish) context.getService(this.itemServiceReference);

        /* init order service */
        this.orderServiceReference = context.getServiceReference(OrderServicePublish.class.getName());
        this.orderServicePublish = (OrderServicePublish) context.getService(this.orderServiceReference);

        /* init order detail */
        this.orderDetailServiceReference = context.getServiceReference(OrderDetailServicePublish.class.getName());
        this.orderDetailServicePublish = (OrderDetailServicePublish) context.getService(this.orderDetailServiceReference);
    }

    /**
     * add a new order record, and then user can enter order details.
     */
    @Override
    public void add() {
        /* "********** ADD ORDER ********** \n" */
        System.out.println(Dividers.ADD_ORDER);
        initNewOrder();

        if (this.customerServicePublish.isEmpty()) {
            System.err.println(Common.NO_CUSTOMERS_FOUND_IN_DB);
        } else if (this.itemServicePublish.isEmpty()) {
            System.out.println(Common.NO_ITEMS_FOUND_IN_DB);
//            System.err.println("Item DB is empty, try adding item(s) instead");
        } else {
            this.customerUserService.viewAll();

            /* getting input for the customer id */
            getCustomerIdForTheOrderFromUser();
            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                /* set the order date. */
                this.order.setOrderedDate(getOrderedDate());

                int orderIdForNewlyAddedOrder = this.orderServicePublish.placeOrder(this.order);
                if (orderIdForNewlyAddedOrder > 0) {
                    /* Order added successfully..!" */
                    System.out.println(Common.NEXT_LINE + EntityNames.ORDER + SuccessfulMessages.ADDED_SUCCESSFUL);
                    System.out.println(Instructions.NEWLY_ADDED_ORDER_ID + orderIdForNewlyAddedOrder);
                    System.out.println(Instructions.NOW_YOU_CAN_ADD_ORDER_DETAILS);
                    while (true) {
                        System.out.println(Instructions.TO_ADD_ORDER_DETAIL);
                        this.userInput = this.scanner.nextLine();
                        if (this.userInput.equals(Common.EXIT_MINUS_99)) {
                            try {
                                Order newlyAddOrder = this.orderServicePublish.findOrder(orderIdForNewlyAddedOrder);
                                List<OrderDetail> orderDetailList = this.orderDetailServicePublish.getOrderDetails(orderIdForNewlyAddedOrder);
                                System.out.println(Dividers.NEWLY_ADDED_ORDER_RECORD_DETAIL);

                                /*"---- Order details ---"*/
                                System.out.println(Dividers.ORDER_DETAILS);

                                printOrderAndOrderDetail(newlyAddOrder, orderDetailList);
                            } catch (IndexOutOfBoundsException e) {
                            }
                            return;
                        }

                        if (this.userInput.matches("Y|y")) {
                            initNewOrderDetail();
                            this.orderDetail.setOrderId(orderIdForNewlyAddedOrder); // foreign key for OrderDetail

                            this.itemUserService.viewAll();
                            System.out.println();

                            getItemCodeFromUser();
                            getQuantityFromUser();
                            this.orderDetail.setUnitPrice(getItemUnitPriceFromDB(this.orderDetail.getItemCode()));
                            if (this.orderDetailServicePublish.addOrderDetail(this.orderDetail)) {
                                /* "\nOrder detail added successfully." */
                                System.out.println(Common.NEXT_LINE + EntityNames.ORDER_DETAIL + SuccessfulMessages.ADDED_SUCCESSFUL);
                            } else {
                                System.out.println(Common.NEXT_LINE + EntityNames.ORDER_DETAIL + FailedMessages.ADDED_UNSUCCESSFUL);
                                // System.err.println("\nOrder detail was NOT added successfully.");
                            }
                        }
                    }
                }
            }
        }


    }

    /**
     * only update the customerId field on an order record.
     */
    @Override
    public void update() {
        /* "********** UPDATE ORDER **********\n"*/
        System.out.println(Dividers.UPDATE_ORDER);
        if (!this.orderServicePublish.isEmpty()) {
            getOrderIdFromUser();
            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                try {
                    Order orderDB = this.orderServicePublish.findOrder(this.userInputOrderId);
                    orderDB.toString();

                    this.customerUserService.viewAll();

                    System.out.println(Instructions.PROVIDE_DETAILS_TO_UPDATE_ORDER);
//                    System.out.println("\nPlease provide following details to update the order record.");
                    /* get the customer ID */
                    getCustomerIdForTheOrderFromUser();
                    if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                        this.order.setId(orderDB.getId());
                        this.order.setOrderedDate(orderDB.getOrderedDate());

                        /* update the order (only customer id will be updated) */
                        this.orderServicePublish.updateOrder(this.order);
                    }
                } catch (IndexOutOfBoundsException e) {
                    /* no order found for that given id */
                }
            }
        } else {
            System.err.println(Common.NO_ORDERS_FOUND_IN_DB);
//            System.err.println("Order DB is empty. Nothing to be displayed.");
        }
    }

    @Override
    public void delete() {
        /*  "********** DELETE ORDER **********\n" */
        System.out.println(Dividers.DELETE_ORDER);
        if (!this.orderServicePublish.isEmpty()) {
            getOrderIdFromUser();

            LinkedList<Order> orderBackUpLinkedList = this.orderServicePublish.getBackUp();
            LinkedList<OrderDetail> orderDetailBackUpLinkedList = this.orderDetailServicePublish.getBackUp();

            if (this.orderServicePublish.deleteOrderByOrderId(this.userInputOrderId)) {

                if (this.orderDetailServicePublish.deleteOrderDetailByOrderId(this.userInputOrderId)) {
                    /* "\nOrder deleted successfully..!" */
                    System.out.println(Common.NEXT_LINE + EntityNames.ORDER + SuccessfulMessages.DELETED_SUCCESSFUL);

                    /* view all the orders. */
                    viewAll();
                } else {
                    /* "\nOrder was NOT deleted successfully..!" */
                    System.err.println(Common.NEXT_LINE + EntityNames.ORDER + FailedMessages.DELETED_UNSUCCESSFUL);

                    /* roll back */
                    this.orderServicePublish.setBackUp(orderBackUpLinkedList);
                    this.orderDetailServicePublish.setBackUp(orderDetailBackUpLinkedList);
                }
            } else {
//                System.err.println("\nOrder was NOT deleted successfully..!");
                System.out.println(Common.NEXT_LINE + EntityNames.ORDER + SuccessfulMessages.DELETED_SUCCESSFUL);
                /* roll back */
                this.orderServicePublish.setBackUp(orderBackUpLinkedList);
            }
        } else {
            /* "No order found. Order DB is empty." */
            System.err.println(Common.NO_ORDERS_FOUND_IN_DB);
        }
    }

    /**
     * prompt the user to enter order id, then
     * prints the order and order details records on the terminal.
     */
    @Override
    public void view() {
        /* "********** VIEW ORDER **********\n" */
        System.out.println(Dividers.VIEW_ORDER);
        if (!this.orderServicePublish.isEmpty()) {
            getOrderIdFromUser();
            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                try {
                    /* ORDER details */
                    Order orderDB = this.orderServicePublish.findOrder(this.userInputOrderId);
                    List<OrderDetail> orderDetailListDB =
                            this.orderDetailServicePublish.getOrderDetails(orderDB.getId());

                    printOrderAndOrderDetail(orderDB, orderDetailListDB);

                } catch (IndexOutOfBoundsException e) {
                    // e.printStackTrace();
                    // System.err.println("No order found for the given order id.");
                }
            }
        } else {
            System.err.println(Common.NO_ORDERS_FOUND_IN_DB);
            // System.err.println("Order DB is empty. Nothing to be displayed.");
        }
    }

    /**
     * prints all the order and order details records on the terminal.
     */
    @Override
    public void viewAll() {
        /* "********** VIEW ALL ORDERS **********\n" */
        System.out.println(Dividers.VIEW_ORDERS);
        if (this.orderServicePublish.isEmpty()) {
            System.out.println(Common.NO_ORDERS_FOUND_IN_DB);
//            System.err.println("Order DB is empty. Nothing to be displayed.");
        } else {
            this.orderServicePublish.findOrders().forEach(orderDB -> {
                printOrder(orderDB);
                printOrderDetail(this.orderDetailServicePublish.getOrderDetails(orderDB.getId()));
            });
        }
    }

    /**
     * creates a new Order object and assign it to order variable.
     */
    private void initNewOrder() {
        this.order = new Order();
    }

    /**
     * creates a new OrderDetail object and assign it to orderDetail variable.
     */
    private void initNewOrderDetail() {
        this.orderDetail = new OrderDetail();
    }

    /**
     * gets the order from the user.
     */
    private void getOrderIdFromUser() {
        initNewOrderDetail();
        boolean isEligible = true;
        do {
            /* "\nEnter Order ID:" */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_ORDER_ID);
            System.out.println(Instructions.TO_EXIT_ENTER_MINUS_99);
            this.userInput = this.scanner.nextLine();

            if (this.userInput.equals(Common.EXIT_MINUS_99)) {
                return;
            } else if (!(this.userInput.matches("^\\d{1,}$"))) {
                /* "Order ID should be an integer." */
                System.err.println(ValidationPrompts.ORDER_ID_INVALID);
            } else {
                if (!(this.orderServicePublish.isContains(Integer.parseInt(this.userInput)))) {
                    isEligible = false;
                    /* "No order found for the given order id." */
                    System.err.println(Common.NO_MATCHING_RECORD_FOUND);
                } else {
                    isEligible = true;
                }
            }

        } while ((!this.userInput.matches("^\\d{1,}$")) || !isEligible);
        this.userInputOrderId = Integer.parseInt(this.userInput);
    }

    /**
     * gets the customer id from the user as input.
     */
    private void getCustomerIdForTheOrderFromUser() {
        initNewOrder();

        boolean isEligible = true;
        do {
            /* "\nEnter Customer ID: " */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_CUSTOMER_ID);
            System.out.println(Instructions.TO_EXIT_ENTER_MINUS_99);
            this.userInput = scanner.nextLine();

            if (this.userInput.equals(Common.EXIT_MINUS_99)) {
                return;
            } else if (!this.userInput.matches("^\\d{1,}$")) {
                /* "Customer ID should only be an integer" */
                System.err.println(EntityProperties.CUSTOMER_ID + ValidationPrompts.ONLY_BE_INTEGER);
            } else {
                if (!this.customerServicePublish.isContain(Integer.parseInt(this.userInput))) {
                    System.err.println(Common.NO_MATCHING_RECORD_FOUND);
//                    System.err.println("No customer found for the given ID.");
                    isEligible = false;
                } else {
                    isEligible = true;
                }
            }


        } while ((!this.userInput.matches("^\\d{1,}$")) || !isEligible);

        this.order.setCustomer(Integer.parseInt(this.userInput));
    }

    /**
     * This method is used to get ordered date date.
     *
     * @returns current date
     */
    private Date getOrderedDate() {
        return new Date(System.currentTimeMillis());
    }


    /**
     * gets the item code input from the user.
     */
    private void getItemCodeFromUser() {
        boolean isEligible = true;
        do {
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_ITEM_CODE);
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("^[1-9]{1,}$"))) {
                System.err.println(ValidationPrompts.ITEM_CODE_INVALID);
//                System.err.println("Item code should only be a number (except zero) without fractions.");
            } else {
                try {
                    int itemCode = Integer.parseInt(this.userInput);
                    /* check whether there is a matching record for the given the id */
                    if (this.itemServicePublish.isContain(itemCode)) {
                        isEligible = false;
                    }
                } catch (NumberFormatException e) {
                    System.err.println(ValidationPrompts.ITEM_CODE_INVALID);
//                    System.err.println("Item code should only be an integer (except zero)");
                    isEligible = false;
                }
            }


        } while ((!this.userInput.matches("^[1-9]{1,}$")) || !isEligible);

        /* set item code to order detail object */
        this.orderDetail.setItemCode(Integer.parseInt(this.userInput));
    }

    /**
     * gets the quantity input from the user.
     */
    private void getQuantityFromUser() {
        do {
            /* get quantity. */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_ORDERING_QUANTITY);
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("^\\d{1,}$"))) {
                System.out.println(ValidationPrompts.ORDERING_QUANTITY_INVALID);
//                System.err.println("Item Quantity should only be a number without fractions");
            } else {
                /* check stock is available fo the item. */
                if (this.itemServicePublish
                        .isInStock(this.orderDetail.getItemCode(), Integer.parseInt(this.userInput))) {
                    /* stock is enough and update the hand on quantity of the item. */
                    this.itemServicePublish
                            .updateItemQuantity(this.orderDetail.getItemCode(), Integer.parseInt(this.userInput));
                } else {
                    /* item stock is not enough or out of stock. */
                    System.err.println(Common.NEXT_LINE + EntityProperties.ITEM_CODE +
                            this.orderDetail.getItemCode() +
                            ValidationPrompts.NOT_ENOUGH_HAND_ON_QUANTITY);

                    /* display the item details for the user. */
                    System.out.println(EntityProperties.ITEM_CODE + +this.orderDetail.getItemCode() + Common.DETAILS);
                    this.itemServicePublish.findItem(Integer.parseInt(this.userInput)).toString();
                    System.out.println();
                }
            }
        } while (!(this.userInput.matches("^\\d{1,}$")));

        /* set ordering-quantity for the given item. */
        this.orderDetail.setQuantity(Integer.parseInt(this.userInput));
    }

    private BigDecimal getItemUnitPriceFromDB(int itemCode) {
        Item item = null;
        try {
            item = this.itemServicePublish.findItem(itemCode);
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
            System.err.println(Common.NO_MATCHING_RECORD_FOUND);
//            System.err.println("No item found for the given item code.");
        }
        return item.getUnitPrice();
    }


    /**
     * print the order related details.
     *
     * @param order: order object
     */
    private void printOrder(Order order) {
        try {
            System.out.println(EntityProperties.ORDER_ID + order.getId() + Common.COMMA_SPACE +
                    EntityProperties.CUSTOMER_ID + order.getCustomer() + Common.COMMA_SPACE +
                    EntityProperties.CUSTOMER_NAME + this.customerServicePublish.findCustomer(order.getCustomer()).getName() + Common.COMMA_SPACE +
                    EntityProperties.ORDER_DATE + new SimpleDateFormat(Common.DD_MM_YYYY).format(order.getOrderedDate()));
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
        }
    }

    /**
     * prints the orderDetail related details.
     *
     * @param orderDetailList: list consist of orderDetails
     */
    private void printOrderDetail(List<OrderDetail> orderDetailList) {
        if (orderDetailList.isEmpty()) {
            System.out.println(Common.NO_ITEMS_ORDERED);
//            System.out.println("No items were ordered...!");
        } else {
            orderDetailList.forEach(orderDetail -> {
                try {
                    Item item = this.itemServicePublish.findItem(orderDetail.getItemCode());
                    System.out.println(
                            Common.TAB + EntityProperties.ORDER_DETAIL_ID + orderDetail.getId() + Common.COMMA_SPACE +
                                    EntityProperties.ITEM_CODE + item.getCode() + Common.COMMA_SPACE +
                                    EntityProperties.ITEM_NAME + item.getName() + Common.COMMA_SPACE +
                                    EntityProperties.QUANTITY + orderDetail.getQuantity() + Common.COMMA_SPACE +
                                    EntityProperties.ITEM_UNIT_PRICE + orderDetail.getUnitPrice() + Common.COMMA_SPACE +
                                    EntityProperties.TOTAL + (orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()))));
                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * prints the both order and orderDetails related info on the console.
     *
     * @param order           : order object
     * @param orderDetailList : list consist of orderDetails
     */
    private void printOrderAndOrderDetail(Order order, List<OrderDetail> orderDetailList) {
        System.out.println(Common.NEXT_LINE + EntityProperties.ORDER_ID + order.getId());
        System.out.println(EntityProperties.CUSTOMER_NAME + this.customerServicePublish.findCustomer(order.getCustomer()).getName());
        System.out.println(EntityProperties.ORDER_DATE + new SimpleDateFormat(Common.DD_MM_YYYY).format(order.getOrderedDate()));

        System.out.println(Dividers.ORDERED_ITEMS);
//        System.out.println("---- Ordered items ----");
        if (orderDetailList.isEmpty()) {
            System.out.println(Common.NO_ITEMS_ORDERED);
//            System.out.println("No items were ordered...!");
        } else {
            orderDetailList.forEach(orderDetail -> {
                try {
                    Item item = this.itemServicePublish.findItem(orderDetail.getItemCode());
                    System.out.println(
                            EntityProperties.ORDER_DETAIL_ID + orderDetail.getId() + Common.COMMA_SPACE +
                                    EntityProperties.ITEM_CODE + item.getCode() + Common.COMMA_SPACE +
                                    EntityProperties.ITEM_NAME + item.getName() + Common.COMMA_SPACE +
                                    EntityProperties.QUANTITY + orderDetail.getQuantity() + Common.COMMA_SPACE +
                                    EntityProperties.ITEM_UNIT_PRICE + orderDetail.getUnitPrice() + Common.COMMA_SPACE +
                                    EntityProperties.TOTAL + (orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()))));
                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
                }
            });
        }
    }

}
