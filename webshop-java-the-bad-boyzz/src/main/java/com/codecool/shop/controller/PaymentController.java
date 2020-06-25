package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.CheckoutDaoMem;
import com.codecool.shop.model.SendMail;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CheckoutDaoMem checkout = CheckoutDaoMem.getInstance();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        context.setVariable("order", checkout );
        engine.process("product/payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CheckoutDaoMem checkoutDetails = CheckoutDaoMem.getInstance();
        checkoutDetails.setCustomerName(req.getParameter("name"));
        checkoutDetails.setCustomerEmail(req.getParameter("email"));
        checkoutDetails.setCustomerAddress(req.getParameter("address"));
        checkoutDetails.setCustomerCountry(req.getParameter("country"));
        checkoutDetails.setCustomerCity(req.getParameter("city"));
        checkoutDetails.setCustomerPhone(req.getParameter("phone"));
        checkoutDetails.setCustomerZip(req.getParameter("zip"));

        String cardOwner = req.getParameter("cardOwner");
        String cardNumber=req.getParameter("cardNumber");
        String expirationDate=req.getParameter("expirationDate");
        String cvv=req.getParameter("CVV");
        String paypalUsername=req.getParameter("paypalUsername");
        String paypalEmail=req.getParameter("paypalEmail");
        int cardNumberFormated;
        int cvvFormated;

        try{
            cardNumberFormated=Integer.parseInt(cardNumber);
            cvvFormated=Integer.parseInt(cvv);
        }catch (NumberFormatException e){
            resp.sendRedirect("/payment-error");
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String to = "barbu.mihai1993@gmail.com";
        String subject = "Order Conf";
        String message =  "message";
        String user = "codecool.shop.romania@gmail.com";
        String pass = "1234asd@";
        SendMail.send(to,subject, message, user, pass);
        out.println("Mail send successfully");




        resp.sendRedirect("/confirmation");
    }

 }