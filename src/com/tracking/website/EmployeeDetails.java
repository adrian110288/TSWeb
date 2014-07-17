package com.tracking.website;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.tracking.dataaccess.*;
import com.tracking.factory.*;

public class EmployeeDetails extends HttpServlet{

	private String employeeId;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		EmployeeDAOFactory fac = (EmployeeDAOFactory) DAOFactory.createFactory(DAOFactory.EMPLOYEE_DAO);
		EmployeeDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		employeeId = req.getParameter("empId");
		
		Object numericResults = (HashMap<String, String>) dao.getEmployeeDetails(employeeId);
		req.setAttribute("numericResults", numericResults);
		
		String url = "/pages/employeeDetails.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(req, resp);
	}

}
