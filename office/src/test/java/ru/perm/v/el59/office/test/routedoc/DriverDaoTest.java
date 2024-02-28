package ru.perm.v.el59.office.test.routedoc;

import ru.el59.office.db.routedoc.Driver;
import ru.perm.v.el59.office.test.model.DaoTest;

public class DriverDaoTest extends DaoTest<Driver,Long> {

	@Override
	protected String getNameDao() {
		return "driverDao";
	}

}
