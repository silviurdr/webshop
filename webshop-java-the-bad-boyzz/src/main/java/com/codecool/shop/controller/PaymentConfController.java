package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoJDBC;
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

@WebServlet(urlPatterns = {"/confirmation"})
public class PaymentConfController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AdminLog log = AdminLog.getInstance();
        log.addToFile("Confirmation");

        HttpSession session = req.getSession();

        String userEmail = (String) session.getAttribute("sessuser");

        CartDao cartDataStore= CartDaoJDBC.getInstance();
        UserDao userDataStore = UserDaoJDBC.getInstance();

        User user = null;

        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        try {
            user = userDataStore.find(userEmail);
            Cart cart = cartDataStore.findByUserID(user.getId());
            float sum = 0;
            int noOfProducts = 0;
            for (Product p : cartDataStore.getCartProducts(cart).keySet()){
                sum+=p.getDefaultPrice();
                noOfProducts += cartDataStore.getCartProducts(cart).get(p);
            }


            cartDataStore.Jsonify(cart);
            String sum2  = String.format("%.1f", sum);
            context.setVariable("total",sum2);
            context.setVariable("products", cartDataStore.getCartProducts(cart) );
//            context.setVariable("productsSet",cartProducts.getAll());
            context.setVariable("name",cart.getCustomerName());
            context.setVariable("email",cart.getCustomerEmail());
            context.setVariable("phone",cart.getCustomerPhone());
            context.setVariable("country",cart.getCustomerCountry());
            context.setVariable("zip",cart.getCustomerZip());
            context.setVariable("city",cart.getCustomerCity());
            context.setVariable("address",cart.getCustomerAddress());
            context.setVariable("noOfProducts", 0);
            context.setVariable("userSession", session.getAttribute("userSession") != null ? session.getAttribute("userSession")  : "No");

            cartDataStore.clearCart(cart);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }





        engine.process("product/confirmation.html", context, resp.getWriter());

    }
}
