package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartProductDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartProductDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoJDBC.getInstance();
        CartProductDao cartProductCategoryDataStore = CartProductDaoMem.getInstance();

        String toAddId = req.getParameter("id");
        String howMany = req.getParameter("howMany");
        String prodId = req.getParameter("prodId");
        int numOfProducts = 0;


        if (howMany != null) {
            int howManyInt = Integer.parseInt(howMany);
            int prodIdInt = Integer.parseInt(prodId);
            for(Product p: cartProductCategoryDataStore.getAll().keySet()) {
                if (p.getId() ==  prodIdInt) {
                    if(howManyInt == 0) {
                        cartProductCategoryDataStore.remove(prodIdInt);
                    } else {
                        cartProductCategoryDataStore.getAll().put(p, howManyInt);
                    }
                }
            }
        }


        if (toAddId != null) {
            cartProductCategoryDataStore.add(productDataStore.find(Integer.parseInt(toAddId)));

        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        for (Product p : cartProductCategoryDataStore.getAll().keySet()) {
            numOfProducts += cartProductCategoryDataStore.getAll().get(p);
        }


        float sum = 0;
        for (Product p : cartProductCategoryDataStore.getAll().keySet()) {
            sum += p.getDefaultPrice() * cartProductCategoryDataStore.getAll().get(p);
        }
        String sum2  = String.format("%.1f", sum);


        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products1", cartProductCategoryDataStore.getAll());
        context.setVariable("productsSet", cartProductCategoryDataStore.getAll());
        context.setVariable("sum", sum2);
        context.setVariable("numOfProducts", numOfProducts);
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        if (toAddId != null) {
//            engine.process("product/index.html", context, resp.getWriter());
            resp.sendRedirect("/");
            toAddId = null;
        } else {
            engine.process("product/cart.html", context, resp.getWriter());

        }

    }

}
