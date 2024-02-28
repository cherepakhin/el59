package ru.perm.v.el59.office.test.shopmodel;

import ru.el59.office.shopmodel.Reason;
import ru.perm.v.el59.office.test.model.DaoTest;

public class ReasonDaoTest extends DaoTest<Reason, Long> {

	@Override
	protected String getNameDao() {
		return "reasonDao";
	}

}
