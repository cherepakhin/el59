package ru.perm.v.el59.office.test.model;

import ru.el59.office.db.Operation;

public class OperationDaoTest extends DaoTest<Operation,Long>{

	@Override
	protected String getNameDao() {
		return "operationDao";
	}

}
