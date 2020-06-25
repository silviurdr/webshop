package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartProductDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CartProductDaoMem implements CartProductDao {

    private ConcurrentHashMap<Product, Integer> data = new ConcurrentHashMap<>();
    private static CartProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartProductDaoMem() {
    }

    public static CartProductDaoMem getInstance() {
        if (instance == null) {
            instance = new CartProductDaoMem();
        }
        return instance;
    }



    @Override
    public void add(Product product) {
        data.merge(product, 1, Integer::sum);
    }


    @Override
    public Product find( int id) {

        for (Product p: data.keySet()) {
            if (id == p.getId()) {
                return p;
            }
        }
        return null;
    }


    @Override
    public void remove(int id) {

        for (Product p: data.keySet()) {
            if (id == p.getId()) {
                data.remove(p);
            }
        }
    }

    @Override
    public ConcurrentHashMap<Product, Integer> getAll() {
        return data;
    }





}
