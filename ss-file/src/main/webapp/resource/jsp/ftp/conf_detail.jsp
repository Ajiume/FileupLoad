<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>

<script type="text/javascript">
//返回操作
function goBack() {
	window.location.href = "<%=request.getContextPath()%>/ftp/conf/dataList";
}
</script>
<body class="input admin">
	<div class="bar">ftp配置明细</div>
	<div class="body">
		<form id="validateForm">
			<table class="inputTable tabContent">
				<tr>
					<th>ip地址:</th>
					<td>${fileFtpConf.ftpAddress}</td>
				</tr>
				<tr>
					<th>端口号:</th>
					<td>${fileFtpConf.ftpPort}</td>
				</tr>
				<tr>
					<th>登录名:</th>
					<td>${fileFtpConf.ftpUsername}</td>
				</tr>
				<tr>
					<th>基准目录:</th>
					<td>${fileFtpConf.ftpBasePath}</td>
				</tr>
				<tr>
					<th>访问地址:</th>
					<td>${fileFtpConf.ftpUrl}</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td>${fileFtpConf.remark}</td>
				</tr>
			</table>
	
			<div class="buttonArea">
				<input type="button" class="formButton" onclick="goBack()" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>