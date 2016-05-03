package com.propertymanager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class LoginActivity extends Activity {
	public ProgressDialog pDialog;
	//public static final Intent KEY_REST_FILTER = null;
	//public static final String KEY_REST_RESPONSE = null;
	public static Context context;
	public static int x = -1;
	// later url should be changed to match actual server URL
	final String myuri = "http://192.168.0.12:7001/PropertyManager/jaxrs/login";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		if (x == -1) {
			TextView tv = (TextView) findViewById(R.id.loginmessage);
			tv.setVisibility(View.INVISIBLE);
		}
		btnLogin.setOnClickListener(
					new OnClickListener() {
				@Override
				public void onClick(View v) {
					/* original 
					new LogInTask(LoginActivity.this).execute(myuri);
					*/
					
					// remove from hear after test complete
					try{
						Intent choiceIntent = new Intent(getBaseContext(), Choices.class);
						choiceIntent.putExtra("userid", "wfi75");
						startActivity(choiceIntent);
						
					} finally {
						finish();
					} 
					// remove untill here
					
				}
			});
		context = this;
		
				
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_login, menu);
		return true;
	}

	public class LogInTask extends AsyncTask<String, Void, String> {
		public Context context;
		
		
		String userid = "-1";
		
		public LogInTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Checking user account..");
			pDialog.setTitle("Logging in");
			pDialog.show();
			//super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
				String message;
				EditText username = (EditText)findViewById(R.id.username);
				String myusername = username.getText().toString();
				EditText password = (EditText)findViewById(R.id.password);
				String mypassword = password.getText().toString();
				
				HttpPost hp = new HttpPost(params[0]);
				
				HttpClient hc = new DefaultHttpClient();
				
				JSONObject jsonOut= new JSONObject();
				try {
					jsonOut.put("username", myusername);
					jsonOut.put("password", mypassword);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					message = jsonOut.toString();
					
					hp.setEntity(new StringEntity(message,"UTF8"));
					hp.setHeader("Content-Type", "application/json");
									
					HttpResponse resp = hc.execute(hp);
					
					if (resp != null) {
						if (resp.getStatusLine().getStatusCode() == 200) {
						
							BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(),"UTF-8"));
							String injson = br.readLine();
							JSONTokener tokener = new JSONTokener(injson);
							try {
								JSONObject json = new JSONObject(tokener);
								userid = (String) json.get("userid");
								return userid;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
							
					}
				
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				hc.getConnectionManager().closeExpiredConnections();
				
				
			return "-1";
		}
		
		@Override
		protected void onPostExecute(String userid) {
			
			if (userid !="-1") {
				try{
					Intent choiceIntent = new Intent(getBaseContext(), Choices.class);
					choiceIntent.putExtra("userid", userid);
					startActivity(choiceIntent);
					
				} finally {
					finish();
				} 
			} else {
				x = 1;
				TextView tv = (TextView) findViewById(R.id.loginmessage);
				tv.setVisibility(View.VISIBLE);
								
			}
			
			pDialog.dismiss();
			super.onPostExecute(userid);
			
		}

	}

	
}
