<%-- 
    Document   : home
    Created on : 25.11.2012, 20:29:09
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
        <title><spring:message code="home.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >  
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script>
        <script>
            $(function() {
                var destinations = ${vacationDestinations};
                $( "#vacationSearch" ).autocomplete({
                    source: destinations
                });
            });
        </script>
    </head>
    <body>
        <div id="header">       
            <jsp:include page="menu.jsp" />
        </div>
        <div id="search">
            <div class="site-width">
                <table>
                    <tr>
                        <td>
                            <form:form method="POST" modelAttribute="searchForm">
                                <form:label for="vacationSearch" path="value"><spring:message code="vacation.destination" />:</form:label>
                                <form:input path="value" id="vacationSearch"/>
                                <button><spring:message code="action.submit" /></button>
                            </form:form>
                        </td>
                    </tr>
                </table>
            </div>
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
                        <th><spring:message code="action.order" /></th>
                    </tr>
                    <c:forEach items="${vacations}" var="vacation">
                        <tr>
                            <td><c:out value="${vacation.destination}" /></td>
                            <td><c:out value="${vacation.startDate}" /></td>
                            <td><c:out value="${vacation.endDate}" /></td>
                            <td><c:out value="${vacation.price}" /></td>
                            <td><c:out value="${vacation.reserved}" /> / <c:out value="${vacation.maxCapacity}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${vacation.reserved == vacation.maxCapacity}">
                                        ---
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/order/vacation/${vacation.id}/1"><spring:message code="action.order" /></a>
                                    </c:otherwise>
                                </c:choose>                        
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="footer.jsp" />
            </div>
        </div>

    </body>
</html>
