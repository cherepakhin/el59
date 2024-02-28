package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.TypeFile;

public class TypeFileDaoTest extends DaoTest<TypeFile, Long> {

	@Override
	protected String getNameDao() {
		return "typeFileDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		TypeFile e = getDao().read(n);
		System.out.println("TypeFile:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
