package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.UserDaoJDBC;
import com.codecool.shop.model.User;
import com.codecool.shop.utils.SaltedHashPassword;
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


@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    UserDao userDao = UserDaoJDBC.getInstance();

    public RegisterController() throws IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("register-error", req.getAttribute("register-error"));

        try {
            engine.process("product/index.html", context, resp.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();
        String userFullName = req.getParameter("fullname");
        String userEmail = req.getParameter("email");
        String emailError = "Email Address Already in Use!";
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


        try {
            if (userDao.find(userEmail)!= null) {
                session.setAttribute("email", "open");
                session.setAttribute("emailError", "Email Address Already in Use!");
                session.setAttribute("registerName", userFullName);
                session.setAttribute("registerMobile", userPhoneNumber);
                resp.sendRedirect("/");
            } else {
                userDao.add(newUser);
                session.setAttribute("email", userEmail);
                session.setAttribute("emailError", emailError);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        resp.sendRedirect("/");

    }

}
