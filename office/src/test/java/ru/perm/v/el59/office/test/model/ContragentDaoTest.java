package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.Contragent;

public class ContragentDaoTest extends DaoTest<Contragent,Long>{

	@Override
	protected String getNameDao() {
		return "contragentDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-2;
		System.out.println("n:"+n);
		Contragent e = getDao().read(n);
		System.out.println("Entity:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}
	
}
