package ru.perm.v.el59.office.web;

import java.io.Serializable;

/**
 * Магазин в заявке web-магазина
 * 
 * @author vasi
 * 
 */
public class ShopDTO implements Serializable {

	private static final long serialVersionUID = -6453414968723100871L;

	private String cod = "";
	private String name = "";

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
