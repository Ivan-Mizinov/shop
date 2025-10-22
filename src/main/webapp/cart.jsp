<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Корзина покупок</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .total {
            font-weight: bold;
            text-align: right;
        }
    </style>
</head>
<body>
<h1>Корзина покупок</h1>

<c:if test="${empty cart || cart.size() == 0}">
    <p>Ваша корзина пуста</p>
</c:if>

<c:if test="${not empty cart}">
    <table>
        <thead>
        <tr>
            <th>Название</th>
            <th>Описание</th>
            <th>Цена (руб.)</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cart}" var="product">
            <tr>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>${product.priceAmount} ₽</td>
                <td>
                    <form method="post" action="/shop/cart">
                        <input type="hidden" name="_method" value="DELETE">
                        <input type="hidden" name="productId" value="${product.id}">
                        <button type="submit">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </form>
    <div class="total">
        Общая сумма:
        <c:set var="total" value="0"/>
        <c:forEach items="${cart}" var="product">
            <c:set var="total" value="${total + product.priceAmount}"/>
        </c:forEach>
            ${total} руб.
    </div>
</c:if>

<a href="/shop/catalog">Вернуться в каталог</a>

</body>
</html>