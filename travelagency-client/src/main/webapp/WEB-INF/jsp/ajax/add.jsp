<%-- 
    Document   : add
    Created on : Dec 15, 2012, 12:21:19 AM
    Author     : David Simansky <d.simansky@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form id="addForm" modelAttribute="addedCustomer">
    <fieldset>
        <legend><h3><spring:message code="customer.add.header" /></h3></legend>
        <table>
            <tr>
                <td>
                    <form:label for="name" path="name">
                        <spring:message code="customer.name" />:
                    </form:label> 
                </td>
                <td>
                    <form:input path="name" class="required"/>
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
                    <form:input path="address" class="required" />
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
                    <form:input path="phoneNumber" class="required" />
                </td>
                <td>
                    <form:errors for="phoneNumber" path="phoneNumber" cssClass="error" />
                </td>
            </tr>
        </table>

    </fieldset>
</form:form>

<button onclick="ShowHideSlide('form')"><spring:message code="action.cancel" /></button>
<button id="addFormSubmit" onclick="doAjaxPost()"><spring:message code="action.save" /></button>