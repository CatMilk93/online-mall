<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<script type="text/javascript" src="/js/sha1.js" charset="utf-8"></script>
<script language="JavaScript" charset="utf-8">
    function check() {
        var username = document.getElementById("username").value, password = document.getElementById("password").value;
        if (isEmpty(username)) {
            document.getElementById("msg").innerHTML = "请输入用户名";
            return false;
        } else if (isEmpty(password)) {
            document.getElementById("msg").innerHTML = "请输入密码";
            return false;
        } else {
            document.getElementById("password").value = hex_hmac_sha1(username, password);
            return true;
        }
    }

    function isEmpty(obj){
        return typeof obj === "undefined" || obj == null || obj === "";
    }
</script>

<body>
<table>
    <form action="saveUser" method="post" onsubmit="return check()">
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username" id="username"/></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password" id="password"/></td>
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
        <tr>
            <td><label id="msg"></label></td>
        </tr>
    </form>
</table>

</body>
</html>
