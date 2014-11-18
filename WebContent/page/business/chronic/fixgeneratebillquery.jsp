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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title></title>
</head>
<body>
<s:form action="fixgeneratebillquery" method="post" theme="simple">
查询条件：<s:select name="term" list="#{'':'全部','name':'姓名','ssn':'社会保障号'}"
		listKey="key" listValue="value">
	</s:select>
	查询值：<s:textfield name="value"></s:textfield>
	<s:submit value="查询"></s:submit>
</s:form>
<table align="center" width="90%" class="t1" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>社会保险号</th>
		<th>审批状态</th>
		<th>审批时间</th>
		<th>操作</th>
	</tr>
	<s:iterator value="cas">
		<s:url id="view" action="viewapprovelist">
			<s:param name="chronicApproveDTO.chronicapproveId">
				<s:property value="chronicapproveId" />
			</s:param>
		</s:url>
		<tr>
			<td><s:property value="familyId"></s:property></td>
			<td>
			<s:a href="%{view}" target="_blank">
			<s:property value="name"></s:property>
			</s:a>
			</td>
			<td><s:property value="ssn"></s:property></td>
			<td><s:if test="aprresult1==1">
			街道同意 
			</s:if> <s:if test="aprresult1==2">
			街道不同意 
			</s:if> <s:if test="aprresult2==1">
			>>区县同意>> 
			</s:if> <s:if test="aprresult2==2">
			>>区县不同意>> 
			</s:if> <s:if test="aprresult3==1">
			市级同意 
			</s:if> <s:if test="aprresult3==2">
			市级不同意 
			</s:if>
			 <s:if test="aprresult3==3">
			体检中
			</s:if>
			</td>
			<td><s:date name="aprtime1" format="yyyy-MM-dd" />至<s:date
				name="aprtime3" format="yyyy-MM-dd" /></td>
			<td><s:if test="#session.user.organizationId.length()==4">
				<s:url id="cancel" action="approvechronicmemberinit">
					<s:param name="chronicApproveDTO.chronicapproveId">
						<s:property value="chronicapproveId" />
					</s:param>
					<s:param name="chronicApproveDTO.ssn">
				d
			</s:param>
				</s:url>
				<s:a href="%{cancel}">作废</s:a>
			</s:if>
			<s:if test="#session.user.organizationId.length()-2==aprlevel*2">
				<s:url id="modify" action="approvechronicmemberinit">
					<s:param name="chronicApproveDTO.chronicapproveId">
						<s:property value="chronicapproveId" />
					</s:param>
					<s:param name="chronicApproveDTO.ssn">
				m
			</s:param>
				</s:url>
				<s:a href="%{modify}">修改</s:a>
			</s:if>
			</td>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="6">
		<div align="center"><s:property value="toolsmenu" escape="false" /></div>
		</td>
	</tr>
</table>
</body>
</html>