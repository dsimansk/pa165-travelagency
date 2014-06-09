<%-- 
    Document   : edit
    Created on : Nov 25, 2012, 7:26:37 PM
    Author     : David Simansky <d.simansky@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!--script>
    $(document).ready(function(){
        $("#editForm").validate({
            submitHandler: function(form) {
                doAjaxPostEdit('${editedCustomer.id}');
            }
        });
    });
</script-->
<form:form id="editForm" modelAttribute="editedCustomer">
    <fieldset>
        <legend><h3><spring:message code="customer.edit.header" /></h3></legend>
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
                    <form:input path="address" class="required"/>
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
<button onclick="doAjaxPostEdit('${editedCustomer.id}')"><spring:message code="action.save" /></button>