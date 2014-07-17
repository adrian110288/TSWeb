package com.tracking.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class DAOHelper {

	public static String GEOCODING_ADDRESS = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	public static String ENCODING = "UTF-8";
	public static String USER_NOT_EXIST = "Username does not exist";
	public static String PASS_NOT_CORRECT = "Incorrect password";
	public static int MAX_RADIUS = 30000;
	
	public static JSONObject getJobJSONFromEntity(Entity temp){
		
		JSONObject tempJson = new JSONObject();
		
		try {
			tempJson.put("id", temp.getKey().getId());
			tempJson.put("jobtitle", temp.getProperty("title"));
			tempJson.put("jobdescription", temp.getProperty("description"));
			tempJson.put("address", temp.getProperty("address"));
			
			GeoPt pt = (GeoPt) temp.getProperty("location");
			
			tempJson.put("latitude", pt.getLatitude());
			tempJson.put("longitude", pt.getLongitude());
			tempJson.put("employeeId", temp.getProperty("employee"));
			tempJson.put("completed", temp.getProperty("completed"));
			
			Date added = (Date) temp.getProperty("dateAdded");
			
			tempJson.put("dateadded", ""+added.getDate()+"/"+added.getMonth()+"/"+added.getYear());
			
			Date due = (Date) temp.getProperty("dateDue");
			
			tempJson.put("dateDue", ""+due.getDate()+"/"+due.getMonth()+"/"+due.getYear());
			tempJson.put("priority", temp.getProperty("priority"));
		
		} catch (JSONException e) {
			e.getMessage();
		}
		
		return tempJson;
	}
	
	public static JSONObject getEmployeeReportJSONFromEntity(Entity temp){
		
		JSONObject tempJson = new JSONObject();
		
		try{
			tempJson.put("id", temp.getKey().getId());
			tempJson.put("activity", temp.getProperty("activity"));
			tempJson.put("comment", temp.getProperty("comment"));
			
			//Date date = (Date) temp.getProperty("date");
			
			//Date d = new Date(temp.getProperty("date").toString());
			tempJson.put("date", temp.getProperty("date"));
			
			tempJson.put("employee", temp.getProperty("employee"));
			
			if(temp.hasProperty("job") && (temp.getProperty("job") != null || (!(temp.getProperty("job")).equals("")))){
				tempJson.put("job", temp.getProperty("job"));
			}
			
			JSONObject locJson = new JSONObject();
			locJson.put("latitude", temp.getProperty("latitude"));
			locJson.put("longitude", temp.getProperty("longitude"));
			tempJson.put("location", locJson);
			
		}
		catch(JSONException jsone){
			jsone.printStackTrace();
		}

		return tempJson;
	}
	
	public static Entity createEntityFromHashMap(HashMap<String, Object> mapIn, String entityKind) {
		
		Iterator<String> set = mapIn.keySet().iterator();
		Entity e = new Entity(entityKind);
		
		while(set.hasNext()) {
		
			String key = set.next();
			
			if (key.equals("password")){
				
		        e.setProperty(key, encryptPassword((String)mapIn.get(key), "MD5"));
		        continue;
			}
			
			if (key.equals("Location")) {
				
				e.setProperty("latitude", ((GeoPt)mapIn.get("Location")).getLatitude());
				e.setProperty("longitude", ((GeoPt)mapIn.get("Location")).getLongitude());
		        continue;	
			}
			
			e.setProperty(key, mapIn.get(key));
		}
			
		return e;
		}
		
		
	
	
	public static String encryptPassword(String passwordIn, String encryptionMethod) {
		
		MessageDigest md;
		StringBuffer md5Password = new StringBuffer();
		
		try {
			md = MessageDigest.getInstance(encryptionMethod);
			md.update(passwordIn.getBytes());
			 
	        byte byteData[] = md.digest();
	        
	        for (int i = 0; i < byteData.length; i++) {
	        
	        	md5Password.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
		} 
        catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		return md5Password.toString();
	}
	
	public static String addressCoord(String address) throws IOException {
		
		URL url = new URL(DAOHelper.GEOCODING_ADDRESS+address+"&sensor=true");
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		String jsonResult = "";
		while ((inputLine = in.readLine()) != null) {
		    jsonResult += inputLine;
		}
		in.close();
		return jsonResult; 
	}
	
}
