package com.tracking.dataaccess;

import java.util.*;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tracking.utilities.DAOHelper;

public class DatastoreEmployeeReportDAO implements EmployeeReportDAO{

	private DatastoreService datastore;
	private Key empReportRepositoryKey;
	private final static String STATUS = "Status";
	private static final Logger log = Logger.getLogger(STATUS);
	
	public DatastoreEmployeeReportDAO() {
		
		datastore = DatastoreServiceFactory.getDatastoreService(); 
		empReportRepositoryKey= KeyFactory.createKey("empReportRepository", "empReps");
	}

	@Override
	public String addNew(Object obj) {
		
		HashMap<String, Object> map = (HashMap<String, Object>) obj;
		
		Entity e = DAOHelper.createEntityFromHashMap(map, STATUS);
		
		Key key = datastore.put(e);
		
		return key.toString();
		
	}

	@Override
	public boolean remove(String idIn) {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public JSONArray getAllByEmployee(String employeeId) {
		
		Entity employee = (Entity) new DatastoreEmployeeDAO().getById(employeeId);
		JSONArray reportsArray = new JSONArray();
		
		Filter filter = new FilterPredicate("employee", FilterOperator.EQUAL, employee.getProperty("username"));
		Query query = new Query("Status").setFilter(filter);
		Iterator<Entity> results = datastore.prepare(query).asIterator();
		
		while(results.hasNext()){
			
			JSONObject jsonEntity = DAOHelper.getEmployeeReportJSONFromEntity(results.next());
			reportsArray.put(jsonEntity);
		}
		return reportsArray;
	}

}
