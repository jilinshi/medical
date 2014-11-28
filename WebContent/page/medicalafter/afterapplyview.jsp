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
<title>医后报销录入成功</title>
</head>
<body style="padding:10px  10px  10px   10px;">

	<table align="center" width="100%" class="t1" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<th colspan="6"><s:property value="medicalafterDTO.membername"/>医后报销录入审批表 
			[当前状态：<s:property value="medicalafterDTO.personstate"/>]
			[身份类别：<s:if test="medicalafterDTO.assistType==11||medicalafterDTO.assistType==10">在保户</s:if>
						<s:else>停保户</s:else>
						<s:if test="medicalafterDTO.asort==1">; 再保障</s:if>]
			</th>
		</tr>
		<tr>
			<td width="17%">家庭编号</td>
			<td width="16%"><s:property value="medicalafterDTO.familyno"/>&nbsp;</td>
			<td width="17%">姓名</td>
			<td width="16%"><s:property value="medicalafterDTO.membername"/>&nbsp;</td>
			<td width="17%">身份证号码</td>
			<td width="17%"><s:property value="medicalafterDTO.paperid"/>&nbsp;</td>
		</tr>
		 <tr>
			<td width="17%">性别</td>
			<td width="16%"><s:property value="medicalafterDTO.sex"/>&nbsp;</td>
			<td width="17%">户主姓名</td>
			<td width="16%"><s:property value="medicalafterDTO.masterName"/>&nbsp;</td>
			<td width="17%">与户主关系</td>
			<td width="17%"><s:property value="medicalafterDTO.relmaster"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">家庭地址</td>
			<td colspan="5" width="17%"><s:property value="medicalafterDTO.famaddr"/>&nbsp;</td>
		</tr>
 		<tr>
			<td width="17%">保险类型</td>
			<td colspan="5" >
				<s:if test="medicalafterDTO.insuretype==1">
					医保	
				</s:if>
				<s:elseif test="medicalafterDTO.insuretype==2">
					农合
				</s:elseif>
				<s:elseif test="medicalafterDTO.insuretype==3">
					其他
				</s:elseif>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td width="17%">票据编号</td>
			<td colspan="5"><s:property value="medicalafterDTO.tiketno"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">医院名称</td>
			<td colspan="2"><s:property value="medicalafterDTO.hospital"/>&nbsp;</td>
			<td width="17%">医院级别</td>
			<td colspan="2">
				<s:if test="medicalafterDTO.hospitallevel==1">
					省级
				</s:if>
				<s:if test="medicalafterDTO.hospitallevel==2">
					市级
				</s:if>
				<s:if test="medicalafterDTO.hospitallevel==3">
					区级
				</s:if>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td width="17%">入院时间</td>
			<td ><s:date name="medicalafterDTO.begintime" format="yyyy-MM-dd"/>&nbsp;</td>
			<td width="17%">出院时间</td>
			<td colspan="3"><s:date name="medicalafterDTO.endtime" format="yyyy-MM-dd"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">救助类型</td>
			<td >
				<s:if test="medicalafterDTO.medicaltype==1">
					住院
				</s:if>
				<s:if test="medicalafterDTO.medicaltype==2">
					门诊大病
				</s:if>
				&nbsp;
			</td>
			<td width="17%">门诊大病</td>
			<td >
			<s:if test="medicalafterDTO.diagnose==-1">
				其他
			</s:if>
			<s:elseif test="medicalafterDTO.diagnose==0001">
				尿毒症
			</s:elseif>
			<s:elseif test="medicalafterDTO.diagnose==0002">
				肝、肾脏移植（抗排异治疗）
			</s:elseif>
			<s:elseif test="medicalafterDTO.diagnose==0004">
				肿瘤（仅限于放疗、化疗）
			</s:elseif>
			<s:elseif test="medicalafterDTO.diagnose==0005">
				骨髓移植（抗排异治疗）
			</s:elseif>
			<s:elseif test="medicalafterDTO.diagnose==0006">
				心脏移植（抗排异治疗）
			</s:elseif>
			</td>
			<td width="17%">患病名称</td>
			<td ><s:property value="medicalafterDTO.sickencontent"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">住院类别</td>
			<td colspan="5">
				<s:if test="medicalafterDTO.wsflag==0">
					普通住院
				</s:if>
				<s:elseif test="medicalafterDTO.wsflag==1">
					外伤、未经新农合转诊的转院
				</s:elseif>
			</td>
		</tr>
		<tr>
			<td width="17%">总费用</td>
			<td><s:property value="medicalafterDTO.totalcost"/>&nbsp;</td>
			<td width="17%">报销金额（医保/农合）</td>
			<td><s:property value="medicalafterDTO.insurepay"/>&nbsp;</td>
			<td width="17%">不参与补偿金额</td>
			<td><s:property value="medicalafterDTO.outpay"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">起付线</td>
			<td><s:property value="medicalafterDTO.payLine"/>&nbsp;</td>
			<td width="17%">医院补助</td>
			<td colspan="3"><s:property value="medicalafterDTO.hospitalpay"/>&nbsp;</td>
		<tr>
			<td width="17%">大病保险金额</td>
			<td><s:property value="medicalafterDTO.capay"/>&nbsp;</td>
			<td width="17%">商业保险</td>
			<td colspan="3"><s:property value="medicalafterDTO.businesspay"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">救助金额</td>
			<td ><s:property value="medicalafterDTO.asisstpay"/>&nbsp;</td>
			<td width="17%">审批意见</td>
			<td colspan="3">
				<s:if test="medicalafterDTO.approveresult==1">
					同意救助
				</s:if>
				<s:if test="medicalafterDTO.approveresult==0">
					不同意救助
				</s:if>
				<s:if test="medicalafterDTO.approveresult==-1">
					作废
				</s:if>
				&nbsp;
			</td>
		</tr>
	</table>
	<div align="center">
	
 	 <s:if test="medicalafterDTO.medicaltype==1">
			<s:url action="printinhospital" id="print">
				<s:param name="medicalafterDTO.maId">
					<s:property value="medicalafterDTO.maId" />
				</s:param>
			</s:url>
	</s:if>
	<s:elseif test="medicalafterDTO.medicaltype==2">
			<s:url action="printoutpatient" id="print">
				<s:param name="medicalafterDTO.maId">
					<s:property value="medicalafterDTO.maId" />
				</s:param>
			</s:url>
	</s:elseif>
	<s:url action="printapp" id="printapp">
		<s:param name="medicalafterDTO.maId">
			<s:property value="medicalafterDTO.maId" />
		</s:param>
	</s:url>
				<s:a
				href="%{print}" cssStyle="cursor:hand" target="_blank">打印报销凭证</s:a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:a
				href="%{printapp}" cssStyle="cursor:hand" target="_blank">打印申请审批表</s:a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="window.close()">关闭</button></div>
</body>
</html>