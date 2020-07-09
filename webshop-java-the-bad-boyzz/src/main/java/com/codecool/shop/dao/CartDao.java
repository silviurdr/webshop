package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import java.util.concurrent.ConcurrentHashMap;

public interface CartDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);

    void clearCart();

    ConcurrentHashMap<Product, Integer> getAll();

}