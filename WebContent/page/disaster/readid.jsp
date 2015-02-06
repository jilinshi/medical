<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>二代证读卡</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
<link href="../css/CSS.CSS" rel="stylesheet" type="text/css" />
<link href="../css/style1.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript">
	function MyGetData()//OCX读卡成功后的回调函数
	{ //aname.value = GT2ICROCX.get_Name();//<-- 姓名--!>
		aname.value = GT2ICROCX.Name;//<-- 姓名--!>		      
		namel.value = GT2ICROCX.NameL;//<-- 姓名--!>		      
		sex.value = GT2ICROCX.Sex;//<-- 性别--!>		
		sexl.value = GT2ICROCX.SexL;//<-- 性别--!>	
		nation.value = GT2ICROCX.Nation;//<-- 民族--!>		
		nationl.value = GT2ICROCX.NationL;//<-- 民族--!>	
		born.value = GT2ICROCX.Born;//<-- 出生--!>	
		bornl.value = GT2ICROCX.BornL;//<-- 出生--!>
		address.value = GT2ICROCX.Address;//<-- 地址--!>
		cardno.value = GT2ICROCX.CardNo;//<-- 卡号--!>
		police.value = GT2ICROCX.Police;//<-- 发证机关--!>
		activity.value = GT2ICROCX.Activity;//<-- 有效期--!>
		activitylfrom.value = GT2ICROCX.ActivityLFrom;//<-- 有效期--!>
		activitylto.value = GT2ICROCX.ActivityLTo;//<-- 有效期--!>
		photo.value = GT2ICROCX.GetPhotoBuffer();

		//jpgPhotoFace1.value = GT2ICROCX.GetFaceJpgBase64(0);//双面
		//jpgPhotoFace1.value = GT2ICROCX.GetFaceJpgBase64(1);//正面
		//jpgPhotoFace2.value = GT2ICROCX.GetFaceJpgBase64(2);//反面	
	}

	function MyClearData()//OCX读卡失败后的回调函数
	{
		aname.value = "";
		namel.value = ""
		sex.value = "";
		sexl.value = "";
		nation.value = "";
		nationl.value = "";
		born.value = "";
		bornl.value = "";
		address.value = "";
		cardno.value = "";
		police.value = "";
		activity.value = "";
		activitylfrom.value = "";
		activitylto.value = "";
		photo.value = "";
		jpgPhotoFace1.value = "";
		jpgPhotoFace2.value = "";
	}

	function MyGetErrMsg()//OCX读卡消息回调函数
	{
		Status.value = GT2ICROCX.ErrMsg;
	}

	function StartRead()//开始读卡
	{
		GT2ICROCX.PhotoPath = "C:\\PhotoPath"

		GT2ICROCX.Start(); //循环读卡

		//单次读卡(点击一次读一次)

		//if (GT2ICROCX.GetState() == 0){
		//	GT2ICROCX.ReadCard()	
		//}
	}

	function print()//打印
	{
		GT2ICROCX.PrintFaceImage(0, 30, 10);//0 双面，1 正面，2 反面
	}
</Script>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetData>
	//设置回调函数
	MyGetData();
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=GetErrMsg>
	//设置回调函数
	MyGetErrMsg();
</SCRIPT>

<SCRIPT LANGUAGE=javascript FOR=GT2ICROCX EVENT=ClearData>
	//设置回调函数
	MyClearData();
</SCRIPT>
<script type="text/javascript">
	function deWrite() {
		 var arr = new Array();
		 arr[0] = document.getElementById('a').value;
		 arr[1] =document.getElementById('b').value;
		 arr[2] = document.getElementById('c').value;
		 arr[3] =document.getElementById('d').value;
		 arr[4] =document.getElementById('e').value;
		 arr[5] =document.getElementById('f').value;
		 arr[6] =document.getElementById('g').value;
		 arr[7] =document.getElementById('h').value;
		 arr[8] ='1';
		 window.returnValue=arr;

	}
</script>
</head>
<body onload="StartRead();deWrite();" onunload="deWrite()">
	<TABLE WIDTH=750 BORDER=1 align="center" CELLPADDING=0 CELLSPACING=0>
		<tr>
			<td width="600">
				<table width="600" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="30"><input type=hidden name=aname size=20
							maxlength=20> 姓名： <input type=text id="a" name=namel
							size=20 maxlength=20 readonly="readonly" onchange="deWrite()">
						</td>
					</tr>
					<tr>
						<td height="30"><input type=hidden name=sex size=5
							maxlength=5> 性别： <input id=f type=text name=sexl size=5
							maxlength=5 readonly="readonly"></td>
					</tr>
					<tr>
						<td height="30"><input type=hidden name=nation size=20
							maxlength=20> 民族： <input id=g type=text name=nationl
							size=20 maxlength=20 readonly="readonly"></td>
					</tr>
					<tr>
						<td height="30"><input type=hidden name=born size=20
							maxlength=20> 出生日期： <input id=h type=text name=bornl
							size=20 maxlength=20 readonly="readonly"></td>
					</tr>
					<tr>
						<td height="30">户口所在地： <input id=d type=text name=address
							size=50 maxlength=50 readonly="readonly">
						</td>
					</tr>
					<tr>
						<td height="30">身份证号： <input id="b" type=text name=cardno
							size=50 maxlength=50 readonly="readonly">
						</td>
					</tr>
					<tr>
						<td height="30">颁发机构： <input id=e type=text name=police
							size=50 maxlength=50 readonly="readonly">
						</td>
					</tr>
					<tr>
						<td height="30">有效期： <input type=text name=activity size=50
							maxlength=50 readonly="readonly">
						</td>
					</tr>
					<tr>
						<td height="30">开始时间： <input type=text name=activitylfrom
							size=50 maxlength=50 readonly="readonly">
						</td>
					</tr>
					<tr>
						<td height="30">结束时间： <input type=text name=activitylto
							size=50 maxlength=50 readonly="readonly">
						</td>
					</tr>
					<tr>
						<td height="30">状态： <input id="c" type=hidden name=photo
							maxlength=50> <input type=hidden name=jpgPhotoFace1
							size=50 maxlength=50> <input type=hidden
							name=jpgPhotoFace2 size=50 maxlength=50> <input type=text
							name=Status size=50 maxlength=50>
						</td>
					</tr>
				</table>
			</td>
			<td width="112" align="center"><OBJECT Name="GT2ICROCX"
					width="102" height="126"
					CLASSID="CLSID:220C3AD1-5E9D-4B06-870F-E34662E2DFEA"
					CODEBASE="IdrOcx.cab#version=1,0,1,2"> </OBJECT> <input
				type="button" value="读卡" onClick="StartRead();deWrite();"> <input
				type="button" value="打印" onClick="print();deWrite();"></td>
		</tr>
	</table>
</body>
</html>
