package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.db.OpGroup;
import ru.perm.v.el59.office.iproviders.IOpGroupProvider;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.util.List;

public class OpGroupProvider extends GenericDaoHibernateImpl<OpGroup, Long>
		implements IOpGroupProvider {

	public OpGroupProvider(Class<OpGroup> type) {
		super(type);
	}

	@Override
	public String[] getAsStringArray() {
		CommonCritery critery = new CommonCritery("");
		List<OpGroup> list = getByCritery(critery);
		String[] ret = new String[list.size() + 1];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i).getName();
		}
		ret[list.size()] = "Все";
		return ret;
	}
}
