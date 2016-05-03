package com.propertymanagerententity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;
	private int accesslevel;
	private String email;
	private String firstname;
	private String lastname;
	private String mobile;
	private String password;

    public User() {
    }


	@Id
	@Column(unique=true, nullable=false, length=25)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	public int getAccesslevel() {
		return this.accesslevel;
	}

	public void setAccesslevel(int accesslevel) {
		this.accesslevel = accesslevel;
	}


	@Column(length=45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(length=45)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	@Column(length=45)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	@Column(length=45)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	@Column(length=45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}