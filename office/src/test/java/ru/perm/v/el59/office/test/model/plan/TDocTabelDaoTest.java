package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.plan.TDocTabel;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TDocTabelDaoTest extends DaoTest<TDocTabel,Long> {

	@Override
	protected String getNameDao() {
		return "tdocTabelDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		TDocTabel e = getDao().read(n);
		System.out.println("TDocTabel:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
