<%-- 
    Document   : detail
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
        <title><spring:message code="tour.detail.title" /></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="tour.detail.header" /></h3>
                ${testString}
                <table>
                    <tr>
                        <td><spring:message code="tour.destination" />: </td>
                        <td><c:out value="${tour.destination}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="tour.description" />: </td>
                        <td><c:out value="${tour.description}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="tour.startdate" />: </td>
                        <td><c:out value="${tour.startDate}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="tour.duration" />: </td>
                        <td><c:out value="${tour.durationInHours}" /></td>
                    </tr>
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/tour/edit/${tour.id}"><spring:message code="action.edit" /></a></td>
                    </tr>
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/tour/delete/${tour.id}"><spring:message code="action.delete" /></a></td>
                    </tr>
                </table>
                <p>
                    <a href="${pageContext.request.contextPath}/tour"><spring:message code="tour.list.header" /></a>
                    <a href="${pageContext.request.contextPath}/tour/add"><spring:message code="tour.add.header" /></a>
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
