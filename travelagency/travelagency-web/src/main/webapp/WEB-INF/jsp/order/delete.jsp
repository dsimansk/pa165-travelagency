<%-- 
    Document   : delete
    Created on : 24.11.2012, 23:27:05
    Author     : Michal Jurc
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="order.delete.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="order.delete.header" /></h3>
                <spring:message code="order.delete.message"/> : #ID <c:out value="${deletedOrder.id}" />?
                <form:form>
                    <button><spring:message code="action.delete"></spring:message></button>
                </form:form>
                <p>
                    <a href="${pageContext.request.contextPath}/order/"><spring:message code="order.list.header" /></a>
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
