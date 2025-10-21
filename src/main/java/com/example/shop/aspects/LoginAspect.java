package com.example.shop.aspects;

import com.example.shop.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
public class LoginAspect {

    @Pointcut("execution(* com.example.shop.servlets.LoginServlet.doPost(..))")
    public void doLogging() {
    }

    @Pointcut("execution(* com.example.shop.servlets.AddProductServlet.doPost(..))")
    public void doAddProduct() {
    }

    @Before("doLogging()")
    public void beforeLogging(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        System.out.printf("Before Login. User request info %s%n", request.getParameter("user"));
    }

    @After("doLogging()")
    public void afterLogging(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String username = user != null ? user.getUsername() : "null";
        System.out.printf("After Login. User session info %s%n ", username);
    }

    @Before("doAddProduct()")
    public void beforeAddProduct(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");
        String username = user != null ? user.getUsername() : "Unauthorised user";

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        System.out.printf("User %s add product with attributes: %s %s %s%n ", username, name, description, price);
    }

    @After("doAddProduct()")
    public void afterAddProduct(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");
        String username = user != null ? user.getUsername() : "null";

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        System.out.printf("User %s added product with attributes: %s %s %s%n ", username, name, description, price);
    }
}
