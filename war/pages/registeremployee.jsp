<%@include file="/includes/header.jsp"%>
	<h1>Register employee</h1>
	<div>
		<p class="response"></p>
	</div>
	
	    <form id="theForm" class="reg"> 
		    <table >
		    	<tr>
		    		<td><label for="firstname">First name</label></td>
		    	</tr>
	   			<tr>
	   				<td><input type="text" name="firstname" id="fn" required></td>
				</tr>
				<tr>
					<td><label for="lastname">Last name</label></td>
				</tr>
	   			<tr>
	   				<td><input type="text" name="lastname" id="ln" required></td>
				</tr>
				<tr>
					<td><label for="username">Username</label></td>
				</tr>
	   			<tr>
	   				<td><input type="text" name="username" id="username"required></td>
	   			</tr>
	   			<tr>
	   				<td><label for="password">Password</label></td>
	   			</tr>
	   			<tr>
	   				<td><input type="password" name="password" id="pass"required></td>
	   			</tr>
	   			<tr>
	   				<td><label for="area">Area</label></td>
	   			</tr>
	   			<tr>
	   				<td>
		   				<select name="area">
			  				<option value = "scotland">Scotland</option>
			          		<option value = "North West">North West</option>
			          		<option value = "North East">North East</option>
			          		<option value = "Wales">Wales</option>
			          		<option value = "Midlands">Midlands</option>
			          		<option value = "Eastern">Eastern</option>
			          		<option value = "South West">South West</option>
			          		<option value = "Southern">Southern</option>
			  				<option value = "South East">South East</option>
		   				</select>
	   				</td>
	   			</tr>
	   			<tr>
	   				<td colspan="2"><input type="submit" name="sbt" value="Add New Employee"></td>
	   			</tr>
	    	</table>   
	    </form>
    
    
    <script type="text/javascript">
    $(document).ready(function(){
     	
    	var responseParagraph = $(".response");
    	
    	$('#theForm').submit(function(event){
    		
    		event.preventDefault();
    		responseParagraph.attr("id","");
    		responseParagraph.empty();
    		
    		var formData;
    		var firstname = $("#fn").val();
    		var lastname = $("#ln").val();
    		var username = $("#username").val();
    		var password = $("#pass").val();
    		
    		if (firstname=="" || lastname=="" || username=="" || password==""){
    			responseParagraph.text("Please fill in all the form fields").attr('id', 'errorResponse');
    		}
    		else {
    			
    			var jsonformData = JSON.stringify($('#theForm').serializeObject());
    			
    			//
    			
    			$.post('/registration', 'formData='+jsonformData+"&format=json", processData);
    			
    			function processData(data){
    				document.getElementById("theForm").reset();
                	responseParagraph.text(data.message).attr("id", data.styleId);
    			}
    		}
    	});
    	
    	$.fn.serializeObject = function () {
    		
    		 var json = {};
    		    var a = this.serializeArray();
    		    $.each(a, function() {
    		        if (json[this.name] !== undefined) {
    		            if (!json[this.name].push) {
    		            	json[this.name] = [json[this.name]];
    		            }
    		            json[this.name].push(this.value || '');
    		        } else {
    		        	json[this.name] = this.value || '';
    		        }
    		    });
    		    
    		    return json;
    	}
     		
    });
    </script>
<%@include file="/includes/footer.jsp"%>