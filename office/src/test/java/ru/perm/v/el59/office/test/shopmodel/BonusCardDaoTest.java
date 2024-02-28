package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertTrue;
import ru.el59.office.shopmodel.BonusCard;
import ru.perm.v.el59.office.test.model.DaoTest;

public class BonusCardDaoTest extends DaoTest<BonusCard, Long> {

	@Override
	protected String getNameDao() {
		return "bonusCardDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		BonusCard e = getDao().read(n);
		System.out.println("BonusCard:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
