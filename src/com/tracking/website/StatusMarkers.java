package com.tracking.website;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.tracking.dataaccess.EmployeeReportDAO;
import com.tracking.factory.DAOFactory;
import com.tracking.factory.EmployeeReportsDAOFactory;

public class StatusMarkers extends HttpServlet {
	
	private String employeeId;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		employeeId = req.getParameter("empId");
		
		EmployeeReportsDAOFactory repFac = (EmployeeReportsDAOFactory) DAOFactory.createFactory(DAOFactory.STATUS_REPORTS_DAO);
		EmployeeReportDAO reportsDAO = repFac.createDataAccessor(EmployeeReportsDAOFactory.DATASTORE);
	
		JSONArray statusMarkers = reportsDAO.getAllByEmployee(employeeId);
		
		resp.getWriter().write(statusMarkers.toString());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}


}
