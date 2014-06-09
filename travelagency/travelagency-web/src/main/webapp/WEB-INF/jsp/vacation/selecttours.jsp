<%-- 
    Document   : addtours
    Created on : 25.11.2012, 18:34:27
    Author     : Michal Jurc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
                <h3><spring:message code="vacation.select.header" /></h3>
                <form:form method="POST" modelAttribute="selectedTours">
                    <table>
                        <tr>
                            <th><spring:message code="tour.destination" /></th>
                            <th><spring:message code="tour.startdate" /></th>
                            <th><spring:message code="tour.duration" /></th>
                            <th><spring:message code="action.select" /></th>
                        </tr>
                        <c:forEach items="${tours}" var="tour">
                            <tr>
                                <td><c:out value="${tour.destination}" /></td>
                                <td><c:out value="${tour.startDate}" /></td>
                                <td><c:out value="${tour.durationInHours}" /></td>
                                <td><form:checkbox path="selectedTourIds" value="${tour.id}" /></td>
                            </tr>
                        </c:forEach>
                        <button><spring:message code="action.select" /></button>
                    </table>
                </form:form>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="../footer.jsp" />
            </div>
        </div>
    </body>
</html>
