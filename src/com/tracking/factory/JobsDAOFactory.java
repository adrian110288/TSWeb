package com.tracking.factory;

import com.tracking.dataaccess.DatastoreEmployeeDAO;
import com.tracking.dataaccess.DatastoreJobDAO;
import com.tracking.dataaccess.EmployeeDAO;
import com.tracking.dataaccess.JobDAO;

public class JobsDAOFactory extends DAOFactory{

	public static String DATASTORE = "datastore";
	public static String SQL_DATABASE = "sql";
	
	public JobDAO createDataAccessor(String type) {
		
		if (type.equals(DATASTORE)) return new DatastoreJobDAO();
		
		return null;
	}
	
}
