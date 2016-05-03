package com.propertymanager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.propertymanagerententity.Property;
import com.propertymanagerhelper.PropertyArrayCreator;
import com.propertymanagerhelper.PropertyDeleteTask;

public class Listall extends Activity implements AdapterView.OnItemClickListener {
	public static Property[] propertyarray;
	public  ProgressDialog pDialog;
	ListView listview;
	//Context main;
	
	/* IDs of context menu items */
	private final int MENU_EDIT = 1;
	private final int MENU_DELETE = 2;
	
	
	// set url before running
	final String myuri = "http://192.168.10.235:7001";
	public ListView propertylist;
	PropertyListAdapter adpter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listall);
		// main = this;
		new ListAllTask(Listall.this).execute(myuri + "/PropertyManager/jaxrs/list/all");


//		registerForContextMenu(listview);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)	{
		// setting the title of the context menu 
	//	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Select Action");
		
		// setting the menu items
		menu.add(1, MENU_EDIT, 1, "Change Availabelity");
		menu.add(1, MENU_DELETE, 2, "Delete This Property");
		
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int selectedPropertyPosition = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
		 Property selectedProperty = PropertyListAdapter.GetItem(selectedPropertyPosition);
		switch(item.getItemId()){
		case MENU_EDIT:
			new PropertyChangeAvailabilityTask(Listall.this).execute(selectedProperty.getId());
						
			break;
		// MENU_DELETE
		case MENU_DELETE:
			new PropertyDeleteTask(Listall.this).execute(selectedProperty.getId());
			break;
		
		}
		return true;
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.listall, menu);
//		
//		
//		return true;
//	}
	
	
	
	
	// thread class 
	public class ListAllTask extends AsyncTask<String, Void, Property[]> {
		public Context context;
		
		
		int userid = -1;
		
		public ListAllTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(Listall.this);
			pDialog.setMessage("Listing All Properties");
			pDialog.setTitle("Please wait while loading data from server...");
			pDialog.show();
			
			super.onPreExecute();
		}
		
		@Override
		protected Property[] doInBackground(String... params) {
				//Property[] propertyarray = null; 
				// creatin an http get request and passing the url as a parameter.
				HttpGet hp = new HttpGet(params[0]);
				// creating a defult http client
				HttpClient hc = new DefaultHttpClient();
				// http response where the response to the get request will be stored
				HttpResponse resp;
				try {
					resp = hc.execute(hp);
					if (resp != null) {
						if (resp.getStatusLine().getStatusCode() < 300) {
							
							// BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(),"UTF-8"));
							String injson = EntityUtils.toString(resp.getEntity()); 
									// br.readLine();
							// JSONTokener tokener = new JSONTokener(injson);
							try {
								JSONArray jsonarray = new JSONArray(injson);
								propertyarray = new Property[jsonarray.length()];
								for (int i = 0; i<jsonarray.length(); i++){
									// extracting json objects from the json array
									JSONObject json = new JSONObject(jsonarray.getJSONObject(i).toString());
									PropertyArrayCreator.JsonToList(propertyarray, i, json);
									
								}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
								
						}
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return propertyarray;
		}
		
		@Override
		protected void onPostExecute(final Property[] propertyarray) {
			
			pDialog.dismiss();
			propertylist = (ListView)findViewById(R.id.list);
			if (adpter == null){
			adpter = new PropertyListAdapter(propertyarray, this.context);
			propertylist.setAdapter(adpter);
			} else { 
				adpter.notifyDataSetChanged();
				propertylist.invalidateViews();
				// propertylist.setAdapter(adpter);
				
			}
			super.onPostExecute(propertyarray);
			
			
			propertylist.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
				{
					Intent detailsIntent = new Intent("android.intent.action.DETAILS");
					detailsIntent.putExtra("property", propertyarray[position]); 
					startActivity(detailsIntent);
					
				}
			});
			
			registerForContextMenu(propertylist);
		}
		
		
	}




	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	public class PropertyChangeAvailabilityTask extends AsyncTask<Integer, Void, String>   {
		public Context context;
		
		

		
		public PropertyChangeAvailabilityTask(Context context) {
			this.context = context;
		}

		@Override
		protected String doInBackground(Integer... params) {
			HttpResponse resp = null;
			HttpPost hp = new HttpPost(myuri + "/PropertyManager/jaxrs/editproperty/avai");
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

		@Override
		protected void onPostExecute(String result) {
			new ListAllTask(this.context).execute(myuri + "/PropertyManager/jaxrs/list/all");
			super.onPostExecute(result);
		}
		
		
	}

}
