package ru.perm.v.el59.office.dao.impl.service;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.VneshVid;
import ru.perm.v.el59.office.iproviders.service.IVneshvidProvider;

public class VneshVidProvider extends GenericDaoHibernateImpl<VneshVid, Long> implements IVneshvidProvider{

	public VneshVidProvider(Class<VneshVid> type) {
		super(type);
	}

}
