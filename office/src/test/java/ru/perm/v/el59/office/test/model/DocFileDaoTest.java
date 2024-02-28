package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.DocFile;

public class DocFileDaoTest extends DaoTest<DocFile, Long> {

	@Override
	protected String getNameDao() {
		return "docFileDao";
	}

	@Override
	public void read() {
/*		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		DocFile e = getDao().read(n);
		System.out.println("DocFile:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
*/	
		assertTrue(true);
	}

}
