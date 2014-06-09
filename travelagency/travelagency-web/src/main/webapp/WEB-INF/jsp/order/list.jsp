<%-- 
    Document   : list
    Created on : Nov 25, 2012, 9:29:04 PM
    Author     : David Simansky <d.simansky@gmail.com>
--%>
<%-- 
    Document   : list
    Created on : Nov 25, 2012, 6:27:46 PM
    Author     : David Simansky <d.simansky@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
                <h3><spring:message code="order.list.header" /></h3>
                <table>
                    <tr>
                        <th>ID</th>
                        <sec:authorize access="hasRole('ROLE_ADMIN')"><th><spring:message code="customer" /></th></sec:authorize>
                        <th><spring:message code="customer.name" /></th>
                        <th><spring:message code="vacation" /></th>
                        <th><spring:message code="tours" /></th>
                        <th><spring:message code="action.show"/></th>
                        <sec:authorize access="hasRole('ROLE_ADMIN')"><th><spring:message code="action.edit"/></th></sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')"><th><spring:message code="action.delete" /></th></sec:authorize>
                    </tr>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td><c:out value="${order.id}" /></td>
                            <sec:authorize access="hasRole('ROLE_ADMIN')"><td><a href="${pageContext.request.contextPath}/customer/get/${order.customer.id}" class="detail"></a></td></sec:authorize>
                            <td><c:out value="${order.customer.name}"/></td>
                            <td><c:out value="${order.vacation.destination}"/></td>
                            <c:choose>
                                <c:when test="${empty order.tours}">
                                    <td><spring:message code="order.notours"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${order.tours}"/></td>
                                </c:otherwise>
                            </c:choose>     
                            <td><a href="${pageContext.request.contextPath}/order/get/${order.id}" class="detail"></a></td>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td><a href="${pageContext.request.contextPath}/order/edit/${order.id}" class="edit"></a></td>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td><a href="${pageContext.request.contextPath}/order/delete/${order.id}" class="delete"></a></td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="../footer.jsp" />
            </div>
        </div>

    </body>
</html>
