package ru.perm.v.el59.office.commerceml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Банк")
public class Bank {
	@XStreamAlias("Наименование")
	private String name;
	@XStreamAlias("СчетКорреспондентский")
	private String korder;
	@XStreamAlias("БИК")
	private String bik;
	@XStreamAlias("Адрес")
	private Address address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKorder() {
		return korder;
	}

	public void setKorder(String korder) {
		this.korder = korder;
	}

	public String getBik() {
		return bik;
	}

	public void setBik(String bik) {
		this.bik = bik;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
