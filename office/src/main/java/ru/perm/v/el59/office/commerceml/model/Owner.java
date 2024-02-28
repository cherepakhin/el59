package ru.perm.v.el59.office.commerceml.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Владелец")
public class Owner extends AEntityCommerce {
	@XStreamAlias("ПолноеНаименование")
	private String fullname;
	@XStreamAlias("ИНН")
	private String inn;
	@XStreamAlias("ОКПО")
	private String okpo;
	@XStreamAlias("РасчетныеСчета")
	private List<ROrder> rorders;

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getOkpo() {
		return okpo;
	}

	public void setOkpo(String okpo) {
		this.okpo = okpo;
	}

	public List<ROrder> getRorders() {
		return rorders;
	}

	public void setRorders(List<ROrder> rorders) {
		this.rorders = rorders;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}
