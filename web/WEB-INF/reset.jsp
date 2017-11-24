<%-- 
    Document   : reset
    Created on : Nov 22, 2017, 1:34:15 PM
    Author     : 734972
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Week 11 Lab</title>
    </head>
    <body>
        <h1>Reset Password</h1>
        <br>
        <form action="reset" method="post">
            Email Address: <input type="text" name="email">
            <br>
            <br>
            <input type="submit" value="Submit">
        </form>
       ${message}
    </body>
</html>
