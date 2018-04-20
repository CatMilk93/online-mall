<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>万能小店</title>
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

<a href="indexView?page=${nextPage}"><h5>查看更多分类</h5></a>

<h4>热门商品</h4>

<c:forEach var="hotProduct" items="${hotProducts}">
    <td><a href="productDetails?productId=${hotProduct.id}">${hotProduct.name}</a></td>
</c:forEach>

<h4>最新商品</h4>
<c:forEach var="newProduct" items="${newProducts}">
    <td><a href="productDetails?productId=${newProduct.id}">${newProduct.name}</a></td>
</c:forEach>

</body>
</html>