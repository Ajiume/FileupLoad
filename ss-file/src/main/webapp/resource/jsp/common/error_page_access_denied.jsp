<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>美的用户行为分析报表平台</title>
<link href="<%=request.getContextPath()%>/resource/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/css/report.css" rel="stylesheet" type="text/css" />
</head>
<body class="errorPage">
	<div class="body">
		<div class="errorBox">
			<div class="errorMessage">
				您无此访问权限!
			</div>
			<div class="errorUrl">点击此处<a href="javascript: void(0);" onclick="window.history.back(); return false;">返回</a>或回到<a href="<%=request.getContextPath()%>/login">登陆页面</a></div>
		</div>
	</div>
</body>
</html>