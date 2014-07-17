package com.tracking;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tracking.dataaccess.DatastoreJobDAO;
import com.tracking.dataaccess.JobDAO;
import com.tracking.factory.DAOFactory;
import com.tracking.factory.EmployeeDAOFactory;
import com.tracking.factory.JobsDAOFactory;

@SuppressWarnings("serial")
public class AllJobsServlet extends HttpServlet {
	
	private String format, origin;
	private JSONArray jobsArray;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		JobsDAOFactory fac = (JobsDAOFactory) DAOFactory.createFactory(DAOFactory.JOBS_DAO);
		JobDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		format = req.getParameter("format");
		origin = (String) req.getAttribute("origin");
		
		
		if(format.equals("json")){
			resp.setContentType("application/json");
			jobsArray = (JSONArray) dao.getAll();
		
			if(origin != null && origin.equals("servlet")){
				
				req.setAttribute("jobList", jobsArray);		
				String url = "/pages/inspectjobs.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
				dispatcher.forward(req, resp);
			}
			else resp.getWriter().write(jobsArray.toString());
		}	
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}
