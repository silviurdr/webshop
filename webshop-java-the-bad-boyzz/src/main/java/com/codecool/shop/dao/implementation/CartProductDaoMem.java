package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartProductDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartProductDaoMem implements CartProductDao {

    private List<Product> data = new ArrayList<>();
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
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return data;
    }



}
