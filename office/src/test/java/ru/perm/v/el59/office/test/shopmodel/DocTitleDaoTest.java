package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ru.el59.office.critery.DocTitleCritery;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.Shop;
import ru.el59.office.db.TypeStock;
import ru.el59.office.db.UserShop;
import ru.el59.office.iproviders.IContragentProvider;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.iproviders.ITypeStockProvider;
import ru.el59.office.iproviders.IUserShopProvider;
import ru.el59.office.iproviders.shopmodel.IDocTitleProvider;
import ru.el59.office.iproviders.shopmodel.ITypeDocShopProvider;
import ru.el59.office.iproviders.shopmodel.ITypePriceProvider;
import ru.el59.office.shopmodel.DocTitle;
import ru.el59.office.shopmodel.TypeDocShop;
import ru.el59.office.shopmodel.TypePrice;
import ru.perm.v.el59.office.test.model.DaoTest;

public class DocTitleDaoTest extends DaoTest<DocTitle, Long> {

	@Override
	protected String getNameDao() {
		return "docTitleDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		DocTitle e = getDao().read(n);
		System.out.println("DocTitle:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}
	
	@Ignore
	@Test
	public void createDocTitle() {
		DocTitle d =new DocTitle();
		IShopProvider shopProvider = context.getBean(IShopProvider.class);
		Shop shop = shopProvider.read("07260");
		assertNotNull(shop);
		d.setShop(shop);
		
		ITypeDocShopProvider typeDocShopProvider = context.getBean(ITypeDocShopProvider.class);
		TypeDocShop typeDocShop = typeDocShopProvider.getAll().get(0);
		assertNotNull(typeDocShop);
		d.setTypeDocShop(typeDocShop);
		
/*		ITypeOperationProvider typeOperationProvider = context.getBean(ITypeOperationProvider.class);
		TypeOperation typeOperation = typeOperationProvider.getAll().get(0);
		assertNotNull(typeOperation);
		d.setTypeOperation(typeOperation);*/
		
		IUserShopProvider userShopProvider = context.getBean(IUserShopProvider.class);
		UserShop userShop = userShopProvider.getByLikeName("").get(0);
		assertNotNull(userShop);
		d.setUserShop(userShop);
		
		IContragentProvider contragentProvider = context.getBean(IContragentProvider.class);
		Contragent contragent = contragentProvider.getByLikeName(shop.getName()).get(0);
		assertNotNull(contragent);
		d.setContragent(contragent);
		
		ITypePriceProvider typePriceProvider = context.getBean(ITypePriceProvider.class);
		TypePrice typePrice = typePriceProvider.getByLikeName("").get(0);
		assertNotNull(typePrice);
		d.setTypePrice(typePrice);
		
		ITypeStockProvider typeStockProvider = context.getBean(ITypeStockProvider.class);
		TypeStock typeStock = typeStockProvider.getAll().get(0);
		assertNotNull(typeStock);
		d.setTypeStock(typeStock);
		
		DocTitle nullDocTitle = ((IDocTitleProvider) getDao()).getNullDocTitle();
		assertNotNull(nullDocTitle);
		d.setParent(nullDocTitle);
		
		Long n=null;
		try {
			n = getDao().create(d);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Не создан");
		}
		System.out.println(n);
		assertNotNull(n);
		
	}
	
	@Test
	public void getByCritery() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		DocTitleCritery docTitleCritery = new DocTitleCritery();
		docTitleCritery.fromdate=cal.getTime();
		docTitleCritery.todate= new Date();
		docTitleCritery.typedocname="Реализация";
		List<DocTitle> list = getDao().getByCritery(docTitleCritery);
		assertTrue(list.size()>0);
	}
}
