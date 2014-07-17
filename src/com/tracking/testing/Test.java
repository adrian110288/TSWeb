package com.tracking.testing;

import static org.junit.Assert.*;

import java.io.IOException;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tracking.utilities.DAOHelper;

public class Test {

	/*@org.junit.Test
	public void test() throws IOException, JSONException {
		
		String string = DAOHelper.addressCoord("noNameAddress");
		assertNull("", string);
		
		JSONObject array = new JSONObject(string);
		String status = array.getString("status");
		
		assertEquals("ZERO_RESULTS", status);
	}*/
	
	@org.junit.Test
	public void test(){
		
		String enc = DAOHelper.encryptPassword("myPassword", "MD5");
		
		assertEquals(32, enc.length());
	}

}
