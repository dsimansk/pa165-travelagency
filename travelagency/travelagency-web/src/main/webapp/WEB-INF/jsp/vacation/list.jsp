<%-- 
    Document   : listVacations
    Created on : 23.11.2012, 18:52:31
    Author     : Michal Jurc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="vacation.list.title" /></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="vacation.list.header" /></h3>
                <table>
                    <tr>
                        <th><spring:message code="vacation.destination" /></th>
                        <th><spring:message code="vacation.startdate" /></th>
                        <th><spring:message code="vacation.enddate" /></th>
                        <th><spring:message code="vacation.price" /></th>
                        <th><spring:message code="vacation.reserved" /> / <spring:message code="vacation.capacity" /></th>
                        <th><spring:message code="vacation.tours" /></th>
                        <th><spring:message code="action.edit" /></th>
                        <th><spring:message code="action.delete" /></th>
                    </tr>
                    <c:forEach items="${vacations}" var="vacation">
                        <tr>
                            <td><c:out value="${vacation.destination}" /></td>
                            <td><c:out value="${vacation.startDate}" /></td>
                            <td><c:out value="${vacation.endDate}" /></td>
                            <td><c:out value="${vacation.price}" /></td>
                            <td><c:out value="${vacation.reserved}" /> / <c:out value="${vacation.maxCapacity}" /></td>
                            <td><a href="${pageContext.request.contextPath}/vacation/tours/${vacation.id}"><spring:message code="tours" /></td>
                            <td><a href="${pageContext.request.contextPath}/vacation/edit/${vacation.id}" class="edit"></a></td>
                            <td><a href="${pageContext.request.contextPath}/vacation/delete/${vacation.id}" class="delete"></a></td>
                        </tr>
                    </c:forEach>
                </table>
                <p>
                    <a href="${pageContext.request.contextPath}/vacation/add"><spring:message code="vacation.add.header" /></a>
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
