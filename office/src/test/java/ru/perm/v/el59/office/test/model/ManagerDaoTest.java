package ru.perm.v.el59.office.test.model;

import ru.el59.office.db.Manager;

public class ManagerDaoTest extends DaoTest<Manager,Long>{

	@Override
	protected String getNameDao() {
		return "managerDao";
	}

}
