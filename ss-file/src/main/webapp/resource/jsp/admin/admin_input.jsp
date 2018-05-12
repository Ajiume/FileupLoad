<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
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
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.tools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/report.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/datePicker/WdatePicker.js"></script>


<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $tab = $("#tab");
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	
	
});
//返回操作
function goBack() {
	window.location.href = "<%=request.getContextPath()%>/admin/list.do";
}
</script>
</head>
<body class="input admin">
	<div class="bar">
		<c:choose>
			<c:when test="${isAddAction}">
				添加用户
			</c:when>
			<c:otherwise>
				查看用户
			</c:otherwise>
		</c:choose>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<%=request.getContextPath()%>/admin/update.do" method="post">
			<input type="hidden" name="fdId" value="${admin.fdId}" />
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="个人资料" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAddAction}">
								<input type="text" name="username" class="formText" title="用户名只允许包含中文、英文、数字和下划线" />
								<label class="requireField">*</label>
							</c:when>
							<c:otherwise>
								<!--  <input type="text" name="username" class="formText" value="${admin.username}" />-->${admin.username}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>
						密 码: 
					</th>
					<td>
						<input type="password" name="password" id="password" value="${admin.password}"  style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;" readonly="readonly"/>
						<!--<c:if test="${isAddAction}"><label class="requireField">*</label></c:if>-->
					</td>
				</tr>
		<!--  		<tr>
					<th>
						重复密码: 
					</th>
					<td>
						<input type="password" name="rePassword" class="formText" />
						<c:if test="${isAddAction}"><label class="requireField">*</label></c:if>
					</td>
				</tr>-->
				<tr>
					<th>
						E-mail: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAddAction}">
								<input type="text" name="email" class="formText" value="" />
							</c:when>
							<c:otherwise>
							<!--  	<input type="text" name="email" class="formText" value="${admin.email}" />-->${admin.email}
							</c:otherwise>
						</c:choose>						
					</td>
				</tr>
				<tr class="roleList">
					<th>
						管理角色: 
					</th>
					<td>
						<c:forEach items="${roleList}" var="role">
							<c:if test="${role.isCheck == 'checked'}">
							<label>
								<input type="radio" name="roleSet" value="${role.fdId}" checked  />${role.name}
							</label>
							</c:if>
							<c:if test="${role.isCheck != 'checked'}">
							<label>
								<input type="radio" name="roleSet" value="${role.fdId}" />${role.name}
							</label>
							</c:if>
						</c:forEach>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<c:if test="${admin.isAccountEnabled}"><input type="checkbox" name="isAccountEnabled" checked onclick="return false" />启用</c:if>
							<c:if test="${admin.isAccountEnabled == false}"><input type="checkbox" name="isAccountEnabled" onclick="return false" />启用</c:if>
						</label>
					</td>
				</tr>
				<c:if test="${isEditAction}">
					<tr>
						<th>&nbsp;</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>如果要修改密码,请到新MIP门户平台修改!</span>
						</td>
					</tr>
				</c:if>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						部门: 
					</th>
					<td>
						<!--  <input type="text" name="department" class="formText" value="${admin.department}" />-->${admin.department}
					</td>
				</tr>
				<tr>
					<th>
						姓名: 
					</th>
					<td>
						<!--<input type="text" name="name" class="formText" value="${admin.name}" />-->${admin.name}
					</td>
				</tr>
				<tr>
					<th>
						有效日期: 
					</th>
					<td>
						<input id="enableDate" type="text" readOnly />
							<img onclick="WdatePicker({el:'enableDate'})" src="<%=request.getContextPath()%>/resource/datePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<c:if test="${admin.username ne 'admin'}">
					<c:if test="${view ne 'y'}">
						<input type="submit" class="formButton" value="更新角色" />&nbsp;&nbsp;
					</c:if>
				</c:if>
				<input type="button" class="formButton" onclick="goBack()" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>