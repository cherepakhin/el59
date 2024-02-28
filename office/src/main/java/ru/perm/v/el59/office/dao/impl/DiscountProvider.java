package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.iproviders.shopmodel.IDiscountProvider;
import ru.perm.v.el59.office.shopmodel.Discount;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class DiscountProvider extends GenericDaoMessageImpl<Discount, Long>
		implements IDiscountProvider {

	public DiscountProvider(Class<Discount> type) {
		super(type);
	}

	@Override
	public List<Discount> getWorkedToday(Boolean ondetail) {
		Criteria criteria = getSession().createCriteria(Discount.class);
		criteria.add(Restrictions.eq("ondetail", ondetail));
		ArrayList<Discount> retList = (ArrayList<Discount>) criteria.list();
		return retList;
	}
}
