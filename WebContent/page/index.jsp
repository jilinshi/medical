<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.medical.dto.UserInfoDTO"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>吉林市医疗救助</title>
</head>
<frameset id="fullrow" rows="80,8,*,23" cols="*" frameborder="no" border="0"
	framespacing="0">
	<frame src="<%=request.getContextPath() + "/page/"%>top.jsp"
		name="topFrame" scrolling="no" noresize="noresize" id="topFrame"
		title="topFrame" />
	<frame src="<%=request.getContextPath() + "/page/"%>contop.jsp"
		name="topcon" scrolling="no" noresize="noresize" id="topcon"
		title="topcon" />
	<frameset id="fullcol" rows="*" cols="180,8,*" framespacing="0" frameborder="no"
		border="0">
		<frame src="<%=request.getContextPath()%>/page/left.jsp"
			name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame"
			title="leftFrame" />
		<frame src="<%=request.getContextPath() + "/page/"%>conleft.jsp"
			name="leftcon" scrolling="no" noresize="noresize" id="leftcon"
			title="leftcon" />
		<frame
			src="<%=request.getContextPath() + "/page/"%>detailframe.jsp?cur_page=1"
			name="mainFrame" id="mainFrame" title="mainFrame" />
	</frameset>
	<frame name="bottom"
		src="<%=request.getContextPath() + "/page/"%>bottom.jsp">
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
