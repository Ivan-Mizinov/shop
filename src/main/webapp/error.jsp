<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка доступа</title>
</head>
<body>
<script>
    alert('<%= request.getAttribute("error") %>');
    history.back();
</script>
</body>
</html>

