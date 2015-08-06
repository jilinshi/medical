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
		$("#begintime_o").datepicker({changeYear: true ,changeMonth: true});
		$("#endtime_o").datepicker({changeYear: true ,changeMonth: true});
	});
</script>
<title>查询医保数据</title>
</head>
<body style="padding: 10px 10px 10px 10px;">
	<s:form theme="simple" method="post" id="myform"
	cssStyle="font-size:12px">
	&nbsp;
		医院名称：<s:select id="hid" cssStyle="width: 340px;" name="medicalafterDTO.hospitalid" list="ybhospitals" listKey="hospitalid" listValue="hospitalname"></s:select>
	&nbsp;
		医保卡号：<s:textfield id="ssn" name="medicalafterDTO.ssn" size="16"></s:textfield>
	&nbsp;
		入院时间：<input size="10" type="text" readonly="readonly" id="begintime_o" name="medicalafterDTO.begintime"
		value="<s:date name="medicalafterDTO.begintime" format="yyyy-MM-dd"/>" /> 
	&nbsp;
		出院时间：<input size="10" type="text" readonly="readonly" id="endtime_o" name="medicalafterDTO.endtime"
		value="<s:date name="medicalafterDTO.endtime" format="yyyy-MM-dd"/>" />
	&nbsp;
		救助类型：<s:select id="medicaltype"
						name="medicalafterDTO.medicaltype" list="#{'1':'住院','2':'门诊大病'}"
						listKey="key" listValue="value" onchange="medicaltypechange(this);"></s:select>
	&nbsp;
	<button type="button" onclick="queryYBXX()">查询</button>
	</s:form>
	<table align="center" width="100%" class="t1" border="0"
		cellpadding="0" cellspacing="0">
		<tr>
			<th colspan="10"><s:property value="medicalafterDTO.membername" />登记信息</th>
		</tr>
		<tr>
			<td width="15%">医保编号</td>
			<td width="18%"><div id="ybnumber"></div></td>
			<td width="15%">医院编号</td>
			<td width="18%"><div id="hospitalid"></div></td>
			<td width="15%">住院流水号</td>
			<td width="19%"><div id="serialno"></div><s:hidden id="serialno_hid"/></td>
		</tr>
		<tr>
			<td width="15%">医疗类别</td>
			<td width="18%"><div id="medtype"></div></td>
			<td width="15%">科室名称</td>
			<td width="18%"><div id="indeptname"></div></td>
			<td width="15%">入院时间</td>
			<td width="19%"><div id="begintime"></div></td>
		</tr>
		<tr>
			<td width="15%">入院诊断疾病编码</td>
			<td width="18%"><div id="diagnoseno_i"></div></td>
			<td width="15%">入院诊断疾病名称</td>
			<td width="18%"><div id="diagnosename_i"></div></td>
			<td width="15%">在院状态</td>
			<td width="19%"><div id="outflag"></div></td>
		</tr>
		<tr>
			<td colspan="6">
				<button id="button_js" type="button" style="width: 98%;" onclick="queryJS()" disabled>查询结算数据</button>
			</td>
		</tr>
		<tr>
			<td width="15%">收据号</td>
			<td width="18%"><div id="shoujuno_js"></div></td>
			<td width="15%">交易类型</td>
			<td width="18%"><div id="businesstype_js"></div></td>
			<td width="15%">结算日期</td>
			<td width="19%"><div id="opertime_js"></div></td>
		</tr>
		<tr>
			<td width="15%">出院诊断疾病编码</td>
			<td width="18%"><div id="diagnoseno_o"></div></td>
			<td width="15%">出院诊断疾病名称</td>
			<td width="18%"><div id="diagnosename_o"></div></td>
			<td width="15%">科室名称</td>
			<td width="19%"><div id="indeptname_js"></div></td>
		</tr>
		<tr>
			<td width="15%">入院时间</td>
			<td width="18%"><div id="begintime_js"></div></td>
			<td width="15%">出院时间</td>
			<td width="18%"><div id="endtime_js"></div></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td width="15%">费用合计</td>
			<td width="18%"><div id="pay_total"></div></td>
			<td width="15%">起付线</td>
			<td width="18%"><div id="pay_line"></div></td>
			<td width="15%">个人自理</td>
			<td width="19%"><div id="pay_self"></div></td>
		</tr>
		<tr>
			<td width="15%">个人支付</td>
			<td width="18%"><div id="pay_person"></div></td>
			<td width="15%">目录内费用合计</td>
			<td width="18%"><div id="pay_out"></div></td>
			<td width="15%">统筹支付</td>
			<td width="19%"><div id="pay_insurance"></div></td>
		</tr>
		<tr>
			<td width="15%">帐户支付</td>
			<td width="18%"><div id="pay_account"></div></td>
			<td width="15%">公务员统筹支付</td>
			<td width="18%"><div id="pay_gwy"></div></td>
			<td width="15%">离休统筹支付</td>
			<td width="19%"><div id="pay_lx"></div></td>
		</tr>
		<tr>
			<td width="15%">工伤统筹支付</td>
			<td width="18%"><div id="pay_gs"></div></td>
			<td width="15%">生育统筹支付</td>
			<td width="18%"><div id="pay_sy"></div></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2">本次照顾费统筹支付</td>
			<td width="15%"><div id="pay_zgf"></div></td>
			<td colspan="2">本次事业工伤统筹支付</td>
			<td width="19%"><div id="pay_sygs"></div></td>
		</tr>
		<tr>
			<td colspan="2">本次定点医疗机构分担</td>
			<td width="15%"><div id="pay_ddyljzfd"></div></td>
			<td colspan="2">本次商业保险支付金额</td>
			<td width="19%"><div id="pay_sybxzfje"></div></td>
		</tr>
		<tr>
			<td width="15%">结算人次</td>
			<td width="18%"><div id="pay_jsrc"></div></td>
			<td width="15%">基本医疗是否封项</td>
			<td width="18%"><div id="pay_fengding"></div></td>
			<td width="15%">单病种标志</td>
			<td width="19%"><div id="pay_dbz"></div></td>
		</tr>
		
	</table>
</body>
<script type="text/javascript">
 	function queryYBXX(){
 		var formParam = $("#myform").serialize();//序列化表格内容为字符串    
 		$.ajax({    
	        type:'post',        
	        url:'<%=basePath%>page/medicalafter/queryyibao.action',    
	        data:formParam,    
	        cache:false,    
	        dataType:'json',    
	        success:function(data){
	        	var dataObj=eval("("+data+")");
	        	var outflag = "";
	        	if(1==dataObj.msg){
		        	if(1==dataObj.outflag){
		        		outflag = "未出院";
		        	}else{
		        		outflag = "已出院";
		        	}
		        	if(0==dataObj.outflag&&1==dataObj.status){
		        		document.getElementById('button_js').disabled = false; 
		        	}
					document.getElementById('ybnumber').innerHTML = dataObj.ybnumber;
					document.getElementById('hospitalid').innerHTML = dataObj.hospitalid;
					document.getElementById('serialno').innerHTML = dataObj.serialno;
					document.getElementById('serialno_hid').value = dataObj.serialno;
					document.getElementById('medtype').innerHTML = dataObj.medtype;
					document.getElementById('indeptname').innerHTML = dataObj.indeptname;
					document.getElementById('begintime').innerHTML = dataObj.begintime;
					document.getElementById('diagnoseno_i').innerHTML = dataObj.diagnoseno_i;
					document.getElementById('diagnosename_i').innerHTML = dataObj.diagnosename_i;
					document.getElementById('outflag').innerHTML = outflag;
	        	}else{
	        		alert("未查到结果！");
	        	}
	        }
	    }); 
 	};
 	
 	function queryJS(){
 		var ssn = $("#ssn")[0].value;
 		var hid = $("#hid")[0].value;
 		var serialno = $("#serialno_hid")[0].value;
 		var arr ={"ssn":ssn,"hid":hid,"serialno":serialno};
 		$.ajax({    
	        type:'post',        
	        url:'<%=basePath%>page/medicalafter/queryjiesuan.action',    
	        data:arr,    
	        cache:false,    
	        dataType:'json',    
	        success:function(data){
	        	var dataObj=eval("("+data+")");
	        	var fengding_flag = "";
	        	var dbz_flag = "";
	        	if(1==dataObj.msg){
	        		if(1==dataObj.pay_fengding){
	        			fengding_flag="封顶";
	        		}else{
	        			fengding_flag="不封顶";
	        		}
	        		if(1==dataObj.pay_dbz){
	        			dbz_flag="是单病种";
	        		}else{
	        			dbz_flag="不是单病种";
	        		}
	        		document.getElementById('shoujuno_js').innerHTML = dataObj.shoujuno_js;
	        		document.getElementById('businesstype_js').innerHTML = dataObj.businesstype_js;
	        		document.getElementById('opertime_js').innerHTML = dataObj.opertime_js;
	        		document.getElementById('diagnoseno_o').innerHTML = dataObj.diagnoseno_o;
	        		document.getElementById('diagnosename_o').innerHTML = dataObj.diagnosename_o;
	        		document.getElementById('indeptname_js').innerHTML = dataObj.indeptname_js;
	        		document.getElementById('begintime_js').innerHTML = dataObj.begintime_js;
	        		document.getElementById('endtime_js').innerHTML = dataObj.endtime_js;
	        		document.getElementById('pay_total').innerHTML = dataObj.pay_total;
	        		document.getElementById('pay_account').innerHTML = dataObj.pay_account;
	        		document.getElementById('pay_insurance').innerHTML = dataObj.pay_insurance;
	        		document.getElementById('pay_person').innerHTML = dataObj.pay_person;
	        		document.getElementById('pay_gwy').innerHTML = dataObj.pay_gwy;
	        		document.getElementById('pay_lx').innerHTML = dataObj.pay_lx;
	        		document.getElementById('pay_gs').innerHTML = dataObj.pay_gs;
	        		document.getElementById('pay_sy').innerHTML = dataObj.pay_sy;
	        		document.getElementById('pay_sygs').innerHTML = dataObj.pay_sygs;
	        		document.getElementById('pay_zgf').innerHTML = dataObj.pay_zgf;
	        		document.getElementById('pay_out').innerHTML = dataObj.pay_out;
	        		document.getElementById('pay_self').innerHTML = dataObj.pay_self;
	        		document.getElementById('pay_line').innerHTML = dataObj.pay_line;
	        		document.getElementById('pay_fengding').innerHTML = fengding_flag;
	        		document.getElementById('pay_dbz').innerHTML = dbz_flag;
	        		document.getElementById('pay_jsrc').innerHTML = dataObj.pay_jsrc;
	        		document.getElementById('pay_ddyljzfd').innerHTML = dataObj.pay_ddyljzfd;
	        		document.getElementById('pay_sybxzfje').innerHTML = dataObj.pay_sybxzfje;
	        	}else{
	        		alert("未查到结果！");
	        	}
	        }
 		});
 	};
</script>
</html>