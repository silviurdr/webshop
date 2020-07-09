package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface CartDao {

    void add(Product product);
    Product find(int id);
    void remove(int id);

    void clearCart();

    ConcurrentHashMap<Product, Integer> getAll();

}