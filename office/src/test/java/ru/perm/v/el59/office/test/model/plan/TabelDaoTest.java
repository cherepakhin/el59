package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.plan.Tabel;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TabelDaoTest extends DaoTest<Tabel,Long> {

	@Override
	protected String getNameDao() {
		return "tabelDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		Tabel e = getDao().read(n);
		System.out.println("Tabel:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
