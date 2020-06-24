package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Order;

public class CheckoutDaoMem extends Order {
    private static CheckoutDaoMem instance = null;
    public static CheckoutDaoMem getInstance() {
        if (instance == null) {
            instance = new CheckoutDaoMem();
        }
        return instance;
    }

}
