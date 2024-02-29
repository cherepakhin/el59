package ru.perm.v.el59.office.dao.impl.plan;

import org.hibernate.Hibernate;
import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.plan.TypeTabel;
import ru.perm.v.el59.office.db.plan.TypeTabelVal;
import ru.perm.v.el59.office.iproviders.plan.ITypeTabelProvider;

import java.util.ArrayList;
import java.util.List;

public class TypeTabelProvider extends GenericDaoHibernateImpl<TypeTabel, Long>
		implements ITypeTabelProvider {

	public TypeTabelProvider(Class<TypeTabel> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TypeTabel initialize(Long id) {
		TypeTabel o = (TypeTabel) getSession().get(TypeTabel.class, id);
		Hibernate.initialize(o.getVals());
		return o;
	}

	public List<TypeTabel> getAll() {
		List<TypeTabel> ret = getByCritery(new CommonCritery(""));
		for (TypeTabel typeTabel : ret) {
			typeTabel = (TypeTabel) initialize(typeTabel.getN());
		}
		return ret;
	}

	@Override
	public List<TypeTabelVal> getListModel(String nameTypeTabelVal) {
		List list = getByCritery(new CommonCritery(nameTypeTabelVal));
		List<TypeTabelVal> ret = new ArrayList<TypeTabelVal>();
		if (list.size() > 0) {
			TypeTabel t = (TypeTabel) list.get(0);
			t = (TypeTabel) initialize(t.getN());
			ret = t.getVals();
		}
		return ret;
	}

}
