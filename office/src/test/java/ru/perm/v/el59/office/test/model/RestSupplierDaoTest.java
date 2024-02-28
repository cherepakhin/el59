package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.RestSupplier;

public class RestSupplierDaoTest extends DaoTest<RestSupplier, Long> {

	@Override
	protected String getNameDao() {
		return "restSupplierDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		RestSupplier e = getDao().read(n);
		System.out.println("Entity:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
