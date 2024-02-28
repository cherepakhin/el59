package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.routedoc.RouteJob;
import ru.perm.v.el59.office.test.model.DaoTest;

public class RouteJobDaoTest extends DaoTest<RouteJob,Long> {

	@Override
	protected String getNameDao() {
		return "routeJobDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		RouteJob e = getDao().read(n);
		System.out.println("RouteJob:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
