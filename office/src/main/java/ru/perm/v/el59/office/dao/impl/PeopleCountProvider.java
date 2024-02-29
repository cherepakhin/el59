package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.PeopleCount;
import ru.perm.v.el59.office.iproviders.IPeopleCountProvider;
import ru.perm.v.el59.office.iproviders.critery.PeopleCountCritery;

import java.util.ArrayList;
import java.util.List;

public class PeopleCountProvider extends
		GenericDaoHibernateImpl<PeopleCount, Long> implements
		IPeopleCountProvider {

	public PeopleCountProvider(Class<PeopleCount> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getByCritery(Object critery) {
		PeopleCountCritery _critery = (PeopleCountCritery) critery;
		Criteria criteria = getSession().createCriteria(PeopleCount.class);
		if ((_critery.fromdate != null) && (_critery.todate != null)) {
			criteria.add(Restrictions.ge("ddate", _critery.fromdate));
			criteria.add(Restrictions.le("ddate", _critery.todate));
		}
		if (_critery.shops.size() > 0)
			criteria.add(Restrictions.in("shop", _critery.shops));
		ArrayList<PeopleCount> list = (ArrayList<PeopleCount>) criteria.list();
		return list;
	}

	@Override
	public void update(PeopleCount o) throws Exception {
		PeopleCount pc = (PeopleCount) o;
		PeopleCountCritery critery = new PeopleCountCritery(pc);
		List<PeopleCount> list = getByCritery(critery);
		if (list.size() > 0) {
			list.get(0).setQty(pc.getQty());
			super.update(list.get(0));
		} else {
			super.create(o);
		}
	}
}
