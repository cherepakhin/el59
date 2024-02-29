package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Классификатор")
public class Classificator {
	@XStreamAlias("Ид")
	private String id;
	@XStreamAlias("Наименование")
	private String name;
	@XStreamAlias("Группы")
	private List<GroupTXml> groups = new ArrayList<GroupTXml>();

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

	public List<GroupTXml> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupTXml> groups) {
		this.groups = groups;
	}
}
