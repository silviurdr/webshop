package com.codecool.shop.controller;

import com.codecool.shop.model.User;
import com.codecool.shop.utils.SaltedHashPassword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String userFullName = req.getParameter("fullname");
        String userEmail = req.getParameter("email");
        String userPhoneNumber = req.getParameter("mobile");
        String userPassword = req.getParameter("password");
        String saltedHashPassword = null;
        try {
            saltedHashPassword = SaltedHashPassword.generateStrongPasswordHash(userPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        User newUser = new User(userFullName, userEmail, userPhoneNumber, saltedHashPassword);

        System.out.println(newUser.toString());


        resp.sendRedirect("/");

    }

}
