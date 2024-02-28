package ru.perm.v.el59.office.dao.impl.plan;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.plan.Smena;
import ru.perm.v.el59.office.iproviders.plan.ISmenaProvider;

public class SmenaProvider extends GenericDaoHibernateImpl<Smena, Long>
		implements ISmenaProvider {

	public SmenaProvider(Class<Smena> type) {
		super(type);
	}

}
