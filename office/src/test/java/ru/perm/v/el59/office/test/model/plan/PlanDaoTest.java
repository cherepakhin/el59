package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ru.el59.office.critery.PlanCritery;
import ru.el59.office.db.Shop;
import ru.el59.office.db.plan.Plan;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.iproviders.plan.IPlanProvider;
import ru.perm.v.el59.office.test.model.DaoTest;

public class PlanDaoTest extends DaoTest<Plan, Long> {

	@Override
	protected String getNameDao() {
		return "planDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		Plan e = getDao().read(n);
		System.out.println("Plan:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

	@Test
	public void getPlan() {
		IShopProvider shopProvider = (IShopProvider) context.getBean("shopDao");
		Shop shop = shopProvider.read("07863");
		IPlanProvider planProvider = (IPlanProvider) getDao();
		PlanCritery planCritery = new PlanCritery();
		planCritery.month = 7;
		planCritery.year = 2014;
		planCritery.shop = shop;
		List<Plan> listPlan = planProvider.getByCritery(planCritery);
		assertTrue(listPlan.size() > 0);

	}

	@Ignore
	@Test
	public void create() {
		IShopProvider shopProvider = (IShopProvider) context.getBean("shopDao");
		Shop shop = shopProvider.read("07863");
		Plan plan = new Plan();
		plan.setShop(shop);
		IPlanProvider planProvider = (IPlanProvider) getDao();
		Long n = null;
		try {
			n = planProvider.create(plan);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(n);
		assertTrue(n > 0L);
		if (n > 0L) {
			try {
				planProvider.delete(plan);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
	}

	@Test(expected = Exception.class)
	public void createExistForAllShop() throws Exception {
		IPlanProvider planProvider = (IPlanProvider) getDao();
		planProvider.createForAllShop(2014, 10);
	}

	@Test
	public void createForAllShop() {
		IPlanProvider planProvider = (IPlanProvider) getDao();
		List<Plan> plans = null;
		int year = 2014;
		int month = 11;
		try {
			plans = planProvider.createForAllShop(year,month);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(plans);
		assertTrue(plans.size()>0);
		try {
			for (Plan plan : plans) {
				planProvider.delete(plan);
			}
		} catch (Exception e) {	
			e.printStackTrace();
			fail();
		}
		PlanCritery planCritery = new PlanCritery();
		planCritery.year=year;
		planCritery.month=month;
		plans=planProvider.getByCritery(planCritery);
		assertTrue(plans.size()==0);
	}
}
