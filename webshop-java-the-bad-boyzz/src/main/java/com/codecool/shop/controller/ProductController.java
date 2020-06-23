package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao productSupplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        //products and supplier
        String categoryId = req.getParameter("category");
        String supplier = req.getParameter("suppliers");

        System.out.println(categoryId);
        System.out.println(supplier);

        if (!categoryId.equals("") && !supplier.equals("")) {
            int categoryIdInt = Integer.parseInt(categoryId);
            int supplierId = Integer.parseInt(supplier);
            context.setVariable("category", productCategoryDataStore.getAll());
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryIdInt), productSupplierDataStore.find(supplierId)));
        }
         else if (categoryId.equals("") && supplier.equals("")){
            context.setVariable("category", productCategoryDataStore.getAll());
            context.setVariable("products", productDataStore.getAll());
        }

        engine.process("product/index.html", context, resp.getWriter());
    }

}



