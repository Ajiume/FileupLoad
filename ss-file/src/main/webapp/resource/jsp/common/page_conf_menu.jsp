<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>FTP配置管理菜单</title>


<link href="<%=request.getContextPath()%>/resource/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/css/report.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="body">
			<dl>
				<dt>
					<span>FTP配置管理</span>
				</dt>
				<dd>
					<a href="<%=request.getContextPath()%>/ftp/conf/dataList" target="mainFrame">FTP配置信息</a>
				</dd>
				<dd>
					<a href="<%=request.getContextPath()%>/ftp/file/dataList" target="mainFrame">文件信息</a>
				</dd>
				<dd>
					<a href="<%=request.getContextPath()%>/sys/model/dataList" target="mainFrame">模块信息</a>
				</dd>
			</dl>
	</div>
</body>
</html>