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
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();

        User user = userDataStore.find(userEmail);
        AdminLog log = AdminLog.getInstance();
        log.jsonifyLog(cartDataStore);
        log.addToFile("Checkout");
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
            cart = cartDataStore.findByUserID(user.getId());
            for (Product p : cartDataStore.getCartProducts(cart).keySet()) {
                noOfProducts += cartDataStore.getCartProducts(cart).get(p);
            }

            for (Product p : cartDataStore.getCartProducts(cart).keySet()) {
                sum += p.getDefaultPrice() * cartDataStore.getCartProducts(cart).get(p);
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
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();

        User user = userDataStore.find(userEmail);
        String forAdminLog = "Proceed checkout";

        try {
            Cart cart = cartDataStore.findByUserID(user.getId());
            cart.setCustomerName(req.getParameter("name"));
            cart.setCustomerEmail(req.getParameter("email"));
            cart.setCustomerAddress(req.getParameter("address"));
            cart.setCustomerCountry(req.getParameter("country"));
            cart.setCustomerCity(req.getParameter("city"));
            cart.setCustomerPhone(req.getParameter("phone"));
            cart.setCustomerZip(req.getParameter("zip"));
//            BillingInformationDao billingInfo=BillingInformationDaoMem.getInstance();
//            billingInfo.add(cart);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        resp.sendRedirect("/payment");
    }

}

