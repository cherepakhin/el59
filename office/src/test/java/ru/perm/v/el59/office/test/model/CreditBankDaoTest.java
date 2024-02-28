package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import ru.el59.office.db.CreditBank;

public class CreditBankDaoTest extends DaoTest<CreditBank,Long>{

	@Override
	protected String getNameDao() {
		return "creditBankDao";
	}

	@Test
	public void update() {
		CreditBank b = getDao().read(new Long(1));
		try {
			getDao().update(b);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error update");
		}
		assertTrue(true);
	}
}
