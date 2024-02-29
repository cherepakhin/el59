package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import ru.perm.v.el59.office.db.GroupT;

public class GroupTovarXml {
	@XStreamAlias("Код")
	private String cod = "0000000000";

	public GroupTovarXml(GroupT g) {
		super();
		if (g != null) {
			cod = g.getCod();
		}
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}
