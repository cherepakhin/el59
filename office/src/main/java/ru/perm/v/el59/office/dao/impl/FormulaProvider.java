package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.dao.CommonCritery;
import ru.el59.office.db.Formula;
import ru.perm.v.el59.dto.office.iproviders.IFormulaProvider;

public class FormulaProvider extends GenericDaoHibernateImpl<Formula, Long>
		implements IFormulaProvider {

	public FormulaProvider(Class<Formula> type) {
		super(type);
	}

	@Override
	public List<Formula> getAll() {
		List<Formula> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

	@Override
	public List<Formula> getWorked() {
		Criteria criteria = getSession().createCriteria(Formula.class);
		criteria.add(Restrictions.eq("worked", true));
		criteria.addOrder(Order.asc("name"));
		ArrayList<Formula> list = (ArrayList<Formula>) criteria.list();
		return list;
	}
}
