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
 * @since : 16/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 16/04/2021
 */
package lk.sliit.code4.osgi.user;

import lk.sliit.code4.osgi.item.entity.Item;

import java.math.BigDecimal;
import java.util.Scanner;

public class ItemUserService implements SuperUserService{
//
//    Item item;
//    Scanner scanner;
//    String userInput;

    @Override
    public void add() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void view() {

    }

    @Override
    public void viewAll() {

    }


//    private Item performAddOrUpdate(boolean isAdding) {
//        item = new Item();
//        if (!isAdding) {
//            /* update operation */
//            do {
//                System.out.println("Enter Item code of the item to be updated:");
//                scanner = new Scanner(System.in);
//                userInput = scanner.nextLine();
//
//                if (!(userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$"))) {
//                    System.err.println("Item name should only be a number (except zero) without fractions.");
//                }
//            } while (userInput.matches("^[1-9]{1,}$"));
//            /* check whether there is a matching record for the given the id */
//
//            item.setCode(Integer.parseInt(userInput));
//        }
//
//
//        /* Item name */
//        do {
//            System.out.println("Enter item name:");
//            scanner = new Scanner(System.in);
//            userInput = scanner.nextLine();
//
//            if (!(userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$"))) {
//                System.err.println("Item name should start with letters. after letters you can specify numbers with or without space.");
//            }
//        } while (!(userInput.matches("^[A-Za-z]{1,}+[ .\\w]*$")));
//        item.setName(userInput);
//
//
//        /* Unit price */
//        do {
//            System.out.println("Enter Item Unit Price:");
//            scanner = new Scanner(System.in);
//            userInput = scanner.nextLine();
//
//            if (!(userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})"))) {
//                System.err.println("Item Unit Price should only be a number with two digit fractions. or without fractions");
//            }
//        } while (!(userInput.matches("(^\\d{1,}+[.]+\\d{2}$)|(\\d{1,})")));
//        item.setUnitPrice(new BigDecimal(userInput));
//
//
//        /* Hand on Quantity */
//        do {
//            System.out.println("Enter Item Hand on Quantity:");
//            scanner = new Scanner(System.in);
//            userInput = scanner.nextLine();
//
//            if (!(userInput.matches("^\\d{1,}$"))) {
//                System.err.println("Item Hand on Quantity should only be a number without fractions");
//            }
//        } while (!(userInput.matches("^\\d{1,}$")));
//        item.setHandOnQuantity(Integer.parseInt(userInput));
//
//        System.out.println(item.toString());
//
//        return item;
//
//    }
//
//
//    private boolean checkAvailabilityOfTheItem(int itemCode) {
//        return false;
//    }
}
