package ru.perm.v.el59.office.test.model.service;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.service.TDocServicePDS;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TDocServicePDSDaoTest extends DaoTest<TDocServicePDS,Long> {

	@Override
	protected String getNameDao() {
		return "tdocServicePDSDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		TDocServicePDS tdoc = getDao().read(n);
		System.out.println("tdoc_n:"+tdoc.getN());
		assertTrue(tdoc.getN().compareTo(n)==0);
	}

}
