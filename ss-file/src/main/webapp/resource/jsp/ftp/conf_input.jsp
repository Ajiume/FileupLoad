<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>

<script type="text/javascript">
jQuery.validator.addMethod("linuxPath", function(value, element) {return this.optional(element) || /^(\/\w+)*\/?$/.test(value);}, $.validator.format("请输入正确的Linux路径格式"));
jQuery.validator.addMethod("httpUrl", function(value, element) {return this.optional(element) || /^((http|ftp|https):\/\/)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,4})*([a-zA-Z0-9\&%_\./-~-]*)?$/.test(value);}, $.validator.format("请输入正确的访问路径格式"));
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"ftpAddress": {
				"required":true,
				"ipv4":true,
			},
			"ftpPort": {
				"required":true,
			},
			"ftpUsername": {
				"required":true,
				"username":true,
			},
			"ftpPassword":{
				"required":true,
			},
			"rePassword":{
				"required":true,
				"equalTo":"#ftpPassword"
			},
			"ftpBasePath": {
				"required":true,
				"linuxPath":true
			},
			"ftpUrl":{
				"httpUrl":true
			},
		},
		messages: {
			"ftpAddress": {
				"required":"请填写IP地址",
				"ipv4": "请填写正确的IP地址"
			},
			"ftpPort": {
				"required":"请填写端口号"
			},
			"ftpUsername": {
				"required":"请填写用户名"
			},
			"ftpBasePath": {
				"required":"请填写基准目录"
			},
			"ftpPassword":{
				"required":"请输入密码",
			},
			"rePassword":{
				"equalTo":"两次输入的密码不一致"
			},
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
});
//返回操作
function goBack() {
	window.location.href = "<%=request.getContextPath()%>/ftp/conf/dataList";
}
</script>
<body class="input admin">
	<div class="bar">
		<c:choose>
			<c:when test="${isAdd}">
				新增FTP配置
			</c:when>
			<c:otherwise>
				修改FTP配置
			</c:otherwise>
		</c:choose>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<%=request.getContextPath()%>/ftp/conf/save" method="post">
			<input type="hidden" name="confId" value="${fileFtpConf.confId}" />
			<table class="inputTable tabContent">
				<tr>
					<th>
						ip地址: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="ftpAddress" name="ftpAddress" class="formText" title="" />
							</c:when>
							<c:otherwise>
								<input type="text" id="ftpAddress" name="ftpAddress" value="${fileFtpConf.ftpAddress}" class="formText" title="" />
							</c:otherwise>
						</c:choose>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						端口号: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="ftpPort" name="ftpPort" class="formText" onkeyup="value=value.replace(/[^\d]/g,'')" title="端口号只能是数字" />
							</c:when>
							<c:otherwise>
								<input type="text" id="ftpPort" name="ftpPort" value="${fileFtpConf.ftpPort}" class="formText" onkeyup="value=value.replace(/[^\d]/g,'')" title="端口号只能是数字"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>
						登录名: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="ftpUsername" name="ftpUsername" class="formText" title="登录名只能是英文字母、数字或下划线" />
							</c:when>
							<c:otherwise>
								<input type="text" id="ftpUsername" name="ftpUsername" value="${fileFtpConf.ftpUsername}" class="formText" title="登录名只能是英文字母、数字或下划线" />
							</c:otherwise>
						</c:choose>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						密码: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="password" id="ftpPassword" name="ftpPassword" class="formText"  />
							</c:when>
							<c:otherwise>
								<input type="password" id="ftpPassword" name="ftpPassword" value="${fileFtpConf.ftpUsername}" class="formText" />
							</c:otherwise>
						</c:choose>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						重复密码: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="password" id="rePassword" name="rePassword" class="formText"  />
							</c:when>
							<c:otherwise>
								<input type="password" id="rePassword" name="rePassword" value="${fileFtpConf.ftpUsername}" class="formText" />
							</c:otherwise>
						</c:choose>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						基准目录: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="ftpBasePath" name="ftpBasePath" class="formText"  />
							</c:when>
							<c:otherwise>
								<input type="text" id="ftpBasePath" name="ftpBasePath" value="${fileFtpConf.ftpBasePath}" class="formText"  />
							</c:otherwise>
						</c:choose>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						访问地址: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="ftpUrl" name="ftpUrl" class="formText" />
							</c:when>
							<c:otherwise>
								<input type="text" id="ftpUrl" name="ftpUrl" value="${fileFtpConf.ftpUrl}" class="formText" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="remark" name="remark" class="formText" />
							</c:when>
							<c:otherwise>
								<input type="text" id="remark" name="remark" value="${fileFtpConf.remark}" class="formText" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>

			<div class="buttonArea">
				<input type="submit" class="formButton" value="提交" />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="goBack()" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>