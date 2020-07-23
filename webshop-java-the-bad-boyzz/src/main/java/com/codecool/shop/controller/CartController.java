package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
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
import java.util.Map;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String userEmail = (String) session.getAttribute("sessuser");

        UserDao userDataStore = UserDaoJDBC.getInstance();
        CartDao cartDataStore = CartDaoJDBC.getInstance();

        User user = null;

        int numOfProducts = 0;
        String howMany = req.getParameter("howMany");
        String prodId = req.getParameter("prodId");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        Cart cart = null;
        float sum = 0;
        try {
            user = userDataStore.find(userEmail);
            if (user!=null) {
                cart = cartDataStore.findByUserID(user.getId());
                if (howMany != null) {
                    int howManyInt = Integer.parseInt(howMany);
                    if (howManyInt==0){
                    }
                    int prodIdInt = Integer.parseInt(prodId);
                    for(Product p: cartDataStore.getCartProducts(cart).keySet()) {
                        if (p.getId() ==  prodIdInt) {
                            cartDataStore.updateProductQuantity(p, cart, howManyInt);
                        }
                    }
                }
                for (Map.Entry<Product, Integer> entry : cartDataStore.getCartProducts(cart).entrySet()) {
                    numOfProducts+=entry.getValue();
                    sum += entry.getKey().getDefaultPrice()* entry.getValue();
                }
                if(numOfProducts==0){
                    cart = cartDataStore.createCart(user);
                }

            }
            String sum2  = String.format("%.1f", sum);


            context.setVariable("products1", cartDataStore.getCartProducts(cart));
            context.setVariable("productsSet", cartDataStore.getCartProducts(cart));
            context.setVariable("sum", sum2);
            context.setVariable("numOfProducts", numOfProducts);
            context.setVariable("noOfProducts", numOfProducts);
            context.setVariable("userSession", session.getAttribute("userSession") != null ? session.getAttribute("userSession")  : "No");



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        engine.process("product/cart.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String userEmail = (String) session.getAttribute("sessuser");

        UserDao userDataStore = UserDaoJDBC.getInstance();
        CartDao cartDataStore = CartDaoJDBC.getInstance();
        ProductDao productDataStore = ProductDaoJDBC.getInstance();



        int prodId =Integer.parseInt(req.getParameter("id"));


        Cart cart = null;
        try {
            User user = userDataStore.find(userEmail);
            cart = cartDataStore.findByUserID(user.getId());
            if (cart == null) {
                cart = cartDataStore.createCart(user);
            }
            cartDataStore.add(productDataStore.find(prodId), cart, cartDataStore.getCartProducts(cart.getId()));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        resp.sendRedirect("/");

    }
}
