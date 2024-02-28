package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.OpGroup;
import ru.el59.office.iproviders.IOpGroupProvider;

public class OpGroupDaoTest extends DaoTest<OpGroup,Long>{

	@Override
	protected String getNameDao() {
		return "opGroupDao";
	}

	@Override
	public void read() {
		IOpGroupProvider provider = (IOpGroupProvider) getDao();
		String[] arr = provider.getAsStringArray();
		assertTrue(arr.length>0);
	}

}
