<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>显示/隐藏菜单</title>

<link href="<%=request.getContextPath()%>/resource/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/report.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $main = $("#main");

	$main.click( function() {
		var mainFrameset = window.parent.window.document.getElementById("mainFrameset");
		if(mainFrameset.cols == "130,6,*") {
			mainFrameset.cols = "0,6,*";
			$main.removeClass("leftArrow");
			$main.addClass("rightArrow");
		} else {
			mainFrameset.cols = "130,6,*";
			$main.removeClass("rightArrow");
			$main.addClass("leftArrow");
		}
	})

})
</script>
<style type="text/css">
<!--
html, body {
	height: 100%;
	overflow:hidden;
}
-->
</style>
</head>
<body class="middle">
	<div id="main" class="main leftArrow"></div>
</body>
</html>