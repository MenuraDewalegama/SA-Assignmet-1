package lk.sliit.code4.osgi.item;/*
@author : Dhanusha Perera
@since : 15/04/2021
*/

import lk.sliit.code4.osgi.item.entity.Item;

import java.util.List;

public interface ItemServicePublish {
    boolean addItem(Item Item);

    boolean updateItem(Item item);

    boolean deleteItem(int itemCode);

    Item findItem(int id);

    List<Item> findItems();


    boolean isContain(int itemCode);

    boolean isEmpty();

}
