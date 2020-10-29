package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoJDBC;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.model.User;
import com.codecool.shop.utils.SaltedHashPassword;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {


    UserDaoJDBC userDaoJDBC = UserDaoJDBC.getInstance();

    public LoginController() throws IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String loginUserEmail = req.getParameter("login-email");
        String password = req.getParameter("login-password");
        String loginPasswordDB = null;

        try {
            loginPasswordDB = userDaoJDBC.getUserPasswordByEmail(loginUserEmail);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        System.out.println(loginPasswordDB);

        try {
            if(SaltedHashPassword.validatePassword(password, loginPasswordDB)) {

                System.out.println("Password matching!");

                HttpSession httpSession = req.getSession(true);
                httpSession.setAttribute("sessuser", loginUserEmail);
                session.setAttribute("userSession", "Yes");
                System.out.println(session.getAttribute("userSession"));

                UserDao userDataStore = UserDaoJDBC.getInstance();
                CartDao cartDataStore = CartDaoJDBC.getInstance();
                int order_id = 0;
                try {
                    order_id = (int) session.getAttribute("order_id");
                }catch (NullPointerException exception){

                }
                String userEmail = (String) session.getAttribute("sessuser");
                User user = userDataStore.find(userEmail);
                if(order_id!=0){
                    cartDataStore.connectUserToCart(order_id,user.getId());
                }


            }

            else {
                JOptionPane.showMessageDialog(null, "Username or password is incorrect");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        resp.sendRedirect("/");

    }

}
