<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>购物车</title>
</head>
<body>

<jsp:include page="header.jsp"/>

<form action="/user/setProductInOrder" method="post">
    <table>
        <c:forEach var="cartItemView" items="${cartItemViews}">
            <tr>
                <td><input type="checkbox" value="${cartItemView.product.id}|${cartItemView.number}" name="productIds"/>
                </td>
                <td>产品名称：${cartItemView.product.name}</td>
                <td>售价：${cartItemView.product.price}元</td>
                <td>个数：${cartItemView.number}个</td>
                <td><a href="/user/deleteCartProduct?id=${cartItemView.id}">删除</a></td>
            </tr>
        </c:forEach>
    </table>

    <input type="submit" value="提交订单">
</form>

</body>
</html>
