package com.tracking.dataaccess;

import com.google.appengine.labs.repackaged.org.json.JSONArray;

public interface EmployeeReportDAO extends BaseDAO{

	public JSONArray getAllByEmployee(String employeeId);
}
