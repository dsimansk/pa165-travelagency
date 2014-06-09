<%-- 
    Document   : list
    Created on : Nov 25, 2012, 6:27:46 PM
    Author     : David Simansky <d.simansky@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<h3><spring:message code="customer.list.header" /></h3>
<button  onclick="loadFormAdd()"><spring:message code="customer.add.header" /></button>
<table>
    <tr>
        <th><spring:message code="customer.name" /></th>
        <th><spring:message code="customer.address" /></th>
        <th><spring:message code="customer.phoneNo" /></th>
        <th><spring:message code="action.edit" /></th>
        <th><spring:message code="action.delete" /></th>
    </tr>
    <c:forEach items="${customers}" var="customer">
        <tr>
            <td><c:out value="${customer.name}" /></td>
            <td><c:out value="${customer.address}" /></td>
            <td><c:out value="${customer.phoneNumber}" /></td>
            <td><a href="#" onclick="loadFormEdit('${customer.id}')" class="edit"></a></td>
            <td><a href="#" onclick="loadFormDelete('${customer.id}')" class="delete"></a></td>
        </tr>
    </c:forEach>
</table>
 