package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.routedoc.SupplierPriority;
import ru.perm.v.el59.office.test.model.DaoTest;

public class SupplierPriorityDaoTest extends DaoTest<SupplierPriority,Long> {

	@Override
	protected String getNameDao() {
		return "supplierPriorityDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		SupplierPriority e = getDao().read(n);
		System.out.println("SupplierPriority:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
