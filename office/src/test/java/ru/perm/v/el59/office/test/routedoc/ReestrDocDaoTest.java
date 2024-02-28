package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.routedoc.ReestrDoc;
import ru.perm.v.el59.office.test.model.DaoTest;

public class ReestrDocDaoTest extends DaoTest<ReestrDoc,Long> {

	@Override
	protected String getNameDao() {
		return "reestrDocDao";
	}
	
	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		ReestrDoc e = getDao().read(n);
		System.out.println("ReestrDoc:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}	

}
