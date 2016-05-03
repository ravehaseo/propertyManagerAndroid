package com.propertymanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		// starting a thread for the splash screen that will last for 5 seonds
		Thread splash = new Thread(){
			@Override
			public void run(){
				try{
					sleep(1000);
					Intent loginIntent = new Intent("android.intent.action.LOGIN");
					startActivity(loginIntent);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			
			}
		};
		splash.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
