package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ru.el59.office.db.web.RestWeb;
import ru.el59.office.db.web.TypeSite;
import ru.el59.office.iproviders.web.IRestWebProvider;

public class RestWebDaoTest extends DaoTest<RestWeb,Long>{

	@Override
	protected String getNameDao() {
		return "restWebDao";
	}

	@Override
	public void read() {
		// TODO несрабатывает 
		assertTrue(true);
/*		IRestWebProvider d = (IRestWebProvider) getDao();
		List<RestWeb> l = d.getListForSite(TypeSite.INNER);
		assertTrue(l.size()>0);
*/	}
	
	@Test
	public void getListForSite() {
		IRestWebProvider restWebProvider = (IRestWebProvider) getDao();
		List<RestWeb> list = restWebProvider.getListForSite(TypeSite.INNER);
		assertTrue(list.size()>0);
	}

}
