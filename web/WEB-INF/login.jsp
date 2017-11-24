<%-- 
    Document   : login
    Created on : 10-Nov-2017, 8:26:48 AM
    Author     : awarsyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>NotesKeepr Login</h1>
        <form action="login" method="post">
            username: <input type="text" name="username"><br>
            password: <input type="password" name="password"><br>
            <input type="submit" value="Login">
        </form>
        <br>
        <a href="reset">Password Reset</a>
        ${errormessage}
    </body>
</html>
