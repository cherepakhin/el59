package ru.perm.v.el59.office.commerceml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Группа")
public class Group extends AEntityCommerce {
	@XStreamAlias("Группы")
	private List<Group> groups = new ArrayList<Group>();

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}
