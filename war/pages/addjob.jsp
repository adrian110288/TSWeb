<%@include file="/includes/header.jsp"%>
	<h1>Add job</h1>
	<div>
		<p class="response"></p>
	</div>
	
    <form id="jobForm" class="reg">
    	<table >
    		<tr>
    			<td><label for="jobtitle">Title</label></td>
    		</tr>
   			<tr>
   				<td><input type="text" name="jobtitle" required></td>
			</tr>
			<tr>
				<td><label for="jobaddress">Address</label></td>
			</tr>
   			<tr>
   				<td><input type="text" name="jobaddress" required></td>
   			</tr>
			<tr>
				<td><label for="jobdescription">Description</label></td>
			</tr>
   			<tr>
   				<td><textarea rows="5" cols="17"  name="jobdescription" required></textarea></td>
			</tr>
			
 			<tr>
				<td><label for="jobdatedue">Date due</label></td>
			</tr>
   			<tr>
   				<td><input type="text" name="jobdatedue" readonly="readonly" id="datepicker" required></td>
   			</tr>
   			<tr>
   				<td colspan="2"><input type="submit" name="sbt" value="Add Job" onsubmit=process()></td>
   			</tr>
    	</table>   
    </form>
    
    <script type="text/javascript">
    	$('document').ready(function(){
    		
    		$( "#datepicker" ).datepicker({
    		      changeMonth: true,
    		      changeYear: true,
    		      dateFormat: "dd/mm/yy"
    		    });
    		
    		var responseParagraph = $(".response");
    		
    		$('#jobForm').submit(function(e){
    			
    			e.preventDefault();
    			responseParagraph.attr("id","");
    			responseParagraph.empty();
    			
    			var jsonformData = JSON.stringify($('#jobForm').serializeObject());
    			
    			$.post('/jobRegistration', 'formData='+jsonformData+"&format=json", processData);
    			
    			function processData(data){
    				document.getElementById("jobForm").reset();
                	responseParagraph.text(data.message).attr("id", data.styleId);
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