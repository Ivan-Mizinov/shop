package com.example.shop.servlets;

import com.example.shop.aspects.CartOperationLoggingAspect;
import com.example.shop.command.AddToCartCommand;
import com.example.shop.command.DeleteFromCartCommand;
import com.example.shop.model.Cart;
import com.example.shop.model.User;
import com.example.shop.repository.CartCommandRepository;
import com.example.shop.repository.CartQueryRepository;
import com.example.shop.repository.InContextCartCommandRepository;
import com.example.shop.repository.InContextCartQueryRepository;
import com.example.shop.services.CartCommandService;
import com.example.shop.services.CartQueryService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext context = getServletContext();
        CartQueryRepository repo = new InContextCartQueryRepository(context);
        CartQueryService service = new CartQueryService(repo);
        try {
            Cart cart = service.getCustomerCart(req);
            req.setAttribute("cart", cart.getProducts());
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        } catch (ServletException e) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        if ("DELETE".equals(method)) {
            doDelete(req, resp);
        } else {
            try {
                String userId = getUserIdFromSession(req);
                Long productId = Long.parseLong(req.getParameter("productId"));

                AddToCartCommand command = new AddToCartCommand(userId, productId);
                CartCommandService cartCommandService = getCartCommandService(req);
                cartCommandService.addToCart(command);
                resp.sendRedirect("/shop/catalog.jsp");
            } catch (ServletException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getUserIdFromSession(HttpServletRequest req) throws ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ServletException("User not authenticated");
        }
        return String.valueOf(user.getId());
    }

    private CartCommandService getCartCommandService(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        CartCommandRepository repo = new InContextCartCommandRepository(context);
        return new CartCommandService(repo, context);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userId = getUserIdFromSession(req);
            Long productId = Long.parseLong(req.getParameter("productId"));

            DeleteFromCartCommand command = new DeleteFromCartCommand(userId, productId);
            CartCommandService cartCommandService = getCartCommandService(req);
            cartCommandService.deleteFromCart(command);

            resp.sendRedirect("/shop/cart");
        } catch (Exception e) {
            req.setAttribute("error", "Error while deleting product: " + e.getMessage());
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }
}
