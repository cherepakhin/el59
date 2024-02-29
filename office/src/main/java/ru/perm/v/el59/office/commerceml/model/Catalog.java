package ru.perm.v.el59.office.commerceml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Каталог")
public class Catalog extends AEntityCommerce {
	@XStreamAlias("ИдКлассификатора")
	private String idClassiff;
	@XStreamAlias("Владелец")
	private Owner owner;
	@XStreamAlias("Товары")
	private List<Good> goods = new ArrayList<Good>();

	public String getIdClassiff() {
		return idClassiff;
	}

	public void setIdClassiff(String idClassiff) {
		this.idClassiff = idClassiff;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public List<Good> getGoods() {
		return goods;
	}

	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}
}
