package com.tracking.dataaccess;

public interface EmployeeDAO extends BaseDAO{
	
	public Object getEmployeeByUsername(String idIn);
	public int login(String usr, String psw);
	public Object getEmployeeDetails(String id);

}
