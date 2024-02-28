package ru.perm.v.el59.office.test.shopmodel;

import ru.el59.office.shopmodel.Expense;
import ru.perm.v.el59.office.test.model.DaoTest;

public class ExpenseDaoTest extends DaoTest<Expense, Long> {

	@Override
	protected String getNameDao() {
		return "expenseDao";
	}


}
