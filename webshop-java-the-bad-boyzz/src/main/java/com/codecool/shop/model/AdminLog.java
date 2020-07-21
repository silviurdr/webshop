package com.codecool.shop.model;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminLog {

    private static AdminLog instance = null;
    CartDaoMem order = CartDaoMem.getInstance();
    private Date date= new Date();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String date1 = dateFormat.format(date);
    public String filename = "src/main/webapp/static/json/"+order.getId()+"-date-"+date1+".json";

    private AdminLog() {

    }

    public static AdminLog getInstance() {
        if (instance == null) {
            instance = new AdminLog();
        }
        return instance;
    }

    public void jsonifyLog(CartDao order) {

        JSONObject jsonObject = new JSONObject();
        String filename = this.filename;
        try {
            FileWriter file = new FileWriter(filename);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("JSON file created: " + jsonObject);
    }

    public void addToFile(String process){
        JSONObject obj = new JSONObject();
        if (process.equals("Checkout")) {
            obj.put("Checkout", true);
            obj.put("Payment Method", false);
            obj.put("Payment Confirmation", false);
        } else if (process.equals("Payment")) {
            obj.put("Checkout", true);
            obj.put("Payment Method", true);
            obj.put("Payment Confirmation", false);
        } else if(process.equals("Confirmation")){
            obj.put("Checkout", true);
            obj.put("Payment Method", true);
            obj.put("Payment Confirmation", true);
        }
        try {
            FileWriter file = new FileWriter(this.filename);
            file.write(obj.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Added to file:");

    }
}
