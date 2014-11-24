<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<s:head theme="simple" />
<sx:head extraLocales="en-us,nl-nl,de-de" />
<base target="_self">
<link rel="stylesheet" href="../css/table-style.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>医后报销录入</title>
</head>
<body style="padding: 10px 10px 10px 10px;">
<table align="center" width="99%" class="t1" border="0" cellpadding="0"
	cellspacing="0">
	<caption style="font-size: 12px">附件信息</caption>
<s:iterator value="medicalafters" >
<tr>
<td width="60px"><s:property value="filename"/></td>
<td >
<img src="<s:property value="filepath"/>"></img>
</td>
</tr>
</s:iterator>
</table>
</body>
</html>