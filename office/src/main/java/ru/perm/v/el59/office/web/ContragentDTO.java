package ru.perm.v.el59.office.web;

import java.io.Serializable;

/**
 * Покупатель web-магазина с фтп
 * 
 * @author vasi
 * 
 */
public class ContragentDTO implements Serializable {

	private static final long serialVersionUID = -7227095727119830451L;

	private String name = "";
	private String address = "";
	private String phone = "";
	private String email = "";
	private String info = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
