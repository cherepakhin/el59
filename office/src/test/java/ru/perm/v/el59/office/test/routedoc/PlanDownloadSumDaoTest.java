package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import ru.el59.office.db.Contragent;
import ru.el59.office.db.DocFile;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.el59.office.db.TypeFile;
import ru.el59.office.db.routedoc.PlanDownload;
import ru.el59.office.db.routedoc.PlanDownloadSum;
import ru.el59.office.iproviders.IContragentProvider;
import ru.el59.office.iproviders.IManagerProvider;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.iproviders.ITypeFileProvider;
import ru.el59.office.iproviders.routedoc.IPlanDownloadProvider;
import ru.el59.office.iproviders.routedoc.IPlanDownloadSumProvider;
import ru.perm.v.el59.office.test.model.DaoTest;
import ru.el59.office.util.Helper;

public class PlanDownloadSumDaoTest extends DaoTest<PlanDownloadSum, Long> {

	@Override
	protected String getNameDao() {
		return "PlanDownloadSumDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		PlanDownloadSum e = getDao().read(n);
		System.out.println("PlanDownloadSum:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

	@Ignore
	@Test
	//(expected=Exception.class)
	public void createByDate() {
		IContragentProvider contragentProvider = context.getBean(IContragentProvider.class);
		Contragent contragent = contragentProvider.getByEqName("\"Адаменкова\" ИП");
		assertNotNull(contragent);
		IShopProvider shopProvider = context.getBean(IShopProvider.class);
		Shop shop = shopProvider.read("07220");
		assertNotNull(shop);
		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH,9);
		cal.set(Calendar.DAY_OF_MONTH,21);
		Date ddate = cal.getTime();
		ddate=Helper.getNullHour(ddate);
		System.out.println(ddate);
		IManagerProvider managerProvider=context.getBean(IManagerProvider.class);
		Manager manager = managerProvider.getByEqName("Черепахин В.Г.");
		IPlanDownloadSumProvider PlanDownloadSumProvider = (IPlanDownloadSumProvider) getDao();
		IPlanDownloadProvider PlanDownloadProvider = context.getBean(IPlanDownloadProvider.class);
		
		// Проверка и удаление если существует перед созданием
		PlanDownload PlanDownload = PlanDownloadProvider.getBy(shop, ddate, contragent);
		if(PlanDownload!=null) {
			try {
				PlanDownloadSumProvider.delete(shop, PlanDownload, contragent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PlanDownloadSum p=null;
		try {
			p = PlanDownloadSumProvider.createPlanDownloadSum(shop, ddate, contragent, manager);
			assertNotNull(p);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		if(p!=null) {
			p.setPlan(new BigDecimal("1.00"));
			try {
				PlanDownloadSumProvider.update(p);
			} catch (Exception e1) {
				e1.printStackTrace();
				fail();
			}
			PlanDownloadSum checkPlanDownloadSum = PlanDownloadSumProvider.getPlanDownloadSum(shop, PlanDownload, contragent);
			assertTrue(checkPlanDownloadSum.getPlan().compareTo(p.getPlan())==0);
			// Очистка созданного
			try {
				PlanDownloadSumProvider.delete(p);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
	}
	
	@Ignore
	@Test
	public void addFile() {
		IContragentProvider contragentProvider = context.getBean(IContragentProvider.class);
		Contragent contragent = contragentProvider.getByEqName("\"Адаменкова\" ИП");
		assertNotNull(contragent);

		IManagerProvider managerProvider=context.getBean(IManagerProvider.class);
		Manager manager = managerProvider.getByEqName("Черепахин В.Г.");
		assertNotNull(manager);
		
		IShopProvider shopProvider = context.getBean(IShopProvider.class);
		Shop shop = shopProvider.read("07220");
		assertNotNull(shop);
		
		ITypeFileProvider typeFileProvider = context.getBean(ITypeFileProvider.class);
		TypeFile typeFile = typeFileProvider.getByEqName("Счет");
		assertNotNull(shop);
		IPlanDownloadSumProvider PlanDownloadSumProvider = (IPlanDownloadSumProvider) getDao();

		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH,9);
		cal.set(Calendar.DAY_OF_MONTH,21);
		Date ddate = cal.getTime();
		ddate=Helper.getNullHour(ddate);
		System.out.println(ddate);

		String sdata="111";
		try {
			PlanDownloadSumProvider.addFile(manager, ddate, shop, contragent, true, "vasi.che@gmail.com", new BigDecimal("123.00"), "123", "filename123.txt", typeFile, sdata.getBytes(), false);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void addDbfFile() {
		IContragentProvider contragentProvider = context.getBean(IContragentProvider.class);
		Contragent contragent = contragentProvider.getByEqName("\"Адаменкова\" ИП");
		assertNotNull(contragent);

		IManagerProvider managerProvider=context.getBean(IManagerProvider.class);
		Manager manager = managerProvider.getByEqName("Черепахин В.Г.");
		assertNotNull(manager);
		
		IShopProvider shopProvider = context.getBean(IShopProvider.class);
		Shop shop = shopProvider.read("07220");
		assertNotNull(shop);
		
		ITypeFileProvider typeFileProvider = context.getBean(ITypeFileProvider.class);
		TypeFile typeFile = typeFileProvider.getByEqName("Счет");
		assertNotNull(shop);
		IPlanDownloadSumProvider PlanDownloadSumProvider = (IPlanDownloadSumProvider) getDao();

		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH,9);
		cal.set(Calendar.DAY_OF_MONTH,21);
		Date ddate = cal.getTime();
		ddate=Helper.getNullHour(ddate);
		System.out.println(ddate);
		BigDecimal summa = new BigDecimal("123.00");
		String filename="/home/vasi/temp/00135441.dbf";
		try {
			byte[] data = FileUtils.readFileToByteArray(new File(filename));
			DocFile docFile = PlanDownloadSumProvider.addFile(manager, ddate, shop, contragent, false, "", summa, "123", filename, typeFile, data, true);
			System.out.println(docFile.getSumma());
			assertTrue(docFile.getSumma().compareTo(summa)!=0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
