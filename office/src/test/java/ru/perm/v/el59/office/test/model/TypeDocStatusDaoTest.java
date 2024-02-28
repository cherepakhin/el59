package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.TypeDocStatus;

public class TypeDocStatusDaoTest extends DaoTest<TypeDocStatus, Long> {

	@Override
	protected String getNameDao() {
		return "typeDocStatusDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		TypeDocStatus e = getDao().read(n);
		System.out.println("TypeDocStatus:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
