package ru.perm.v.el59.office.commerceml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("СтавкаНалога")
public class Tax {
	@XStreamAlias("Наименование")
	private String name;
	@XStreamAlias("Ставка")
	private String val;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
