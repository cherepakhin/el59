package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import ru.el59.office.critery.RestCritery;
import ru.el59.office.db.RestCur;
import ru.el59.office.iproviders.IRestCurProvider;

public class RestCurDaoTest extends DaoTest<RestCur, Long> {

	@Override
	protected String getNameDao() {
		return "restCurDao";
	}

	@Override
	public void read() {
		IRestCurProvider restCurDao = (IRestCurProvider)getDao();
		RestCritery critery = new RestCritery();
		critery.ddate=new Date();
		List<RestCur> list = restCurDao.getByCritery(critery);
		assertTrue(list.size()>0);
	}
	
	@Test
	public void readWithPrice() {
		IRestCurProvider restCurDao = (IRestCurProvider)getDao();
		RestCritery critery = new RestCritery();
		critery.ddate=new Date();
		critery.insertPrice=true;
		List<RestCur> list = restCurDao.getByCritery(critery);
		assertTrue(list.size()>0);
	}

}
