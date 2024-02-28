package ru.perm.v.el59.office.dao.impl.shopmodel;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.IExpenseProvider;
import ru.perm.v.el59.office.shopmodel.Expense;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class ExpenseProvider extends GenericDaoMessageImpl<Expense, Long>
		implements IExpenseProvider {

	public ExpenseProvider(Class<Expense> type) {
		super(type);
	}

	@Override
	public List<Expense> getAll() {
		List<Expense> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

	@Override
	public List<Expense> getNegative() {
		Criteria criteria = getSession().createCriteria(Expense.class);
		criteria.add(Restrictions.lt("znak", 0));
		List<Expense> ret = criteria.list();
		return ret;
	}

	@Override
	public List<Expense> getPositive() {
		Criteria criteria = getSession().createCriteria(Expense.class);
		criteria.add(Restrictions.gt("znak", 0));
		List<Expense> ret = criteria.list();
		return ret;
	}
}
