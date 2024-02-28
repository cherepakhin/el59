package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Связь")
public class LinkGroupT {
	@XStreamAlias("Ид")
	private String cod;
	@XStreamAlias("Индекc")
	private Integer ord;

	public LinkGroupT(String cod, Integer ord) {
		super();
		this.cod = cod;
		this.ord = ord;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}
}
