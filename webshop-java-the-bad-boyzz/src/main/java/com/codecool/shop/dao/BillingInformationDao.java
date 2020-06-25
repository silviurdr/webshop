package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.LinkedHashMap;

public interface BillingInformationDao {
    void add(Order order);
    void remove(int id);
    void clear();
    Order getFirst();
    LinkedHashMap<Integer,Order> getAll();
}
