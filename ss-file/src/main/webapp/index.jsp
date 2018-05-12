<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript">
	$(function() {
		$("#initForm").submit();
	});
</script>
</head>
<body>
	<form id="initForm" action="<%=request.getContextPath()%>/login"></form>
</body>
</html>