<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="../css/table-style.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="../js/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="../js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<title>灾后救助录入</title>
</head>
<body style="padding: 10px 10px 10px 10px;">
	<table align="center" width="100%" class="t1" border="0"
		cellpadding="0" cellspacing="0">
		<tr>
			<th colspan="6">灾后救助录入审批</th>
		</tr>
		<tr>
			<td width="17%">姓名</td>
			<td width="16%"><s:property value="disasterafterDTO.membername" />&nbsp;</td>
			<td width="17%">身份证号码</td>
			<td width="16%"><s:property value="disasterafterDTO.paperid" />&nbsp;</td>
			<td width="17%">性别</td>
			<td width="17%">
				<s:if test="disasterafterDTO.sex==1">
					男
				</s:if>
				<s:elseif test="disasterafterDTO.sex==0">
					女
				</s:elseif>
			&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">家庭地址</td>
			<td colspan="5" width="17%"><s:property
					value="disasterafterDTO.famaddr" />&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">填写银行账号</td>
			<td colspan="2" width="17%"><s:property
					value="disasterafterDTO.bankaccounts" />&nbsp;</td>
			<td width="17%">票据编号</td>
			<td colspan="2"><s:property value="disasterafterDTO.tiketno" />&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">保险类型</td>
			<td colspan="5">
			<s:if test="disasterafterDTO.insuretype==1">
			医保
			</s:if>
			<s:elseif test="disasterafterDTO.insuretype==2">
			农合
			</s:elseif>
			<s:elseif test="disasterafterDTO.insuretype==3">
			其他
			</s:elseif>
			&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">医院名称</td>
			<td colspan="2"><s:property value="disasterafterDTO.hospital" />&nbsp;</td>
			<td width="17%">医院级别</td>
			<td colspan="2"><s:if test="disasterafterDTO.hospitallevel==3">
					省级
				</s:if> <s:if test="disasterafterDTO.hospitallevel==2">
					市级
				</s:if> <s:if test="disasterafterDTO.hospitallevel==1">
					区级
				</s:if> &nbsp;</td>
		</tr>
		<tr>
			<td width="17%">入院时间</td>
			<td><s:date name="disasterafterDTO.begintime"
					format="yyyy-MM-dd" />&nbsp;</td>
			<td width="17%">出院时间</td>
			<td colspan="3"><s:date name="disasterafterDTO.endtime"
					format="yyyy-MM-dd" />&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">救助类型</td>
			<td><s:if test="disasterafterDTO.medicaltype==1">
					住院
				</s:if> <s:if test="disasterafterDTO.medicaltype==2">
					门诊大病
				</s:if>&nbsp;</td>
			<td width="17%">门诊大病</td>
			<td colspan="1"><s:if test="disasterafterDTO.diagnose==-1">
						其他
					</s:if> <s:elseif test="disasterafterDTO.diagnose==0001">
						尿毒症
					</s:elseif> <s:elseif test="disasterafterDTO.diagnose==0002">
						肝、肾脏移植（抗排异治疗）
					</s:elseif> <s:elseif test="disasterafterDTO.diagnose==0004">
						肿瘤（仅限于放疗、化疗）
					</s:elseif> <s:elseif test="disasterafterDTO.diagnose==0005">
						骨髓移植（抗排异治疗）
					</s:elseif> <s:elseif test="disasterafterDTO.diagnose==0006">
						心脏移植（抗排异治疗）
					</s:elseif></td>
			<td width="17%">患病名称</td>
			<td ><s:property value="disasterafterDTO.sickencontent"/>&nbsp;</td>
		</tr>

		<tr>

			<td width="17%">住院类别</td>
			<td colspan="5">
				<s:if test="disasterafterDTO.wsflag==0">
					普通住院
				</s:if>
				<s:elseif test="disasterafterDTO.wsflag==1">
					外伤、未经新农合转诊的转院
				</s:elseif>
			</td>
		</tr>

	<tr>
			<td width="17%">总费用</td>
			<td><s:property value="disasterafterDTO.totalcost"/>&nbsp;</td>
			<td width="17%">报销金额（医保/农合）</td>
			<td><s:property value="disasterafterDTO.insurepay"/>&nbsp;</td>
			<td width="17%">不参与补偿金额</td>
			<td><s:property value="disasterafterDTO.outpay"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">起付线</td>
			<td><s:property value="disasterafterDTO.payLine"/>&nbsp;</td>
			<td width="17%">医院补助</td>
			<td colspan="3"><s:property value="disasterafterDTO.hospitalpay"/>&nbsp;</td>
		<tr>
			<td width="17%">大病保险金额</td>
			<td><s:property value="disasterafterDTO.capay"/>&nbsp;</td>
			<td width="17%">商业保险</td>
			<td colspan="3"><s:property value="disasterafterDTO.businesspay"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="17%">救助金额</td>
			<td ><s:property value="disasterafterDTO.asisstpay"/>&nbsp;</td>
			<td width="17%">审批意见</td>
			<td colspan="3">
				<s:if test="disasterafterDTO.approveresult==1">
					同意救助
				</s:if>
				<s:if test="disasterafterDTO.approveresult==0">
					不同意救助
				</s:if>
				<s:if test="disasterafterDTO.approveresult==-1">
					作废
				</s:if>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td width="17%">救助原因</td>
			<td colspan="5"><s:property value="disasterafterDTO.approvecontent"/>&nbsp;</td>
		</tr>
	</table>
	<div align="center">
	
 	 <s:if test="disasterafterDTO.medicaltype==1">
			<s:url action="printinhospital" id="print">
				<s:param name="disasterafterDTO.daId">
					<s:property value="disasterafterDTO.daId" />
				</s:param>
			</s:url>
	</s:if>
	<s:elseif test="disasterafterDTO.medicaltype==2">
			<s:url action="printoutpatient" id="print">
				<s:param name="disasterafterDTO.daId">
					<s:property value="disasterafterDTO.daId" />
				</s:param>
			</s:url>
	</s:elseif>
	<s:url action="printapp" id="printapp">
		<s:param name="disasterafterDTO.daId">
			<s:property value="disasterafterDTO.daId" />
		</s:param>
	</s:url>
				<s:a
				href="%{print}" cssStyle="cursor:hand" target="_blank">打印报销凭证</s:a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:a
				href="%{printapp}" cssStyle="cursor:hand" target="_blank">打印申请审批表</s:a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="history.go(-1)">关闭</button></div>
</body>
</html>