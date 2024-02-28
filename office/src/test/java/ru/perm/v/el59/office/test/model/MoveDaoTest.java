package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ru.el59.office.critery.MoveCritery;
import ru.el59.office.db.Move;
import ru.el59.office.db.Shop;
import ru.el59.office.iproviders.IMoveProvider;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.util.Helper;

public class MoveDaoTest extends DaoTest<Move,Long>{

	@Override
	protected String getNameDao() {
		return "moveDao";
	}

	@Override
	public void read() {
		IMoveProvider moveDao = (IMoveProvider)getDao();
		MoveCritery critery = new MoveCritery();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 8);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		critery.fromDate=cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, 2);
		critery.toDate=cal.getTime();
		List<Move> list = moveDao.getByCritery(critery);
		assertTrue(list.size()>0);
	}

/*	@Test
	public void sumFact() {
		IShopProvider shopDao=(IShopProvider) context.getBean("shopDao");
		List<Shop> listShop = shopDao.getWorkedShop();
		assertTrue(listShop.size()>0);
		
		MoveCritery critery = new MoveCritery();
		
		critery.arrshops.add(listShop.get(0));
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 8);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		critery.fromDate=cal.getTime();
		
		IMoveProvider moveDao = (IMoveProvider)getDao();
		SummaInOut sumInOut = moveDao.getSumFact(critery);
		System.out.println(sumInOut);
		assertTrue(sumInOut.getSummain().compareTo(BigDecimal.ZERO)>0);
		assertTrue(sumInOut.getSummaout().compareTo(BigDecimal.ZERO)>0);
	}
*/	
	@Test
	public void getShopAndPartner() {
		IMoveProvider moveProvider=context.getBean(IMoveProvider.class);
		IShopProvider shopProvider=context.getBean(IShopProvider.class);
		Shop shop = shopProvider.read("07220");
		ArrayList<Shop> listShop = moveProvider.getShopAndParther(shop);
		System.out.println(listShop);
		assertTrue(listShop.size()>1);
	}
	
	@Test
	public void testGetResultCompetition() {
		IMoveProvider moveProvider=context.getBean(IMoveProvider.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, Calendar.MARCH);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date fromDate = Helper.getNullHour(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, 30);
		Date toDate = Helper.getNullHour(cal.getTime());
		
		List<Move> results = moveProvider.getResultCompetition(fromDate, toDate);
		for (Move move : results) {
			System.out.println(String.format("%s %.2f",move.getSeller(),move.getSummaout()));
		}
		assertTrue(results.size()>0);
				
	}
}
