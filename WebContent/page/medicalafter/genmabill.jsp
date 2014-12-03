<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:head theme="simple" />
<link rel="stylesheet" href="../css/table-style.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发放明细统计</title>
</head>
<body>
<table align="center" width="99%" class="t1" border="0" cellpadding="0"
	cellspacing="0">
	<caption style="font-size: 12px">发放明细统计</caption>
	<tr>
		<th>来源</th>
		<th>机构编码</th>
		<th>业务批次</th>
		<th>救助人次</th>
		<th>救助金额</th>
	</tr>
	<s:iterator value="medicalafters" >
		<tr>
			<td><s:property value="ds"/></td>
			<td><s:property value="onNo"/></td>
			<td><s:property value="batchname"/></td>
			<td><s:property value="rc"/></td>
			<td><s:property value="asisstpay"/></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>