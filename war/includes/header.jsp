<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Tracking System Project</title>
		
		<link rel="stylesheet" type="text/css" href="<%= contextPath %>/styles/global.css" media="screen" />
		<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
		
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDcOWIHnbbq4mn7EXtRVhDgI36NTivx_iY&sensor=false">
    </script>
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
<%-- 				<img src="<%= contextPath %>/images/globe.png" alt="" title="" /> --%>
				<h1>Tracking System Project</h1>
			</div>
			<div id="nav">
				
				<ul>
					<li><a id="nav-first-child" href="<%= contextPath %>/index.jsp">Home</a></li>
					<li><a href="<%= contextPath %>/pages/addjob.jsp">Add new job</a></li>
					<li><a href="<%= contextPath %>/pages/registeremployee.jsp">Add new employee</a></li>
					<li><a href="<%= contextPath %>/allemployees">Inspect employees</a></li>
					<li><a href="<%= contextPath %>/alljobswebsite?format=json">Inspect jobs</a></li>
					
				</ul>
			</div>
			<div id="mainContainer">
		

