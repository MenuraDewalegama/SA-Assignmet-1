package lk.sliit.code4.osgi.item;

import lk.sliit.code4.osgi.item.entity.Item;

import java.util.List;

public interface ItemServicePublish {
    boolean addItem(Item Item);

    boolean updateItem(Item item);

    boolean deleteItem(int itemCode);

    Item findItem(int id);

    List<Item> findItems();

    boolean isInStock(int itemCode, int userRequestingQuantity);

    boolean updateItemQuantity(int itemCode, int userRequestingQuantity);

    boolean isContain(int itemCode);

    boolean isEmpty();

}
