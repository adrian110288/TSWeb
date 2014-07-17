<%@page import="java.util.HashMap"%>
<%@include file="/includes/header.jsp"%>
	
	<%
		HashMap<String, String> values = (HashMap<String, String>) request.getAttribute("numericResults");
	%>
	
	
	<h1>Employee details</h1>
	<h2><%= ""+values.get("employee") %></h2>
	
	<div id="map-canvas"></div>
	
	<div id="numericDetails">
		<p>Total jobs completed: <span class="resultNumber"><%= values.get("completed") %></span><br></p>
		<p>Total jobs selected: <span class="resultNumber"><%= values.get("selected") %></span><br></p>
		<p>Total reports made: <span class="resultNumber"><%= values.get("reportsTotal") %></span><br></p>
		<p>Reports made this month: <span class="resultNumber"><%= values.get("reportsThisMonth") %></span><br></p>
	</div>
	
	<script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDcOWIHnbbq4mn7EXtRVhDgI36NTivx_iY&sensor=false">
    </script>
    
    <script type="text/javascript">
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(53.479251000000000000,-2.247926000000006700),
          zoom: 11
        };
        
        var map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
        
        var queryString = window.location.search.substring(1);
        
        $.getJSON('/markers', queryString, function(data){
        	        	        	
        	$.each(data, function(index, value){
        		
        		var marker = new google.maps.Marker({
        			position: new google.maps.LatLng(value.location.latitude, value.location.longitude),
        			map: map,
        			title: value.activity
        		});
        		
        		var infoWindow = new google.maps.InfoWindow({
        			content: '<div class="info"><h2>'+value.activity+'</h2><br><span>Job:</span> '+
        					value.job+'<br><span>Comment:</span> '+
        					value.comment+'<br><span>Date:</span> '+value.date+'</div>'
        		});
        		
        		google.maps.event.addListener(marker, 'click', function(){
        			infoWindow.open(map, marker);
        		});
        	});
        	
        });
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    <style type="text/css">
    .info{
    	width:250px;
    	height:100px;
    }
    .info span{
    	text-decoration:underline;
    }
    .info h2{
    	padding-bottom:0;
    	margin-bottom:0;
    	padding-top:0;
    	margin-top:0;    }
    </style>
    
<%@include file="/includes/footer.jsp"%>