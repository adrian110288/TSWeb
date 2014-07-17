package com.tracking.factory;

import com.tracking.dataaccess.DatastoreEmployeeReportDAO;
import com.tracking.dataaccess.DatastoreJobDAO;
import com.tracking.dataaccess.EmployeeReportDAO;
import com.tracking.dataaccess.JobDAO;

public class EmployeeReportsDAOFactory extends DAOFactory{

	public static String DATASTORE = "datastore";
	public static String SQL_DATABASE = "sql";
	
	public EmployeeReportDAO createDataAccessor(String type) {
		
		if (type.equals(DATASTORE)) return new DatastoreEmployeeReportDAO();
		
		return null;
	}
	
}
