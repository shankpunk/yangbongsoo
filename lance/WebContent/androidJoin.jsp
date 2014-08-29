<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.it.insert.AndroidInsertController"%>   
<%
	request.setCharacterEncoding("UTF-8");
	
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	String name = request.getParameter("name"); 
	String phone_number = request.getParameter("phone_number");
	String gender = request.getParameter("gender");
	String result = "molla";

	if(id !=null && pass != null && name != null && phone_number != null && gender != null){
		result = AndroidInsertController.androidInsertData(id,pass,name,phone_number,gender);
		System.out.println("체크 :" + result);
	}
	  
	
%>       
<html>
<body>
<%=result %>
</body>
</html>