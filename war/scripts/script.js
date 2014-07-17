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
			
			$.post('registration', 'formData='+jsonformData+"&format=json", processData);
			
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


