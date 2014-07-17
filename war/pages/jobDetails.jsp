<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*, com.google.appengine.api.datastore.Entity, com.google.appengine.api.datastore.GeoPt" %>
<%@include file="/includes/header.jsp"%>
	
	<% 
		Entity job = (Entity) request.getAttribute("jobDetails");
		Map<String, Object> map = job.getProperties();
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
	%>
	
	<h1><%= map.get("title") %></h1>
	<p id="addressInfo"><%= map.get("address") %></p>
	<div id="map-canvas"></div>
		
	<div id="desc">
		<h2>Description</h2>
		<p><%= map.get("description") %></p>
	</div>
	<div id="priority">
		<h2>Priority</h2>
		<p><%= map.get("priority") %></p>
	</div>
	<div id="added">
		<h2>Date added</h2>
		<p><%= dateFormat.format((Date) map.get("dateAdded")) %></p>
	</div>
	<div id="due">
		<%
			dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		%>
		<h2>Date due</h2>
		<p><%= dateFormat.format((Date) map.get("dateDue")) %></p>
	</div>
	<div id="employee">
		<%
			String emp = (String)map.get("employee");
			if(emp==null || emp.equals("null"))
				emp = "No employee assigned";
		%>
		<h2>Employee</h2>
		<p><%= emp %></p>
	</div>
	<div id="completed">
		<h2>Completed</h2>
		<p><%= map.get("completed") %></p>
	</div>	
		
	<script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDcOWIHnbbq4mn7EXtRVhDgI36NTivx_iY&sensor=false">
    </script>
    <script type="text/javascript">
    <%
	    GeoPt loc = (GeoPt) map.get("location");
		long lat = (long)loc.getLatitude();
		long lng = (long)loc.getLongitude();
    %>
       
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(<%= loc.getLatitude() %>, <%= loc.getLongitude() %>),
          zoom: 14
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);

        var marker = new google.maps.Marker({
   			position: new google.maps.LatLng(<%= loc.getLatitude() %>, <%= loc.getLongitude() %>),
   			map: map,
   			title: ""
   		});
 
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    <style type="text/css">
    
    	#addressInfo{
    		font-size:18px;
    		font-style:italic;
    	}
    
    </style>
<%@include file="/includes/footer.jsp" %>