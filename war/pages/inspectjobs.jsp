<%@include file="/includes/header.jsp"%>
<%@ page import="java.util.*, com.google.appengine.api.datastore.Entity, com.google.appengine.labs.repackaged.org.json.*" %>

	<h1>Inspect jobs</h1>
	
	<ul>
			<%
				JSONArray list = (JSONArray) request.getAttribute("jobList");
					
				for (int i=0;i<list.length();i++) {
					
					int count = i+1;
					
					JSONObject o  = list.getJSONObject(i);
					String id = (String) o.getString("id");
					String title = (String) o.getString("jobtitle");
					String address = (String) o.getString("address");
			%>	
					<li><a href="/jobDtl?jobId=<%= id%>"><%= count+". "+title+", <span>"+address+"</span>" %></a></li>
						
			<%	
				
				}
			 %>
		</ul>
		 <style type="text/css">
		 ul li span{
		 	font-style: italic;
		 }
		 </style>
<%@include file="/includes/footer.jsp"%>