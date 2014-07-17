package com.tracking;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.tracking.dataaccess.DatastoreJobDAO;
import com.tracking.dataaccess.JobDAO;
import com.tracking.factory.DAOFactory;
import com.tracking.factory.JobsDAOFactory;

public class SelectedJobsServlet extends HttpServlet {
	
	private String format, employee;
	private JSONArray jobsArray;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		JobsDAOFactory fac = (JobsDAOFactory) DAOFactory.createFactory(DAOFactory.JOBS_DAO);
		JobDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		format = req.getParameter("format");
		employee = req.getParameter("emp");
		
		if(format.equals("json")){
			resp.setContentType("application/json");
			jobsArray = dao.getOutstandingJobsByEmployee(employee);
			resp.getWriter().write(jobsArray.toString());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}
