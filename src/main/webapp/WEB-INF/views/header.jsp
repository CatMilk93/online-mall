<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<c:choose>
    <c:when test="${empty username}">
        <a href="/login">登录</a>
        <a href="/register">注册</a>
    </c:when>
    <c:otherwise>
        ${username}
        <a href="/user/logout">退出</a>
    </c:otherwise>
</c:choose>
<a href="/user/product/cartItem">购物车</a>
<a href="/user/order">我的订单</a>


<h2><a href="/indexView">万能小店</a></h2>