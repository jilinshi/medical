<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询页面</title>
<s:head theme="simple" />
<link rel="stylesheet" href="../css/table-style.css" type="text/css"></link>
<link type="text/css" href="../js/themes/base/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="../js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#opertime1").datepicker({changeYear: true ,changeMonth: true});
		$("#opertime2").datepicker({changeYear: true ,changeMonth: true});
	});
	function cancel(id){
		 var arr = {
			"disasterafterDTO.daId" : id
		}; 
		$.ajax( {
			type : "post",
			url : "page/disaster/cancel.action",
			data : arr,
			timeout : 20000,
			error : function() {
				alert('服务器错误');
			},
			async : false,
			dataType : 'json',
			success : function(json) {
				json = eval('(' + json + ')');
				var u = json['u'];
				if(u==1){
					alert("成功！");
				}else if(u==0){
					alert("失败！");
				}
			}
		}); 
	}
</script>
</head>
<body>
<s:form theme="simple" action="querydisaster" method="post"
	cssStyle="font-size:12px">&nbsp;&nbsp;
	<%-- 选择地区：<s:select
		name="oid" list="orgs" listKey="orgid" listValue="orgname"></s:select>&nbsp;&nbsp; --%>
	查询条件：
	<s:select value="term" name="term"
		list="#{'':'全部','MEMBERNAME':'姓名','PAPERID':'身份证号码'}"
		label="查询条件：" listKey="key" listValue="value">
	</s:select>&nbsp;&nbsp;
	操作符：
	<s:select value="operational" name="operational"
		list="#{'=':'等于','like':'相似于'}" label="操作符：" listKey="key"
		listValue="value">
	</s:select>&nbsp;&nbsp;
	查询值：
	<s:textfield name="value" size="16"></s:textfield>
	&nbsp;&nbsp;救助时间： 
		<input size="10" type="text" readonly="readonly" id="opertime1" name="opertime1"
		value="<s:date name="opertime1" format="yyyy-MM-dd"/>" />至
		<input size="10" type="text" readonly="readonly" id="opertime2" name="opertime2"
		value="<s:date name="opertime2" format="yyyy-MM-dd"/>" />
		&nbsp;&nbsp;审批：
		<s:select list="#{'':'全部','1':'同意救助','0':'不同意救助','-1':'作废'}" name="approveresult"></s:select>
	<s:submit value="查询"></s:submit>&nbsp;&nbsp;
	<button onclick="window.open('../downloadExcel.action?type=9')">导出excel</button>
</s:form>
<table align="center" width="99%" class="t1" border="0" cellpadding="0"
	cellspacing="0">
	<caption style="font-size: 12px">业务信息</caption>
	<tr>
		<th width="5%">姓名</th>
		<th width="12%">身份证号码</th>
		<th width="6%">业务类别</th>
		<th width="6%">保险类型</th>
		<th width="6%">总费用</th>
		<th width="6%">报销金额（医保/农合）</th>
		<th width="6%">不参与补偿金额</th>
		<th width="6%">起付线</th>
		<th width="6%">医院补助</th>
		<th width="6%">商业保险</th>
		<th width="6%">大病保险金额</th>
		<th width="6%">救助金额</th>
		<th width="8%">救助时间</th>
		<th width="7%">审批状态</th>
		<th align="center">操作</th>
	</tr>
	<s:iterator value="disasterafterDTOs" >
		<tr>
			<td width="5%"><s:property value="membername"/></td>
			<td width="12%"><s:property value="paperid"/></td>
			<td width="6%">
				<s:if test="medicaltype==1">住院</s:if>
				<s:elseif test="medicaltype==2">门诊</s:elseif>
			</td>
			
			<td width="6%">
				<s:if test="insuretype==1">医保</s:if>
				<s:elseif test="insuretype==2">农合</s:elseif>
				<s:elseif test="insuretype==3">其他</s:elseif>
			</td>
			<td width="6%"><div align="right"><s:property value="totalcost"/>&nbsp;</div></td>
			<td width="6%"><div align="right"><s:property value="insurepay"/>&nbsp;</div></td>
			<td width="6%"><div align="right"><s:property value="outpay"/>&nbsp;</div></td>
			<td width="6%"><div align="right"><s:property value="payLine"/>&nbsp;</div></td>
			<td width="6%"><div align="right"><s:property value="hospitalpay"/>&nbsp;</div></td>
			<td width="6%"><div align="right"><s:property value="businesspay"/>&nbsp;</div></td>
			<td width="6%"><div align="right"><s:property value="capay"/>&nbsp;</div></td>
			<td width="6%"><div align="right"><s:property value="asisstpay"/>&nbsp;</div></td>
			<td width="8%"><s:date name="updatetime" format="yyyy-MM-dd"/></td>
			<td  width="7%">
				<s:if test="approveresult==1">同意救助</s:if>
				<s:elseif test="approveresult==0">不同意救助</s:elseif>
				<s:elseif test="approveresult==-1">作废</s:elseif>
			</td>
			<td >
			<div align="center">
				<a href="javascript:void(0)" onclick="cancel('<s:property value="daId" />')">作废</a>
			</div>
			</td>
		</tr>
	</s:iterator>
</table>
<div align="center" style="font-size: 12px"><s:property
	value="toolsmenu" escape="false" /></div>
</body>
</html>