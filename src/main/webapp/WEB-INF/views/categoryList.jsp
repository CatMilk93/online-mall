<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>产品分类</title>
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


<c:forEach var="productList" items="${productList}">
    <tr>
        <td>产品名称：<a href="productDetails?productId=${productList.id}">${productList.name}</a></td>
        <td>售价：${productList.price}</td>
        <td>上架时间：${productList.createTime}</td>
    </tr>
    <br/>

</c:forEach>
<a href="categoryList?listPage=${listPage}&id=${categoryId}"><h5>下一页</h5></a>

</body>
</html>
