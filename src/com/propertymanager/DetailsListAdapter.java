package com.propertymanager;

import java.util.ArrayList;

import com.propertymanagerententity.Property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DetailsListAdapter extends BaseAdapter {
	
	public static ArrayList<String> pd;
	Context context;

	public DetailsListAdapter(Property property, Context cont) {
		
		context = cont;
		pd = new ArrayList<String>();
		int id = property.getId();
		pd.add("ID: " + String.valueOf(id));
		pd.add("Type: " + property.getType());
		pd.add("Offer: " + property.getOffertype());
		pd.add("Rent Amount: " + String.valueOf(property.getRentprice()));
		pd.add("Seal Amount: " + String.valueOf(property.getSealprice()));
		pd.add("Area: " + String.valueOf(property.getArea()));
		pd.add("Rooms: " + String.valueOf(property.getRooms()));
		pd.add("Kitchen: " + property.getKitchen());
		pd.add("Bathrooms: " + String.valueOf(property.getBaths()));
		pd.add("Garden: " + property.getGarden());
		pd.add("Pool: " + property.getPool());
		pd.add("Alarm: " + property.getAlarm());
		pd.add("Security: " + property.getSecurity());
		pd.add("Area Code: " + String.valueOf(property.getZipcode()));
		pd.add("Address: " + property.getAddress());
		pd.add("Floor: " + String.valueOf(property.getFloor()));
		pd.add("Door: " + String.valueOf(property.getDoor()));
		pd.add("Discreption: " + property.getDiscreption());
		
	}

	@Override
	public int getCount() {
		return pd.size();
	}

	@Override
	public Object getItem(int arg0) {
		return pd.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup viewgroup) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		converView = inflater.inflate(R.layout.propertydetailslist, viewgroup , false);
		
		TextView field = (TextView) converView.findViewById(R.id.field);
		field.setText(pd.get(arg0));
		
		return converView;
	}

}
