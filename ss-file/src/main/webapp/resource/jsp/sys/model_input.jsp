<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>

<script type="text/javascript">
jQuery.validator.addMethod("filePath", function(value, element) {return this.optional(element) || /^((\/\w+)*)|([a-zA-Z]\:(\\\w+)*)$/.test(value);}, $.validator.format("请输入正确的路径格式(不能包含中文)"));
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
			"modelName": {
				"required":true,
				"username":true,
				"remote":{
					url: "<%=request.getContextPath()%>/sys/model/checkModelNameUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	modelName: function() {
				            return $("#modelName").val();
				        },
				        originalModelName: function() {//原始的服务器IP，新增时为空
				    		if (${sysModel == null}) {
				    			return "";
				    		} else {
					            return "${sysModel.modelName}";
				    		}
				        },
				    }
				},
			},
			"modelPath": {
				"filePath":true,
				"remote":{
					url: "<%=request.getContextPath()%>/sys/model/checkModelPathUnique",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	modelPath: function() {
				            return $("#modelPath").val();
				        },
				        originalModelPath: function() {//原始的服务器IP，新增时为空
				    		if (${sysModel == null}) {
				    			return "";
				    		} else {
					            return "${sysModel.modelPath}";
				    		}
				        },
				    }
				},
			},
		},
		messages: {
			"modelName": {
				"required":"请填写模块名称",
				"remote":"模块名称重复"
			},
			"modelPath": {
				"required":"请填写模块目录",
				"remote":"模块目录重复"
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
	window.location.href = "<%=request.getContextPath()%>/sys/model/dataList";
}
</script>
<body class="input admin">
	<div class="bar">
		<c:choose>
			<c:when test="${isAdd}">
				新增模块
			</c:when>
			<c:otherwise>
				修改模块
			</c:otherwise>
		</c:choose>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<%=request.getContextPath()%>/sys/model/save" method="post">
			<input type="hidden" name="modelId" value="${sysModel.modelId}" />
			<table class="inputTable tabContent">
				<tr>
					<th>
						模块名称: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="modelName" name="modelName" class="formText" title="" />
							</c:when>
							<c:otherwise>
								<input type="text" id="modelName" name="modelName" value="${sysModel.modelName}" class="formText" title="" />
							</c:otherwise>
						</c:choose>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						模块目录: 
					</th>
					<td>
						<c:choose>
							<c:when test="${isAdd}">
								<input type="text" id="modelPath" name="modelPath" class="formText" title="" />
							</c:when>
							<c:otherwise>
								<input type="text" id="modelPath" name="modelPath" value="${sysModel.modelPath}" class="formText" title="" />
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
								<input type="text" id="remark" name="remark" value="${sysModel.remark}" class="formText" />
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