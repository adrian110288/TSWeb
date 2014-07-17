package com.tracking.factory;

import com.tracking.dataaccess.DatastoreEmployeeDAO;
import com.tracking.dataaccess.EmployeeDAO;

public class EmployeeDAOFactory extends DAOFactory{

	public static String DATASTORE = "datastore";
	public static String SQL_DATABASE = "sql";
	
	public EmployeeDAO createDataAccessor(String type) {
		
		if (type.equals(DATASTORE)) return new DatastoreEmployeeDAO();
		
		return null;
	}
}
