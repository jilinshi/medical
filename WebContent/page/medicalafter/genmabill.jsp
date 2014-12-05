<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:head theme="simple" />
<base target="_self">
<link rel="stylesheet" href="../css/table-style.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>发放明细统计</title>
</head>
<body>
	<s:form action="genmabill.action" method="post">
		<table align="center" width="99%" class="t1" border="0"
			cellpadding="0" cellspacing="0">
			<caption style="font-size: 12px">发放明细统计</caption>
			<tr>
				<th>来源</th>
				<th>机构</th>
				<th>业务批次</th>
				<th>救助人次</th>
				<th>救助金额</th>
			</tr>
			<s:iterator value="medicalafters">
				<tr>
					<td><s:property value="ds" /></td>
					<td><s:property value="approvecontent" /></td>
					<td><s:property value="batchname" /></td>
					<td><s:property value="rc" /></td>
					<td><s:property value="asisstpay" /></td>
				</tr>
			</s:iterator>
		</table>
		<s:if test="result=='结算完毕'">
		<div align="center">
		<s:property value="result"/>
		</div>
		</s:if>
		<s:else>
		<div align="center">
			<button type="submit">生成账单</button>
		</div>
		</s:else>
		
	</s:form>
</body>
</html>