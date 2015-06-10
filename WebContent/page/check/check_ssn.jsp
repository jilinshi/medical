<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医保卡信息核对</title>
<link href="../css/table-style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function checkssn(memberId,ds,paperid,membername){
	var url="installSsnInit.action?memberId="+memberId+"&ds="+ds+"&paperid="+paperid+"&membername="+encodeURI(encodeURI(membername));
	window.showModalDialog(url,'',"dialogHeight: 370px; dialogWidth: 720px; center: Yes; resizable: Yes; status: No;");
}
function checkssn1(memberId,ds,paperid,membername){
	var url="viewcheckhistory.action?memberId="+memberId+"&ds="+ds+"&paperid="+paperid+"&membername="+encodeURI(encodeURI(membername));
	window.showModalDialog(url,'',"dialogHeight: 600px; dialogWidth: 800px; center: Yes; resizable: Yes; status: No;");
}
</script>
</head>
<body>
<s:form theme="simple" action="checkSsn" method="post"
	cssStyle="font-size:12px">
选择地区：<s:select value="oid"
		name="oid" list="orgs" listKey="orgid" listValue="orgname"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;	
 查询条件：
  <s:select value="term" name="term"
		list="#{'':'全部','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
		label="查询条件：" listKey="key" listValue="value">
	</s:select>
  操作符：
  <s:select value="operational" name="operational"
		list="#{'=':'等于','like':'相似于'}" label="操作符：" listKey="key"
		listValue="value">
	</s:select>
  查询值：
  <s:textfield name="value"></s:textfield>
	<s:submit value="查询"></s:submit>
</s:form>
<table align="center" width="99%" class="t1" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>身份证号</th>
		<th>来源</th>
		<th>医保卡号1</th>
		<th>医保卡号2</th>
		<th>医保卡号3</th>
		<th>医保卡号4</th>
		<th>救助状态</th>
		<th>再保障状态</th>
		<th>操作</th>
	</tr>
	<s:iterator value="cds">
		<tr>
			<td><s:property value="familyno" /></td>
			<td><s:property value="membername" /></td>
			<td><s:property value="paperid" /></td>
			<td>
				<s:if test="ds==1">城市</s:if>
				<s:elseif test="ds==2">农村</s:elseif>
				<s:else>不详</s:else>
			</td>
			<td><s:property value="ssn" /></td>
			<td><s:property value="ssn1" /></td>
			<td><s:property value="ssn2" /></td>
			<td><s:property value="ssn3" /></td>
			<td>
				<s:if test="assistType==00">
					普通居民
				</s:if>
				<s:elseif test="assistType==11||assistType==10">
					在保户
				</s:elseif>
			</td>
			<td>
				<s:if test="asort==1">
					是
				</s:if>
				<s:else>
					否
				</s:else>
			</td>
			<td><a href="javascript:void(0)" onclick="checkssn('<s:property value='memberId'/>','<s:property value='ds'/>','<s:property value='paperid'/>','<s:property value='membername'/>');">医保卡号核对</a>
			&nbsp;&nbsp;<a href="javascript:void(0)" onclick="checkssn1('<s:property value='memberId'/>','<s:property value='ds'/>','<s:property value='paperid'/>','<s:property value='membername'/>');">查看核对记录</a>
			</td>
		
		</tr>
	</s:iterator>
</table>
<div align="center" style="font-size: 12px"><s:property
	value="toolsmenu" escape="false" /></div>
</body>
</html>
