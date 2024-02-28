package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ru.el59.office.db.Shop;
import ru.el59.office.iproviders.IShopProvider;

public class ShopDaoTest extends DaoTest<Shop,String>{

	@Override
	protected String getNameDao() {
		return "shopDao";
	}

	@Test
	public void create() throws Exception {
		Shop shop = new Shop();
		shop.setCod("00001");
		shop.setName("name");
		shop.setSinonim("sinonim");
		IShopProvider shopDao=(IShopProvider) getDao();
		String cod=shopDao.create(shop);
		assertTrue(cod.equals("00001"));
		Shop shop1 = shopDao.read("00001");
		assertTrue(shop1.getCod().equals(shop.getCod()));
		assertTrue(shop1.getName().equals(shop.getName()));
		assertTrue(shop1.getSinonim().equals(shop.getSinonim()));
		shopDao.delete(shop1);
	}
}
