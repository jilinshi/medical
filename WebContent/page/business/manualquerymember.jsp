<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.medical.dto.UserInfoDTO"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	UserInfoDTO user = (UserInfoDTO) session.getAttribute("user");
	String orgid = user.getOrganizationId();
	if(4!=orgid.length()){
		response.sendRedirect("error.jsp?a=1");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>手动报销审批</title>
</head>
<body>
<p align="center" style="font-size: 12px"><br>
<s:form action="manualquerymember" method="post" theme="simple">
社会保险号：<s:textfield name="personDTO.ssn"></s:textfield>
	<br>
	<div align="center"><s:actionerror theme="simple"
		cssStyle="font-size: 12px;color:red" /></div>
	<div align="center"><s:submit value="查询"></s:submit></div>
	<div align="center"><span style="color:red"><s:property value="result"/></span></div>
</s:form></p>
</body>
</html>