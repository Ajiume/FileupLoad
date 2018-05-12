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

<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $allChecked = $("#validateForm .allChecked");
	
	$allChecked.click( function() {
		var $this = $(this);
		var $thisCheckbox = $this.parent().parent().find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.attr("checked", false);
		} else {
			$thisCheckbox.attr("checked", true);
		}
		return false;
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"role.name": "required"
		},
		messages: {
			"role.name": "请填写角色名称"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请至少选择一个菜单权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
})
</script>

<style>
	fieldset {
		margin:0px 5px;
		padding-bottom:5px;
		padding-top:5px;
		padding-left:5px;
		padding-right:5px;
		border-top:#99bbe8 1px solid;
		border-right:#99bbe8 1px solid;
		border-bottom:#99bbe8 1px solid;
		border-left:#99bbe8 1px solid;
	}

	legend {
		font-size:1em;
		color:#f30;
	}	
</style>
</head>
<body class="input role">
	<div class="bar">
		<c:choose>
			<c:when test="${isAddAction}">
				添加角色
			</c:when>
			<c:otherwise>
				编辑角色
			</c:otherwise>
		</c:choose>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<c:choose><c:when test='${isAddAction}'><%=request.getContextPath()%>/role/save.do</c:when><c:otherwise><%=request.getContextPath()%>/role/update.do</c:otherwise></c:choose>" method="post">
			<input type="hidden" name="fdId" value="${recordRole.fdId}" />
			<table class="inputTable">
				<tr>
					<th>
						角色名称: 
					</th>
					<td>
						<input type="text" name="name" class="formText" value="${recordRole.name}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述: 
					</th>
					<td>
						<textarea name="description" class="formTextarea">${recordRole.description}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
			</table>
			<fieldset>
			<legend>菜单权限</legend>
			<table class="inputTable">
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">系统管理: </a>
					</th>					
					<td>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_ADMIN')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_ADMIN"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_ADMIN')}"> checked</c:if> />管理员管理
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_ROLE')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_ROLE"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_ROLE')}"> checked</c:if> />角色管理
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_QUARTZ_CONFIG')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_QUARTZ_CONFIG"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_QUARTZ_CONFIG')}"> checked</c:if> />定时任务管理
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_QUARTZ_LOG')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_QUARTZ_LOG"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_QUARTZ_LOG')}"> checked</c:if> />日志管理
							</label>
						</c:if>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">基础模块管理: </a>
					</th>					
					<td>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_BASE_PORTAL')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_BASE_PORTAL"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_BASE_PORTAL')}"> checked</c:if> />门户信息管理
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_BASE_PAGE')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_BASE_PAGE"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_BASE_PAGE')}"> checked</c:if> />页面信息管理
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_BASE_MODULE')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_BASE_MODULE"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_BASE_MODULE')}"> checked</c:if> />模块信息管理
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_BASE_PORTLET')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_BASE_PORTLET"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_BASE_PORTLET')}"> checked</c:if> />部件信息管理
							</label>
						</c:if>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">用户日志报表管理: </a>
					</th>					
					<td>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_LOG_USER')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_LOG_USER"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_LOG_USER')}"> checked</c:if> />用户日志报表
							</label>
						</c:if>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">操作日志报表管理: </a>
					</th>					
					<td>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_LOG_PORTAL')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_LOG_PORTAL"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_LOG_PORTAL')}"> checked</c:if> />门户日志报表
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_LOG_PAGE')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_LOG_PAGE"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_LOG_PAGE')}"> checked</c:if> />页面日志报表
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_LOG_MODULE')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_LOG_MODULE"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_LOG_MODULE')}"> checked</c:if> />模块日志报表
							</label>
						</c:if>
						<c:if test="${fn:contains(role.authorityListStore, 'ROLE_LOG_PORTLET')}">
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="ROLE_LOG_PORTLET"<c:if test="${fn:contains(recordRole.authorityListStore, 'ROLE_LOG_PORTLET')}"> checked</c:if> />部件日志报表
							</label>
						</c:if>
					</td>
				</tr>
			</table>
			</fieldset>
			<br />			
			<c:if test="${recordRole.isSystem}">
				<table>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示: </b>系统内置角色不允许修改!</span>
							</td>
						</tr>
				</table>
			</c:if>
			<div class="buttonArea">
							<c:choose>
								<c:when test="${recordRole.isSystem}">
									&nbsp;
								</c:when>
								<c:otherwise>
									<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
								</c:otherwise>
							</c:choose>
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>