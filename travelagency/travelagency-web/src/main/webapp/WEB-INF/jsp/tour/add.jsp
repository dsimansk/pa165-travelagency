<%-- 
    Document   : add
    Created on : 25.11.2012, 15:26:39
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
        <title><spring:message code="tour.add.title" /></title>        
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" type="text/css" >
        <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
        <script>
            $(function() {
                $( "#startDate" ).datepicker();
                $( "#startDate" ).datepicker( "option", "dateFormat", "dd/mm/yy" );
            });
        </script>
    </head>
    <body>
        <div id="header">       
            <jsp:include page="../menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <form:form method="POST" modelAttribute="addedTour">
                    <fieldset>
                        <legend><h3><spring:message code="tour.add.header" /></h3></legend>
                        <table>
                            <tr>
                                <td>
                                    <form:label for="destination" path="destination">
                                        <spring:message code="tour.destination" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input path="destination" />
                                </td>
                                <td>
                                    <form:errors for="destination" path="destination" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label for="description" path="description">
                                        <spring:message code="tour.description" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input path="description" />
                                </td>
                                <td>
                                    <form:errors for="description" path="description" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label for="startDate" path="startDate">
                                        <spring:message code="tour.startdate" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input id="startDate" path="startDate" />
                                </td>
                                <td>
                                    <form:errors for="startDate" path="startDate" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label for="durationInHours" path="durationInHours">
                                        <spring:message code="tour.duration" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input path="durationInHours" />
                                </td>
                                <td>
                                    <form:errors for="durationInHours" path="durationInHours" cssClass="error" />
                                </td>
                            </tr>
                        </table>
                        <button><spring:message code="action.save" /></button>
                    </fieldset>
                </form:form>
                <a href="${pageContext.request.contextPath}/tour"><spring:message code="tour.list.header" /></a>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="../footer.jsp" />
            </div>
        </div>
    </body>
</html>
