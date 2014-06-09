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
        <title><spring:message code="customer.delete.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="customer.delete.header" /></h3>
                <spring:message code="customer.delete.message"/> : <c:out value="${deletedCustomer.name}" />, <c:out value="${deletedCustomer.address}" />, <c:out value="${deletedCustomer.phoneNumber}" /> ?
                <form:form method="POST">
                    <button><spring:message code="action.delete"></spring:message></button>
                </form:form>
                <p>
                    <a href="${pageContext.request.contextPath}/customer"><spring:message code="customer.list.header" /></a>
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
