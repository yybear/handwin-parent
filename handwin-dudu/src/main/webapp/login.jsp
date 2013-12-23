<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title> </title>
		<meta content="width=device-width, initial-scale=1.0" name="viewport">
		<meta content=" " name="description">
		<meta content=" " name="author">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/base.css">
	</head>
	
	<body>
		<div class="container">
			<%
				if("1".equals(request.getParameter("r"))) {
			%>
				<div class="alert alert-warning">登陆失败！</div>
			<%
				}
			%>
			<form class="form-signin" role="form" method="post" action='${ctx}/login?callback=<%=request.getParameter("callback") %>'>
				<h2 class="form-signin-heading">嘟嘟管理员登陆</h2>
				<input type="text" class="form-control" placeholder="账号"
					required="true" autofocus="" name="account"> 
				<input type="password" name="passwd"
					class="form-control" placeholder="密码" required="true"> 
					<!-- <label class="checkbox"> <input type="checkbox" value="remember-me"> Remember me </label>-->
				<button class="btn btn-lg btn-primary btn-block" type="submit">登陆</button>
			</form>
	
		</div>
	</body>
</html>