package com.tracking.dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.tracking.utilities.DAOHelper;

public class DatastoreJobReportsDAO implements JobReportsDAO{

	private DatastoreService datastore;
	private Key reportRepositoryKey;
	private final String REPORT = "JobReport"; 
	
	public DatastoreJobReportsDAO(){
		datastore = DatastoreServiceFactory.getDatastoreService(); 
		reportRepositoryKey= KeyFactory.createKey("ReportRepository", "reports");
	}
	
	private Key getReportRepositoryKey() {
		return reportRepositoryKey;
	}

	private void setReportRepositoryKey(Key reportRepositoryKey) {
		this.reportRepositoryKey = reportRepositoryKey;
	}

	@Override
	public String addNew(Object obj) {
		
		HashMap<String, Object> map = (HashMap<String, Object>) obj;
		map.put("date", new Date());
		
		Entity e = DAOHelper.createEntityFromHashMap(map, REPORT);
		
		Key key = datastore.put(e);
		
		return key.toString();
	}

	@Override
	public boolean remove(String idIn) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean exist(String id){
	
		Filter filter1 = new FilterPredicate("jobId", FilterOperator.EQUAL, id);
		
		Query q = new Query("Report", reportRepositoryKey).setFilter(filter1);
		
		Iterator<Entity> i = datastore.prepare(q).asIterator();
		
		return i.hasNext()?true:false;
	}

	@Override
	public Object getById(String idIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
