package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.PeopleCount;

public class PeopleCountDaoTest extends DaoTest<PeopleCount,Long>{

	@Override
	protected String getNameDao() {
		return "peopleCountDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		PeopleCount c = getDao().read(n);
		System.out.println("tdoc_n:"+c.getN());
		assertTrue(c.getN().compareTo(n)==0);
	}
	

}
