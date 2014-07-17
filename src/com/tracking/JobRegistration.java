package com.tracking;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.mortbay.util.ajax.JSON;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.labs.repackaged.org.json.*;
import com.tracking.dataaccess.*;
import com.tracking.factory.*;
import com.tracking.utilities.*;

public class JobRegistration extends HttpServlet {

	private String jsonData, format;
	private static final Logger log = Logger.getLogger(JobRegistration.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		JobsDAOFactory fac = (JobsDAOFactory) DAOFactory.createFactory(DAOFactory.JOBS_DAO);
		JobDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		format = req.getParameter("format");
		
		if (format.equals("json")) {
			resp.setContentType("application/json");
		
			try{
				jsonData = req.getParameter("formData");
				JSONObject formData = new JSONObject(jsonData);
				
				String[] keys = formData.getNames(formData);
				HashMap<String, Object> map = new HashMap<String, Object>();
				
				for(int i=0;i<keys.length; i++) {
					map.put(keys[i], formData.getString(keys[i]));
				}
	
				String encodedAddress = URLEncoder.encode((String)map.get("jobaddress"), DAOHelper.ENCODING);
				
				JSONObject geocoding = new JSONObject(addressCoord(encodedAddress));
								
				if(geocoding.getString("status").equals("OK")){
					JSONArray array = geocoding.getJSONArray("results");
					JSONObject results = array.getJSONObject(0);
					JSONObject location = results.getJSONObject("geometry").getJSONObject("location");
					String lat = location.getString("lat");
					String lng = location.getString("lng");
					
					map.put("location", new GeoPt(Float.parseFloat(lat), Float.parseFloat(lng)));
					map.remove("jobaddress");					
					map.put("jobaddress", array.getJSONObject(0).getString("formatted_address"));
									
					String jobIdResp = dao.addNew(map);

					resp.getWriter().write(JSONResponseCreator.getJobRegResponse(jobIdResp).toString());
				}
				
				else if(geocoding.getString("status").equals("OVER_QUERY_LIMIT")){ 
					resp.getWriter().write(JSONResponseCreator.getExceptionResponse(geocoding.getString("error_message").toString()).toString());
				}
				
				else if(geocoding.getString("status").equals("ZERO_RESULTS")){ 
					resp.getWriter().write(JSONResponseCreator.getExceptionResponse("Address not found").toString());
				}
			}		
			catch(com.google.appengine.labs.repackaged.org.json.JSONException jsonException) {
				try {
					resp.getWriter().write(JSONResponseCreator.getExceptionResponse(jsonException).toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String addressCoord(String address) throws IOException {
		
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
