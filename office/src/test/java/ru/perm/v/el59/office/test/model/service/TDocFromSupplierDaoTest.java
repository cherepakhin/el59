package ru.perm.v.el59.office.test.model.service;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.service.TDocFromSupplier;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TDocFromSupplierDaoTest extends DaoTest<TDocFromSupplier,Long> {

	@Override
	protected String getNameDao() {
		return "tdocFromSupplierDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		TDocFromSupplier tdoc = getDao().read(n);
		System.out.println("tdoc_n:"+tdoc.getN());
		assertTrue(tdoc.getN().compareTo(n)==0);
	}

}
