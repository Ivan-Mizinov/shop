package com.example.shop.servlets;

import com.example.shop.command.AddOrderCommand;
import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import com.example.shop.repository.CartQueryRepository;
import com.example.shop.repository.InContextCartQueryRepository;
import com.example.shop.services.CartQueryService;
import com.example.shop.services.OrderService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/order/create")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String userId = req.getParameter("userId");
        ServletContext context = req.getServletContext();
        CartQueryRepository repo = new InContextCartQueryRepository(context);
        CartQueryService service = new CartQueryService(repo);
        Cart cart = service.getCustomerCart(req);

        List<String> productsIds = cart.getProducts().stream()
                .map(Product::getId)
                .map(String::valueOf)
                .collect(Collectors.toList());

        OrderService orderService = new OrderService(context);

        AddOrderCommand command = new AddOrderCommand();
        command.setUserId(userId);
        command.setProducts(productsIds);
        orderService.createOrder(command);
        resp.getWriter().println("Order created");
    }
}
