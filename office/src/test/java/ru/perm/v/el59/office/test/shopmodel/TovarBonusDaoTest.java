package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertTrue;
import ru.el59.office.shopmodel.TovarBonus;
import ru.perm.v.el59.office.test.model.DaoTest;

public class TovarBonusDaoTest extends DaoTest<TovarBonus, Long> {

	@Override
	protected String getNameDao() {
		return "tovarBonusDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		TovarBonus e = getDao().read(n);
		System.out.println("TovarBonus:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}
}
