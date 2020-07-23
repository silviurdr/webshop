package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
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

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String emailValidation = (String) session.getAttribute("email");
        String emailError = "Email Address Already in Use!";
        String userEmail = (String) session.getAttribute("sessuser");

        ProductDao productDataStore = ProductDaoJDBC.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
        SupplierDao productSupplierDataStore = SupplierDaoJDBC.getInstance();
        CartDao cartDataStore = CartDaoJDBC.getInstance();
        int noOfProducts = 0;
        UserDao userDataStore = UserDaoJDBC.getInstance();
        Cart cart = null;
        try {
            User user = userDataStore.find(userEmail);
            if (user!=null){
                cart = cartDataStore.findByUserID(user.getId());
                if(cart!=null){
                    for (int nrProduct : cartDataStore.getCartProducts(cart).values()) {
                        noOfProducts+=nrProduct;
                    }
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
        context.setVariable("emailValidation", emailValidation);
        context.setVariable("emailError", emailError);
        context.setVariable("registerName", session.getAttribute("registerName"));
        context.setVariable("registerMobile", session.getAttribute("registerMobile"));
        context.setVariable("userSession", session.getAttribute("userSession") != null ? session.getAttribute("userSession")  : "No");


        //products
        if (categoryId != 0 && supplier != 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId), productSupplierDataStore.find(supplier)));
        } else if (categoryId != 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
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



