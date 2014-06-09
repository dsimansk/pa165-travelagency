<%-- 
    Document   : success
    Created on : 25.11.2012, 15:25:54
    Author     : Michal Jurc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="tour.message.title" /></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="${successMessage}" /></h3>
                <a href="${pageContext.request.contextPath}/tour/"><spring:message code="tour.list.header" /></a>
                <c:if test="${successMessage eq 'add.success'}">
                    <a href="${pageContext.request.contextPath}/tour/add"><spring:message code="tour.add.header" /></a>
                </c:if>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="../footer.jsp" />
            </div>
        </div>
    </body>
</html>
