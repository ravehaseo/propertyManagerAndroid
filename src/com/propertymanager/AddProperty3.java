package com.propertymanager;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.propertymanagerententity.Property;
import com.propertymanagerhelper.JsonObjectCreator;

	

public class AddProperty3 extends Activity {
	
	final String myuri = "http://192.168.10.235:7001";
	JSONObject outjson, injson;
	public  ProgressDialog pDialog;
	
	EditText addrooms, addbaths;
	Spinner addkitchen, addpool, addgarden;
	CheckBox addalarm, addsecurity, addavailable;
	Button prev, next;
	
	public int propertyid;
	
	Property newproperty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addproperty2);
		
		// getting the property object passed from the previous 
		newproperty = (Property) getIntent().getSerializableExtra("property");
		
		
		addrooms = (EditText) findViewById(R.id.addrooms);
		addbaths = (EditText) findViewById(R.id.addbaths);
		addkitchen = (Spinner) findViewById(R.id.addkitchen);
		addpool = (Spinner) findViewById(R.id.addpool);
		addgarden = (Spinner) findViewById(R.id.addgarden);
		addalarm = (CheckBox) findViewById(R.id.addalarm);
		addsecurity = (CheckBox) findViewById(R.id.addsecurity);
		addavailable = (CheckBox) findViewById(R.id.addavailable);
		prev = (Button) findViewById(R.id.addprevious2);
		next = (Button) findViewById(R.id.addnext2);
		
		// if form already been filed then we put the data out to the UI
		if (!(newproperty.getKitchen().equals(""))) {
			
			if (newproperty.getRooms() != 0)
				addrooms.setText(String.valueOf(newproperty.getRooms()));
			
			if (newproperty.getBaths() != 0)
				addbaths.setText(String.valueOf(newproperty.getBaths()));
			
			if (newproperty.getKitchen() == addkitchen.getItemAtPosition(1))
				addkitchen.setSelection(1);
			
			if (newproperty.getGarden() == addkitchen.getItemAtPosition(1))
				addkitchen.setSelection(1);
			else
			if (newproperty.getGarden() == addkitchen.getItemAtPosition(2))
				addkitchen.setSelection(2);
			
			if (newproperty.getPool() == addpool.getItemAtPosition(1))
				addpool.setSelection(1);
			else
			if (newproperty.getPool() == addpool.getItemAtPosition(2))
				addpool.setSelection(2);
			
			if (newproperty.getAlarm().equals("yes"))
				addalarm.setChecked(true);
			else addalarm.setChecked(false);
			
			if (newproperty.getSecurity().equals("yes"))
					addsecurity.setChecked(true);
			else addsecurity.setChecked(false);
			
			if (newproperty.getAvailabel().equals("no"))
				addavailable.setChecked(false);
			else addavailable.setChecked(true); 
		}
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!(addrooms.getText().toString().equals("")))
					newproperty.setRooms(Integer.valueOf(addrooms.getText().toString()));
				else {
					Toast.makeText(getApplicationContext(), "Please enter a valid number of rooms",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				if (!(addbaths.getText().toString().equals("")))
					newproperty.setBaths(Integer.valueOf(addbaths.getText().toString()));
				else {
					Toast.makeText(getApplicationContext(), "Please enter a valid number of baths",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				newproperty.setKitchen(addkitchen.getSelectedItem().toString());
				
				newproperty.setPool(addpool.getSelectedItem().toString());
				
				newproperty.setGarden(addgarden.getSelectedItem().toString());
				
				if (addalarm.isChecked())
					newproperty.setAlarm("yes");
				else newproperty.setAlarm("no");
				
				if (addsecurity.isChecked())
					newproperty.setSecurity("yes");
				else newproperty.setSecurity("no");
				
				if (addavailable.isChecked())
					newproperty.setAvailabel("yes");
				else newproperty.setAvailabel("no");
				
				outjson = JsonObjectCreator.PropertytoJson(newproperty);
				
				new AddPropertyTask(AddProperty3.this).execute(myuri);
				
				
			}
			
			
		});
		
		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!(addrooms.getText().toString().equals("")))
					newproperty.setRooms(Integer.valueOf(addrooms.getText().toString()));
				else {
					Toast.makeText(getApplicationContext(), "Please enter a valid number of rooms",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				if (!(addbaths.getText().toString().equals("")))
					newproperty.setBaths(Integer.valueOf(addbaths.getText().toString()));
				else {
					Toast.makeText(getApplicationContext(), "Please enter a valid number of baths",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				newproperty.setKitchen(addkitchen.getSelectedItem().toString());
				
				newproperty.setPool(addpool.getSelectedItem().toString());
				
				newproperty.setGarden(addgarden.getSelectedItem().toString());
				
				if (addalarm.isChecked())
					newproperty.setAlarm("yes");
				else newproperty.setAlarm("no");
				
				if (addsecurity.isChecked())
					newproperty.setSecurity("yes");
				else newproperty.setSecurity("no");
				
				if (addavailable.isChecked())
					newproperty.setAvailabel("yes");
				else newproperty.setAvailabel("no");
				
				// calling the next form 
				try {
					Intent addproperty2 = new Intent("android.intent.ADDPROPERTY2");
					addproperty2.putExtra("property", newproperty);
					startActivity(addproperty2);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					finish();
				}
				
				
				
			}
		});
		
		
	}
	// thread class
	public class AddPropertyTask extends AsyncTask<String, Void, Integer> {
		public Context context;
		

		public AddPropertyTask(Context context) {
			super();
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(AddProperty3.this);
			pDialog.setMessage("Listing All Properties");
			pDialog.setTitle("Please wait while loading data from server...");
			pDialog.show();
			
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {
			int propertyid = -1;
			HttpResponse resp = null;
			HttpPost hp = new HttpPost(params[0] + "/PropertyManager/jaxrs/add/property");
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
				if (!(resp.equals(null))) {
					if (resp.getStatusLine().getStatusCode() == 200) {
						injson = new JSONObject(resp.getEntity().toString());
						propertyid = injson.getInt("propertyid");
					} else {
						pDialog.dismiss();
						Toast.makeText(getApplicationContext(), "there was an error: " + resp.getStatusLine().getStatusCode() + ". please tray againe",
								Toast.LENGTH_LONG).show();
						return null;
						
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return propertyid;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			
			pDialog.dismiss();
			if (!(result.equals(null))){
				Toast.makeText(getApplicationContext(), "property added successfuly",
						Toast.LENGTH_LONG).show();
				try {
					Intent addpicture = new Intent("android.intent.action.ADDPICTURES");
					addpicture.putExtra("propertyid", propertyid);
					startActivity(addpicture);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
			
			super.onPostExecute(result);
		}
		
	}
}
