<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>

<table>
    <form action="saveUser" method="post">
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td>姓名:</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>邮箱:</td>
            <td><input type="text" name="email"/></td>
        </tr>
        <tr>
            <td>电话:</td>
            <td><input type="text" name="telephone"/></td>
        </tr>
        <tr>
            <td>出生日期:</td>
            <td><input type="text" name="birthday"/></td>
        </tr>
        <tr>
            <td>性别:</td>
            <td><select name="gender">
                <option value="0">男</option>
                <option value="1">女</option>
            </select></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"/></td>
        </tr>
    </form>
</table>

</body>
</html>
