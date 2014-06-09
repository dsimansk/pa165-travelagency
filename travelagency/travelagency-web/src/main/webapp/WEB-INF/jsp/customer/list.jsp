<%-- 
    Document   : list
    Created on : Nov 25, 2012, 6:27:46 PM
    Author     : David Simansky <d.simansky@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="customer.list.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="customer.list.header" /></h3>
                <table>
                    <tr>
                        <th><spring:message code="customer.name" /></th>
                        <th><spring:message code="customer.address" /></th>
                        <th><spring:message code="customer.phoneNo" /></th>
                        <th><spring:message code="action.edit" /></th>
                        <th><spring:message code="action.delete" /></th>
                    </tr>
                    <c:forEach items="${customers}" var="customer">
                        <tr>
                            <td><c:out value="${customer.name}" /></td>
                            <td><c:out value="${customer.address}" /></td>
                            <td><c:out value="${customer.phoneNumber}" /></td>
                            <td><a href="${pageContext.request.contextPath}/customer/edit/${customer.id}" class="edit"></a></td>
                            <td><a href="${pageContext.request.contextPath}/customer/delete/${customer.id}" class="delete"></a></td>
                        </tr>
                    </c:forEach>
                </table>
                <p>
                    <a href="${pageContext.request.contextPath}/customer/add"><spring:message code="customer.add.header" /></a>
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
