package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.subs.CorrectionVal;

public class CorrectionValDaoTest extends DaoTest<CorrectionVal,Long>{

	@Override
	protected String getNameDao() {
		return "correctionValDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		CorrectionVal e = getDao().read(n);
		System.out.println("CorrectionName:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
