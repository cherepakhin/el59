package ru.perm.v.el59.office.dao.impl.service;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.TDoc;
import ru.perm.v.el59.office.iproviders.critery.TDocCritery;
import ru.perm.v.el59.office.iproviders.service.ITDocProvider;

import java.util.List;

public class TDocProvider extends GenericDaoHibernateImpl<TDoc, Long> implements ITDocProvider {

	public TDocProvider(Class<TDoc> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TDoc> getByCritery(Object critery) {
		TDocCritery tdoc = (TDocCritery) critery;
		Criteria criteria = getSession().createCriteria(TDoc.class);
		criteria.addOrder(Order.asc("n"));
		if (tdoc.parent != null)
			criteria.add(Restrictions.eq("parent", tdoc.parent));
		if (tdoc.root != null)
			criteria.add(Restrictions.eq("rootDoc", tdoc.root));
		return criteria.list();
	}

	@Override
	public TDoc initialize(Long id) {
		TDoc o = (TDoc) getSession().get(TDoc.class, id);
		if(o!=null && o.getTdocimage()!=null) {
			Hibernate.initialize(o.getTdocimage());
		}
		return o;
	}
}
