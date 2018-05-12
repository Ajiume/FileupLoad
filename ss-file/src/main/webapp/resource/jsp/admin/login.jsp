<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Login Page</title>
</head>
<body onload="">
	<c:out value="123456" />
	<form name='f' id="f" action='j_spring_security_check' method='POST'>
		<table>
			<tr>
				<td>${ SPRING_SECURITY_LAST_EXCEPTION.message }</td>
			</tr>
			<tr>
				<td>用户名:</td><!-- ${SPRING_SECURITY_LAST_USERNAME} -->
				<td><input type='text' name='j_username' value='admin'></td>
			</tr>
			<tr>
				<td>密　码:</td>
				<td><input type='password' name='j_password' value="1" /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
		</table>
	</form>
	<script>
	//document.getElementById('submit').click();
	</script>
</body>
</html>