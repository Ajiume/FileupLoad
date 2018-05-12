<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>

<script type="text/javascript">
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
			"file": {
				"required":true,
			},
		},
		messages: {
			"file": {
				"required":"请选择上传文件",
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
	window.location.href = "<%=request.getContextPath()%>/ftp/file/dataList";
}
function checkFileSize(target) {
	var fileSize = 0;
	var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
	if (isIE && !target.files) {     
		var filePath = target.value;     
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
		var file = fileSystem.GetFile(filePath);     
		fileSize = file.Size;    
	} else {    
		fileSize = target.files[0].size;     
	}   
	if (fileSize > 10485760) {
		$.dialog({type: "warn", content: "附件不能大于10M，请重新选择！", modal: true, autoCloseTime: 3000});
		target.value="";
		return;
	}
}
</script>
<body class="input admin">
	<div class="bar">文件上传</div>
	<div class="body">
		<form id="validateForm" action="<%=request.getContextPath()%>/ftp/file/upload" method="post"
		 enctype="multipart/form-data">
			<table class="inputTable tabContent">
				<tr>
					<th>ftp服务器:</th>
					<td>
					<select id="confId" name="confId">
						<c:forEach items="${ftpConfs}" var="conf">
							<option value="${conf.confId}">${conf.ftpAddress}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<th>所属模块:</th>
					<td>
					<select id="modelName" name="modelName">
						<c:forEach items="${models}" var="model">
							<option value="${model.modelName}">${model.modelName}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<th>上传文件:</th>
					<td><input type="file" name="file" id="file" onchange="checkFileSize(this);"/></td>
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