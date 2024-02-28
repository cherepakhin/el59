package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import ru.el59.office.db.PriceType;
import ru.el59.office.iproviders.IPriceTypeProvider;

public class PriceTypeDaoTest extends DaoTest<PriceType,Long>{

	@Override
	protected String getNameDao() {
		return "priceTypeDao";
	}
	
	/**
	 * Тест синхронизации основного прайса
	 */
	@Test
	public void updatePriceTypeBase() {
		IPriceTypeProvider priceTypeProvider=(IPriceTypeProvider) getDao();
		PriceType priceType = priceTypeProvider.getByEqName("Розничный");
		assertEquals(priceType.getName(),"Розничный");
		priceType.setName("Розничный");
		try {
			priceTypeProvider.update(priceType);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	/**
	 * Тест синхронизации локального прайса
	 */
	@Test
	public void updatePriceTypeLocal() {
		IPriceTypeProvider priceTypeProvider=(IPriceTypeProvider) getDao();
		List<PriceType> listPriceType = priceTypeProvider.getIsBase(false);
		assertTrue(listPriceType.size()>0);
		PriceType priceType = listPriceType.get(0);
		String name = priceType.getName();
		priceType.setName(name);
		System.out.println(name);
		try {
			priceTypeProvider.update(priceType);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}
}
