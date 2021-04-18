package lk.sliit.code4.osgi.user.service;

import lk.sliit.code4.osgi.item.ItemServicePublish;
import lk.sliit.code4.osgi.item.entity.Item;
import lk.sliit.code4.osgi.user.constant.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.math.BigDecimal;
import java.util.Scanner;

public class ItemUserService implements SuperUserService {

    private static ItemUserService itemUserService = null;


    /* variables */
    BundleContext context;

    ItemServicePublish itemServicePublish;
    ServiceReference itemServiceReference;

    Item item;
    Scanner scanner;
    String userInput;

    /**
     * default constructor
     */
    private ItemUserService() {
    }

    /**
     * Expose the instance.
     * make the class singleton.
     */
    public static ItemUserService getInstance() {
        return (itemUserService == null)
                ? itemUserService = new ItemUserService()
                : itemUserService;
    }

    /**
     * set bundleContext the class.
     */
    @Override
    public void setBundleContext(BundleContext context) {
        this.context = context;
        initServiceReferences();
    }

    /**
     * initialize service references.
     */
    private void initServiceReferences() {
        this.itemServiceReference = context.getServiceReference(ItemServicePublish.class.getName());
        this.itemServicePublish = (ItemServicePublish) context.getService(this.itemServiceReference);
    }

    /**
     * Adds a new item,
     * prompt the user to enter required details to add a new item.
     */
    @Override
    public void add() {
        /* "********** ADD ITEM **********\n" */
        System.out.println(Dividers.ADD_ITEM);
        item = new Item();

        /* getting input form the user */
        getItemName();
        getItemUnitPrice();
        getItemHandOnQuantity();

        if (itemServicePublish.addItem(this.item)) {
            /* "\nItem Added successfully..!" */
            System.out.println(Common.NEXT_LINE + EntityNames.ITEM + SuccessfulMessages.ADDED_SUCCESSFUL);

            /* view all items. */
            viewAll();
//            itemServicePublish.findItems().forEach(System.out::println);
        } else {
            /* "\nItem Added Failed..!" */
            System.err.println(Common.NEXT_LINE + EntityNames.ITEM + FailedMessages.ADDED_UNSUCCESSFUL);
        }

    }

    /**
     * update existing item.
     * prompts the user get the required inputs.
     */
    @Override
    public void update() {
        /* "********** UPDATE ITEM **********\n" */
        System.out.println(Dividers.UPDATE_ITEM);

        item = new Item();

        if (!checkItemDBIsEmpty()) {
            /* getting input form the user */
            getItemCode();
            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                getItemName();
                getItemUnitPrice();
                getItemHandOnQuantity();

                if (itemServicePublish.updateItem(item)) {
                    /* "\nItem Updated successfully..!" */
                    System.out.println(Common.NEXT_LINE + EntityNames.ITEM + SuccessfulMessages.UPDATED_SUCCESSFUL);

                    /* view all the items. */
                    viewAll();
//                    itemServicePublish.findItems().forEach(System.out::println);
                } else {
                    /* "\nItem Updated Failed..!" "\nItem Updated Failed..!" */
                    System.err.println(Common.NEXT_LINE + EntityNames.ITEM + FailedMessages.UPDATED_UNSUCCESSFUL);
                }
            }
        } else {
            /* "Item DB is empty, try add item(s) instead." */
            System.err.println(Common.NO_ITEMS_FOUND_IN_DB);
        }
    }

    /**
     * delete a existing customer using item id.
     * prompts the user get the required inputs.
     */
    @Override
    public void delete() {
        /* "********** DELETE ITEM **********\n" */
        System.out.println(Dividers.DELETE_ITEM);
        this.item = new Item();

        if (!checkItemDBIsEmpty()) {
            /* getting input form the user */
            getItemCode();

            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                if (itemServicePublish.deleteItem(this.item.getCode())) {
                    /* "\nItem Deleted successfully..!" */
                    System.out.println(Common.NEXT_LINE + EntityNames.ITEM + SuccessfulMessages.DELETED_SUCCESSFUL);

                    /* view all the items. */
                    viewAll();
//                    itemServicePublish.findItems().forEach(System.out::println);
                } else {
                    /* "\nItem Deletion Failed..!" */
                    System.err.println(Common.NEXT_LINE + EntityNames.ITEM + FailedMessages.DELETED_UNSUCCESSFUL);
                }
            }
        } else {
            /* "Item DB is empty, try add item(s) instead." */
            System.err.println(Common.NO_ITEMS_FOUND_IN_DB);
        }
    }

    /**
     * prints item details by given item id.
     */
    @Override
    public void view() {
//        System.out.println("********** VIEW ITEM **********\n");
        item = new Item();

        if (!checkItemDBIsEmpty()) {
            /* getting input form the user */
            getItemCode();
            if (!this.userInput.equals(Common.EXIT_MINUS_99)) {
                try {
                    this.item = itemServicePublish.findItem(this.item.getCode());
                    if (this.item != null) {
                        /* "\n*** Item Details ***" */
                        System.out.println(Dividers.ITEM_DETAILS);
                        System.out.println(item.toString());
                    } else {
                        /* "No item details found for the given item code!" */
                        System.err.println(Common.NO_MATCHING_RECORD_FOUND);
                    }
                } catch (IndexOutOfBoundsException e) {
                    // e.printStackTrace();
                    System.err.println(Common.NO_MATCHING_RECORD_FOUND);
                    // System.err.println("No item details found for the given item code!");
                }
            }
        } else {
            /*  "Item DB is empty, try add item(s) instead." */
            System.err.println(Common.NO_ITEMS_FOUND_IN_DB);
        }
    }

    /**
     * prints all the items.
     */
    @Override
    public void viewAll() {
//        System.out.println("********** VIEW ALL ITEM **********\n");
        if (this.itemServicePublish.isEmpty()) {
            System.err.println(Common.NO_ITEMS_FOUND_IN_DB);
//            System.err.println("Item DB is empty, no items to be listed.");
            this.itemServicePublish.addItem(new Item(0, "Intel Core i7 CPU", new BigDecimal(65000), 25));
            this.itemServicePublish.addItem(new Item(0, "AMD Athlon Silver 3050u", new BigDecimal(55000), 14));
        } else {
            /* "\n*** Item details ***" */
            System.out.println(Dividers.ITEM_DETAILS);
            this.itemServicePublish.findItems().forEach(System.out::println);
        }
    }



    /* helper methods */

    /**
     * check the item db is empty or not.
     * if empty @returns true,
     * otherwise false
     */
    private boolean checkItemDBIsEmpty() {
        return this.itemServicePublish.isEmpty();
    }

    /**
     * get item code from the user as an input.
     */
    private void getItemCode() {
        boolean isEligible = true;
        do {
            /* "\nEnter Item code of the item:" */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_ITEM_CODE);
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (this.userInput.equals(Common.EXIT_MINUS_99)) {
                return;
            } else if (!(this.userInput.matches("^[1-9]{1,}$"))) {
                /* "Item code should only be a integer (except zero) without fractions." */
                System.err.println(ValidationPrompts.ITEM_CODE_INVALID);
            } else {
                try {
                    int itemCode = Integer.parseInt(this.userInput);
                    /* check whether there is a matching record for the given the id */
                    if (this.itemServicePublish.isContain(itemCode)) {
                        isEligible = false;
                    }
                } catch (NumberFormatException e) {
//                    System.err.println("Item code should only be an integer (except zero)");
                    System.err.println(ValidationPrompts.ITEM_CODE_INVALID);
                    isEligible = false;
                }
            }


        } while ((!this.userInput.matches("^[1-9]{1,}$")) || !isEligible);

        item.setCode(Integer.parseInt(this.userInput));
    }

    /**
     * get item name from the user as an input.
     */
    private void getItemName() {
        /* Item name */
        do {
            /* "\nEnter item name:" */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_ITEM_NAME);
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$"))) {
                System.err.println(ValidationPrompts.ITEM_NAME_INVALID);
            }
        } while (!(this.userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$")));
//        System.out.println("userInput(itemName): " + this.userInput);
        this.item.setName(this.userInput);
    }

    /**
     * gets item unit price from the user as an input.
     */
    private void getItemUnitPrice() {
        /* Unit price */
        do {
            /* "\nEnter Item Unit Price:" */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_ITEM_UNIT_PRICE);
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})"))) {
                /* "Item Unit Price should only be a number with two digit fractions. or without fractions" */
                System.err.println(ValidationPrompts.ITEM_UNIT_PRICE_INVALID);
            }
        } while (!(this.userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})")));
//        System.out.println("userInput (unitPrice): " + this.userInput);
        this.item.setUnitPrice(new BigDecimal(this.userInput));
    }

    /**
     * gets item hand on quantity from the user as an input.
     */
    private void getItemHandOnQuantity() {
        /* Hand on Quantity */
        do {
            /*  "\nEnter Item Hand on Quantity:" */
            System.out.println(Common.NEXT_LINE + Instructions.ENTER_ITEM_HAND_ON_QUANTITY);
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("^\\d{1,}$"))) {
//              /* "Item Hand on Quantity should only be a number without fractions" */
                System.err.println(ValidationPrompts.ITEM_HAND_ON_QUANTITY_INVALID);
            }
        } while (!(this.userInput.matches("^\\d{1,}$")));
//        System.out.println("userInput (handOnQty): " + this.userInput);
        this.item.setHandOnQuantity(Integer.parseInt(this.userInput));

//        System.out.println(item.toString());
    }
}
