package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertTrue;
import ru.el59.office.shopmodel.BonusCardMove;
import ru.perm.v.el59.office.test.model.DaoTest;

public class BonusCardMoveDaoTest extends DaoTest<BonusCardMove, Long> {

	@Override
	protected String getNameDao() {
		return "bonusCardMoveDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		BonusCardMove e = getDao().read(n);
		System.out.println("BonusCardMove:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
