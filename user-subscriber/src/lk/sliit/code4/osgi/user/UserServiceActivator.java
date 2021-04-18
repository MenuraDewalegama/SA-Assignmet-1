package lk.sliit.code4.osgi.user;

import lk.sliit.code4.osgi.user.constant.*;
import lk.sliit.code4.osgi.user.service.CustomerUserService;
import lk.sliit.code4.osgi.user.service.ItemUserService;
import lk.sliit.code4.osgi.user.service.OrderUserService;
import lk.sliit.code4.osgi.user.service.SuperUserService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserServiceActivator implements BundleActivator {

    int userInstructionNumber;
    SuperUserService itemUserService = ItemUserService.getInstance();
    SuperUserService customerUserService = CustomerUserService.getInstance();
    SuperUserService orderUserService = OrderUserService.getInstance();

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(ServiceLifeCycle.LIFT_CYCLE_STARTED);

        /* share bundleContext */
        itemUserService.setBundleContext(context);
        customerUserService.setBundleContext(context);
        orderUserService.setBundleContext(context);

        Scanner scanner = new Scanner(System.in);

        try {
            userInstruction();
            System.out.println(Instructions.SELECT_OPTION);
            this.userInstructionNumber = scanner.nextInt();

            switch (this.userInstructionNumber) {
                case InputTypes.ADD_CUSTOMER:
                    this.customerUserService.add();
                    break;
                case InputTypes.UPDATE_CUSTOMER:
                    this.customerUserService.update();
                    break;

                case InputTypes.DELETE_CUSTOMER:
                    this.customerUserService.delete();
                    break;

                case InputTypes.VIEW_CUSTOMER:
                    this.customerUserService.view();
                    break;

                case InputTypes.VIEW_CUSTOMERS:
                    this.customerUserService.viewAll();
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
                    this.orderUserService.add();
                    break;
                case InputTypes.UPDATE_ORDER:
                    printNotYetImplemented();
//                    this.orderUserService.update();
                    break;

                case InputTypes.DELETE_ORDER:
                    this.orderUserService.delete();
                    break;

                case InputTypes.VIEW_ORDER:
                    this.orderUserService.view();
                    break;

                case InputTypes.VIEW_ORDERS:
                    this.orderUserService.viewAll();
                    break;


                /* ORDER DETAIL */
//                case InputTypes.ADD_ORDER_DETAIL:
//                    printNotYetImplemented();
//                    break;
//                case InputTypes.UPDATE_ORDER_DETAIL:
//                    printNotYetImplemented();
//                    break;
//
//                case InputTypes.DELETE_ORDER_DETAIL:
//                    printNotYetImplemented();
//                    break;
//
//                case InputTypes.VIEW_ORDER_DETAIL:
//                    printNotYetImplemented();
//                    break;
//
//                case InputTypes.VIEW_ORDER_DETAILS:
//                    printNotYetImplemented();
//                    break;

                default:
                    System.err.println(Instructions.UNKNOWN_INSTRUCTION);

            }
        } catch (InputMismatchException e) {
//            e.printStackTrace();
            System.err.println(ValidationPrompts.INVALID_INPUT_ONLY_INTEGERS);
        }

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(ServiceLifeCycle.LIFT_CYCLE_STOPPED);
    }


    private void userInstruction() {
        System.out.println();
        System.out.println(Dividers.CUSTOMER);
        System.out.println(Instructions.ADD_CUSTOMER + InputTypes.ADD_CUSTOMER);
        System.out.println(Instructions.UPDATE_CUSTOMER + InputTypes.UPDATE_CUSTOMER);
        System.out.println(Instructions.DELETE_CUSTOMER + InputTypes.DELETE_CUSTOMER);
        System.out.println(Instructions.VIEW_CUSTOMER + InputTypes.VIEW_CUSTOMER);
        System.out.println(Instructions.VIEW_CUSTOMERS + InputTypes.VIEW_CUSTOMERS);
        System.out.println();

        System.out.println();
        System.out.println(Dividers.ITEM);
        System.out.println(Instructions.ADD_ITEM + InputTypes.ADD_ITEM);
        System.out.println(Instructions.UPDATE_ITEM + InputTypes.UPDATE_ITEM);
        System.out.println(Instructions.DELETE_ITEM + InputTypes.DELETE_ITEM);
        System.out.println(Instructions.VIEW_ITEM + InputTypes.VIEW_ITEM);
        System.out.println(Instructions.VIEW_ITEMS + InputTypes.VIEW_ITEMS);
        System.out.println();

        System.out.println();
        System.out.println(Dividers.ORDER);
        System.out.println(Instructions.ADD_ORDER + InputTypes.ADD_ORDER);
        System.out.println(Instructions.UPDATE_ORDER + InputTypes.UPDATE_ORDER);
        System.out.println(Instructions.DELETE_ORDER + InputTypes.DELETE_ORDER);
        System.out.println(Instructions.VIEW_ORDER + InputTypes.VIEW_ORDER);
        System.out.println(Instructions.VIEW_ORDERS + InputTypes.VIEW_ORDERS);
        System.out.println();

//        System.out.println("");
//        System.out.println("*** OrderDetail ****");
//        System.out.println("ADD_ORDER_DETAIL : " + InputTypes.ADD_ORDER_DETAIL);
//        System.out.println("UPDATE_ORDER_DETAIL : " + InputTypes.UPDATE_ORDER_DETAIL);
//        System.out.println("DELETE_ORDER_DETAIL : " + InputTypes.DELETE_ORDER_DETAIL);
//        System.out.println("VIEW_ORDER_DETAIL : " + InputTypes.VIEW_ORDER_DETAIL);
//        System.out.println("VIEW_ORDER_DETAILS : " + InputTypes.VIEW_ORDER_DETAILS);
//        System.out.println("");
    }

    /**
     * prints the services are not yet implemented.
     */
    private void printNotYetImplemented() {
        System.out.println(ValidationPrompts.NOT_YET_IMPLEMENTED);
    }
}
