<%-- 
    Document   : home
    Created on : 25.11.2012, 20:29:09
    Author     : Michal Jurc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="home.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >   
    </head>
    <body>
        <div id="header">       
            <jsp:include page="menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3>REST client for Travel Agency</h3>
                <div id="readme">
                    <ul>
                        <li>Remote host's URL is http://localhost:8080/pa165/</li>
                        <li>Remote host should be running, otherwise this application will not function.</li>
                        <li>This application provides simple user interface for administration of tours and customers.</li>
                        <li>This application also provides small demo of user interface done in AJAX.</li>
                    </ul>
                </div>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="footer.jsp" />
            </div>
        </div>

    </body>
</html>
