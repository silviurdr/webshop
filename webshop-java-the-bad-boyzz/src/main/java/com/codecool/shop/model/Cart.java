package com.codecool.shop.model;


public class Cart {
    private int id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerCountry;
    private String customerZip;
    private String customerCity;
    private String customerAddress;
    private int customerId;

    public Cart(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void updateCustomerData(String customerName, String customerEmail, String customerPhone, String customerCountry,
                                   String customerZip, String customerCity, String customerAddress){
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerCountry = customerCountry;
        this.customerZip = customerZip;
        this.customerCity = customerCity;
        this.customerAddress = customerAddress;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerZip() {
        return customerZip;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public void setCustomerZip(String customerZip) {
        this.customerZip = customerZip;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
