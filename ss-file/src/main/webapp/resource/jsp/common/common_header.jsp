<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	//response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1/*");
	response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title></title>

<link href="<%=request.getContextPath()%>/resource/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.pager.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.tools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/report.js"></script>
<!--时间与日期控制插件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/date/jquery-calendar/laydate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/datePicker/WdatePicker.js"></script>

</head>