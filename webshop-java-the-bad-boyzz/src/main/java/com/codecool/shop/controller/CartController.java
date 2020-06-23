package com.codecool.shop.controller;

import com.codecool.shop.dao.CartProductDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.CartProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartProductDao cartProductCategoryDataStore = CartProductDaoMem.getInstance();
        Set<Product> prodForCart = new HashSet<>(cartProductCategoryDataStore.getAll());

        String toAddId = req.getParameter("id");

        if (toAddId != null){
            cartProductCategoryDataStore.add(productDataStore.find(Integer.parseInt(toAddId)));

        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        float sum = 0;
        for (Product p : cartProductCategoryDataStore.getAll()){
            sum+=p.getDefaultPrice();
        }
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products1", cartProductCategoryDataStore.getAll());
        context.setVariable("productsSet", prodForCart);
        context.setVariable("sum", sum);
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        if (toAddId != null){
//            engine.process("product/index.html", context, resp.getWriter());
            resp.sendRedirect("/");
            toAddId = null;
        }else{
            engine.process("product/cart.html", context, resp.getWriter());

        }

    }

}
