package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.BillingInformationDao;
import com.codecool.shop.dao.CartProductDao;
import com.codecool.shop.dao.implementation.BillingInformationDaoMem;
import com.codecool.shop.dao.implementation.CartProductDaoMem;
import com.codecool.shop.dao.implementation.CheckoutDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;

@WebServlet(urlPatterns = {"/confirmation"})
public class PaymentConfController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BillingInformationDao billingInfo= BillingInformationDaoMem.getInstance();
        CartProductDao cartProducts=CartProductDaoMem.getInstance();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        Set<Product> prodForCart = new HashSet<>(cartProducts.getAll());
        float sum = 0;
        for (Product p : cartProducts.getAll()){
            sum+=p.getDefaultPrice();
        }

        String sum2  = String.format("%.1f", sum);
        context.setVariable("total",sum2);
        context.setVariable("products", cartProducts.getAll() );
        context.setVariable("productsSet",prodForCart);
        context.setVariable("billingInfo",billingInfo.getFirst());
        engine.process("product/confirmation.html", context, resp.getWriter());

    }
}
