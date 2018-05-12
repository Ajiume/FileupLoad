<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ss.file.util.PropertyUtil"%>
<%
	String loginName = null;
	String userName = null;
	String deptName = null;
	Map<String,Cookie> cookieMap = null;
	Cookie[] cookies = request.getCookies();
	if(cookies != null){
		cookieMap = new HashMap<String,Cookie>();
	    for(Cookie cookie : cookies){
	        cookieMap.put(cookie.getName(), cookie);
	    }
	}
	
	if (cookieMap != null && cookieMap.size() > 0) {
		if(cookieMap.containsKey(PropertyUtil.loadProperties(PropertyUtil.COOKIE_NAME))){
	        Cookie cookie = (Cookie)cookieMap.get(PropertyUtil.loadProperties(PropertyUtil.COOKIE_NAME));
	        String tmp = cookie.getValue();
	        String[] userInfo = tmp.split("@@@");
	        loginName = userInfo[0];
	        userName = userInfo[1];
	        deptName = java.net.URLDecoder.decode(userInfo[2], "UTF-8");
	    }
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>文件管理系统</title>

<link href="<%=request.getContextPath()%>/resource/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/report.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $menuItem = $("#menu .menuItem");
	var $previousMenuItem;
	var depValue=$("#depName").val();
	$menuItem.click( function() {
		var $this = $(this);
		if ($previousMenuItem != null) {
			$previousMenuItem.removeClass("current");
		}
		$previousMenuItem = $this;
		$this.addClass("current");
	})
	if(depValue == null || depValue == "null"){
		depValue="";
	}
	$("#depNameStrong").html(depValue);
})
</script>
</head>
<body class="header">
	<div class="body">
		<div class="bodyLeft">
			<div class="logo"></div>
		</div>
		<div class="bodyRight" style="float:right;margin-right:30px;">
		<div class="link">
				<span class="welcome">
					<strong><%=userName%></strong>&nbsp;您好!&nbsp;
				</span>
				<span class="welcome">
					部门:&nbsp;<strong id="depNameStrong"></strong>
				</span>
				<input id="depName" type="hidden" value="<%=deptName%>"/>
			</div>
			<div id="menu" class="menu">
				<ul>
					<li class="menuItem">
						<a href="<%=request.getContextPath()%>/ftp/confMenu" target="menuFrame" hidefocus>文件管理</a>
					</li>
					<li class="home">
						<a href="<%=request.getContextPath()%>/ftp/index" target="mainFrame" hidefocus>后台首页</a>
					</li>
	            </ul>
	            <div class="info">
					<a class="profile" href="<%=request.getContextPath()%>/admin/edit.do?view=y&id=<sec:authentication property='principal.fdId' />" target="mainFrame">个人资料</a>
					<a class="logout" href="<%=request.getContextPath()%>/ftp/logout" target="_top">退出</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>