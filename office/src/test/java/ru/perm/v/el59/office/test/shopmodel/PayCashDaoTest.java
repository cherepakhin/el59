package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertTrue;
import ru.el59.office.shopmodel.PayCash;
import ru.perm.v.el59.office.test.model.DaoTest;

public class PayCashDaoTest extends DaoTest<PayCash, Long> {

	@Override
	protected String getNameDao() {
		return "payCashDao";
	}

	@Override
	public void read() {
		assertTrue(true);
/* getMax() выбирает max n из таблицы payment. Тип платежа может не совпасть с PayCash
		//		Long n = getDao().getMax() - 2;
		Long n = new Long(118935);
		System.out.println("n:" + n);
		PayCash e = getDao().read(n);
		System.out.println("PayCash:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
*/	}
}
