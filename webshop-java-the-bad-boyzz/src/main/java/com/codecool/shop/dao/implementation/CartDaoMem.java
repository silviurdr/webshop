package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CartDaoMem implements CartDao {

    private int id;
    private static int count=0;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerCountry;
    private String customerZip;
    private String customerCity;
    private String customerAddress;
    private int topay;
    private ConcurrentHashMap<Product, Integer> data = new ConcurrentHashMap<>();
    private static CartDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
        id =++count;
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }



    public String getCustomerName() {
        return customerName;
    }

    public void Jsonify() {
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put("Name", this.getCustomerName());
        jsonObject.put("Phone", this.getCustomerPhone());
        jsonObject.put("Adress", this.getCustomerAddress());
        jsonObject.put("City", this.getCustomerCity());
        jsonObject.put("Country", this.getCustomerCountry());
        jsonObject.put("Email", this.getCustomerEmail());
        jsonObject.put("Zip", this.getCustomerZip());

        try {
            FileWriter file = new FileWriter("src/main/webapp/static/json/paymentDetails.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerZip() {
        return customerZip;
    }

    public void setCustomerZip(String customerZip) {
        this.customerZip = customerZip;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getId() {
        return id;
    }

    public int getTopay() {
        return topay;
    }

    public void setTopay(int topay) {
        this.topay = topay;
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

    @Override
    public void clearCart() {
        data.clear();
    }



}
