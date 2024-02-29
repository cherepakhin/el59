package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

public class ListNnum {
	@XStreamImplicit(itemFieldName = "Ид")
	List<Integer> list = new ArrayList<Integer>();

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

}
