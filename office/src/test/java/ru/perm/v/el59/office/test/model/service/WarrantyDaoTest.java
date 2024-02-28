package ru.perm.v.el59.office.test.model.service;

import ru.el59.office.db.service.Warranty;
import ru.perm.v.el59.office.test.model.DaoTest;

public class WarrantyDaoTest extends DaoTest<Warranty,Long> {

	@Override
	protected String getNameDao() {
		return "warrantyDao";
	}

}
