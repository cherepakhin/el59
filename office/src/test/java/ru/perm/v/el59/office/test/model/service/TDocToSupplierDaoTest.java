package ru.perm.v.el59.office.test.model.service;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.service.TDocToSupplier;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TDocToSupplierDaoTest extends DaoTest<TDocToSupplier,Long> {

	@Override
	protected String getNameDao() {
		return "tdocToSupplierDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		TDocToSupplier tdoc = getDao().read(n);
		System.out.println("tdoc_n:"+tdoc.getN());
		assertTrue(tdoc.getN().compareTo(n)==0);
	}

}
