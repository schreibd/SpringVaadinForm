package com.example.testApplication.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Address")
public class Address {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "streetnr")
	private String streetAndNr;

	@Column(name = "postcode")
	private String postcode;

	@Column(name = "city")
	private String city;

	public Long getId() {
		return id;
	}

	public void setStreet(String streetAndNr) {
		this.streetAndNr = streetAndNr;
	}

	public String getStreet() {
		return streetAndNr;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return String.format("Partner[id=%d, streetnr='%s', postcode='%s', city='%s' ]", id, streetAndNr, postcode,
				city);
	}
}
