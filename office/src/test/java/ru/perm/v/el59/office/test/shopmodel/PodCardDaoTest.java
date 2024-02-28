package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertTrue;
import ru.el59.office.shopmodel.PodCard;
import ru.perm.v.el59.office.test.model.DaoTest;

public class PodCardDaoTest extends DaoTest<PodCard,Long> {

	@Override
	protected String getNameDao() {
		return "podCardDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		PodCard e = getDao().read(n);
		System.out.println("PodCard:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}

}
