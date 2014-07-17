package com.tracking.website;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tracking.dataaccess.EmployeeDAO;
import com.tracking.factory.DAOFactory;

public class EmployeeDelete extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		EmployeeDAO dao = (EmployeeDAO) DAOFactory.getDataAccessorObject(DAOFactory.EMPLOYEE_DAO, DAOFactory.DATASTORE);
		
		String employeeId = req.getParameter("employeeId");
		
		boolean deleted = dao.remove(employeeId);
		resp.getWriter().write(deleted+"");
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}
