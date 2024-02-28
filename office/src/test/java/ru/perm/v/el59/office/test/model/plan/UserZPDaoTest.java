package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ru.el59.office.critery.PlanCritery;
import ru.el59.office.db.plan.UserZP;
import ru.el59.office.iproviders.plan.IUserZPProvider;
import ru.perm.v.el59.office.test.model.DaoTest;

public class UserZPDaoTest extends DaoTest<UserZP,Long> {

	@Override
	protected String getNameDao() {
		return "userZPDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		UserZP e = getDao().read(n);
		System.out.println("UserZP:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}
	
	@Test
	public void getForCompetition() {
		IUserZPProvider userZPDao = (IUserZPProvider) getDao();
		PlanCritery planCritery = new PlanCritery();
		planCritery.month=11;
		planCritery.year=2014;
		List<UserZP> listUserZP = userZPDao.getByCritery(planCritery);
		assertTrue(listUserZP.size()>0);
	}

}
