package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.FeatureOld;

public class FeatureOldDaoTest extends DaoTest<FeatureOld, Long> {

	@Override
	protected String getNameDao() {
		return "featureOldDao";
	}

	@Override
	public void read() {
		FeatureOld f = getDao().read(new Long(5));
		assertTrue(f.getN().equals(new Long(5)));
	}

}
