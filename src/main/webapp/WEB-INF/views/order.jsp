<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的订单</title>
</head>
<body>

<jsp:include page="header.jsp"/>


<table>
    <c:forEach var="orderView" items="${orderView}">
        <tr>
            <td>订单ID：${orderView.id}</td>
            <td>下单时间：${orderView.createTime}</td>
            <td>订单状态：${orderView.status.desc}</td>
            <td>订单总金额：${oid2TotalPriceMap[orderView.id]}</td>
        </tr>
        <c:forEach var="orderitem" items="${orderView.items}">
            <tr>
                <td>商品名称：${orderitem.product.name}</td>
                <td>售价：${orderitem.product.price}元</td>
                <td>购买个数：${orderitem.number}个</td>
                <td>商品总金额：${orderitem.product.price * orderitem.number}</td>
            </tr>
        </c:forEach>
    </c:forEach>
</table>

</body>
</html>
