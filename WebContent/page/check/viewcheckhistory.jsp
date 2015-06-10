<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table align="center" width="99%" class="t1" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<th>发送时间</th>
			<th>返回时间</th>
			<th>返回内容</th>

		</tr>
		<s:iterator value="ybcds">
			<tr>
				<td><s:property value="ssn1" /></td>
				<td><s:property value="ssn2" /></td>
				<td><s:property value="message" /></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>