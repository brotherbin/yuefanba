<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName()
	+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
div.error-wrap{
	width: 500px;
	height: 305px;
	margin: 100px auto;
}
</style>
<title>404错误！您查看的网页不存在</title>
</head>
<body>
	<div class="error-wrap">
		<a href="http://www.sf-express.com">
			<img src="<%= basePath%>img/404.png"/>
		</a>
	</div>
</body>
</html>