package com.propertymanager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
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

import com.propertymanagerententity.Property;
import com.propertymanagerhelper.PropertyArrayCreator;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AddPicture extends Activity {
	public  ProgressDialog pDialog;
	private static final int REQ_CAM = 101;
	private ImageView ivIcon;
	private Button getpicture;
	private int propertyid;
	private final String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp_image.jpg";
	private Bitmap img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_picture);
		
		ivIcon = (ImageView) findViewById(R.id.imageView1);
		getpicture = (Button) findViewById(R.id.addicon);
		
		propertyid = (int) getIntent().getIntExtra("propertyid", -1);
		
		getpicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File iconFile = new File(imagePath);
				Uri fileUri = Uri.fromFile(iconFile);
				Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(i, REQ_CAM);
				
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_CAM) {
			FileInputStream fis = null;
			try {
				Options opt = new BitmapFactory.Options();
				opt.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(imagePath, opt);
				int imgWidth = opt.outWidth;
				//int imgHeight = opt.outHeight;
				// setting the size to 86 of the original image
				int realWidth = ivIcon.getMeasuredWidth();
				int scalefactor = Math.round((float)imgWidth / (float) realWidth ); 
				opt.inSampleSize = scalefactor;
				opt.inJustDecodeBounds = false;
				
				img = BitmapFactory.decodeFile(imagePath, opt);
				fis = new FileInputStream(imagePath);
				
				ivIcon.setImageBitmap(img);
				new FileUploadTask(AddPicture.this).execute(imagePath);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			// add this to the postexcute instead of here 
			finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// till here 
		} else {
			super.onActivityResult(requestCode, resultCode, data);			
		}
	}	
	
	private class FileUploadTask extends AsyncTask<String, Void, Integer> {
		Context context;
		
		public FileUploadTask(Context context) {
			super();
			this.context = context;
		}



		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(AddPicture.this);
			pDialog.setMessage("Listing All Properties");
			pDialog.setTitle("Please wait while loading data from server...");
			pDialog.show();
			
			super.onPreExecute();
		}
		
		
		
		@Override
		protected Integer doInBackground(String... params) {
			int serverResponseCode = 0;
			String myurl = "http//192.168.10.235:7001/PropertyManager/jaxrs/fileadder";
			
			String fileName = params[0];
			
			HttpURLConnection conn = null;
			DataOutputStream dos= null;
			String lineEnd = "\r\n";
			String boundary = "*****";
			String twoHyphens = "--";
			int bytesRead, bytesAvailabel, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;
			File sourceFile = new File(params[0]);
			
			if (!sourceFile.isFile()) {
				pDialog.dismiss();
				Log.e("uploadFile", "Source File not exist:" + imagePath);
				
				Toast.makeText(getApplicationContext(), "Source File not exist :"+ imagePath,
						Toast.LENGTH_LONG).show();
				return null;
				
			} else {
				try {
					// openin a url connectio
					FileInputStream fis= new FileInputStream(sourceFile);
					URL url = new URL(myurl);
					
					// Opening HTTP connection to the given url
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoInput(true); 		// allows input
					conn.setDoOutput(true);		// allows output
					conn.setUseCaches(false);	// dont use cached copy
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Connection", "Keep-Alive");
	                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	                conn.setRequestProperty("uploaded_file", fileName);
	                
	                dos = new DataOutputStream(conn.getOutputStream());
	                
	                dos.writeBytes(twoHyphens + boundary + lineEnd);
	                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                             + fileName + "\"" + lineEnd);
	                dos.writeBytes(lineEnd);
	                
	                // create a buffer of max size
	                bytesAvailabel = fis.available();
	                
	                bufferSize = Math.min(bytesAvailabel, maxBufferSize);
	                buffer = new byte[bufferSize];
	                
	                // reading the file ad writing it to the form 
					bytesRead = fis.read(buffer, 0, bufferSize);
					
					while (bytesRead > 0) {
						dos.write(buffer, 0, bufferSize);
						bytesAvailabel = fis.available();
						bufferSize = Math.min(bytesAvailabel, maxBufferSize);
						bytesRead = fis.read(buffer, 0, bufferSize);
					}
					
					// sending the multipart form data 
					dos.writeBytes(lineEnd);
					dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
					dos.writeBytes("propertyid : " + String.valueOf(propertyid) );
					
					// Responses from server (code and message)
					serverResponseCode = conn.getResponseCode();
					String serverResponseMessage = conn.getResponseMessage();
					
					Log.i("uploadFile", "HTTP Response is : "
	                       + serverResponseMessage + ": " + serverResponseCode);
					
					if (serverResponseCode == 200) {
						runOnUiThread(new Runnable() {
                            public void run() {
                               Toast.makeText(AddPicture.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                            }
                        });   
					}
					// closing the streams
					fis.close();
					dos.flush();
					dos.close();
				} catch (MalformedURLException e) {
					pDialog.dismiss();  
	                e.printStackTrace();
	                runOnUiThread(new Runnable() {
	                	public void run() {
	                
	                		Toast.makeText(AddPicture.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	                	}
	                });
	                Log.e("Upload file to server", "error: " + e.getMessage(), e);
				} catch (Exception e) {
					pDialog.dismiss();  
					e.printStackTrace();
	                   
					runOnUiThread(new Runnable() {
						public void run() {
					
							Toast.makeText(AddPicture.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
						}	
					});
					Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);  
	             }
				pDialog.dismiss();       
	            return serverResponseCode; 
			}
		}
//
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		
			
	}
}





