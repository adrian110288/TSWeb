package com.tracking;

import java.io.*;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tracking.dataaccess.*;
import com.tracking.factory.*;
import com.tracking.utilities.JSONResponseCreator;

public class Registration extends HttpServlet {

	private String format;
	private DatastoreEmployeeDAO dao;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		EmployeeDAOFactory fac = (EmployeeDAOFactory) DAOFactory.createFactory(DAOFactory.EMPLOYEE_DAO);
		EmployeeDAO dao = fac.createDataAccessor(EmployeeDAOFactory.DATASTORE);
		
		format = req.getParameter("format");
			
			if (format.equals("json")) {
				resp.setContentType("application/json");
				
				try{				
					JSONObject o = new JSONObject(req.getParameter("formData"));
					
					String[] keys = o.getNames(o);
					HashMap<String, String> map = new HashMap<String, String>();
					
					for(int i=0;i<keys.length; i++) {
						map.put(keys[i], o.getString(keys[i]));
					}
					
					String registerResponse = dao.addNew(map);
					System.out.println(JSONResponseCreator.getEmpRegResponse(registerResponse).toString());
					resp.getWriter().write(JSONResponseCreator.getEmpRegResponse(registerResponse).toString());	
					
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			else if(format.equals("xml")){
				
			}
			
			else if(format.equals("namevalue")){
				
			}
	}
}
