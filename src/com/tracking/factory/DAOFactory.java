package com.tracking.factory;

import com.tracking.dataaccess.*;

public class DAOFactory {

	public static String EMPLOYEE_DAO = "employee";
	public static String JOBS_DAO = "jobs";
	public static String STATUS_REPORTS_DAO = "statusReports";
	public static String DATASTORE = "datastore";
	public static String SQL_DATABASE = "sql";
	
	public static Object createFactory(String category){
		
		if (category.equals(EMPLOYEE_DAO)) return new EmployeeDAOFactory();
		else if (category.equals(JOBS_DAO)) return new JobsDAOFactory();
		else if (category.equals(STATUS_REPORTS_DAO)) return new EmployeeReportsDAOFactory();
		return null;
	}
	
	public static Object getDataAccessorObject(String type, String storageOption){
		
		if (type.equals("employee")){
			
			if(storageOption.equals("datastore")) {
				return new DatastoreEmployeeDAO();
			}
			else{
				
			}
		}
		
		else if (type.equals("jobs")){
			
			if(storageOption.equals("datastore")) {
				return new DatastoreJobDAO();
			}
			else{
				
			}
		}
		
		else if (type.equals("statusReports")){
			
			if(storageOption.equals("datastore")) {
				return new DatastoreEmployeeReportDAO();
			}
			else{
				
			}
		}
		return null;
		
		
	}
}
