package ru.perm.v.el59.office.dao.impl;


import ru.perm.v.el59.office.db.GroupContragent;
import ru.perm.v.el59.office.iproviders.IGroupContragentProvider;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.List;

public class GroupContragentProvider extends GenericDaoMessageImpl<GroupContragent,Long> implements
		IGroupContragentProvider {

	public GroupContragentProvider(Class<GroupContragent> type) {
		super(type);
	}

	@Override
	public List<GroupContragent> getAll() {
		List<GroupContragent> ret = getByCritery(new CommonCritery(""));
		return ret;
	}
}
