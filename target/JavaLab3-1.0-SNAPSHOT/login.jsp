<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Lab5</title>
</head>
<body>
<p>Вход</p>
<form action="login" method="POST">
    <table>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="pass"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Зарегистрироваться"></td>
        </tr>
    </table>
</form>
<a href="registration">Зарегистрироваться</a>
</body>
</html>