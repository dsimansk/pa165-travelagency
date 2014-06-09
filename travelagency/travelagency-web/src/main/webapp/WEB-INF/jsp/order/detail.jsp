<%-- 
    Document   : detail
    Created on : Nov 25, 2012, 9:56:27 PM
    Author     : David Simansky <d.simansky@gmail.com>
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="order.detail.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="order.detail.header" /></h3>
                <h4><spring:message code="customer.detail.header" /></h4>
                <table>
                    <tr>
                        <td><spring:message code="customer.name" />: </td>
                        <td><c:out value="${customer.name}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="customer.address" />: </td>
                        <td><c:out value="${customer.address}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="customer.phoneNo" />: </td>
                        <td><c:out value="${customer.phoneNumber}" /></td>
                    </tr>
                </table>
                <h4><spring:message code="vacation.detail.header" /></h4>
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
                </table>  
                <h4><td><spring:message code="tour.list.header" /></h4>                   
                <table>
                    <tr>
                        <th><spring:message code="tour.destination" /></th>
                        <th><spring:message code="tour.startdate" /></th>
                        <th><spring:message code="tour.duration" /></th>
                    </tr>
                    <c:forEach items="${tours}" var="tour">
                        <tr>
                            <td><c:out value="${tour.destination}" /></td>
                            <td><c:out value="${tour.startDate}" /></td>
                            <td><c:out value="${tour.durationInHours}" /></td>
                        </tr>
                    </c:forEach>

                </table>
                <a href="${pageContext.request.contextPath}/order/"><spring:message code="order.list.header" /></a>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="../footer.jsp" />
            </div>
        </div>

    </body>
</html>
