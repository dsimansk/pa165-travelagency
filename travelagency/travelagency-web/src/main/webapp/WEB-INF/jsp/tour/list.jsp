<%-- 
    Document   : list
    Created on : 25.11.2012, 15:26:28
    Author     : Michal Jurc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="tour.list.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="tour.list.header" /></h3>
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
                            <td><a href="${pageContext.request.contextPath}/tour/edit/${tour.id}" class="edit"></a></td>
                            <td><a href="${pageContext.request.contextPath}/tour/delete/${tour.id}" class="delete"></a></td>
                        </tr>
                    </c:forEach>
                </table>
                <p>
                    <a href="${pageContext.request.contextPath}/tour/add/"><spring:message code="tour.add.header" /></a>
                </p>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="../footer.jsp" />
            </div>
        </div>
    </body>
</html>