package ru.perm.v.el59.office.test.model.docw;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.web.SubsFeature;
import ru.perm.v.el59.office.test.model.DaoTest;

public class SubsFeatureDaoTest extends DaoTest<SubsFeature, Long> {

	@Override
	protected String getNameDao() {
		return "subsFeatureDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		SubsFeature e = getDao().read(n);
		System.out.println("SubsFeature:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
