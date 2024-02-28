package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.plan.RestForPlan;
import ru.perm.v.el59.office.test.model.DaoTest;

public class RestForPlanDaoTest extends DaoTest<RestForPlan,Long> {

	@Override
	protected String getNameDao() {
		return "restForPlanDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		RestForPlan e = getDao().read(n);
		System.out.println("Entity:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
