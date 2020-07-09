package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.CartDaoMem;

import java.util.LinkedHashMap;

public interface BillingInformationDao {
    void add(CartDaoMem order);
    void remove(int id);
    void clear();
    CartDaoMem getFirst();
    LinkedHashMap<Integer,CartDaoMem> getAll();
}
