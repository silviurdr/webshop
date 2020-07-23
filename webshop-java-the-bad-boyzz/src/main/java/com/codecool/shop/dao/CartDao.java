package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface CartDao {

    void add(Product product, Cart cart, int quantity, HashMap<Integer, Integer> cartProducts) throws SQLException;
    void add(Product product, Cart cart, HashMap<Integer, Integer> cartProducts) throws SQLException;
    HashMap getCartProducts(int order_id) throws SQLException ;
    void updateProductQuantity(Product product, Cart cart, int quantity) throws SQLException;
    void updateCustomerInfo(Cart cart) throws SQLException;
    Cart findById(int id) throws SQLException;
    Cart findByUserID(int id) throws SQLException;
    void remove(Product product, Cart cart) throws SQLException;
    Cart createCart(User user) throws SQLException;
    void clearCart(Cart cart) throws SQLException;
    void Jsonify(Cart cart);


    ConcurrentHashMap<Product, Integer> getCartProducts(Cart cart) throws SQLException;

}