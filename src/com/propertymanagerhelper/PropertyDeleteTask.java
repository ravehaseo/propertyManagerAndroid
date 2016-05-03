package com.propertymanagerhelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class PropertyDeleteTask extends AsyncTask<Integer, Void, String> {
	public Context context;
	public static String myUrl = "http://192.168.10.221:7001/PropertyManager/jaxrs/delete/property";

	public PropertyDeleteTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(Integer... params) {
		HttpResponse resp = null;
		HttpPost hp = new HttpPost(myUrl);
		HttpClient hc = new DefaultHttpClient();
		JSONObject outjson = new JSONObject();
		try {
			outjson.put("propertyid", params[0]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringEntity myentity = null;
		try {
			
			myentity = new StringEntity(outjson.toString());
			myentity.setContentType("application/json");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			hp.setEntity(myentity);
			resp = hc.execute(hp);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		if (resp.getStatusLine().getStatusCode() == 204)
			return "Availabilit Changed"; 
		else 
			return "Couldn't change Availability ";
	
	}
}
