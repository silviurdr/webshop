package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.CartProductDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import com.codecool.shop.utils.SaltedHashPassword;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();
        CartProductDao cartProductCategoryDataStore = CartProductDaoMem.getInstance();
        int noOfProducts = 0;


        for (Product p : cartProductCategoryDataStore.getAll().keySet()) {
            noOfProducts += cartProductCategoryDataStore.getAll().get(p);
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        //products and supplier
        int categoryId = req.getParameter("category") == null ? 0 : Integer.parseInt(req.getParameter("category"));
        int supplier = req.getParameter("suppliers") == null ? 0 : Integer.parseInt(req.getParameter("suppliers"));

        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", productSupplierDataStore.getAll());
        context.setVariable("noOfProducts", noOfProducts);

        //products
        if (categoryId != 0 && supplier != 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId), productSupplierDataStore.find(supplier)));
        } else if (categoryId != 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
        } else if (supplier != 0 ) {
            context.setVariable("products", productDataStore.getBy(productSupplierDataStore.find(supplier)));
        } else  {
            context.setVariable("products", productDataStore.getAll());
        }



        engine.process("product/index.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String userFullName = req.getParameter("fullname");
        String userEmail = req.getParameter("email");
        String userPhoneNumber = req.getParameter("mobile");
        String userPassword = req.getParameter("password");
        String saltedHashPassword = SaltedHashPassword.generateSaltedHashPassword(userPassword);

        User newUser = new User(userFullName, userEmail, userPhoneNumber, saltedHashPassword);


        resp.sendRedirect("/");

    }



}



