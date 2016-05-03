package com.propertymanager;

import com.propertymanagerententity.Owner;
import com.propertymanagerententity.Property;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Choices extends Activity {
	public static int userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userid = getIntent().getIntExtra("userid", -1);
		setContentView(R.layout.activity_choices);
		Button btnListAll = (Button) findViewById(R.id.btnlistall);
		Button btnSerach = (Button) findViewById(R.id.btnsearchforproperty);
		Button btnAdd = (Button) findViewById(R.id.btnaddnewproperty);
		
		Button btnExit = (Button) findViewById(R.id.btnexitpropertymanager);
		btnListAll.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent listAllIntent = new Intent(getBaseContext(), Listall.class);
							startActivity(listAllIntent);	
			}
		});
		btnSerach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent searchIntent = new Intent(getBaseContext(), Search.class);
				startActivity(searchIntent);	
			}
		});
		
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent AddIntent = new Intent(getBaseContext(), AddProperty.class);
				Property prop = new Property();
				Owner owner = new Owner();
				owner.setMobile("0");
				prop.setZipcode(0);
				prop.setKitchen("");
				prop.setOwner(owner);
				AddIntent.putExtra("property", prop);
				startActivity(AddIntent);	
			}
		});
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choices, menu);
		return true;
	}

}
