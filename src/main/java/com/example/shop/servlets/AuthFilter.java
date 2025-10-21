package com.example.shop.servlets;

import com.example.shop.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/*"})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (uri.contains("/login")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession httpSession = req.getSession(false);

        if (httpSession == null) {
            resp.sendRedirect("/shop/login");
            return;
        }

        User user = (User) httpSession.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/shop/login");
            return;
        }

        if (uri.contains("/add_product") && !user.getRole().equals("admin")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
