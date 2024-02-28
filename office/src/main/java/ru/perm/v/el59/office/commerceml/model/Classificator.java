package ru.perm.v.el59.office.commerceml.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Классификатор")
public class Classificator extends AEntityCommerce {
	@XStreamAlias("Владелец")
	private Owner owner;
	@XStreamAlias("Группы")
	private List<Group> groups = new ArrayList<Group>();
	@XStreamAlias("Свойства")
	private List<PropertyName> listPropertyName = new ArrayList<PropertyName>();

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<PropertyName> getListPropertyName() {
		return listPropertyName;
	}

	public void setListPropertyName(List<PropertyName> listPropertyName) {
		this.listPropertyName = listPropertyName;
	}
}
