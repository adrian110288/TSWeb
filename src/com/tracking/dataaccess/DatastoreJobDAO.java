package com.tracking.dataaccess;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.labs.repackaged.org.json.*;
import com.tracking.utilities.DAOHelper;
import com.tracking.website.JobDetails;

public class DatastoreJobDAO implements JobDAO{

	private DatastoreService datastore;
	private final String JOB = "Job";
	private Key jobRepositoryKey;
	private static final Logger log = Logger.getLogger(DatastoreJobDAO.class.getName());
	
	public DatastoreJobDAO(){
		datastore = DatastoreServiceFactory.getDatastoreService(); 
		jobRepositoryKey= KeyFactory.createKey("JobRepository", "jobs");
	}
	
	@Override
	public JSONArray getRecommended(double lat, double lng){
		
		JSONArray a = getAll();
		JSONArray recomm = new JSONArray();
		String distance, address;
		JSONObject temp;
		
		try{
			for(int i=0;i<a.length();i++){
				
				JSONObject o = (JSONObject)a.get(i);
				address = URLEncoder.encode(o.getString("address"), DAOHelper.ENCODING);
				
				temp = fetchDistanceJSON(lat, lng, address);
								
				if(temp.getString("status").equals("OK")){
						
					distance = temp.getJSONArray("rows").getJSONObject(0).
							getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getString("value");
						
					if(Integer.parseInt(distance)<= DAOHelper.MAX_RADIUS) {
						o.put("distance", distance);							
						recomm.put(o);
					}
				}
			}
		}
		catch(JSONException jse){
			
		}
		catch(Exception e){}

		return recomm;
	}

	@Override
	public JSONArray getOutstandingJobsByEmployee(String idIn) {
		
		Filter filter1 = new FilterPredicate("employee", FilterOperator.EQUAL, idIn);
		//Filter filter2 = new FilterPredicate("completed", FilterOperator.EQUAL, false);
		//Filter composite = CompositeFilterOperator.and(filter1,filter2);
		
		Query q = new Query("Job", jobRepositoryKey).setFilter(filter1);
		
		Iterator<Entity> i = datastore.prepare(q).asIterator();
		
		JSONArray jobs = new JSONArray();
		
		while(i.hasNext()){
			
			Entity temp = i.next();
			
			boolean reportExist = new DatastoreJobReportsDAO().exist(temp.getKey().getId()+"");
			
			if (reportExist == false)
				jobs.put(DAOHelper.getJobJSONFromEntity(temp));
			
			
			
			
		}
		
		return jobs;
		
		}

	@Override
	public JSONArray getAllOutstandingJobs() {
		return null;
	}

	@Override
	public JSONArray getAllCompletedJobsByEmployee(String idIn) {
		
		Filter filter1 = new FilterPredicate("employee", FilterOperator.EQUAL, idIn);
		Filter filter2 = new FilterPredicate("completed", FilterOperator.EQUAL, true);
		Filter composite = CompositeFilterOperator.and(filter1,filter2);
		
		Query q = new Query("Job", jobRepositoryKey).setFilter(composite);
		
		Iterator<Entity> i = datastore.prepare(q).asIterator();
		
		JSONArray jobs = new JSONArray();
		
		while(i.hasNext()){
			jobs.put(DAOHelper.getJobJSONFromEntity(i.next()));
		}
		
		return jobs;
		
	}

	@Override
	public JSONArray getAllCompletedJobs() {
		
		Filter filter = new FilterPredicate("completed", FilterOperator.EQUAL, true);
		Query q = new Query("Job", jobRepositoryKey).setFilter(filter);
		Iterator<Entity> i = datastore.prepare(q).asIterator();
		
		JSONArray jobs = new JSONArray();
		
		while(i.hasNext()){
			jobs.put(DAOHelper.getJobJSONFromEntity(i.next()));
		}
		
		return jobs;
	}

	@Override
	public String addNew(Object obj) {
		
		HashMap<String, Object> map = (HashMap<String, Object>) obj;
		
		Entity e = DAOHelper.createEntityFromHashMap(map, JOB);
		
		Key key = datastore.put(e);
		
		return key.toString();
	}

	@Override
	public boolean remove(String idIn) {
		
		Filter filter = new FilterPredicate("title", FilterOperator.EQUAL, idIn);		

		Query q = new Query("Job", jobRepositoryKey).setAncestor(jobRepositoryKey).setFilter(filter);
		List<Entity> jobs = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		if(jobs == null && jobs.size() == 0 && jobs.size() > 1) return false;
		else {
			datastore.delete(jobs.get(0).getKey());
			return true;
		}
		
	}

	@Override
	public Object getById(String idIn) {

		Key k = KeyFactory.createKey(jobRepositoryKey, "Job", Long.parseLong(idIn));
		Entity e = null;
		try {
			 e = datastore.get(k);
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}
		return e;
	}
	
	public boolean setJobAsCompleted(String idIn){
		
		Filter filter = new FilterPredicate("title", FilterOperator.EQUAL, idIn);
		Query q = new Query("Job", jobRepositoryKey).setFilter(filter);
		List<Entity> list = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		if(list!=null && list.size()==1) {
			Entity e = list.get(0);
			e.setProperty("completed", true);
			datastore.put(e);
			return true;
		}
		
		else return false;
	}
	
	public boolean setEmployeeToJob(boolean select, String idIn, String emp) {
		
		Filter filter = new FilterPredicate("title", FilterOperator.EQUAL, idIn);
		Query q = new Query("Job", jobRepositoryKey).setFilter(filter);
		List<Entity> list = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		log.info(list!=null ? "Fetched list contains "+list.size()+"item(s)" : "List object is null");
		
		if(list!=null && list.size()==1) {
			
			log.info("List !=null AND list size is 1");
			
			if(select) {
				
				log.info("Job is to be selected (select==true)");
			
				Entity e = list.get(0);
				e.setProperty("employee", emp);
				Key id = datastore.put(e);
				
				log.info("Entity id: "+id.getId());
				
				return true;
			}
			
			else if (!select){
				
				log.info("Job is to be dropped (select==false)");
				
				Entity e = list.get(0);
				e.setProperty("employee", null);
				Key id = datastore.put(e);
				
				log.info("Entity id: "+id.getId());
				
				return true;
			}
		}
		
		
		return false;
	}
	
	public boolean setJobPriority(String idIn, int priority){
		
		Filter filter = new FilterPredicate("title", FilterOperator.EQUAL, idIn);
		Query q = new Query("Job", jobRepositoryKey).setFilter(filter);
		List<Entity> list = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		if(list!=null && list.size()==1) {
			Entity e = list.get(0);
			e.setProperty("priority", priority);
			datastore.put(e);
			return true;
		}
		
		else return false;
	}

	@Override
	public JSONArray getAll() {
		
		Filter filter1 = new FilterPredicate("dateDue", FilterOperator.GREATER_THAN_OR_EQUAL, new Date());
		Filter filter2 = new FilterPredicate("completed", FilterOperator.EQUAL, false);
		Filter filter3 = new FilterPredicate("employee", FilterOperator.EQUAL, null);
		Filter composite = CompositeFilterOperator.and(filter1,filter2, filter3);
		
		Query q = new Query(JOB, jobRepositoryKey).setFilter(composite);
		
		Iterator<Entity> i = datastore.prepare(q).asIterator();
		
		JSONArray jobs = new JSONArray();
		
		while(i.hasNext()){
			jobs.put(DAOHelper.getJobJSONFromEntity(i.next()));
		}
		
		return jobs;
		
		}
	
	private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
	
	private JSONObject fetchDistanceJSON(double lat, double lng, String address) throws JSONException {
		
		String jsonResult = "";
		
		try{
			URL url = new URL("http://maps.googleapis.com/maps/api/distancematrix/json?origins="+lat+"+"+lng+"&destinations="+address+"&mode=car&sensor=false");
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
			    jsonResult += inputLine;
			}
			
			in.close();
			
			
		}
		catch(IOException ioe){}
		
		return  new JSONObject(jsonResult);
	}
	

}
