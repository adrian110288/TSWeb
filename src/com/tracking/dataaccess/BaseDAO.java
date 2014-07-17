package com.tracking.dataaccess;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.labs.repackaged.org.json.JSONArray;

public interface BaseDAO {
	
	public String addNew(Object obj);
	public boolean remove(String idIn);
	public Object getById(String idIn);
	public Object getAll();
}
