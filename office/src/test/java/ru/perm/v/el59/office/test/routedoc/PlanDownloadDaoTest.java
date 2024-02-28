package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import ru.el59.office.critery.PlanDownloadCritery;
import ru.el59.office.db.GroupContragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.el59.office.db.TypeFile;
import ru.el59.office.db.routedoc.PlanDownload;
import ru.el59.office.iproviders.IManagerProvider;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.iproviders.routedoc.IPlanDownloadProvider;
import ru.perm.v.el59.office.test.model.DaoTest;
import ru.el59.office.util.ComparatorAEntity;
import ru.el59.office.util.Helper;

public class PlanDownloadDaoTest extends DaoTest<PlanDownload, Long> {

	@Override
	protected String getNameDao() {
		return "planDownloadDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		PlanDownload e = getDao().read(n);
		System.out.println("PlanDownload:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

	@Ignore
	@Test
	public void createPlan() {
		IPlanDownloadProvider planDownloadProvider =(IPlanDownloadProvider) getDao();
		Date ddate= new Date();
		ddate=Helper.getNullHour(ddate);
		// Проверка. Удаление перед созданием 
		List<Date> listDdate = planDownloadProvider.getLastDate(10);
		assertTrue(listDdate.size()>0);
		if(listDdate.get(0).equals(ddate)) {
			List<PlanDownload> listPlandowload = planDownloadProvider.getLastPlanDownload();
			assertTrue(listPlandowload.size()>0);
			for (PlanDownload PlanDownload : listPlandowload) {
				try {
					planDownloadProvider.delete(PlanDownload);
				} catch (Exception e) {
					fail("Ошибка при удалении");
					e.printStackTrace();
				}
			}
		}
		
		
		IManagerProvider managerProvider = (IManagerProvider) context.getBean("managerDao");
		String nameManager = "Черепахин В.Г.";
		Manager manager = managerProvider.getByEqName(nameManager);
		assertNotNull("Нет manager "+nameManager,manager);
		try {
			planDownloadProvider.createForAllShop(ddate, manager);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}
	
	@Test
	public void existOnDate() {
		IPlanDownloadProvider PlanDownloadProvider =(IPlanDownloadProvider) getDao();
		List<Date> listDdate = PlanDownloadProvider.getLastDate(10);
		Date ddate = listDdate.get(0);
		assertTrue(listDdate.size()>0);

		List<PlanDownload> listPlandowload = PlanDownloadProvider.getLastPlanDownload();
		System.out.println(listPlandowload.get(0).getDdate());
		assertTrue(listPlandowload.size()>0);
		assertTrue(listPlandowload.get(0).getDdate().equals(ddate));
	}
	
	@Test
	public void getLastPlanDownload() {
		IPlanDownloadProvider PlanDownloadProvider =(IPlanDownloadProvider) getDao();
		List<Date> listDdate = PlanDownloadProvider.getLastDate(10);
		assertTrue(listDdate.size()>0);
	}
	
	
	@Test
	public void getPivot() {
		IShopProvider shopProvider = context.getBean(IShopProvider.class);
		IPlanDownloadProvider planDownloadProvider =(IPlanDownloadProvider) getDao();
		List<Date> listDdate = planDownloadProvider.getLastDate(10);
		Date ddate = listDdate.get(2);
		PlanDownloadCritery critery = new PlanDownloadCritery();
		critery.ddate=ddate;
		critery.listShop.add(shopProvider.read("07220"));
		Map<Shop, Map<GroupContragent, PlanDownload>> mapShop = null;
		try {
			mapShop = planDownloadProvider.getPivot(critery);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		System.out.println(mapShop);
		ArrayList<Shop> shops = new ArrayList<Shop>();
		shops.addAll(mapShop.keySet());
		Collections.sort(shops);
		ComparatorAEntity comparatorAEntity = new ComparatorAEntity();
		for (Shop shop : shops) {
			System.out.println(shop);
			Map<GroupContragent, PlanDownload> mapGroupContragent = mapShop.get(shop);
			ArrayList<GroupContragent> groupContragents = new ArrayList<GroupContragent>();
			groupContragents.addAll(mapGroupContragent.keySet());
			Collections.sort(groupContragents,comparatorAEntity);
			for (GroupContragent groupContragent : groupContragents) {
				System.out.println(groupContragent.getName());
				PlanDownload p=mapGroupContragent.get(groupContragent);
				System.out.println(p.getSumma()+":"+p.getPlanUsed());
				HashMap<TypeFile, BigDecimal> mapTypeFile = p.getMapTypeFileSum();
				ArrayList<TypeFile> typeFiles = new ArrayList<TypeFile>();
				typeFiles.addAll(mapTypeFile.keySet());
				Collections.sort(typeFiles,comparatorAEntity);
				for (TypeFile typeFile : typeFiles) {
					System.out.println(typeFile.getName()+":"+mapTypeFile.get(typeFile));
				}
			}
		}
		assertTrue(mapShop.keySet().size()>0);
	}

}
