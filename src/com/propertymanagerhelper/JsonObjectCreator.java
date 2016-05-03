package com.propertymanagerhelper;

import org.json.JSONException;
import org.json.JSONObject;

import com.propertymanagerententity.Property;

public class JsonObjectCreator {
	public static JSONObject outjson;
	
	
	public static JSONObject PropertytoJson(Property property) {
		try {
			// Putting Property to the json object
			// putting media data ** at start it is set to a string "null"
			outjson = new JSONObject();
			outjson.put("icon", "null");
			outjson.put("picture1", "null");
			outjson.put("picture2", "null");
			outjson.put("picture3", "null");
			outjson.put("picture4", "null");
			
			// putting owner data
			outjson.put("firstname", property.getOwner().getFirstname());
			outjson.put("lastname", property.getOwner().getLastname());
			outjson.put("email", property.getOwner().getEmail());
			outjson.put("mobile", property.getOwner().getMobile());
			
			// putting property data
			outjson.put("offertype", property.getOffertype());
			outjson.put("type", property.getType());
			outjson.put("rentprice", property.getRentprice());
			outjson.put("sealprice", property.getSealprice());
			outjson.put("rooms", property.getRooms());
			outjson.put("area", property.getArea());
			outjson.put("address", property.getAddress());
			outjson.put("alarm", property.getAlarm());
			outjson.put("available", property.getAvailabel());
			outjson.put("baths", property.getBaths());
			outjson.put("discreption", " discreption"+property.getDiscreption());
			outjson.put("door", property.getDoor());
			outjson.put("floor", property.getFloor());
			outjson.put("garden", property.getGarden());
			outjson.put("kitchen", property.getKitchen());
			outjson.put("pool", property.getPool());
			outjson.put("security", property.getSecurity());
			outjson.put("zipcode", property.getZipcode());
		} catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outjson;
	}

}
