<%-- 
    Document   : test
    Created on : Jan 17, 2013, 1:10:13 AM
    Author     : Michal Jurc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <body>
        <h3>Message : ${message}</h3>	
        <h3>Username : ${username}</h3>	
        <h3>Customer id: ${id}</h3>

        <a href="${pageContext.request.contextPath}/logout" > Logout</a>
    </body>
</html>