<%-- 
    Document   : tourSelect
    Created on : Nov 25, 2012, 10:51:38 PM
    Author     : David Simansky <d.simansky@gmail.com>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="order.list.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="vacation.select.header" /></h3>
                <table>
                    <tr>
                        <td><spring:message code="vacation.destination" /></td>
                        <td><c:out value="${vacation.destination}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="vacation.startdate" /></td>
                        <td><c:out value="${vacation.startDate}" /></td>
                    </tr>    
                    <tr>
                        <td><spring:message code="vacation.enddate" /></td>
                        <td><c:out value="${vacation.endDate}" /></td>
                    </tr>    
                    <tr>    
                        <td><spring:message code="vacation.price" /></td>
                        <td><c:out value="${vacation.price}" /></td>
                    </tr>    
                    <tr>
                        <td><spring:message code="vacation.reserved" /> / <spring:message code="vacation.capacity" /></td>
                        <td><c:out value="${vacation.reserved}" /> / <c:out value="${vacation.maxCapacity}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="vacation.tours" /></td>
                    </tr>
                </table>  
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

                    </table>
                    <button><spring:message code="action.select" /></button>                 
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
