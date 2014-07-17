<%@include file="/includes/header.jsp"%>
<%@ page import="java.util.*, com.google.appengine.api.datastore.Entity" %>

	<h1>Inspect employees</h1>
	
	<div id="employeeSearch">
		<form action="" method="get">
			<label>Search employee by</label>
			<select name="srchcrit">
  				<option value = "fname">first name</option>
          		<option value = "lname">last name</option>
          		<option value = "pno">phone number</option>
          		<option value = "login">user name</option>
			</select>
			<input type="text" name="search-field" autofocus="autofocus" >
			<input type="submit" title="search" value="Search">
		</form>
	</div>
		<ul>
			<%
				List<Entity> list = (List<Entity>) request.getAttribute("employeeList");
				ListIterator i = list.listIterator();
		
				int count = 1;
			
				while(i.hasNext()){
					Entity e = (Entity)i.next();
					String fname = (String) e.getProperty("firstname");
					String lname = (String) e.getProperty("lastname");
			%>	
					<li><a href="/empDtl?empId=<%= e.getKey().getId()%>"><%= count+". "+fname+" "+lname %></a></li>
						
			<%	
				count++;
				}
			 %>
		</ul>
		
		<script type="text/javascript">
			
		$(document).ready(function(){
				
			var button = '<input type="button" value="Delete employee" class="btnEmp" id="btnVisible">';
			var employeeIdtoDelete;
			var deleteBtn;
			
			$("#mainContainer ul").each(function(){
				
				$(this).find('li').each(function(){
					$(this).hover(function(){
												
						$(this).append(button);
						employeeIdtoDelete = $('#btnVisible').prev().attr('href').substring(14);
						
						var deleteBtn = $(this).children().last();
						
						deleteBtn.click(function(){
							
							$.post('/employeeDelete', 'employeeId='+employeeIdtoDelete, processData);
			    			
			    			function processData(data){
			    				
			    				if (data == "true")
			    				
			    					deleteBtn.parent().remove();
			    			}
						});
						
					}, function(){
						$('#btnVisible').remove();
					});
				});
			});

			});
		
		
		</script>
	
	
<%@include file="/includes/footer.jsp"%>