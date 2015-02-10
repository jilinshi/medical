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
		var div_wsflag_title = document.getElementById("div_wsflag_title");
		var div_wsflag = document.getElementById("div_wsflag");
		var div_sickencontent_title = document.getElementById("div_sickencontent_title");
		var div_sickencontent = document.getElementById("div_sickencontent");
		var insuretype = $("input[name='disasterafterDTO.insuretype']:checked")
		.val();
		 if(b.value==1){
			diagnose.value = "-1";
			diagnose.disabled = true;
			sickencontent.value = "";
			sickencontent.disabled = false;
			div_sickencontent.style.display="block";
			div_sickencontent_title.style.display="block";
			if(insuretype==2){
				div_wsflag_title.style.display="block";
				div_wsflag.style.display="block";
			}else{
				div_wsflag_title.style.display="none";
				div_wsflag.style.display="none";
			}
		}else if (b.value==2){
			diagnose.value = "-1";
			diagnose.disabled = false;
			sickencontent.value = "";
			sickencontent.disabled = true;
			div_sickencontent.style.display="none";
			div_sickencontent_title.style.display="none";
			div_wsflag_title.style.display="none";
			div_wsflag.style.display="none";
		} 

	}
	
	function insuretypechange(v){
		var div_wsflag_title = document.getElementById("div_wsflag_title");
		var div_wsflag = document.getElementById("div_wsflag");
		if(v.value=="2"){
			div_wsflag_title.style.display='block';
			div_wsflag.style.display='block';
		}else{
			div_wsflag_title.style.display='none';
			div_wsflag.style.display='none';
		}
	}
	
	function validate(){
		var paperid = $("#paperid")[0].value;
		$.ajax({    
	        type:'post',        
	        url:'<%=basePath%>page/disaster/getinforbypaperid.action?paperid='+paperid,  
	        cache:false,    
	        dataType:'json',    
	        success:function(data){
	        	//alert(data);
	        	var dataObj=eval("("+data+")");
	        	alert(dataObj.message);
	        	if(dataObj.flag==1){
		        	var s = "本年度总救助金额："+dataObj.all_sumpay+ "元；"
		        			+"本年度住院次数："+dataObj.in_num+"次, 本年度住院累计救助金："+dataObj.in_sumpay+"元; " 
		        			+" 本年度门诊次数："+dataObj.out_num+"次, 本年度门诊累计救助金："+dataObj.out_sumpay+"元;"
		        	$('#Msg')[0].innerText = s;
		        	$('#sub')[0].disabled = false;
	        	}else{
	        		$('#sub')[0].disabled = true;
	        	}
	        }
	    }); 
	}
	function readid(){
		var windowprops = "dialogWidth:760px;dialogHeight:400px";
		var URL = "readid.jsp";
		var arrs= window.showModalDialog(URL,window,windowprops);
		disasterappform.membername.value=arrs[0];
		disasterappform.paperid.value=arrs[1];
		var sex=-1;
		if(arrs[5]=='女'){
			sex=1;
		}else if(arrs[5]=='男'){
			sex=0;
		}
		$("input[name=disasterafterDTO.sex]:eq("+sex+")").attr("checked",'checked'); 
		disasterappform.famaddr.value=arrs[3];
	}
</script>
<title>灾后救助录入</title>
</head>
<body style="padding: 10px 10px 10px 10px;">
	<s:form id="disasterappform" enctype="multipart/form-data" action="disasterapp" method="post"
		theme="simple" onsubmit="return check();"> 
		<table align="center" width="100%" class="t1" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<th colspan="6">灾后救助录入审批<font color="red" style="font-size:15px"><div id="Msg"></div></font>
				</th>
			</tr>
			<tr>
				<td colspan="6"><button onclick="readid()">身份证扫描</button></td>
			</tr>
			<tr>
				<td width="17%">姓名</td>
				<td width="16%"><s:textfield id="membername"
						name="disasterafterDTO.membername"></s:textfield>&nbsp;</td>
				<td width="17%">身份证号码</td>
				<td width="16%"><s:textfield id="paperid"
						name="disasterafterDTO.paperid"></s:textfield>&nbsp;
				<button onclick="validate()">验证</button>
				</td>
				<td width="17%">性别</td>
				<td width="17%"><s:radio id="sex"
						name="disasterafterDTO.sex"
						list="%{#{'1':'男','0':'女'}}" onclick="insuretypechange(this)"></s:radio>&nbsp;</td>
			</tr> 
			<tr>
				<td width="17%">家庭地址</td>
				<td colspan="5" width="17%"><s:textfield id="famaddr"
						name="disasterafterDTO.famaddr" cssStyle="width:92%"></s:textfield>&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">填写银行账号</td>
				<td colspan="2" width="17%">
				<s:textfield id="bankaccounts"
						name="disasterafterDTO.bankaccounts" cssStyle="width:80%"></s:textfield> </td>
			<td width="17%">票据编号</td>
				<td colspan="2"><s:textfield id="tiketno"
						name="disasterafterDTO.tiketno" cssStyle="width:80%"></s:textfield>&nbsp;</td>
			</tr> 
			<tr>
				<td width="17%">保险类型</td>
				<td colspan="5">
				<s:radio id="insuretype"
						name="disasterafterDTO.insuretype"
						list="%{#{'1':'医保','2':'农合','3':'其他'}}" value="3" onclick="insuretypechange(this)"></s:radio>
				&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">医院名称</td>
				<td colspan="2"><s:textfield id="hospital"
						name="disasterafterDTO.hospital" cssStyle="width:320"></s:textfield>&nbsp;</td>
				<td width="17%">医院级别</td>
				<td colspan="2"><s:radio id="hospitallevel"
						name="disasterafterDTO.hospitallevel"
						list="%{#{'3':'省级','2':'市级','1':'区级'}}" value="1"></s:radio>&nbsp;</td>
			</tr>
			<tr>
				<td width="17%">入院时间</td>
				<td><input type="text" readonly="readonly" id="begintime"
					name="disasterafterDTO.begintime"
					value="<s:date name="disasterafterDTO.begintime" format="yyyy-MM-dd"/>" /></td>
				<td width="17%">出院时间</td>
				<td colspan="3"><input type="text" readonly="readonly"
					id="endtime" name="disasterafterDTO.endtime"
					value="<s:date name="disasterafterDTO.endtime" format="yyyy-MM-dd"/>" /></td>
			</tr>
			<tr>
				<td width="17%">救助类型</td>
				<td><s:select id="medicaltype"
						name="disasterafterDTO.medicaltype" list="#{'1':'住院','2':'门诊大病'}"
						listKey="key" listValue="value" onchange="medicaltypechange(this);"></s:select></td>
				<td width="17%">门诊大病</td>
				<td colspan="1">
						<s:select disabled="true" id="diagnose" name="disasterafterDTO.diagnose" list="#{'-1':'其他','0001':'尿毒症','0002':'肝、肾脏移植（抗排异治疗）','0004':'肿瘤（仅限于放疗、化疗）','0005':'骨髓移植（抗排异治疗）','0006':'心脏移植（抗排异治疗）'}" listKey="key" listValue="value"></s:select>
					</td>
					<td>
					<div id="div_sickencontent_title" style="display:block">
					患病名称
					</div>
					</td>
					<td>
					<div id="div_sickencontent" style="display:block">
					<s:textfield id="sickencontent" disabled="false"
						name="disasterafterDTO.sickencontent"/>
					</div>	
					</td>
			</tr>
			
			<tr>
			
				<td width="17%">
				<div id="div_wsflag_title" style="display:none">住院类别</div>
				</td>
				<td	colspan="5">
				<div id="div_wsflag" style="display:none">
				<s:radio list="#{'0':'普通住院','1':'外伤、未经新农合转诊的转院'}" name="disasterafterDTO.wsflag"
						listKey="key" listValue="value" value="0"></s:radio>
				</div>
				</td>
			</tr>
			
			<tr>
				<td width="17%">总费用</td>
				<td><s:textfield id="totalcost"
						name="disasterafterDTO.totalcost" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">报销金额（医保/农合）</td>
				<td><s:textfield id="insurepay"
						name="disasterafterDTO.insurepay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">不参与补偿金额</td>
				<td><s:textfield id="outpay" name="disasterafterDTO.outpay"
						value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
			</tr>
			<tr>
				<td width="17%">起付线</td>
				<td><s:textfield id="payLine" name="disasterafterDTO.payLine"
						value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">医院补助</td>
				<td colspan="3"><s:textfield id="hospitalpay"
						name="disasterafterDTO.hospitalpay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
			</tr>
			<tr>
				<td width="17%">商业保险</td>
				<td><s:textfield id="businesspay"
						name="disasterafterDTO.businesspay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">大病保险金额</td>
				<td colspan="3"><s:textfield id="capay" name="disasterafterDTO.capay"
						value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td >
				</td>
			</tr>
			<tr>
				<td width="17%">救助金额</td>
				<td><s:textfield id="asisstpay"
						name="disasterafterDTO.asisstpay" value="0"
						onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
						onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
				<td width="17%">审批意见</td>
				<td colspan="3"><s:select id="approveresult"
						name="disasterafterDTO.approveresult"
						list="#{'1':'同意救助','0':'不同意救助','-1':'作废'}" listKey="key"
						listValue="value"></s:select></td>
			</tr>
			<tr>
				<td width="17%">救助原因</td>
				<td colspan="5">
					<s:textarea name="disasterafterDTO.approvecontent" rows="2" cols="100%"></s:textarea>
				</td>
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
		</div>
	</s:form> 
</body>
<script type="text/javascript">
	function check() {
		var flag = true;
		var insuretype = $("input[name='disasterafterDTO.insuretype']:checked")
				.val();
		var tiketno = $("#tiketno")[0].value;
		var hospital = $("#hospital")[0].value;
		var hospitallevel = $(
				"input[name='disasterafterDTO.hospitallevel']:checked").val();
		var begintime = $("#begintime")[0].value;
		var endtime = $("#endtime")[0].value;
		var sickencontent = $("#sickencontent")[0].value;
		var medicaltype = $("#medicaltype")[0].value;
		var diagnose = $("#diagnose")[0].value;
		var filenames = document.getElementsByName("filebase64");
		//
		var membername = $("#membername")[0].value;
		var paperid = $("#paperid")[0].value;
		var sex =  $("input[name='disasterafterDTO.sex']:checked")
		.val();
		var bankaccounts = $("#bankaccounts")[0].value;
		var famaddr = $("#famaddr")[0].value;
		if("" == membername){
			alert("请填写姓名！");
			flag = false;
			return flag;
		}
		if("" == paperid){
			alert("请填写身份证号码！");
			flag = false;
			return flag;
		}
		if("1" == sex || "0" == sex){
		}else{
			alert("请填写性别！");
			flag = false;
			return flag;
		}
		if("" == famaddr){
			alert("请填写家庭地址！");
			flag = false;
			return flag;
		}
		if("" == bankaccounts){
			alert("请填写银行账号！");
			flag = false;
			return flag;
		}
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
		}  */
		return flag;
	}

	function updateload() {
		var windowprops = "dialogWidth:860px;dialogHeight:700px";
		window.showModalDialog("<%=path%>/page/medicalafter/index.html", window, windowprops);
	}	
</script>
</html>