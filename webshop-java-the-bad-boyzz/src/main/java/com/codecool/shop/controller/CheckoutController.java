package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoJDBC;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.model.AdminLog;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //cart and details for checkout
        HttpSession session = req.getSession();

        String userEmail = (String) session.getAttribute("sessuser");


        UserDao userDataStore = UserDaoJDBC.getInstance();
        CartDao cartDataStore = CartDaoJDBC.getInstance();


        AdminLog log = AdminLog.getInstance();
        int noOfProducts = 0;
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

        Cart cart = null;
        try {
            User user = userDataStore.find(userEmail);
            cart = cartDataStore.findByUserID(user.getId());
            log.jsonifyLog(cart);
            log.addToFile("Checkout");
            for (Product p : cartDataStore.getCartProducts(cart).keySet()) {
                noOfProducts++;
                sum += p.getDefaultPrice();
            }

            float totalSum = sum - redeemCodeValue;

            String sum2  = String.format("%.1f", totalSum);



            context.setVariable("products1", cartDataStore.getCartProducts(cart));
            context.setVariable("productsSet", cartDataStore.getCartProducts(cart));
            context.setVariable("sum", sum2);
            context.setVariable("noOfProducts", noOfProducts);
            context.setVariable("redeemCode", redeemCode);
            context.setVariable("redeemCodeValue", redeemCodeValue);
            context.setVariable("userSession", session.getAttribute("userSession") != null ? session.getAttribute("userSession")  : "No");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        engine.process("product/checkout.html", context, resp.getWriter());

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userEmail = (String) session.getAttribute("sessuser");



        CartDao cartDataStore= CartDaoJDBC.getInstance();
        UserDao userDataStore = UserDaoJDBC.getInstance();


        String forAdminLog = "Proceed checkout";

        try {
            User user = userDataStore.find(userEmail);
            Cart cart = cartDataStore.findByUserID(user.getId());
            cart.updateCustomerData(req.getParameter("name"),req.getParameter("email"),req.getParameter("address"),
                    req.getParameter("country"),req.getParameter("zip"),req.getParameter("city"),req.getParameter("phone"));
            cartDataStore.updateCustomerInfo(cart);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        resp.sendRedirect("/payment");
    }

}

