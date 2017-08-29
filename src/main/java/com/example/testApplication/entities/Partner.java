package com.example.testApplication.entities;

import javax.persistence.*;

@Entity
@Table(name = "Partner")
public class Partner {

	@Id
	@GeneratedValue
	Long id;

	@Column(name = "name")
	String name;

	@Column(name = "phone")
	String phone;

	@Column(name = "mail")
	String mail;

	@ManyToOne(optional = false)
	private Address address;


	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return String.format(
				"Partner[id=%d, name='%s', phone='%s', mail='%s', streetnr='%s', postcode='%s', city='%s' ]", id, name,
				phone, mail, address.getStreet(), address.getPostcode(), address.getCity());
	}
}
