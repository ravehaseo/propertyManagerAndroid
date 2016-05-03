package com.propertymanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.propertymanagerententity.Property;

public class PropertyDetails extends Activity {

	Context main;
	public ListView detailslist;
	DetailsListAdapter adpter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_details);
		main = this;
		// getting the property passed from the listview 
		Property property = (Property) getIntent().getSerializableExtra("property");
		// vreating the listview of the property details
		detailslist = (ListView)findViewById(R.id.listdetails);
		adpter = new DetailsListAdapter(property, this);
		detailslist.setAdapter(adpter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.property_details, menu);
		return true;
	}

}
