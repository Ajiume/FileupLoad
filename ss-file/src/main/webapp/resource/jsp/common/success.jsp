<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>

<script type="text/javascript">
var logInfo = "${redirectObject.logInfo}";

$().ready( function() {

	function redirectUrl() {
		<c:choose>
			<c:when test="${redirectObject.redirectUrl != null}">
				window.location.href = "<%=request.getContextPath()%>/${requestScope.redirectObject.redirectUrl}";
			</c:when>
			<c:otherwise>
				window.history.back();
			</c:otherwise>
		</c:choose>
	}
	
	$.dialog({type: "success", title: "操作提示", content: logInfo, ok: "确定", okCallback: redirectUrl, cancelCallback: redirectUrl, width: 380, modal: true});

});
</script>
</head>
<body class="success">
</body>
</html>