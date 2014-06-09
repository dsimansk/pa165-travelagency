<%-- 
    Document   : registration
    Created on : 18.1.2013, 22:27:36
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
        <title><spring:message code="customer.add.title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/design/favicon.png" >  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" >
    </head>
    <body>
        <div id="header">       
            <jsp:include page="menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">        
                <form:form method="POST" modelAttribute="registration">
                    <fieldset>
                        <legend><h3><spring:message code="registration.header" /></h3></legend>
                        <table>
                            <tr>
                                <td>
                                    <form:label for="name" path="name">
                                        <spring:message code="customer.name" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input path="name" />
                                </td>
                                <td>
                                    <form:errors for="name" path="name" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label for="address" path="address">
                                        <spring:message code="customer.address" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input path="address" />
                                </td>
                                <td>
                                    <form:errors for="address" path="address" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label for="phoneNumber" path="phoneNumber">
                                        <spring:message code="customer.phoneNo" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input path="phoneNumber" />
                                </td>
                                <td>
                                    <form:errors for="phoneNumber" path="phoneNumber" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label for="username" path="username">
                                        <spring:message code="registration.username" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:input path="username" />
                                </td>
                                <td>
                                    <form:errors for="username" path="username" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <form:label for="password" path="password">
                                        <spring:message code="registration.password" />:
                                    </form:label> 
                                </td>
                                <td>
                                    <form:password path="password" />
                                </td>
                                <td>
                                    <form:errors for="password" path="password" cssClass="error" />
                                </td>
                            </tr>
                        </table>
                        <button><spring:message code="action.save" /></button>
                    </fieldset>
                </form:form>
                <a href="${pageContext.request.contextPath}/"><spring:message code="customer.list.header" /></a>

            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="footer.jsp" />
            </div>
        </div>
    </body>
</html>
