package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Классификатор")
public class Catalog {
	@XStreamAlias("Ид")
	private String id;
	@XStreamAlias("Наименование")
	private String name;
	@XStreamAlias("Товары")
	private List<TovarInfoXml> listTovarInfoXml = new ArrayList<TovarInfoXml>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TovarInfoXml> getListTovarInfoXml() {
		return listTovarInfoXml;
	}

	public void setListTovarInfoXml(List<TovarInfoXml> listTovarInfoXml) {
		this.listTovarInfoXml = listTovarInfoXml;
	}
}
