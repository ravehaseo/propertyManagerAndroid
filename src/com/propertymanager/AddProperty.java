package com.propertymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.propertymanagerententity.Owner;
import com.propertymanagerententity.Property;

public class AddProperty extends Activity {
	
	
	// owner screen components 
	EditText ownerfirst, ownerlast, ownermobile, owneremail;
	Button next, prev;
	Property newproperty;
	Owner newowner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user);
		
		
		newproperty = (Property) getIntent().getSerializableExtra("property");
		newowner = newproperty.getOwner();
		
		
		// first view **** defining the view components
		ownerfirst = (EditText) findViewById(R.id.ownerfirst);
		ownerlast = (EditText) findViewById(R.id.ownerlast);
		ownermobile = (EditText) findViewById(R.id.ownermobile);
		owneremail = (EditText) findViewById(R.id.owneremail);
		next = (Button) findViewById(R.id.ownernext);
		prev = (Button) findViewById(R.id.adduserdummy);
		prev.setVisibility(View.INVISIBLE);
		
		// if coming back from the next menu we set the inputs back to previous
		int x = Integer.valueOf(newowner.getMobile());
		if ( x!= 0) {
			ownerfirst.setText(newproperty.getOwner().getFirstname());
			ownerlast.setText(newproperty.getOwner().getLastname());
			ownermobile.setText(newproperty.getOwner().getMobile());
			owneremail.setText(newproperty.getOwner().getEmail());
		}

				
		// clicking the next button on first screen
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// getting the values of the owner from the fields
				newowner.setFirstname(ownerfirst.getText().toString());
				newowner.setLastname(ownerlast.getText().toString());
				newowner.setMobile(ownermobile.getText().toString());
				newowner.setEmail(owneremail.getText().toString());
				newproperty.setOwner(newowner);
				
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


}
