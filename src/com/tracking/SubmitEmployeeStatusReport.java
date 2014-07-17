package com.tracking;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tracking.dataaccess.DatastoreEmployeeReportDAO;
import com.tracking.dataaccess.EmployeeReportDAO;
import com.tracking.dataaccess.JobDAO;
import com.tracking.factory.DAOFactory;
import com.tracking.factory.EmployeeReportsDAOFactory;
import com.tracking.factory.JobsDAOFactory;
import com.tracking.utilities.JSONResponseCreator;

public class SubmitEmployeeStatusReport extends HttpServlet {

	private DatastoreEmployeeReportDAO dao;
	private String format, params;
	private String emp, date, lat, lng, act, job, comm;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		EmployeeReportsDAOFactory fac = (EmployeeReportsDAOFactory) DAOFactory.createFactory(DAOFactory.STATUS_REPORTS_DAO);
		EmployeeReportDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		format = req.getParameter("format");
		params = req.getParameter("params");
		
		try {
			JSONObject formData = new JSONObject(params);
			
			String[] keys = formData.getNames(formData);
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			for(int i=0;i<keys.length; i++) {
				map.put(keys[i], formData.getString(keys[i]));
			}	
			
			GeoPt loc = new GeoPt(Float.parseFloat((String) map.get("lat")), Float.parseFloat((String) map.get("long")));
			
			map.remove("lat");
			map.remove("long");
			map.put("Location", loc);

			if(format.equals("json")) {
				resp.setContentType("application/json");
						
				String key = dao.addNew(map);
				resp.getWriter().write(JSONResponseCreator.getStatusSubmissionResponse(key).toString());		
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}
}
