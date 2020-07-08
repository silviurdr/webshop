package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartProductDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
        SupplierDao productSupplierDataStore = SupplierDaoJDBC.getInstance();
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


        try {
            context.setVariable("category", productCategoryDataStore.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        context.setVariable("supplier", productSupplierDataStore.getAll());
        context.setVariable("noOfProducts", noOfProducts);

        //products
        if (categoryId != 0 && supplier != 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId), productSupplierDataStore.find(supplier)));
        } else if (categoryId != 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
            System.out.println(productDataStore.getBy(productCategoryDataStore.find(categoryId)));
        } else if (supplier != 0 ) {
            context.setVariable("products", productDataStore.getBy(productSupplierDataStore.find(supplier)));
        } else  {
            try {
                context.setVariable("products", productDataStore.getAll());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        engine.process("product/index.html", context, resp.getWriter());


    }

}



