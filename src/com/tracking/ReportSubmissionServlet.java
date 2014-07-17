package com.tracking;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tracking.dataaccess.DatastoreJobReportsDAO;

public class ReportSubmissionServlet extends HttpServlet{

	private String format, data;
	private DatastoreJobReportsDAO dao;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		dao = new DatastoreJobReportsDAO();
		
		format = req.getParameter("format");
		data = req.getParameter("data");
		
		try {
			JSONObject formData = new JSONObject(data);
			
			String[] keys = formData.getNames(formData);
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			for(int i=0;i<keys.length; i++) {
				map.put(keys[i], formData.getString(keys[i]));
			}
			
			String keyId = dao.addNew(map);
			boolean submitted = keyId!=null ? true : false;
			
			resp.getWriter().write(submitted+"");
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
