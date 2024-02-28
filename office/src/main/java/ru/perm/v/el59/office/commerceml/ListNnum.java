package ru.perm.v.el59.office.commerceml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

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
