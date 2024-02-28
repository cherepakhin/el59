package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.routedoc.Procuratory;
import ru.perm.v.el59.office.test.model.DaoTest;

public class ProcuratoryDaoTest extends DaoTest<Procuratory, Long> {

	@Override
	protected String getNameDao() {
		return "procuratoryDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		Procuratory e = getDao().read(n);
		System.out.println("Procuratory:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
