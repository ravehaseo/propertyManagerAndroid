package com.propertymanager;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.propertymanagerententity.Property;

public class PropertyListAdapter extends BaseAdapter {
	
	public static ArrayList<Property> property;
	Context context;
	//LayoutInflater propLayInf;
	
	public PropertyListAdapter(Property[] prop, Context cont) {
		property = new ArrayList<Property>();
		for (int i = 0; i<prop.length; i++) {
			property.add(prop[i]);
		}
		context = cont;
		//propLayInf = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// returns the number of objects in the array i.e the number of properties listed
		return property.size();
	}

	@Override
	public Property getItem(int arg0) {
		// returns the location of the object on the list i.e the position
		return property.get(arg0);
	}
	 
	public static Property GetItem (int arg0) {
		return property.get(arg0);
		
		
	}
	

	@Override
	public long getItemId(int arg0) {
		// return the id of the property in the database
		return property.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup viewgroup) {
		// creating a layout inflater to inflate the views of the property into the list view	
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		converView = inflater.inflate(R.layout.propertylistitem, viewgroup , false);
		
			/*
			 * if (!(temp.equals("null"))) {
			 
				// getting the icon image from url and shoing it insted of the defult image
				try {
					InputStream instream = (InputStream) new URL(property[arg0].getMedia().getIcon()).getContent();
					Drawable d = Drawable.createFromStream(instream, "srcName");
					ImageView icon = (ImageView)converView.findViewById(R.id.listicon);
					icon.setImageDrawable(d);
				} catch (Exception e) {
					e.printStackTrace();
				}
				*/
				
				// setting the property type field 
				TextView typeProp = (TextView) converView.findViewById(R.id.rowtype);
				typeProp.setText("Type: " + property.get(arg0).getType());
				
				// setting the offer type field
				TextView typeoffe = (TextView) converView.findViewById(R.id.rowoffertype);
				typeoffe.setText("For: " + property.get(arg0).getOffertype());
				
				// setting the rental price field
				TextView rentprice = (TextView) converView.findViewById(R.id.rentprice);
				rentprice.setText("Rent amount: " + property.get(arg0).getRentprice());
				
				// setting the seal price field
				TextView sealprice = (TextView) converView.findViewById(R.id.sealprice);
				sealprice.setText("Seal amount: " + property.get(arg0).getSealprice());
				
				// setting the number of rooms field
				TextView rooms = (TextView) converView.findViewById(R.id.rowrooms);
				rooms.setText("Rooms: " + property.get(arg0).getRooms());
				
				// setting the area field
				TextView area = (TextView) converView.findViewById(R.id.rowarea);
				area.setText("Area: " + property.get(arg0).getArea() + " m2");
				
				// setting the zipcode field
				TextView zip = (TextView) converView.findViewById(R.id.rowzipcode);
				zip.setText("Zip Code: " + property.get(arg0).getZipcode());
				
				// setting the availability ribbon color
				Property currentProperty = property.get(arg0);
				View availability = converView.findViewById(R.id.AvRibbon);
				int ava = 0;
				if (currentProperty.getAvailabel().equalsIgnoreCase("yes")) ava = 1;
				if (currentProperty.getAvailabel().equalsIgnoreCase("no")) ava = 0;
				switch (ava) {
				case 1: 
					availability.setBackgroundColor(viewgroup.getResources().getColor(R.color.available)); 
					break;
				case 0: 
					availability.setBackgroundColor(viewgroup.getResources().getColor(R.color.notavailable));
					break;
				}
				
				
		return converView;
	}

}
