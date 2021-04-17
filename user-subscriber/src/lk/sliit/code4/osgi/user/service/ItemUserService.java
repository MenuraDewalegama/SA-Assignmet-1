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
 * @since : 16/04/2021
 * @since : 16/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 16/04/2021
 */
package lk.sliit.code4.osgi.user.service;

import lk.sliit.code4.osgi.item.ItemServicePublish;
import lk.sliit.code4.osgi.item.entity.Item;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.math.BigDecimal;
import java.util.Scanner;

public class ItemUserService implements SuperUserService {

    BundleContext context;

    ItemServicePublish itemServicePublish;
    ServiceReference itemServiceReference;

    Item item;
    Scanner scanner;
    String userInput;

    @Override
    public void setBundleContext(BundleContext context) {
        this.context = context;
        initServiceReferences();
    }

    private void initServiceReferences() {
        itemServiceReference = context.getServiceReference(ItemServicePublish.class.getName());
        itemServicePublish = (ItemServicePublish) context.getService(itemServiceReference);
    }

    @Override
    public void add() {
        item = new Item();

        /* getting input form the user */
        getItemName();
        getItemUnitPrice();
        getItemHandOnQuantity();

        if (itemServicePublish.addItem(this.item)) {
            System.out.println("Item Added successfully..!");
            itemServicePublish.findItems().forEach(System.out::println);
        } else {
            System.err.println("Item Added Failed..!");
        }

    }

    @Override
    public void update() {
        item = new Item();

        if (!checkItemDBIsEmpty()) {
            /* getting input form the user */
            getItemCode();
            getItemName();
            getItemUnitPrice();
            getItemHandOnQuantity();

            if (itemServicePublish.updateItem(item)) {
                System.out.println("Item Updated successfully..!");
                itemServicePublish.findItems().forEach(System.out::println);
            } else {
                System.err.println("Item Updated Failed..!");
            }
        } else {
            System.err.println("Item DB is empty, try add item(s) instead.");
        }
    }

    @Override
    public void delete() {
        this.item = new Item();

        if (!checkItemDBIsEmpty()) {
            /* getting input form the user */
            getItemCode();

            if (itemServicePublish.deleteItem(this.item.getCode())) {
                System.out.println("Item Deleted successfully..!");
                itemServicePublish.findItems().forEach(System.out::println);
            } else {
                System.err.println("Item Deletion Failed..!");
            }
        } else {
            System.err.println("Item DB is empty, try add item(s) instead.");
        }
    }

    @Override
    public void view() {
        item = new Item();

        if (!checkItemDBIsEmpty()) {
            /* getting input form the user */
            getItemCode();
            try {
                this.item = itemServicePublish.findItem(this.item.getCode());
                if (this.item != null) {
                    System.out.println("*** Item Details ***");
                    item.toString();
                } else {
                    System.err.println("No item details found for the given item code!");
                }
            } catch (IndexOutOfBoundsException e) {
//                e.printStackTrace();
                System.err.println("No item details found for the given item code!");
            }
        } else {
            System.err.println("Item DB is empty, try add item(s) instead.");
        }
    }

    @Override
    public void viewAll() {
        if (this.itemServicePublish.isEmpty()) {
            System.err.println("Item DB is empty, no items to be listed.");
        } else {
            this.itemServicePublish.findItems().forEach(System.out::println);
        }
    }



    /* helper methods */

    private boolean checkItemDBIsEmpty() {
        return this.itemServicePublish.isEmpty();
    }

    private void getItemCode() {
        /* update operation */
        boolean isEligible = true;
        do {
            System.out.println("Enter Item code of the item:");
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("^[1-9]{1,}$"))) {
                System.err.println("Item code should only be a number (except zero) without fractions.");
            } else {
                try {
                    int itemCode = Integer.parseInt(this.userInput);
                    /* check whether there is a matching record for the given the id */
                    if (this.itemServicePublish.isContain(itemCode)) {
                        isEligible = false;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Item code should only be an integer (except zero)");
                    isEligible = false;
                }
            }


        } while ((!this.userInput.matches("^[1-9]{1,}$")) && !isEligible);

        item.setCode(Integer.parseInt(this.userInput));
    }

    private void getItemName() {
        /* Item name */
        do {
            System.out.println("Enter item name:");
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$"))) {
                System.err.println("Item name should start with letters. after letters you can specify numbers with or without space.");
            }
        } while (!(this.userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$")));
        System.out.println("userInput(itemName): " + this.userInput);
        this.item.setName(this.userInput);
    }

    private void getItemUnitPrice() {
        /* Unit price */
        do {
            System.out.println("Enter Item Unit Price:");
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})"))) {
                System.err.println("Item Unit Price should only be a number with two digit fractions. or without fractions");
            }
        } while (!(this.userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})")));
        System.out.println("userInput (unitPirce): " + this.userInput);
        this.item.setUnitPrice(new BigDecimal(this.userInput));
    }

    private void getItemHandOnQuantity() {
        /* Hand on Quantity */
        do {
            System.out.println("Enter Item Hand on Quantity:");
            this.scanner = new Scanner(System.in);
            this.userInput = this.scanner.nextLine();

            if (!(this.userInput.matches("^\\d{1,}$"))) {
                System.err.println("Item Hand on Quantity should only be a number without fractions");
            }
        } while (!(this.userInput.matches("^\\d{1,}$")));
        System.out.println("userInput (handOnQty): " + this.userInput);
        this.item.setHandOnQuantity(Integer.parseInt(this.userInput));

        System.out.println(item.toString());
    }
}
