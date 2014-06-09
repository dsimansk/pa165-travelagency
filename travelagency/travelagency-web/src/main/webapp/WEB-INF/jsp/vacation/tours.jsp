<%-- 
    Document   : listbyvacation
    Created on : 25.11.2012, 15:27:42
    Author     : Michal Jurc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="vacation.select.title" /></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <table>
                    <tr>
                        <th><spring:message code="tour.destination" /></th>
                        <th><spring:message code="tour.startdate" /></th>
                        <th><spring:message code="tour.duration" /></th>
                        <th><spring:message code="action.edit" /></th>
                        <th><spring:message code="action.delete" /></th>
                    </tr>
                    <c:forEach items="${tours}" var="tour">
                        <tr>
                            <td><c:out value="${tour.destination}" /></td>
                            <td><c:out value="${tour.startDate}" /></td>
                            <td><c:out value="${tour.durationInHours}" /></td>
                            <td><a href="${pageContext.request.contextPath}/tour/edit/${tour.id}"><spring:message code="action.edit" /></a></td>
                            <td><a href="${pageContext.request.contextPath}/tour/delete/${tour.id}"><spring:message code="action.delete" /></a></td>
                        </tr>
                    </c:forEach>
                </table>
                <a href="${pageContext.request.contextPath}/vacation/tours/${vacationId}/select"><spring:message code="action.select" /></a>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="../footer.jsp" />
            </div>
        </div>
    </body>
</html>
