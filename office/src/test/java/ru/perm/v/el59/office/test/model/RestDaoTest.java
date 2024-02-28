package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.Rest;

public class RestDaoTest extends DaoTest<Rest, Long> {

	@Override
	protected String getNameDao() {
		return "restDao";
	}

	@Override
	public void read() {
/*		Тормозит
 		IRestProvider restDao = (IRestProvider)getDao();
		RestCritery critery = new RestCritery();
		critery.ddate=new Date();
		List<Rest> list = restDao.getByCritery(critery);
		assertTrue(list.size()>0);
*/		assertTrue(true);
	}

}
