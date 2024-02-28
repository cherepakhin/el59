package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertNotNull;
import ru.el59.office.db.HistoryTovar;

public class HistoryTovarDaoTest extends DaoTest<HistoryTovar,Long>{

	
	@Override
	public void read() {
		HistoryTovar t = getDao().read(0L);
		assertNotNull(t);
	}

	@Override
	protected String getNameDao() {
		return "historyTovarDao";
	}

}
