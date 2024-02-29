package ru.perm.v.el59.office.dao.impl.shopmodel;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.db.CreditBank;
import ru.perm.v.el59.office.iproviders.shopmodel.IBankActionProvider;
import ru.perm.v.el59.office.shopmodel.BankAction;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.ArrayList;
import java.util.List;

public class BankActionProvider extends GenericDaoMessageImpl<BankAction, Long>
		implements IBankActionProvider {

	public BankActionProvider(Class<BankAction> type) {
		super(type);
	}

	@Override
	public List<BankAction> getAll(CreditBank bank) {
		Criteria criteria = getSession().createCriteria(BankAction.class);
		if (bank != null) {
			criteria.add(Restrictions.eq("bank", bank));
			return criteria.list();
		} else {
			return new ArrayList<BankAction>();
		}
	}

	@Override
	public BankAction initialize(Long id) {
		BankAction o = (BankAction) getSession().get(BankAction.class, id);
		Hibernate.initialize(o.getTypeprices());
		Hibernate.initialize(o.getDiscounts());
		return o;
	}

	/*
	 * @Override public void update(Object o) { getSession().update(o); //
	 * getCommander().update(o,"*"); }
	 */
}
