<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Добавить новый товар</title>
    <style>
        .form-group {
            clear: both;
            text-align: right;
            line-height: 25px;
        }
        label {
            float: left;
            padding-right: 10px;
        }
        .main {
            float: left;
        }
    </style>
</head>
<body>
    <h1>Добавить новый товар</h1>
    <div class="main">
        <form action="/shop/add-product" method="POST">
            <div class="form-group">
                <label for="name">Название товара:</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="form-group">
                <label for="description">Описание:</label>
                <textarea id="description" name="description" rows="3" required></textarea>
            </div>

            <div class="form-group">
                <label for="price">Цена (руб.):</label>
                <input type="number" step="0.01" id="price" name="price" required>
            </div>

            <button type="submit">Добавить товар</button>
        </form>
    </div>

</body>
</html>