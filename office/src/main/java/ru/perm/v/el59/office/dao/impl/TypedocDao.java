package ru.perm.v.el59.office.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.critery.TypedocCritery;
import ru.perm.v.el59.office.db.TypeDoc;

public class TypedocDao extends GenericDaoHibernateImpl<TypeDoc, Long> {

	public TypedocDao(Class<TypeDoc> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getByCritery(Object critery) {
		TypedocCritery c = (TypedocCritery) critery;
		Criteria q = getSession().createCriteria(TypeDoc.class);
		if (c.getName() != null)
			q.add(Restrictions.like("name", c.getName(), MatchMode.ANYWHERE)
					.ignoreCase());
		List list = q.list();
		return list;
	}
}
