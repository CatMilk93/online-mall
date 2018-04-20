<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<table>
    <tr>
        <td>分类</td>
        <c:forEach var="category" items="${categories}">
            <td><a href="categoryList?id=${category.id}">${category.name}</a></td>
        </c:forEach>
    </tr>
</table>
<a href="categoryList?page=${nextPage}"><h5>查看更多分类</h5></a>

<tr>
    <td>产品名称：${product.name}</td>
    <td>售价：${product.price}</td>
    <td>上架时间：${product.createTime}</td>
</tr>

<form action="/user/setCartProduct" method="post">
    购买数量：<input type="text" name="number">
    <input type="hidden" name="productId" value="${productId}">
    <input type="submit" name="setCart" value="加入到购物车">
</form>

</body>
</html>