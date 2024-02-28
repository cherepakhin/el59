package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.routedoc.PathPage;
import ru.perm.v.el59.office.test.model.DaoTest;

public class PathPageDaoTest extends DaoTest<PathPage,Long> {

	@Override
	protected String getNameDao() {
		return "pathPageDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		PathPage e = getDao().read(n);
		System.out.println("PathPage:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
