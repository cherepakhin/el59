package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.Analog;

public class AnalogDaoTest extends DaoTest<Analog,Long>{

	@Override
	protected String getNameDao() {
		return "analogDao";
	}
	
	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		Analog e = getDao().read(n);
		System.out.println("Entity:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}	

}
