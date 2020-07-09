package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.BillingInformationDao;
import com.codecool.shop.dao.CartProductDao;
import com.codecool.shop.dao.implementation.BillingInformationDaoMem;
import com.codecool.shop.dao.implementation.CartProductDaoMem;
import com.codecool.shop.dao.implementation.CheckoutDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //cart and details for checkout
        CartProductDao cartProductCategoryDataStore = CartProductDaoMem.getInstance();
        int noOfProducts = 0;
        float sum = 0;
        HttpSession session = req.getSession();

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


        for (Product p : cartProductCategoryDataStore.getAll().keySet()) {
            noOfProducts += cartProductCategoryDataStore.getAll().get(p);
        }

        for (Product p : cartProductCategoryDataStore.getAll().keySet()) {
            sum += p.getDefaultPrice() * cartProductCategoryDataStore.getAll().get(p);
        }

        float totalSum = sum - redeemCodeValue;

        String sum2  = String.format("%.1f", totalSum);



        context.setVariable("products1", cartProductCategoryDataStore.getAll());
        context.setVariable("productsSet", cartProductCategoryDataStore.getAll());
        context.setVariable("sum", sum2);
        context.setVariable("noOfProducts", noOfProducts);
        context.setVariable("redeemCode", redeemCode);
        context.setVariable("redeemCodeValue", redeemCodeValue);
        context.setVariable("userSession", session.getAttribute("userSession") != null ? session.getAttribute("userSession")  : "No");


        engine.process("product/checkout.html", context, resp.getWriter());

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order= new Order();
        order.setCustomerName(req.getParameter("name"));
        order.setCustomerEmail(req.getParameter("email"));
        order.setCustomerAddress(req.getParameter("address"));
        order.setCustomerCountry(req.getParameter("country"));
        order.setCustomerCity(req.getParameter("city"));
        order.setCustomerPhone(req.getParameter("phone"));
        order.setCustomerZip(req.getParameter("zip"));
        BillingInformationDao billingInfo=BillingInformationDaoMem.getInstance();
        billingInfo.add(order);
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

