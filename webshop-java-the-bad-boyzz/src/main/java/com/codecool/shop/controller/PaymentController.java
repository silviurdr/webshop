package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.CheckoutDaoMem;
//import com.codecool.shop.model.SendMail;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

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
        String name="Misu";
        String to = "barbu.mihai1993@gmail.com";
        String subject = "Order Conf";
        int orderID=12;
        String total="1000";
        String message =  "message";
        String user = "codecool.shop.romania@gmail.com";
        String pass = "1234asd@";
        send(to,name, orderID, total);
        out.println("Mail send successfully");




        resp.sendRedirect("/confirmation");
    }
    public static void send(String custEmail, String fullName, int orderId, String total)
    {
        String to = custEmail;
        String host = "smtp.gmail.com";
        String subject = "EDUCATIONAL PROJECT - shop order confirmation";
        String body =  "EDUCATIONAL PROJECT - content with order details";
        final String user = "codecool.shop.romania@gmail.com";
        final String pass = "1234asd@";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(user,pass);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("EDUCATIONAL PROJECT - WEB SHOP ORDER CONFIRMATION");
            message.setText("**** EDUCATIONAL PROJECT**** NOT AN ACTUAL ORDER\n" +
                    "Hello " + fullName +",\n" +
                    "\n" +
                    "Thanks for purchasing from our shop.\n" +
                    "Your order, ID: " + orderId + ", totalling " + total + " USD, was processed successfully" +
                    " and we'll be shipping it shortly.\n" +
                    "If you have any questions, you can always reach us at orders@webshop.com\n" +
                    "\n" +
                    "Thanks again for the business and have a wonderful day! \n" +
                    "The Web Shop Team"

            );

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

 }