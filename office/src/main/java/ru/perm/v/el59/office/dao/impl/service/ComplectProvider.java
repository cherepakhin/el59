package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.Complect;
import ru.perm.v.el59.office.iproviders.service.IComplectProvider;

public class ComplectProvider extends GenericDaoHibernateImpl<Complect, Long> implements IComplectProvider{

	public ComplectProvider(Class<Complect> type) {
		super(type);
	}

}
