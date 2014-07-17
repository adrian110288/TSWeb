package com.tracking.website;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;
import com.tracking.dataaccess.*;
import com.tracking.factory.*;

public class AllEmployees extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		EmployeeDAOFactory fac = (EmployeeDAOFactory) DAOFactory.createFactory(DAOFactory.EMPLOYEE_DAO);
		EmployeeDAO dao = fac.createDataAccessor(EmployeeDAOFactory.DATASTORE);
		
		List<Entity> results = (List<Entity>) dao.getAll();
		//addMarker();
		
		req.setAttribute("employeeList", results);
		
		String url = "/pages/inspectemployees.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		doPost(req, resp);
	}
	
	private void addMarker(){
		
		DatastoreService datastore = datastore = DatastoreServiceFactory.getDatastoreService();
		
		Entity e =new Entity("Status");
		e.setProperty("activity", "Starting shift");
		e.setProperty("comment", "");
		e.setProperty("date", new Date());
		e.setProperty("employee", "kmumba");
		e.setProperty("latitude", 53.4410743713);
		e.setProperty("longitude", -2.2864151001);
		
		Transaction t = datastore.beginTransaction();
		datastore.put(e);
		t.commit();
	}
}

