<%-- 
    Document   : delete
    Created on : 24.11.2012, 23:27:05
    Author     : Michal Jurc
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h3><spring:message code="customer.delete.header" /></h3>
<spring:message code="customer.delete.message"/> : <c:out value="${deletedCustomer.name}" />, <c:out value="${deletedCustomer.address}" />, <c:out value="${deletedCustomer.phoneNumber}" /> ?
<button onclick="ShowHideSlide('form')"><spring:message code="action.cancel" /></button>
<button onclick="doAjaxPostDelete('${deletedCustomer.id}')"><spring:message code="action.delete"></spring:message></button>


