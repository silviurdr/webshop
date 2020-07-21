package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.BillingInformationDao;

import java.util.LinkedHashMap;

public class BillingInformationDaoMem implements BillingInformationDao {

    private LinkedHashMap<Integer,Cart> data = new LinkedHashMap<>();
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
    public void add(Cart order) {
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
    public LinkedHashMap<Integer, Cart> getAll() {
        return data;
    }
    @Override
    public Cart getFirst(){
        return data.get(1);
    }

}
