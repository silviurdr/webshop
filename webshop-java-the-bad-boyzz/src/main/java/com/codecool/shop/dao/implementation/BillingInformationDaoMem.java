package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.BillingInformationDao;
import com.codecool.shop.model.Order;

import java.util.LinkedHashMap;

public class BillingInformationDaoMem implements BillingInformationDao {

    private LinkedHashMap<Integer,Order> data = new LinkedHashMap<>();
    private static BillingInformationDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private BillingInformationDaoMem() {
    }

    public static BillingInformationDaoMem getInstance() {
        if (instance == null) {
            instance = new BillingInformationDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        data.put(1,order);
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public LinkedHashMap<Integer, Order> getAll() {
        return data;
    }
    @Override
    public Order getFirst(){
        return data.get(1);
    }

}
