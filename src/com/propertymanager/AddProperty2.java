package com.propertymanager;

import com.propertymanagerententity.Property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProperty2 extends Activity {
	
	// adding property screen 1 components
		EditText zipcode, address, floor, door, price, area;
		Spinner addoffertype, addpropertytpe;
		public String zipcod, addres,  flor, dor, pric, are, offer, type;
		Button next, prev;
		Property newproperty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_property);
		
		newproperty = (Property) getIntent().getSerializableExtra("property");
		
		newproperty.setRentprice(0);
		newproperty.setSealprice(0);
		zipcode = (EditText) findViewById(R.id.addzipcode);
		address = (EditText) findViewById(R.id.addaddress);
		floor = (EditText) findViewById(R.id.addfloor);
		door = (EditText) findViewById(R.id.adddoor);
		price = (EditText) findViewById(R.id.addprice);
		area = (EditText) findViewById(R.id.addarea);
		addoffertype = (Spinner) findViewById(R.id.addoffertype);
		addpropertytpe = (Spinner) findViewById(R.id.addpropertytype);
		next = (Button) findViewById(R.id.addpropertynext1);
		prev = (Button) findViewById(R.id.addpropertyprev1);
		
		
		// if form already been filed then we put the data to the UI 
		if (newproperty.getZipcode() != 0) {
			zipcode.setText(String.valueOf(newproperty.getZipcode()));
			address.setText(newproperty.getAddress());
			floor.setText(String.valueOf(newproperty.getFloor()));
			door.setText(String.valueOf(newproperty.getDoor()));
			area.setText(String.valueOf(newproperty.getArea()));
			if (newproperty.getOffertype().equals("Sale")) {
				addoffertype.setSelection(0);
				price.setText(String.valueOf(newproperty.getSealprice()));
			}
				
			else {
				addoffertype.setSelection(1);
				price.setText(String.valueOf(newproperty.getRentprice()));
			}
			if (newproperty.getType().equalsIgnoreCase((String) addpropertytpe.getItemAtPosition(0)))
				addpropertytpe.setSelection(0);
			else 
			if (newproperty.getType().equalsIgnoreCase((String) addpropertytpe.getItemAtPosition(1)))
				addpropertytpe.setSelection(1);
			else
			if (newproperty.getType().equalsIgnoreCase((String) addpropertytpe.getItemAtPosition(2)))
				addpropertytpe.setSelection(2);
			else
			if (newproperty.getType().equalsIgnoreCase((String) addpropertytpe.getItemAtPosition(3)))
				addpropertytpe.setSelection(3);
			else
			if (newproperty.getType().equalsIgnoreCase((String) addpropertytpe.getItemAtPosition(4)))
				addpropertytpe.setSelection(4);
		}
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!(zipcode.getText().toString().equals("")))
					newproperty.setZipcode(Integer.valueOf(zipcode.getText().toString()));
				else {
					Toast.makeText(getApplicationContext(), "Please enter a valid Zip Code",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (!(address.getText().toString().equals(""))) 
					newproperty.setAddress(address.getText().toString());
				else {
					Toast.makeText(getApplicationContext(), "Please enter a valid address",
							Toast.LENGTH_LONG).show();
					return;
				}
					
				if (!(floor.getText().toString().equals("")))
					newproperty.setFloor(Integer.valueOf(floor.getText().toString()));
				else {
					newproperty.setFloor(0);
				}
				if (!(door.getText().toString().equals("")))
					newproperty.setDoor(Integer.valueOf(door.getText().toString()));
				else {
					newproperty.setDoor(0);
				}
				
					newproperty.setOffertype(addoffertype.getSelectedItem().toString());
				if (!(area.getText().toString().equals("")))
					newproperty.setArea(Integer.valueOf(area.getText().toString()));
				else {
					Toast.makeText(getApplicationContext(), "Please enter a valid area",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				newproperty.setType(addpropertytpe.getSelectedItem().toString());
				
				if (newproperty.getOffertype().equals("Rent")) {
					if (!(price.getText().toString().equals(""))) {
						newproperty.setRentprice(Integer.valueOf(price.getText().toString()));
						newproperty.setSealprice(0);
					} else {
						Toast.makeText(getApplicationContext(), "Please enter a valid price",
								Toast.LENGTH_LONG).show();
						return;
					}
				} else 
				if (newproperty.getOffertype().equals("Sale")) {
					if (!(price.getText().toString().equals(""))) {
						newproperty.setSealprice(Integer.valueOf(price.getText().toString()));
						newproperty.setRentprice(0);
					} else {
						Toast.makeText(getApplicationContext(), "Please enter a valid price",
								   Toast.LENGTH_LONG).show();
						return;
					}
				}
					
				try {
					Intent addproperty = new Intent("android.intent.ADDPROPERTY3");
					addproperty.putExtra("property", newproperty);
					startActivity(addproperty);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		});
		
		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!(zipcode.getText().toString().equals("")))
					newproperty.setZipcode(Integer.valueOf(zipcode.getText().toString()));
				
				if (!(address.getText().toString().equals(""))) 
					newproperty.setAddress(address.getText().toString());
				
									
				if (!(floor.getText().toString().equals("")))
					newproperty.setFloor(Integer.valueOf(floor.getText().toString()));
				
				if (!(door.getText().toString().equals("")))
					newproperty.setDoor(Integer.valueOf(door.getText().toString()));
								
					newproperty.setOffertype(addoffertype.getSelectedItem().toString());
				if (!(area.getText().toString().equals("")))
					newproperty.setArea(Integer.valueOf(area.getText().toString()));
								
				newproperty.setType(addpropertytpe.getSelectedItem().toString());
				
				if (newproperty.getOffertype().equals("Rent")) {
					if (!(price.getText().toString().equals(""))) {
						newproperty.setRentprice(Integer.valueOf(price.getText().toString()));
						newproperty.setSealprice(0);
					} 
				} else 
				if (newproperty.getOffertype().equals("Sale")) {
					if (!(price.getText().toString().equals(""))) {
						newproperty.setSealprice(Integer.valueOf(price.getText().toString()));
						newproperty.setRentprice(0);
					} 
				}
					
				try {
					Intent addproperty = new Intent("android.intent.ADDPROPERTY");
					addproperty.putExtra("property", newproperty);
					startActivity(addproperty);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		});
	
	}
	
	

}
