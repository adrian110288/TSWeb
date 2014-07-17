package com.tracking.website;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.tracking.dataaccess.EmployeeDAO;
import com.tracking.dataaccess.JobDAO;
import com.tracking.factory.DAOFactory;
import com.tracking.factory.EmployeeDAOFactory;
import com.tracking.factory.JobsDAOFactory;

public class JobDetails extends HttpServlet{
	
	private String jobId;
	private static final Logger log = Logger.getLogger(JobDetails.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		JobsDAOFactory fac = (JobsDAOFactory) DAOFactory.createFactory(DAOFactory.JOBS_DAO);
		JobDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		jobId = req.getParameter("jobId");
		log.warning(jobId);
		Entity e = (Entity) dao.getById(jobId);

		log.warning(e+"");
		
		req.setAttribute("jobDetails", e);
		
		String url = "/pages/jobDetails.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(req, resp);
	
	}
	

}
