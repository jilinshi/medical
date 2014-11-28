<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<sj:head jqueryui="true" />
<base href="<%=basePath%>">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV='pragma' CONTENT='no-cache'>
<META HTTP-EQUIV='Cache-Control' CONTENT='no-cache, must-revalidate'>
<META HTTP-EQUIV='expires' CONTENT='0'>
<title>核对医保卡号</title>
<script type="text/javascript">
	function savessn(){
			 var ssncheck = $("input[name='ssncheck']:checked").val();
			 var arr = {
				"checkDTO.ds" : $('#ds')[0].value,
				"checkDTO.memberId" : $('#memberid')[0].value,
				"checkDTO.ssn1" : $('#ssn1')[0].value,
				"checkDTO.ssn2" : $('#ssn2')[0].value,
				"checkDTO.ssn3" : $('#ssn3')[0].value,
				"checkDTO.paperid" : $('#paperid')[0].value,
				"checkDTO.ssncheck" : ssncheck
			}; 
			$.ajax( {
				type : "post",
				url : "page/check/installSsn.action",
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
						alert("同步医保卡号成功！");
					}else if(u==0){
						alert("同步医保卡号失败！");
					}
				}
			});
	}
</script>
</head>
<body >
<table align="center" width="99%" class="t1" border="0" cellpadding="0"
	cellspacing="0" style="margin:10px 10px 10px 10px;">
	<tr>
		<th scope="col">民政端：</th>
		<th scope="col">医保端：</th>
	</tr>
	<tr>
		<td width="50%">
			<table align="center" width="99%" class="t1" border="0" cellpadding="10"
				cellspacing="1" >
				<tr>
					<td width="40%" style="text-align:right">家庭编号：</td>
					<td><s:property value="checkDTO.familyno"/>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">姓名：</td>
					<td><s:property value="checkDTO.membername"/>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">身份证号码：</td>
					<td><s:property value="checkDTO.paperid"/>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">医保卡号:</td>
					<td><s:property value="checkDTO.ssn"/>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">救助状态</td>
					<td>
						<s:if test="checkDTO.assistType==00">
							普通居民
						</s:if>
						<s:elseif test="checkDTO.assistType==10||checkDTO.assistType==11">
							在保户
						</s:elseif>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">再保障状态</td>
					<td>
						<s:if test="checkDTO.asort==1">
							是
						</s:if>
						<s:else>
							否
						</s:else>
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
		<td width="50%">
			<table align="center" width="99%" class="t1" border="0" cellpadding="10"
				cellspacing="1" >
				<tr>
					<td width="40%" colspan="2" style="text-align:center;color:red"><s:property value="ybcheckDTO.message"/>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">姓名：</td>
					<td><s:property value="ybcheckDTO.ybmembername"/>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">身份证号码：</td>
					<td><s:property value="ybcheckDTO.ybpaperid"/>&nbsp;</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">医保卡号1:</td>
					<td>
						<s:if test="ybcheckDTO.ssn1!=null">
							<input type="radio" name="ssncheck" value="<s:property value='ybcheckDTO.ssn1'/>" />&nbsp;
						</s:if>
						<s:property value="ybcheckDTO.ssn1"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">医保卡号2:</td>
					<td>
						<s:if test="ybcheckDTO.ssn2!=null">
						<input type="radio" name="ssncheck" value="<s:property value='ybcheckDTO.ssn2'/>" />&nbsp;
						</s:if>
						<s:property value="ybcheckDTO.ssn2"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td width="40%" style="text-align:right">医保卡号3:</td>
					<td>
						<s:if test="ybcheckDTO.ssn3!=null">
						<input type="radio" name="ssncheck" value="<s:property value='ybcheckDTO.ssn3'/>" />&nbsp;
						</s:if>
						<s:property value="ybcheckDTO.ssn3"/>&nbsp;
					</td>
				</tr>
				<s:hidden id="ssn1" name="ybcheckDTO.ssn1"></s:hidden>
				<s:hidden id="ssn2" name="ybcheckDTO.ssn2"></s:hidden>
				<s:hidden id="ssn3" name="ybcheckDTO.ssn3"></s:hidden>
				<s:hidden id="memberid" name="checkDTO.memberId"></s:hidden>
				<s:hidden id="ds" name="checkDTO.ds"></s:hidden>
				<s:hidden id="paperid" name="checkDTO.paperid"></s:hidden>
			</table>
		</td>
	</tr>
</table>
<div align="right">
	<input type="button" name="savessn" value="同步医保卡号"
			onClick="javascript: savessn();"/>&nbsp;&nbsp;
	<input type="button" name="Submit" value="关闭"
			onClick="javascript: window.close();"/>
</div>
</body>
</html>