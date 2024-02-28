package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.DiapazonK;

public class DiapazonKDaoTest extends DaoTest<DiapazonK,Long>{

	@Override
	protected String getNameDao() {
		return "diapazonKDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		DiapazonK d = getDao().read(n);
		System.out.println("DiapazonK:"+d.getN());
		assertTrue(d.getN().compareTo(n)==0);
	}

}
