<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);

	String message = null;
	if (request.getAttribute("error") != null) {
		message = (String) request.getAttribute("error");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>文件管理系统</title>
<meta name="Author" content="midea" />
<meta name="Copyright" content="midea" />
<link href="<%=request.getContextPath()%>/resource/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/resource/css/report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/report.js"></script>
<script type="text/javascript">
$().ready( function() {
	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $isRememberUsername = $("#isRememberUsername");

	// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
	if(getCookie("adminUsername") != null) {
		$isRememberUsername.attr("checked", true);
		$username.val(getCookie("adminUsername"));
		$password.focus();
	} else {
		$isRememberUsername.attr("checked", false);
		$username.focus();
	}

	// 提交表单验证,记住登录用户名
	$loginForm.submit( function() {
		if ($username.val() == "") {
			$.dialog({type: "warn", content: "请输入您的用户名!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($password.val() == "") {
			$.dialog({type: "warn", content: "请输入您的密码!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($captcha.val() == "") {
			$.dialog({type: "warn", content: "请输入您的验证码!", modal: true, autoCloseTime: 3000});
			return false;
		}
		
		if ($isRememberUsername.attr("checked") == true) {
			var expires = new Date();
			//expires.setTime(expires.getTime() + 1000 * 60 * 60 * 24 * 7);
			//setCookie("adminUsername", $username.val(), expires);
		} else {
			//removeCookie("adminUsername");
		}
		
	});

	// 刷新验证码 code
	$captchaImage.click( function() {
		var timestamp = (new Date()).valueOf();
		var imageSrc = $captchaImage.attr("src");
		if(imageSrc.indexOf("?") >= 0) {
			imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
		}
		imageSrc = imageSrc + "?timestamp=" + timestamp;
		$captchaImage.attr("src", imageSrc);
	});

	<%if (message != null) {%>
		$.dialog({type: "error", content: "<%=message%>", modal: true, autoCloseTime: 3000});
	<%}%>

});
</script>
</head>
<body class="login">
	<script type="text/javascript">

		// 登录页面若在框架内，则跳出框架
		if (self != top) {
			top.location = self.location;
		};

	</script>
	<div class="blank"></div>
	<div class="blank"></div>
	<div class="blank"></div>
	<div class="body">
		<form id="loginForm" action="<%=request.getContextPath()%>/ftp/sysUser/login" method="post">
            <table class="loginTable">
            	<tr>
            		<td rowspan="3">
            			<img src="<%=request.getContextPath()%>/resource/images/login_logo.png" title="文件管理系统" />
            		</td>
                    <th>
                    	用户名:
                    </th>
					<td>
                    	<input type="text" id="username" name="z_Name" class="formText" />
                    </td>
                </tr>
                <tr>
					<th>
						密&nbsp;&nbsp;&nbsp;码:
					</th>
                    <td>
                    	<input type="password" id="password" name="z_Pass" class="formText" />
                    </td>
                </tr>
                <tr>
                	<th>
                		&nbsp;
                	</th>
                    <td>
                    	&nbsp;
                    </td>
                </tr>
                <tr>
                	<td>
                		&nbsp;
                	</td>
                	<th>
                		&nbsp;
                	</th>
                    <td>
                    	<label>
                    		<input type="checkbox" id="isRememberUsername" />&nbsp;记住用户名
                    	</label>
                    </td>
                </tr>
                <tr>
                	<td>
                		&nbsp;
                	</td>
                	<th>
                		&nbsp;
                	</th>
                    <td>
                        <input type="button" class="homeButton" value="" hidefocus /><input type="submit" class="submitButton" value="登 录" hidefocus />
                    </td>
                </tr>
            </table>
        </form>
	</div>
</body>
</html>