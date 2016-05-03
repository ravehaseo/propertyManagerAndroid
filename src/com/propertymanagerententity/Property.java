package com.propertymanagerententity;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the property database table.
 * 
 */
@Entity
@Table(name="property")
public class Property implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String address;
	private String alarm;
	private int area;
	private String availabel;
	private int baths;
	private String discreption;
	private int door;
	private int floor;
	private String garden;
	private String kitchen;
	private String offertype;
	private String pool;
	private int rentprice;
	private int rooms;
	private int sealprice;
	private String security;
	private String type;
	private int zipcode;
	private Media media;
	private Owner owner;

    public Property() {
    }


	@Id
	@Column(unique=true, nullable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(nullable=false, length=145)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Column(nullable=false, length=3)
	public String getAlarm() {
		return this.alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}


	@Column(nullable=false)
	public int getArea() {
		return this.area;
	}

	public void setArea(int area) {
		this.area = area;
	}


	@Column(nullable=false, length=45)
	public String getAvailabel() {
		return this.availabel;
	}

	public void setAvailabel(String availabel) {
		this.availabel = availabel;
	}


	@Column(nullable=false)
	public int getBaths() {
		return this.baths;
	}

	public void setBaths(int baths) {
		this.baths = baths;
	}


	@Column(nullable=false, length=145)
	public String getDiscreption() {
		return this.discreption;
	}

	public void setDiscreption(String discreption) {
		this.discreption = discreption;
	}


	@Column(nullable=false)
	public int getDoor() {
		return this.door;
	}

	public void setDoor(int door) {
		this.door = door;
	}


	@Column(nullable=false)
	public int getFloor() {
		return this.floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}


	@Column(nullable=false, length=8)
	public String getGarden() {
		return this.garden;
	}

	public void setGarden(String garden) {
		this.garden = garden;
	}


	@Column(nullable=false, length=45)
	public String getKitchen() {
		return this.kitchen;
	}

	public void setKitchen(String kitchen) {
		this.kitchen = kitchen;
	}


	@Column(nullable=false, length=45)
	public String getOffertype() {
		return this.offertype;
	}

	public void setOffertype(String offertype) {
		this.offertype = offertype;
	}


	@Column(nullable=false, length=8)
	public String getPool() {
		return this.pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}


	@Column(nullable=false)
	public int getRentprice() {
		return this.rentprice;
	}

	public void setRentprice(int rentprice) {
		this.rentprice = rentprice;
	}


	@Column(nullable=false)
	public int getRooms() {
		return this.rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}


	@Column(nullable=false)
	public int getSealprice() {
		return this.sealprice;
	}

	public void setSealprice(int sealprice) {
		this.sealprice = sealprice;
	}


	@Column(nullable=false, length=3)
	public String getSecurity() {
		return this.security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}


	@Column(nullable=false, length=45)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Column(nullable=false)
	public int getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}


	//bi-directional one-to-one association to Media
	@OneToOne(mappedBy="property")
	public Media getMedia() {
		return this.media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}
	

	//bi-directional many-to-one association to Owner
    @ManyToOne
	@JoinColumn(name="ownerid", nullable=false)
	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
}