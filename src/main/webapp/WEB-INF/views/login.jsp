<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
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

<form action="login" method="post" onsubmit="return check()">
    user: <input type="text" name="username" id="username"/>
    password:<input type="password" name="password" id="password"/>
    <input type="submit" value="submit"/>
    <br/>
    <label id="msg"></label>
</form>

</body>
</html>
