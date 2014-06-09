<%-- 
    Document   : login
    Created on : Jan 17, 2013, 12:53:30 AM
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
    </head>
    <body onload='document.f.j_username.focus();'>
        <div id="header">       
            <jsp:include page="menu.jsp" />
        </div>
        <div id="content">
            <div class="site-width">
                <h3><spring:message code="login.header" /></h3>

                <c:if test="${not empty error}">
                    <div class="errorblock">
                        <spring:message code="login.bad" />
                    </div>
                </c:if>

                <form name='f' action="<c:url value='j_spring_security_check' />"method='POST'>

                    <table>
                        <tr>
                            <td>User:</td>
                            <td>
                                <input type='text' name='j_username' value=''>
                            </td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td>    
                                <input type='password' name='j_password' />
                            </td>
                        </tr>
                    </table>
                    <button type="reset"><spring:message code="reset"/></button>
                    <button type="submit"><spring:message code="login"/></button>

                </form>
            </div>
        </div>

        <div id="footer">
            <div class="site-width">
                <jsp:include page="footer.jsp" />
            </div>
        </div>
    </body>
</html>