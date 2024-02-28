package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.Doc;

public class DocDaoTest extends DaoTest<Doc, Long> {

	@Override
	protected String getNameDao() {
		return "docDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		Doc e = getDao().read(n);
		System.out.println("Doc:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
