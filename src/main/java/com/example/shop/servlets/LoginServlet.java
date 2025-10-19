package com.example.shop.servlets;

import com.example.shop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String username = req.getParameter("user");
        String password = req.getParameter("password");

        List<User> users = (List<User>) req.getServletContext().getAttribute("users");
        User user = null;

        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                user = u;
                break;
            }
        }

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("catalog");
        } else {
            resp.sendRedirect("login?error=true");
        }
    }
}
