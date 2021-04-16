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
 * @since : 16/04/2021
 * @since : 16/04/2021
 * @since : 16/04/2021
 * @since : 16/04/2021
 * @since : 16/04/2021
 */
/**
 * @author : Dhanusha Perera
 * @since : 16/04/2021
 */
package lk.sliit.code4.osgi.user;

import lk.sliit.code4.osgi.user.constant.InputTypes;
import lk.sliit.code4.osgi.user.service.ItemUserService;
import lk.sliit.code4.osgi.user.service.SuperUserService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserServiceActivator implements BundleActivator {

    int userInstructionNumber;
    SuperUserService itemUserService = new ItemUserService();

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("UserServiceActivator is started...!");
        itemUserService.setBundleContext(context);

        Scanner scanner = new Scanner(System.in);

        try {
            userInstruction();
            System.out.println("Select the option by using respective number:");
            this.userInstructionNumber = scanner.nextInt();

            switch (this.userInstructionNumber) {
                case InputTypes.ADD_CUSTOMER:
                    printNotYetImplemented();
                    break;
                case InputTypes.UPDATE_CUSTOMER:
                    printNotYetImplemented();
                    break;

                case InputTypes.DELETE_CUSTOMER:
                    printNotYetImplemented();
                    break;

                case InputTypes.VIEW_CUSTOMER:
                    printNotYetImplemented();
                    break;

                case InputTypes.VIEW_CUSTOMERS:
                    printNotYetImplemented();
                    break;


                /* ITEM */
                case InputTypes.ADD_ITEM:
                    this.itemUserService.add();
                    break;
                case InputTypes.UPDATE_ITEM:
                    this.itemUserService.update();
                    break;

                case InputTypes.DELETE_ITEM:
                    this.itemUserService.delete();
                    break;

                case InputTypes.VIEW_ITEM:
                    this.itemUserService.view();
                    break;

                case InputTypes.VIEW_ITEMS:
                    this.itemUserService.viewAll();
                    break;


                /* ORDERS */
                case InputTypes.ADD_ORDER:
                    printNotYetImplemented();
                    break;
                case InputTypes.UPDATE_ORDER:
                    printNotYetImplemented();
                    break;

                case InputTypes.DELETE_ORDER:
                    printNotYetImplemented();
                    break;

                case InputTypes.VIEW_ORDER:
                    printNotYetImplemented();
                    break;

                case InputTypes.VIEW_ORDERS:
                    printNotYetImplemented();
                    break;


                /* ORDER DETAIL */
                case InputTypes.ADD_ORDER_DETAIL:
                    printNotYetImplemented();
                    break;
                case InputTypes.UPDATE_ORDER_DETAIL:
                    printNotYetImplemented();
                    break;

                case InputTypes.DELETE_ORDER_DETAIL:
                    printNotYetImplemented();
                    break;

                case InputTypes.VIEW_ORDER_DETAIL:
                    printNotYetImplemented();
                    break;

                case InputTypes.VIEW_ORDER_DETAILS:
                    printNotYetImplemented();
                    break;

                default:
                    System.err.println("Unknown instruction.");

            }
        } catch (InputMismatchException e) {
//            e.printStackTrace();
            System.err.println("Invalid input, only accept given integers above.");
        }

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("UserServiceActivator is stopped...!");
    }


    private void userInstruction() {
        System.out.println("");
        System.out.println("*** Customer ****");
        System.out.println("ADD_CUSTOMER : " + InputTypes.ADD_CUSTOMER);
        System.out.println("UPDATE_CUSTOMER : " + InputTypes.UPDATE_CUSTOMER);
        System.out.println("DELETE_CUSTOMER : " + InputTypes.DELETE_CUSTOMER);
        System.out.println("VIEW_CUSTOMER : " + InputTypes.VIEW_CUSTOMER);
        System.out.println("VIEW_CUSTOMERS : " + InputTypes.VIEW_CUSTOMERS);
        System.out.println("");

        System.out.println("");
        System.out.println("*** Item ****");
        System.out.println("ADD_ITEM : " + InputTypes.ADD_ITEM);
        System.out.println("UPDATE_ITEM : " + InputTypes.UPDATE_ITEM);
        System.out.println("DELETE_ITEM : " + InputTypes.DELETE_ITEM);
        System.out.println("VIEW_ITEM : " + InputTypes.VIEW_ITEM);
        System.out.println("VIEW_ITEMS : " + InputTypes.VIEW_ITEMS);
        System.out.println("");

        System.out.println("");
        System.out.println("*** Order ****");
        System.out.println("ADD_ORDER : " + InputTypes.ADD_ORDER);
        System.out.println("UPDATE_ORDER : " + InputTypes.UPDATE_ORDER);
        System.out.println("DELETE_ORDER : " + InputTypes.DELETE_ORDER);
        System.out.println("VIEW_ORDER : " + InputTypes.VIEW_ORDER);
        System.out.println("VIEW_ORDERS : " + InputTypes.VIEW_ORDERS);
        System.out.println("");

        System.out.println("");
        System.out.println("*** OrderDetail ****");
        System.out.println("ADD_ORDER_DETAIL : " + InputTypes.ADD_ORDER_DETAIL);
        System.out.println("UPDATE_ORDER_DETAIL : " + InputTypes.UPDATE_ORDER_DETAIL);
        System.out.println("DELETE_ORDER_DETAIL : " + InputTypes.DELETE_ORDER_DETAIL);
        System.out.println("VIEW_ORDER_DETAIL : " + InputTypes.VIEW_ORDER_DETAIL);
        System.out.println("VIEW_ORDER_DETAILS : " + InputTypes.VIEW_ORDER_DETAILS);
        System.out.println("");
    }

    private void printNotYetImplemented(){
        System.out.println("This feature is not yest implemented, sorry for the inconvenience");
    }
}
