package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.subs.CorrectionName;

public class CorrectionNameDaoTest extends DaoTest<CorrectionName,Long>{

	@Override
	protected String getNameDao() {
		return "correctionNameDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		CorrectionName e = getDao().read(n);
		System.out.println("CorrectionName:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
