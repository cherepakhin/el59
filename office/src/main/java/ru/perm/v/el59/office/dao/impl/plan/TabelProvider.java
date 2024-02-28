package ru.perm.v.el59.office.dao.impl.plan;

import org.hibernate.Hibernate;

import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.plan.Tabel;
import ru.perm.v.el59.office.iproviders.plan.ITabelProvider;

public class TabelProvider extends GenericDaoHibernateImpl<Tabel, Long> implements ITabelProvider{

	public TabelProvider(Class<Tabel> type) {
		super(type);
	}

	@Override
	public Tabel initialize(Long id) {
		Tabel o = (Tabel) getSession().get(Tabel.class, id);
		Hibernate.initialize(o.getSmena());
		return o;
	}

}