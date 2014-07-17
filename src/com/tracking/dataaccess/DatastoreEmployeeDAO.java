package com.tracking.dataaccess;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.labs.repackaged.org.json.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.tracking.utilities.DAOHelper;

public class DatastoreEmployeeDAO implements EmployeeDAO{

	private static DatastoreService datastore;
	private final String EMPLOYEE = "Employee";
	
	private final int USERNAME_DOES_NOT_EXIST = -1;
	private final int PASSWORD_DOES_NOT_MATCH = 0;
	private final int SUCCESSFUL_LOGIN = 1;

	public DatastoreEmployeeDAO(){
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	@Override
	public String addNew(Object obj) {
		
		HashMap<String, Object> map = (HashMap<String, Object>) obj;	
		
		if (exists((String)map.get("username")) == null) {
			
			Entity e = DAOHelper.createEntityFromHashMap(map, EMPLOYEE);
			
			Key idKey = datastore.put(e);
			return idKey.toString();
		}
		
		else{
			return null;
		}
	}

	@Override
	public boolean remove(String idIn) {
	
		try{
			datastore.delete(this.getKey(idIn));
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
		
	}
	
	private Key getKey(String id) {
		return  KeyFactory.createKey("Employee", Long.parseLong(id));
	}

	@Override
	public Object getById(String idIn) {
		
		Key k = KeyFactory.createKey("Employee", Long.parseLong(idIn));
		Entity e;
		
		try {
			e = datastore.get(k);
			return e;
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;	
	}

	@Override
	public Object getAll() {
		
		Query q = new Query(EMPLOYEE);
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return results;
	}

	@Override
	public Entity getEmployeeByUsername(String idIn) {
		return null;
	}

	@Override
	public int login(String usr, String psw) {
		
		Entity e = exists(usr);
		
		if (e == null) return USERNAME_DOES_NOT_EXIST; 
		
		else {
			
			String passMd5 = DAOHelper.encryptPassword(psw, "MD5");
			String empPass = (String) e.getProperty("password");
			
			if(passMd5.equals(empPass)){
				return SUCCESSFUL_LOGIN;
			}
			return PASSWORD_DOES_NOT_MATCH;
		}
	}
	
	/*
	 *Private method for checking whether Employee with a given username exists in a database 
	 */
	private Entity exists(String usrIn){
		
		Filter filter1 = new FilterPredicate("username", FilterOperator.EQUAL, usrIn);
		
		Query q = new Query(EMPLOYEE).setFilter(filter1);	
		List<Entity> list = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public Object getEmployeeDetails(String id) {
		
		Filter filter1;
		int jobsSelected = 0, jobsCompleted=0, reportsMadeTotal=0, reportsMadeThisMonth=0;
		HashMap<String, String> finalResults = new HashMap<String, String>();
		
		
		Entity e;
		
			e = (Entity) this.getById(id);
			finalResults.put("employee", (String) e.getProperty("firstname")+" "+e.getProperty("lastname"));
			
			filter1 = new FilterPredicate("employee", FilterOperator.EQUAL, e.getProperty("username"));
			
			/*
			 * Getting jobs completed and jobs selected info
			 */
			Query q = new Query("Job").setFilter(filter1);
			List<Entity> all = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
			Iterator<Entity> i = all.iterator();
			
			while(i.hasNext()){
				Entity entity = i.next();
				
				if(((Boolean)entity.getProperty("completed")) == true) jobsCompleted++;
				else jobsSelected++;
			}
			
			finalResults.put("completed", jobsCompleted+"");
			finalResults.put("selected", jobsSelected+"");
			
			/*
			 * Getting reports made info
			 */
			filter1 = new FilterPredicate("employee", FilterOperator.EQUAL, e.getProperty("username"));
			Query q2 = new Query("Report").setFilter(filter1);
			
			List<Entity> reportsList = datastore.prepare(q2).asList(FetchOptions.Builder.withDefaults());
			if(reportsList != null) reportsMadeTotal = reportsList.size();
			
			finalResults.put("reportsTotal", reportsMadeTotal+"");
			
			/*
			 * Getting jobs completed this month info
			 */
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			
			Iterator<Entity> i2 = reportsList.iterator();
			
			while(i2.hasNext()) {
				Entity ent = i2.next();
				Calendar repDate = Calendar.getInstance();
				repDate.setTime((Date)ent.getProperty("date"));
				
				if(month == repDate.get(Calendar.MONTH) && year == repDate.get(Calendar.YEAR)) reportsMadeThisMonth++;				
			}
			
			finalResults.put("reportsThisMonth", reportsMadeThisMonth+"");
				
		return finalResults;
	}
}
