package com.tracking.utilities;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class JSONResponseCreator {
	
	private static JSONObject res;
	
	public static JSONObject getEmpRegResponse(String resp) throws JSONException{
		
		res = new JSONObject();
		
		if (resp == null){
			res.put("message", "Employee already exists in database");
			res.put("styleId", "errorResponse");
		}
		else {
			res.put("message", "Employee successfully added to data store");
			res.put("styleId", "successfulResponse");
			res.put("employeeId", resp);
		}
		return res;
	}
	
	public static JSONObject getJobRegResponse(String resp) throws JSONException{
		
		res = new JSONObject();
		
		if (resp == null){
			res.put("message", "Error while adding new job. "+resp);
			res.put("styleId", "errorResponse");
			return res;
		}
		else {
			res.put("message", "Job added successfully");
			res.put("styleId", "successfulResponse");
			res.put("jobId", resp);
			return res;
		}
		
	}
	
	public static JSONObject getExceptionResponse(Exception e) throws JSONException{
		
		res = new JSONObject();
		
		res.put("message", e.getMessage());
		res.put("styleId", "errorResponse");
		
		return res;
	}
	
	public static JSONObject getExceptionResponse(String s) throws JSONException{
		
		res = new JSONObject();
		
		res.put("message", s);
		res.put("styleId", "errorResponse");
		
		return res;
	}
	
	public static JSONObject getStatusSubmissionResponse(String key) throws JSONException{
		
		res = new JSONObject();
		
		res.put("key", key);
		res.put("message", "Report submitted");
		res.put("submit", true);
		
		return res;
		
	}
}
