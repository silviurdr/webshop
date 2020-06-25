package com.codecool.shop.model;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Order {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerCountry;
    private String customerZip;
    private String customerCity;
    private String customerAddress;
    private int topay;

    public String getCustomerName() {
        return customerName;
    }
    public Order(){
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
            FileWriter file = new FileWriter("/home/www/Desktop/OOP/nearby-el/webshop/webshop-java-the-bad-boyzz/src/main/webapp/static/json/paymentDetails.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created: " + jsonObject);
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

    public int getTopay() {
        return topay;
    }

    public void setTopay(int topay) {
        this.topay = topay;
    }
}
