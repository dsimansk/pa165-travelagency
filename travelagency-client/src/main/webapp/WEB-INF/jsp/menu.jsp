<%-- 
    Document   : menu
    Created on : Nov 28, 2012, 2:11:09 PM
    Author     : styx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="logo">
    <a href="${pageContext.request.contextPath}" >
        <img src="${pageContext.request.contextPath}/resources/design/logo.png" >
    </a>
</div>  

<div class="banner"></div>    

<div id="menu">
    <ul id="menu_ul">
        <li class="menu_li" ><a href="${pageContext.request.contextPath}/"  ><spring:message code="home" /></a></li>
        <li class="menu_li" ><a href="${pageContext.request.contextPath}/customer/"  ><spring:message code="customers" /></a></li>
        <li class="menu_li" ><a href="${pageContext.request.contextPath}/tour/"  ><spring:message code="tours" /></a></li>
        <li class="menu_li" ><a href="${pageContext.request.contextPath}/ajax/customer/" ><spring:message code="customers" /> - AJAX</a></li>
    </ul> 
</div>
