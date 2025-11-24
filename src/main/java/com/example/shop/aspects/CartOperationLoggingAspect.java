package com.example.shop.aspects;

import com.example.shop.model.Product;
import com.example.shop.model.User;
import com.example.shop.query.GetProductQuery;
import com.example.shop.repository.InContextProductQueryRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
public class CartOperationLoggingAspect {

    @Pointcut("execution(* com.example.shop.servlets.CartServlet.doPost(..))")
    public void doCartOperation() {
    }


    @After("doCartOperation()")
    public void afterCartOperation(JoinPoint joinPoint) {
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        ServletContext context = request.getSession().getServletContext();
        InContextProductQueryRepository repo = new InContextProductQueryRepository(context);

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String username = user != null ? user.getUsername() : "Unauthorised user";

        Long productId = Long.parseLong(request.getParameter("productId"));

        Product product = repo.findById(new GetProductQuery(productId));
        if (product == null) {
            System.out.printf("User %s tried to access non-existent product ID: %d%n", username, productId);
            return;
        }

        String productName = product.getName();
        String productPrice = product.getPrice().toString();

        String method = request.getParameter("_method");

        if ("DELETE".equals(method)) {
            System.out.printf("User %s, deleted %s (price: %s)%n", username, productName, productPrice);
        } else {
            System.out.printf("User %s, added %s(price: %s)%n", username, productName, productPrice);
        }
    }
}
