package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertNotNull;
import ru.el59.office.db.Tovar;

public class TovarDaoTest extends DaoTest<Tovar,Integer>{

	
	@Override
	public void read() {
		Tovar t = getDao().read(98210010);
		assertNotNull(t);
	}

	@Override
	protected String getNameDao() {
		return "tovarDao";
	}

}
