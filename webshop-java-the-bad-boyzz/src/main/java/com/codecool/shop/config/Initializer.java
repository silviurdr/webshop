package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
		try {
			ProductDao productDataStore = ProductDaoJDBC.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProductCategoryDao productCategoryDataStore = null;
		try {
			productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SupplierDao supplierDataStore = null;
		try {
			supplierDataStore = SupplierDaoJDBC.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//setting up a new supplier
//        Supplier amazon = new Supplier("Amazon", "Digital content and services");
//        supplierDataStore.add(amazon);
//        Supplier lenovo = new Supplier("Lenovo", "Computers");
//        supplierDataStore.add(lenovo);
//
//        //setting up a new product category
//        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
//        ProductCategory notebook = new ProductCategory("Notebook", "Hardware", "A notebook computer is a battery- or AC-powered personal computer generally smaller than a briefcase that can easily be transported and conveniently used in temporary spaces such as on airplanes, in libraries, temporary offices, and at meetings..");
//        productCategoryDataStore.add(tablet);
//        productCategoryDataStore.add(notebook);

        //setting up products and printing it
//        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
//        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
//        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
//        productDataStore.add(new Product("HP Pavilion", 700, "USD", "HP Laptop. Great value at a greater price", notebook, amazon));
    }
}
