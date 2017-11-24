<%-- 
    Document   : resetNewPassword
    Created on : Nov 23, 2017, 10:31:09 PM
    Author     : 734972
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset New Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <br>
        <form action="reset" method="post">
            <input type="password" name="password">
            <br>
            <br>
            <input type="submit" value="Submit">
            <input type="hidden" name="action" value="newPassword"/>
            <input type="hidden" name="uuid" value="${uuid}"/>
        </form>
       ${message}
    </body>
</html>
