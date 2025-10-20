<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Catalog</title>
    <style>
        .product {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
        }

        .product-name {
            font-size: 1.2em;
            font-weight: bold;
        }

        .product-price {
            color: #e83e8c;
            font-size: 1.1em;
        }
    </style>
</head>
<body>
    <h1>Catalog</h1>

    <div>
        <c:forEach var="product" items="${products}">
            <div class="product">
                <div class="product-name">
                        ${product.name}
                </div>
                <div class="product-description">
                        ${product.description}
                </div>
                <div class="product-price">
                        ${product.price} ₽
                </div>
                <form action="/shop/cart" method="POST" style="margin-top: 10px;">
                    <input type="hidden" name="productId" value="${product.id}">
                    <button type="submit">Добавить в корзину</button>
                </form>
            </div>
        </c:forEach>
    </div>

    <a href="/shop/cart">Перейти в корзину</a>
</body>
</html>