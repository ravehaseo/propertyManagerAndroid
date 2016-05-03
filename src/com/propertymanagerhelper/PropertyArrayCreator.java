package com.propertymanagerhelper;

import org.json.JSONException;
import org.json.JSONObject;

import com.propertymanagerententity.Media;
import com.propertymanagerententity.Owner;
import com.propertymanagerententity.Property;

public class PropertyArrayCreator {
	
	
	public PropertyArrayCreator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Property[] JsonToList(Property[] propertyarray, int i, JSONObject json) throws JSONException{
		
		propertyarray[i] = new Property();
		// getting media data
		Media media = new Media();
		media.setIcon(json.getString("icon"));
		media.setPicture1(json.getString("picture1"));
		media.setPicture2(json.getString("picture2"));
		media.setPicture3(json.getString("picture3"));
		media.setPicture4(json.getString("picture4"));
		// getting owner data
		Owner owner = new Owner();
		owner.setOwnerid(json.getInt("ownerid"));
		owner.setLastname(json.getString("lastname"));
		owner.setFirstname(json.getString("firstname"));
		owner.setMobile(json.getString("mobile"));
		// getting property data
		//Property property = new Property();
		//property = new Property();
		propertyarray[i].setOffertype(json.getString("offertype"));
		propertyarray[i].setType(json.getString("type"));
		propertyarray[i].setRentprice(json.getInt("rentprice"));
		propertyarray[i].setSealprice(json.getInt("sealprice"));
		propertyarray[i].setRooms(json.getInt("rooms"));
		propertyarray[i].setArea(json.getInt("area"));
		propertyarray[i].setAddress(json.getString("address"));
		propertyarray[i].setAlarm(json.getString("alarm"));
		propertyarray[i].setAvailabel(json.getString("available"));
		propertyarray[i].setBaths(json.getInt("baths"));
		propertyarray[i].setDiscreption(json.getString("discreption"));
		propertyarray[i].setDoor(json.getInt("door"));
		propertyarray[i].setFloor(json.getInt("floor"));
		propertyarray[i].setGarden(json.getString("garden"));
		propertyarray[i].setId(json.getInt("id"));
		propertyarray[i].setKitchen(json.getString("kitchen"));
		propertyarray[i].setOwner(owner);
		propertyarray[i].setPool(json.getString("pool"));
		propertyarray[i].setSecurity(json.getString("security"));
		propertyarray[i].setZipcode(json.getInt("zipcode"));
		// ****
		propertyarray[i].setOwner(owner);
		propertyarray[i].setMedia(media);
		//propertyarray[i] = property;
		return propertyarray;
		
	}

}
