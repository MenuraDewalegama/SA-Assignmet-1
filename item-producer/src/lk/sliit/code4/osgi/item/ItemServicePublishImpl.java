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
 * @since : 15/04/2021
 * @since : 15/04/2021
 * @since : 15/04/2021
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
package lk.sliit.code4.osgi.item;

import lk.sliit.code4.osgi.item.entity.Item;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ItemServicePublishImpl implements ItemServicePublish {

    int counter = 0;
    LinkedList<Item> itemLinkedList = new LinkedList<Item>();

    Item item;
    Scanner scanner = new Scanner(System.in);
    String userInput;

    @Override
    public boolean addItem(Item item) {
        boolean result = false;
        if (item != null) {
            item.setCode(++counter);
            result = this.itemLinkedList.add(item);
        }
        return result;
    }

    @Override
    public boolean updateItem(Item item) {

        boolean result = false;
        if (item != null) {
            try {
                Item itemResult = this.itemLinkedList
                        .stream()
                        .filter(itemDB -> itemDB.getCode() == itemDB.getCode())
                        .collect(Collectors.toList())
                        .get(0);

                if (this.itemLinkedList.remove(itemResult)) {
                    result = this.itemLinkedList.add(item);
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
    public boolean deleteItem(int itemCode) {
        return this.itemLinkedList.removeIf(orderDetailDB -> orderDetailDB.getCode() == itemCode);
    }

    @Override
    public Item findItem(int code) {
        return this.itemLinkedList
                .stream()
                .filter(itemDB -> itemDB.getCode() == code)
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public List findItems() {
        return this.itemLinkedList.stream().collect(Collectors.toList());
    }


    @Override
    public boolean isContain(int itemCode) {
        boolean result = false;
        try {
            Item item = this.itemLinkedList
                    .stream()
                    .filter(itemDB -> itemDB.getCode() == itemCode)
                    .collect(Collectors.toList()).get(0);
            if (item!=null){
                result = true;
            }
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
            result = false;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return (this.itemLinkedList.isEmpty());
    }

    /*    @Override
    public Item itemAddORUpdateUserService(boolean isAdding) {
        item = new Item();
        if (!isAdding) {
            boolean isEligibilityToUpdate = true;
            *//* update operation *//*
            do {
                System.out.println("Enter Item code of the item to be updated:");
                scanner = new Scanner(System.in);
                userInput = scanner.nextLine();

                if (!(userInput.matches("^[1-9]{1,}$"))) {
                    System.err.println("Item Code should only be a number (except zero) without fractions.");
                } else {
                    try {
                        if (this.itemLinkedList.isEmpty()) {
                            isEligibilityToUpdate = false;
                            System.err.println("No items found in the DB...!");
                        } else if ((!this.itemLinkedList.contains(findItem(Integer.parseInt(userInput))))) {
                            isEligibilityToUpdate = false;
                            System.err.println("No items found for the given ID in the DB...!");
                        } else {
                            isEligibilityToUpdate = true;
                        }
                    } catch (NumberFormatException e) {
                        isEligibilityToUpdate = false;
                        System.err.println("User given input is invalid, only accept integers...!");
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e){
                        isEligibilityToUpdate = false;
                        System.err.println("No items found for the given ID in the DB...!");
                        e.printStackTrace();
                    }
                }
            } while ((!userInput.matches("^[1-9]{1,}$")) && !isEligibilityToUpdate);
            *//* check whether there is a matching record for the given the id *//*

            item.setCode(Integer.parseInt(userInput));
        }


        *//* Item name *//*
        do {
            System.out.println("Enter item name:");
//            scanner = new Scanner(System.in);
            userInput = scanner.nextLine();

            if (!(userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$"))) {
                System.err.println("Item name should start with letters. after letters you can specify numbers with or without space.");
            }
        } while (!(userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$")));
        System.out.println("userInput(itemName): " + userInput);
        item.setName(userInput);


        *//* Unit price *//*
        do {
            System.out.println("Enter Item Unit Price:");
//            scanner = new Scanner(System.in);
            userInput = scanner.nextLine();

            if (!(userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})"))) {
                System.err.println("Item Unit Price should only be a number with two digit fractions. or without fractions");
            }
        } while (!(userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})")));
        System.out.println("userInput (unitPirce): " + userInput);
        item.setUnitPrice(new BigDecimal(userInput));


        *//* Hand on Quantity *//*
        do {
            System.out.println("Enter Item Hand on Quantity:");
//            scanner = new Scanner(System.in);
            userInput = scanner.nextLine();

            if (!(userInput.matches("^\\d{1,}$"))) {
                System.err.println("Item Hand on Quantity should only be a number without fractions");
            }
        } while (!(userInput.matches("^\\d{1,}$")));
        System.out.println("userInput (handOnQty): " + userInput);
        item.setHandOnQuantity(Integer.parseInt(userInput));

        System.out.println(item.toString());

        return item;

    }*/


/*    @Override
    public boolean itemDeleteUserService(int itemCode) {
        *//* delete operation *//*
        do {
            System.out.println("Enter Item code of the item to be deleted:");
//            scanner = new Scanner(System.in);
            userInput = scanner.nextLine();

            if (!(userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$"))) {
                System.err.println("Item Code should only be a number (except zero) without fractions.");
            }
        } while ((!userInput.matches("^[1-9]{1,}$")) && this.itemLinkedList.contains(findItem(Integer.parseInt(userInput))));
        *//* check whether there is a matching record for the given the id *//*

        item.setCode(Integer.parseInt(userInput));

        return deleteItem(itemCode);
    }*/
}
