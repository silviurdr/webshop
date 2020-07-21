package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;

import java.util.LinkedHashMap;

public interface BillingInformationDao {
    void add(Cart order);
    void remove(int id);
    void clear();
    Cart getFirst();
    LinkedHashMap<Integer,Cart> getAll();
}
