package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.plan.Smena;
import ru.perm.v.el59.office.test.model.DaoTest;

public class SmenaDaoTest extends DaoTest<Smena, Long> {

	@Override
	protected String getNameDao() {
		return "smenaDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		Smena e = getDao().read(n);
		System.out.println("Entity:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
