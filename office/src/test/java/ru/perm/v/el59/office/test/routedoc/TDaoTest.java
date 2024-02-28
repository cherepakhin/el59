package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.plan.Plan;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TDaoTest extends DaoTest<Plan,Long> {

	@Override
	protected String getNameDao() {
		return "planDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		Plan e = getDao().read(n);
		System.out.println("Plan:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
