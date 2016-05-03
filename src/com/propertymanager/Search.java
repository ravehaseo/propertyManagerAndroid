package com.propertymanager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.propertymanagerententity.Property;
import com.propertymanagerhelper.PropertyArrayCreator;
import com.propertymanagerhelper.PropertyDeleteTask;

public class Search extends Activity {
	
	// set url before running
	final String myuri = "http://192.168.10.235:7001";
	
	private Spinner searchoffertype, searchproptype, searchrooms, searchzip, searchprice;
	private Button btnsearch;
	public static Property[] propertyarray;
	public  ProgressDialog pDialog;
	public ListView listview;
	public String offer, type, price, zip, rooms;
	
	/* IDs of context menu items */
	private final int MENU_EDIT = 1;
	private final int MENU_DELETE = 2;
	
	
	public ListView propertylist;
	PropertyListAdapter adpter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		// setting the drop down menus 
		searchoffertype = (Spinner) findViewById(R.id.searchoffertype);
		ArrayAdapter<CharSequence> otarr = ArrayAdapter.createFromResource(this, R.array.offer_type, android.R.layout.simple_list_item_1);
		otarr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		searchoffertype.setAdapter(otarr);
		
		searchproptype = (Spinner) findViewById(R.id.searchproptype);
		ArrayAdapter<CharSequence> ptarr = ArrayAdapter.createFromResource(this, R.array.search_proptype, android.R.layout.simple_list_item_1);
		ptarr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		searchproptype.setAdapter(ptarr);
		
		searchrooms = (Spinner) findViewById(R.id.searchrooms);
		ArrayAdapter<CharSequence> rnarr = ArrayAdapter.createFromResource(this, R.array.rooms, android.R.layout.simple_list_item_1);
		rnarr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		searchrooms.setAdapter(rnarr);
		
		searchzip = (Spinner) findViewById(R.id.searchzip);
		ArrayAdapter<CharSequence> ziparr = ArrayAdapter.createFromResource(this, R.array.distrect, android.R.layout.simple_list_item_1);
		ziparr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		searchzip.setAdapter(ziparr);
		
		searchprice = (Spinner) findViewById(R.id.searchprice);
		ArrayAdapter<CharSequence> pricearr = ArrayAdapter.createFromResource(this, R.array.price, android.R.layout.simple_list_item_1);
		pricearr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		searchprice.setAdapter(pricearr);
		
		// adding the Search button and creating the onclik listner for it.
		btnsearch = (Button) findViewById(R.id.btnsearch);
		btnsearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				offer = String.valueOf(searchoffertype.getSelectedItem());
				type = String.valueOf(searchproptype.getSelectedItem()); 
				price = String.valueOf(searchprice.getSelectedItem());
				zip = String.valueOf(searchzip.getSelectedItem());
				rooms = String.valueOf(searchrooms.getSelectedItem());
						
				new SearchTask(Search.this).execute(myuri + "/PropertyManager/jaxrs/search/property");
			}
		});
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
			new PropertyChangeAvailabilityTask(Search.this).execute(selectedProperty.getId());
						
			break;
		// MENU_DELETE
		case MENU_DELETE:
			new PropertyDeleteTask(Search.this).execute(selectedProperty.getId());
			break;
		
		}
		return true;
	}
	
	// thread class 
		public class SearchTask extends AsyncTask<String, Void, Property[]> {
			public Context context;
			
			
			int userid = -1;
			
			public SearchTask(Context context) {
				this.context = context;
			}
			
			@Override
			protected void onPreExecute() {
				pDialog = new ProgressDialog(Search.this);
				pDialog.setMessage("Listing All Properties");
				pDialog.setTitle("Please wait while loading data from server...");
				pDialog.show();
				
				super.onPreExecute();
			}
			
			@Override
			protected Property[] doInBackground(String... params) {
				JSONObject outjson = new JSONObject();
				try {
					outjson.put("offer", offer);
					outjson.put("type", type);
					outjson.put("price", price);
					outjson.put("zip", zip);
					outjson.put("rooms", rooms);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				//Property[] propertyarray = null; 
				HttpResponse resp = null;
				HttpPost hp = new HttpPost(params[0]);
				HttpClient hc = new DefaultHttpClient();
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
					if (resp != null) {
						if (resp.getStatusLine().getStatusCode() < 300) {
							// putting the response to a string
							String injson = EntityUtils.toString(resp.getEntity()); 
							// converting the string to a JSONArray 
							try {
								JSONArray jsonarray = new JSONArray(injson);
								propertyarray = new Property[jsonarray.length()];
								for (int i = 0; i<jsonarray.length(); i++){
									// extracting JSON objects from the JSONArray
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
				setContentView(R.layout.activity_listall);
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
				
				// setting the item click listener
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
				new SearchTask(this.context).execute("http://192.168.10.235:7001/PropertyManager/jaxrs/editproperty/avai");
				super.onPostExecute(result);
			}
		}
}
