package lk.sliit.code4.osgi.item;

import lk.sliit.code4.osgi.item.entity.Item;

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
        return this.itemLinkedList.removeIf(itemDB -> itemDB.getCode() == itemCode);
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
            if (item != null) {
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

    @Override
    public boolean isInStock(int itemCode, int userRequestingQuantity) {
        Item item = findItem(itemCode);
        return (((item.getHandOnQuantity()-userRequestingQuantity)>=0) && (item.getHandOnQuantity() != 0));
    }

    @Override
    public boolean updateItemQuantity(int itemCode, int userRequestingQuantity) {
        try {
            Item item = findItem(itemCode);
            item.setHandOnQuantity(item.getHandOnQuantity() - userRequestingQuantity);
        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
            System.out.println("No item found for the given item Code");
        }
            return updateItem(item);
    }
}
