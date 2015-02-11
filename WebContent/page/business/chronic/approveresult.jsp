<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.medical.common.FileHandle"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			String a=request.getScheme() + "://"
			+ request.getServerName()+"/pic";
			String f =FileHandle.getInstance().webpath1;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>慢性病审批</title>
<script type="text/javascript">
 function printhr(id){
	 window.open("<%=basePath%>page/business/printhr.action?chronicApproveDTO.chronicapproveId="+id,null,
	 "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
	 	 
		}
</script>
</head>
<body>
<br />
<table align="center" width="90%" class="t1" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>姓名</th>
		<td><s:property value="chronicApproveDTO.name" /></td>
		<th>社会保险号</th>
		<td><s:property value="chronicApproveDTO.ssn" /></td>
	</tr>
	<tr>
		<th>身份证号</th>
		<td><s:property value="chronicApproveDTO.paperid" /></td>
		<th>出生日期</th>
		<td><s:date name="chronicApproveDTO.birthday" format="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<th>性别</th>
		<td><s:property value="chronicApproveDTO.sex" /></td>
		<th>与户主关系</th>
		<td><s:property value="chronicApproveDTO.relmaster" /></td>
	</tr>
	<tr>
		<th>户主姓名</th>
		<td><s:property value="chronicApproveDTO.masterName" /></td>
		<th>家庭地址</th>
		<td><s:property value="chronicApproveDTO.rpraddress" /></td>
	</tr>
	<tr>
		<th>救助病种</th>
		<td colspan="3"><s:property value="chronicApproveDTO.linkmode" />
		</td>
	</tr>
	<tr>
		<th>救助病种</th>
		<td colspan="3"><s:property value="chronicApproveDTO.entityval" />
		</td>
	</tr>
	<tr>
		<th>拟救助金额</th>
		<td colspan="3"><s:property value="chronicApproveDTO.p_salmoney" />&nbsp;元</td>
	</tr>
	<tr>
		<th colspan="4">审批详细信息</th>
	</tr>
	<s:if test="#session.user.organizationId.length()<=8">
		<tr>
			<td>街道审批:</td>
			<td><s:if test="chronicApproveDTO.aprresult1==1">
			同意
			</s:if> <s:if test="chronicApproveDTO.aprresult1==2">
			不同意
			</s:if></td>
			<td><s:property value="chronicApproveDTO.apridea1" /></td>
			<td><s:date name="chronicApproveDTO.aprtime1"
				format="yyyy-MM-dd" /></td>
		</tr>
	</s:if>
	<s:if test="#session.user.organizationId.length()<=6">
		<tr>
			<td>区县审批:</td>
			<td><s:if test="chronicApproveDTO.aprresult2==1">
			同意
			</s:if> <s:if test="chronicApproveDTO.aprresult2==2">
			不同意
			</s:if></td>
			<td><s:property value="chronicApproveDTO.apridea2" /></td>
			<td><s:date name="chronicApproveDTO.aprtime2"
				format="yyyy-MM-dd" /></td>
		</tr>
	</s:if>
	<s:if test="#session.user.organizationId.length()<=4">
		<tr>
			<td>市级审批:</td>
			<td><s:if test="chronicApproveDTO.aprresult3==1">
			同意
			</s:if> <s:if test="chronicApproveDTO.aprresult3==2">
			不同意
			</s:if> <s:if test="chronicApproveDTO.aprresult3==3">
			体检中
			</s:if></td>
			<td><s:property value="chronicApproveDTO.apridea3" /></td>
			<td><s:date name="chronicApproveDTO.aprtime3"
				format="yyyy-MM-dd" /></td>
		</tr>
	</s:if>
	<tr>
		<th colspan="4">附件</th>
	</tr>
	<tr>
		<td colspan="4"><s:iterator value="hrs">
			<a href="<%=a%>/<%=f%><s:property value="value"/>" target="_blank"><s:property
				value="key" /></a>
		</s:iterator></td>
	</tr>
	<tr>
		<td colspan="4">
		<s:if test="#session.user.organizationId.length()==4">
		<s:if test="chronicApproveDTO.aprresult3==3">
			<button type="button"
				onclick="printhr(<s:property value="chronicApproveDTO.chronicapproveId" />)">打印</button>
		</s:if>
		</s:if>
		<button type="button"
			onclick="window.close();">关闭</button>
		</td>
	</tr>
</table>
</body>
</html>