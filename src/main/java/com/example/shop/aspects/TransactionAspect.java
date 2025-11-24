package com.example.shop.aspects;

import com.example.shop.model.Cart;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
public class TransactionAspect {

    @Around("execution(* com.example.shop.services.CartQueryService.*Product(..))")
    public Object manageTransactions(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = getSessionFromArgs(joinPoint.getArgs());
        Cart originalCart = (Cart) session.getAttribute("cart");

        try {
            Object result = joinPoint.proceed();
            session.setAttribute("cart", originalCart);
            return result;
        } catch (Throwable e) {
            if (originalCart != null) {
                session.setAttribute("cart", originalCart);
            } else {
                session.removeAttribute("cart");
            }
            throw e;
        }
    }

    private HttpSession getSessionFromArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                return ((HttpServletRequest) arg).getSession();
            }
        }
        throw new IllegalArgumentException("HttpServletRequest not found in method arguments");
    }
}
