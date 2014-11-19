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
<link type="text/css" href="../js/themes/base/jquery.ui.all.css"
	rel="stylesheet" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="../js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#begintime").datepicker();
		$("#endtime").datepicker();
	});
</script>
<script type="text/javascript">
	function del(a){
		var num = a.id.split('-')[1];
		var div1 = document.getElementById("hiddiv");
		var span01 = document.getElementById("s"+num);
		var hidden01 = document.getElementById("h"+num);
		var delimg = document.getElementById("d-"+num);
		div1.removeChild(span01);
		div1.removeChild(hidden01);
		div1.removeChild(delimg);
	}
	var num=0;
	function add(str) {
		var div1 = document.getElementById("hiddiv");
		var span01 = document.createElement("span");
		span01.setAttribute("id", "s" + (num));
		span01.innerText = "文件" + (num);
		var hidden01 = document.createElement("input");
		hidden01.setAttribute("type", "hidden");
		hidden01.setAttribute("name", "filebase64");
		hidden01.setAttribute("value", str);
		hidden01.setAttribute("id", "h" + num);
		var delimg = document.createElement("img");
		delimg.setAttribute("src", "../images/del.gif");
		delimg.setAttribute("id", "d-" + num);
		delimg.setAttribute("onclick", function(){del(this);});
		div1.appendChild(span01);
		div1.appendChild(hidden01);
		div1.appendChild(delimg);
		num=num+1;
	}
	function medicaltypechange(b){
		var diagnose = document.getElementById("diagnose");
		var sickencontent = document.getElementById("sickencontent");
		 if(b.value==1){
			diagnose.value = "-1";
			diagnose.disabled = true;
			sickencontent.value = "";
			sickencontent.disabled = false;
		}else if (b.value==2){
			diagnose.value = "-1";
			diagnose.disabled = false;
			sickencontent.value = "";
			sickencontent.disabled = true;
		} 

	}
</script>
<title>医后报销录入</title>
</head>
<body style="padding: 10px 10px 10px 10px;">
	<s:form id="aaaaa" enctype="multipart/form-data" action="afterapply" method="post"
		theme="simple" onsubmit="return check();">
		<s:hidden name="medicalafterDTO.familyno"></s:hidden>
		<s:hidden name="medicalafterDTO.membername"></s:hidden>
		<s:hidden name="medicalafterDTO.paperid"></s:hidden>
		<s:hidden name="medicalafterDTO.ssn"></s:hidden>
		<s:hidden name="medicalafterDTO.memberId"></s:hidden>
		<s:hidden name="medicalafterDTO.memberType"></s:hidden>
		<s:hidden name="medicalafterDTO.personstate"></s:hidden>
		<s:hidden name="medicalafterDTO.assistType"></s:hidden>
		<s:hidden name="medicalafterDTO.asort"></s:hidden>
		<s:hidden name="medicalafterDTO.sex"></s:hidden>
		<s:hidden name="medicalafterDTO.masterName"></s:hidden>
		<s:hidden name="medicalafterDTO.relmaster"></s:hidden>
		<s:hidden name="medicalafterDTO.famaddr"></s:hidden>
		<s:hidden name="medicalafterDTO.onNo"></s:hidden>
		<s:hidden name="medicalafterDTO.birthday"></s:hidden>
		<s:hidden name="medicalafterDTO.telephone"></s:hidden>
		<s:hidden name="medicalafterDTO.birthday"></s:hidden>
		<s:hidden name="medicalafterDTO.famcount"></s:hidden>
		<s:hidden name="medicalafterDTO.persontype"
			value="%{medicalafterDTO.assistType}%{medicalafterDTO.asort}"></s:hidden>
			<s:hidden name="medicalafterDTO.actId" id="actid"></s:hidden>
		<table align="center" width="100%" class="t1" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<th colspan="6"><s:property value="medicalafterDTO.membername" />医后报销录入审批表
					[当前状态：<s:property value="medicalafterDTO.personstate" />] [身份类别：<s:if
						test="medicalafterDTO.assistType==11||medicalafterDTO.assistType==10">在保户</s:if>
					<s:else>停保户</s:else> <s:if test="medicalafterDTO.asort==1">; 再保障</s:if>]
				</th>
			</tr>
			<tr>
				<td width="17%">家庭编号</td>
				<td width="16%"><s:property value="medicalafterDTO.familyno" />&nbsp;</td>
				<td width="17%">姓名</td>
				<td width="16%"><s:property value="medicalafterDTO.membername" />&nbsp;</td>
				<td width="17%">身份证号码</td>
				<td width="17%"><s:property value="medicalafterDTO.paperid" />&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">性别</td>
				<td width="16%"><s:property value="medicalafterDTO.sex" />&nbsp;</td>
				<td width="17%">户主姓名</td>
				<td width="16%"><s:property value="medicalafterDTO.masterName" />&nbsp;</td>
				<td width="17%">与户主关系</td>
				<td width="17%"><s:property value="medicalafterDTO.relmaster" />&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">家庭地址</td>
				<td colspan="5" width="17%"><s:property
						value="medicalafterDTO.famaddr" />&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">保险类型</td>
				<td colspan="5">
				<s:if test="medicalafterDTO.memberType==1">
				<s:radio id="insuretype"
						name="medicalafterDTO.insuretype"
						list="%{#{'1':'医保','2':'农合','3':'其他'}}" value="1"></s:radio>
				</s:if>
				<s:elseif test="medicalafterDTO.memberType==2">
				<s:radio id="insuretype"
						name="medicalafterDTO.insuretype"
						list="%{#{'1':'医保','2':'农合','3':'其他'}}" value="2"></s:radio>
				</s:elseif>
				<s:else>
				<s:radio id="insuretype"
						name="medicalafterDTO.insuretype"
						list="%{#{'1':'医保','2':'农合','3':'其他'}}" value="3"></s:radio>
				</s:else>		
				&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">票据编号</td>
				<td colspan="5"><s:textfield id="tiketno"
						name="medicalafterDTO.tiketno"></s:textfield>&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">医院名称</td>
				<td colspan="2"><s:textfield id="hospital"
						name="medicalafterDTO.hospital" cssStyle="width:320"></s:textfield>&nbsp;</td>
				<td width="17%">医院级别</td>
				<td colspan="2"><s:radio id="hospitallevel"
						name="medicalafterDTO.hospitallevel"
						list="%{#{'3':'省级','2':'市级','1':'区级'}}"></s:radio>&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">入院时间</td>
				<td><input type="text" readonly="readonly" id="begintime"
					name="medicalafterDTO.begintime"
					value="<s:date name="medicalafterDTO.begintime" format="yyyy-MM-dd"/>" /></td>
				<td width="17%">出院时间</td>
				<td colspan="3"><input type="text" readonly="readonly"
					id="endtime" name="medicalafterDTO.endtime"
					value="<s:date name="medicalafterDTO.endtime" format="yyyy-MM-dd"/>" /></td>
			</tr>
			<tr>
				<td width="17%">救助类型</td>
				<td><s:select id="medicaltype"
						name="medicalafterDTO.medicaltype" list="#{'1':'住院','2':'门诊大病'}"
						listKey="key" listValue="value" onchange="medicaltypechange(this);"></s:select></td>
				<td width="17%">门诊大病</td>
				<td colspan="1">
						<s:select disabled="true" id="diagnose" name="medicalafterDTO.diagnose" list="#{'-1':'其他','0001':'尿毒症','0002':'肝、肾脏移植（抗排异治疗）','0004':'肿瘤（仅限于放疗、化疗）','0005':'骨髓移植（抗排异治疗）','0006':'心脏移植（抗排异治疗）'}" listKey="key" listValue="value"></s:select>
					</td>
					<td>患病名称</td>
					<td><s:textfield id="sickencontent" disabled="false"
						name="medicalafterDTO.sickencontent"/></td>
			</tr>
			<tr>
				<td width="17%">总费用</td>
				<td><s:textfield id="totalcost"
						name="medicalafterDTO.totalcost" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">报销金额（医保/农合）</td>
				<td><s:textfield id="insurepay"
						name="medicalafterDTO.insurepay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">不参与补偿金额</td>
				<td><s:textfield id="outpay" name="medicalafterDTO.outpay"
						value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
			</tr>
			<tr>
				<td width="17%">起付线</td>
				<td><s:textfield id="payLine" name="medicalafterDTO.payLine"
						value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">医院补助</td>
				<td colspan="3"><s:textfield id="hospitalpay"
						name="medicalafterDTO.hospitalpay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
			</tr>
			<tr>
				<td width="17%">大病保险金额</td>
				<td><s:textfield id="capay" name="medicalafterDTO.capay"
						value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">商业保险</td>
				<td colspan="3"><s:textfield id="businesspay"
						name="medicalafterDTO.businesspay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
			</tr>
			<tr>
				<td width="17%">救助金额</td>
				<td><s:textfield id="asisstpay"
						name="medicalafterDTO.asisstpay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td colspan="2">
					<button type="button" style="width: 250px;" onclick="countassist()">计算救助金</button>
				</td>
				<td width="17%">审批意见</td>
				<td><s:select id="approveresult"
						name="medicalafterDTO.approveresult"
						list="#{'1':'同意救助','0':'不同意救助','-1':'作废'}" listKey="key"
						listValue="value"></s:select></td>
			</tr>
			<tr>
				<td colspan="6">
					<button type="button" onclick="updateload(document.getElementById('a1'))">  
						<!--  -->
						票据拍照
					</button></td>
			</tr>

		</table>
		<div id="hiddiv"></div>
		<div align="center">
			<s:submit value="保存" id="sub" disabled="true"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button onclick="window.close()">关闭</button>
		</div>
	</s:form>
</body>
<script type="text/javascript">
	function check() {
		var flag = true;
		var insuretype = $("input[name='medicalafterDTO.insuretype']:checked")
				.val();
		var tiketno = $("#tiketno")[0].value;
		var hospital = $("#hospital")[0].value;
		var hospitallevel = $(
				"input[name='medicalafterDTO.hospitallevel']:checked").val();
		var begintime = $("#begintime")[0].value;
		var endtime = $("#endtime")[0].value;
		var sickencontent = $("#sickencontent")[0].value;
		var medicaltype = $("#medicaltype")[0].value;
		var diagnose = $("#diagnose")[0].value;
		var filenames = document.getElementsByName("filebase64");
		if ("1" == insuretype || "2" == insuretype || "3" == insuretype) {
		} else {
			alert("请选择保障类别！");
			flag = false;
			return flag;
		}
		if ("" == tiketno) {
			alert("请填写票据编号！");
			flag = false;
			return flag;
		}
		if ("" == hospital) {
			alert("请填写医院名称！");
			flag = false;
			return flag;
		}
		if ("1" == hospitallevel || "2" == hospitallevel
				|| "3" == hospitallevel) {
		} else {
			alert("请选择医院级别！");
			flag = false;
			return flag;
		}
		if ("" == begintime) {
			alert("请输入开始时间！");
			flag = false;
			return flag;
		}
		if ("" == endtime) {
			alert("请输入结束时间！");
			flag = false;
			return flag;
		}
		if(medicaltype=="1"){
			if ("" == sickencontent) {
				alert("请输入患病名称！");
				flag = false;
				return flag;
			}
		}else if(medicaltype=="2"){
			var value = "";
			if("-1"==diagnose){
				value="其他";
			}else if("0001"==diagnose){
				value="尿毒症";
			}else if("0002"==diagnose){
				value="肝、肾脏移植（抗排异治疗）";
			}else if("0004"==diagnose){
				value="肿瘤（仅限于放疗、化疗）";
			}else if("0005"==diagnose){
				value="骨髓移植（抗排异治疗）";
			}else if("0006"==diagnose){
				value="心脏移植（抗排异治疗）";
			}
			alert("门诊大病选择："+value);
		}
		/* if(filenames.length>0){
			alert("有附件");
		}else{
			alert("必须上传附件！");
			flag = false;
			return flag;
		} */
		return flag;
	}

	function updateload() {
		var windowprops = "dialogWidth:860px;dialogHeight:700px";
		window.showModalDialog("index.html", window, windowprops);
	}
	function countassist(){
		var formParam = $("#aaaaa").serialize();//序列化表格内容为字符串    
	    $.ajax({    
	        type:'post',        
	        url:'<%=basePath%>page/medicalafter/countassist.action',    
	        data:formParam,    
	        cache:false,    
	        dataType:'json',    
	        success:function(data){
	        	alert(data);
	        	var dataObj=eval("("+data+")");
	        	 alert(dataObj.r);
	        	 $("#asisstpay")[0].value=dataObj.assitpay;
	        	 $("#actid")[0].value=dataObj.actId;
	        	 $("#sub")[0].disabled = false;
	        }    
	    }); 
	}
</script>
</html>