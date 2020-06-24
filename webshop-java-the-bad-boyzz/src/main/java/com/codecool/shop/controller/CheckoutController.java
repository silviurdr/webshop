package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartProductDao;
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
import java.util.HashSet;
import java.util.Set;


@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //cart and details for checkout
        CartProductDao cartProductCategoryDataStore = CartProductDaoMem.getInstance();
        Set<Product> prodForCart = new HashSet<>(cartProductCategoryDataStore.getAll());
        int noOfProducts = cartProductCategoryDataStore.getAll().size();
        float sum = 0;

        // disscount code
        String redeemCode  = req.getParameter("redeem");
        int redeemCodeValue = 0;

        try {
            if (redeemCode.equals("discount")) {
                redeemCodeValue = 5;
            }
        }catch (NullPointerException e) {
        }

        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());




        for (Product p : cartProductCategoryDataStore.getAll()){
            sum+=p.getDefaultPrice();
        }
        float totalSum = sum - redeemCodeValue;

        context.setVariable("products1", cartProductCategoryDataStore.getAll());
        context.setVariable("productsSet", prodForCart);
        context.setVariable("sum", totalSum);
        context.setVariable("noOfProducts", noOfProducts);
        context.setVariable("redeemCode", redeemCode);
        context.setVariable("redeemCodeValue", redeemCodeValue);


        engine.process("product/checkout.html", context, resp.getWriter());

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CheckoutDaoMem checkoutDetails = CheckoutDaoMem.getInstance();
        checkoutDetails.setCustomerName(req.getParameter("name"));
        checkoutDetails.setCustomerEmail(req.getParameter("email"));
        checkoutDetails.setCustomerAddress(req.getParameter("address"));
        checkoutDetails.setCustomerCountry(req.getParameter("country"));
        checkoutDetails.setCustomerCity(req.getParameter("city"));
        checkoutDetails.setCustomerPhone(req.getParameter("phone"));
        checkoutDetails.setCustomerZip(req.getParameter("zip"));

        resp.sendRedirect("/payment");
    }

}

