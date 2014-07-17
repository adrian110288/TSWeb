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

public class ToggleJobServlet extends HttpServlet {
	
	private String format, job, emp;
	private boolean select;
	private boolean res;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		JobsDAOFactory fac = (JobsDAOFactory) DAOFactory.createFactory(DAOFactory.JOBS_DAO);
		JobDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		format = req.getParameter("format");
		select = Boolean.parseBoolean(req.getParameter("select"));
		if(select) emp = req.getParameter("empId");
		job = req.getParameter("job");
		
		if(format.equals("json")){
			resp.setContentType("application/json");
			res = dao.setEmployeeToJob(select, job , emp);
			resp.getWriter().write(res+"");

			
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
