package com.propertymanagerententity;


import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the owners database table.
 * 
 */
@Entity
@Table(name="owner")
public class Owner implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ownerid;
	private String email;
	private String firstname;
	private String lastname;
	private String mobile;
	private List<Property> properties;

    public Owner() {
    }


	@Id
	@Column(unique=true, nullable=false)
	public int getOwnerid() {
		return this.ownerid;
	}

	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}


	@Column(nullable=false, length=80)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(nullable=false, length=45)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	@Column(nullable=false, length=45)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	@Column(nullable=false, length=17)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	//bi-directional many-to-one association to Property
	@OneToMany(mappedBy="owner")
	public List<Property> getProperties() {
		return this.properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
}