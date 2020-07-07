package com.codecool.shop.controller;

import com.codecool.shop.model.User;
import com.codecool.shop.utils.SaltedHashPassword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/register"})
public class UserController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String userFullName = req.getParameter("fullname");
        String userEmail = req.getParameter("email");
        String userPhoneNumber = req.getParameter("mobile");
        String userPassword = req.getParameter("password");
        String saltedHashPassword = SaltedHashPassword.generateSaltedHashPassword(userPassword);

        User newUser = new User(userFullName, userEmail, userPhoneNumber, saltedHashPassword);

        System.out.println(newUser.toString());


        resp.sendRedirect("/");

    }

}
