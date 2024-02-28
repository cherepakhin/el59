package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.Warranty;
import ru.perm.v.el59.office.iproviders.service.IWarrantyProvider;

public class WarrantyProvider extends GenericDaoHibernateImpl<Warranty, Long> implements IWarrantyProvider{

	public WarrantyProvider(Class<Warranty> type) {
		super(type);
	}

}
