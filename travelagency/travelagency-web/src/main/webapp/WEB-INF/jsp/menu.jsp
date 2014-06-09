<%-- 
    Document   : menu
    Created on : Nov 28, 2012, 2:11:09 PM
    Author     : styx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="logo">
    <a href="${pageContext.request.contextPath}" >
        <img src="${pageContext.request.contextPath}/resources/design/logo.png" >
    </a>
</div>  

<div class="banner"></div>    

<div id="menu">
    <ul id="menu_ul"">
        <li class="menu_li" ><a href="${pageContext.request.contextPath}/"  ><spring:message code="home" /></a></li>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class="menu_li" ><a href="${pageContext.request.contextPath}/customer/"  ><spring:message code="customers" /></a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class="menu_li" ><a href="${pageContext.request.contextPath}/vacation/"  ><spring:message code="vacations" /></a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li class="menu_li" ><a href="${pageContext.request.contextPath}/tour/"  ><spring:message code="tours" /></a></li>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')">
            <li class="menu_li" ><a href="${pageContext.request.contextPath}/order/" ><spring:message code="orders" /></a></li>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <li class="menu_li" ><a href="${pageContext.request.contextPath}/login" ><spring:message code="login" /></a></li>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <li class="menu_li" ><a href="${pageContext.request.contextPath}/register" ><spring:message code="register" /></a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <li class="menu_li" ><a href="${pageContext.request.contextPath}/logout" ><spring:message code="logout" /></a></li>
        </sec:authorize>
    </ul> 
</div>
