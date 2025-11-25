package com.example.shop.servlets;

import com.example.shop.command.AddOrderCommand;
import com.example.shop.services.OrderService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/order/create")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userId = req.getParameter("userId");
        List<String> productsIds = Arrays.asList(req.getParameterValues("product"));

        ServletContext context = req.getServletContext();
        OrderService orderService = new OrderService(context);

        AddOrderCommand command = new AddOrderCommand();
        command.setUserId(userId);
        command.setProducts(productsIds);
        orderService.createOrder(command);
        resp.getWriter().println("Order created");
    }
}
