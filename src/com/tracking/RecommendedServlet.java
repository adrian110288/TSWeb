package com.tracking;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tracking.dataaccess.DatastoreJobDAO;
import com.tracking.dataaccess.JobDAO;
import com.tracking.factory.DAOFactory;
import com.tracking.factory.JobsDAOFactory;

@SuppressWarnings("serial")
public class RecommendedServlet extends HttpServlet {
	
	private String format, lat, lng;
	private JSONArray jobsArray;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		JobsDAOFactory fac = (JobsDAOFactory) DAOFactory.createFactory(DAOFactory.JOBS_DAO);
		JobDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		format = req.getParameter("format");
		lat = req.getParameter("lat");
		lng = req.getParameter("lng");
		
		if(format.equals("json")){
			resp.setContentType("application/json");
			jobsArray = dao.getRecommended(Double.parseDouble(lat), Double.parseDouble(lng));
			resp.getWriter().write(jobsArray.toString());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}