<%-- 
    Document   : index
    Created on : Dec 14, 2012, 8:36:11 PM
    Author     : David Simansky <d.simansky@gmail.com>
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
        <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.10.0/jquery.validate.min.js"></script>
        <script type="text/javascript">
            getAll();
            function doAjaxPost() {
                clearInfo();
                var name = $('#name').val();
                var address = $('#address').val();
                var number = $('#phoneNumber').val();
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/ajax/customer/",
                    data: "name=" + name + "&address=" + address + "&phoneNumber=" +number,
                    success: function(response){
                        // we have the response
                        //loadFormAdd();
                        if (response.indexOf('Error') > -1) {
                            $('#error').html(response);
                            $('#error').slideDown(500);
                        } else {
                            $('#message').html(response);
                            $('#message').slideDown(500);
                            cancelForm();
                        }
                        getAll();
                    },
                    error: function(e){
                        $('#error').html(e);
                        $('#error').slideDown(500);
                    }
                });
            }
            function doAjaxPostEdit(id) {
                // get the form values
                clearInfo();
                var name = $('#name').val();
                var address = $('#address').val();
                var number = $('#phoneNumber').val();
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/ajax/customer/edit/"+id,
                    data: "name=" + name + "&address=" + address + "&phoneNumber=" +number,
                    success: function(response){
                        // we have the response
                        if (response.indexOf('Error') > -1) {
                            $('#error').html(response);
                            $('#error').slideDown(500);
                        } else {
                            $('#message').html(response);
                            $('#message').slideDown(500);
                            cancelForm();
                        }
                        getAll();
                    },
                    error: function(e){
                        $('#error').html(e);
                        $('#error').slideDown(300);
                    }
                });
            }
            function doAjaxPostDelete(id) {
                // get the form values
                clearInfo();
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/ajax/customer/delete/"+id,
                    success: function(response){
                        // we have the response
                        if (response.indexOf('Error') > -1) {
                            $('#error').html(response);
                            $('#error').slideDown(500);
                        } else {
                            $('#message').html(response);
                            $('#message').slideDown(500);
                            cancelForm();
                        }
                        getAll();
                    },
                    error: function(e){
                        $('#error').html(e);
                        $('#error').slideDown(300);
                    }
                });
            }
            function getAll() {
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/ajax/customer/all",
                    success: function(response){
                        // we have the response
                        if (response.indexOf('Error') > -1) {
                            $('#error').html(response);
                            $('#error').slideDown(300);
                        } else {
                            $('#list').html(response);
                        }
                        
                    },
                    error: function(e){
                        $('#error').html(response);
                        $('#error').slideDown(500);
                    }
                });
            }
            function ShowHideSlide(id) {
                if($('#'+id).is(":visible")) {
                    $('#'+id).slideUp(500);
                } else {
                    //slideDown clicked div, hide/slide up all others and changes div class to have right arrow image next to it
                    $('#'+id).slideDown(500);
                }
            }
            function loadFormEdit(id) {
                cancelForm();
                clearInfo();
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/ajax/customer/edit/"+id,
                    success: function(response){
                        // we have the response
                        $('#form').html(response);
                        $('#form').slideDown(500);
                    },
                    error: function(e){
                        $('#error').html(response);
                    }
                });
            }
            function loadFormAdd() {
                cancelForm();
                clearInfo();
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/ajax/customer/add/",
                    success: function(response){
                        // we have the response
                        $('#form').html(response);
                        $('#form').slideDown(500);
                    },
                    error: function(e){
                        $('#error').html(response);
                    }
                });
            }
            function loadFormDelete(id) {
                cancelForm();
                clearInfo();
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/ajax/customer/delete/"+id,
                    success: function(response){
                        // we have the response
                        $('#form').html(response);
                        $('#form').slideDown(500);
                    },
                    error: function(e){
                        $('#error').html(response);
                    }
                });
            }
            function cancelForm(){
                $('#form').find("input[type=text], textarea").val("");
                $('#form').slideUp(300);
            }
            function clearInfo() {
                $('#message').slideUp(300);
                $('#error').slideUp(300);
            }
            $(document).ready(
            function(response){
                // we have the response
                if (response.indexOf('Error') > -1) {
                    $('#error').html(response);
                    $('#error').slideDown(500);
                } else {
                    $('#message').html(response);
                    $('#message').slideDown(500);
                    cancelForm();
                }
            }); 
        </script>
</head>
<body>
    <div id="header">       
        <jsp:include page="../menu.jsp" />
    </div>
    <div id="content">
        <div class="site-width">
            <div id="error" style="display:none"></div>
            <div id="message" style="display:none"></div>
            <div id="form" style="display:none"></div>
            <div class="clearfix"></div>
            <div id="list" style="margin-top: 20px">

            </div>
        </div>
    </div>
    <div id="footer">
        <div class="site-width">
            <jsp:include page="../footer.jsp" />
        </div>
    </div> 
</body>
</html>