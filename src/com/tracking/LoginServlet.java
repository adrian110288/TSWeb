package com.tracking;

import java.io.*;
import java.util.List;
import javax.jdo.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.labs.repackaged.org.json.*;
import com.tracking.dataaccess.*;
import com.tracking.factory.*;
import com.tracking.utilities.DAOHelper;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	
	private String usr,paswrd, fn, ln, area;
	private PrintWriter pw;
	private JSONObject jsonResponse;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		EmployeeDAOFactory fac = (EmployeeDAOFactory) DAOFactory.createFactory(DAOFactory.EMPLOYEE_DAO);
		EmployeeDAO dao = fac.createDataAccessor(JobsDAOFactory.DATASTORE);
		
		pw = resp.getWriter();
		resp.setContentType("application/json");		

		try {
			jsonResponse = new JSONObject();
			
			usr = req.getParameter("username");
			paswrd = req.getParameter("password");
			int loginStatus = dao.login(usr, paswrd);
						
			jsonResponse.put("status", loginStatus);
			
			if(loginStatus == 0) jsonResponse.put("message", DAOHelper.PASS_NOT_CORRECT);
			else if(loginStatus == -1) jsonResponse.put("message", DAOHelper.USER_NOT_EXIST);
			
			pw.write(jsonResponse.toString());
			
		} catch (JSONException e) {
			pw.write(e.getMessage());
		}
		
		catch (Exception e) {
			pw.write(e.getMessage());
		}
			
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
