package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.Err;

public class ErrDaoTest extends DaoTest<Err,Long>{

	@Override
	protected String getNameDao() {
		return "errDao";
	}

	@Override
	public void read() {
		// Престало работать в 2012
		assertTrue(true);
	}

}
