package com.tracking.dataaccess;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;

public interface JobDAO extends BaseDAO {

	public JSONArray getRecommended(double lat, double lng);
	public JSONArray getOutstandingJobsByEmployee(String idIn);
	public JSONArray getAllOutstandingJobs();
	public JSONArray getAllCompletedJobsByEmployee(String idIn);
	public JSONArray getAllCompletedJobs();
	public boolean setJobAsCompleted(String idIn);
	public boolean setEmployeeToJob(boolean select, String idIn, String emp);
}
