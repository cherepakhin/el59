package ru.perm.v.el59.office.test.shopmodel;

import ru.el59.office.shopmodel.Discount;
import ru.perm.v.el59.office.test.model.DaoTest;

public class DiscountDaoTest extends DaoTest<Discount, Long> {

	@Override
	protected String getNameDao() {
		return "discountDao";
	}


}
