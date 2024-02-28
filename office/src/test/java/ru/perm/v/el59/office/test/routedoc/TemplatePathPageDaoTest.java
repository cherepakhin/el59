package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.routedoc.TemplatePathPage;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TemplatePathPageDaoTest extends DaoTest<TemplatePathPage,Long> {

	@Override
	protected String getNameDao() {
		return "templatePathPageDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		TemplatePathPage e = getDao().read(n);
		System.out.println("TemplatePathPage:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
