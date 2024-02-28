package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertTrue;
import ru.el59.office.shopmodel.DocDetail;
import ru.perm.v.el59.office.test.model.DaoTest;

public class DocDetailDaoTest extends DaoTest<DocDetail, Long> {

	@Override
	protected String getNameDao() {
		return "docDetailDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		DocDetail e = getDao().read(n);
		System.out.println("DocDetail:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
