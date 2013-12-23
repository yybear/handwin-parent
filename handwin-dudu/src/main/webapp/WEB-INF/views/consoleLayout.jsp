<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
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
		<script type="text/javascript" src="${ctx}/static/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
		<tiles:insertAttribute name="header" ignore="true" />
		<div id="wrap">
			<tiles:insertAttribute name="content" ignore="true" />
    	</div>
		<tiles:insertAttribute name="footer" ignore="true" />
	 </body>
</html>