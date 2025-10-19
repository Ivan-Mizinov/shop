<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="/shop/login" method="POST" >
        Логин: <input type="text" name="user" />
        <br />
        Пароль: <input type="password" name="password" />
        <input type="submit" value="Войти" />
    </form>
</body>
</html>
