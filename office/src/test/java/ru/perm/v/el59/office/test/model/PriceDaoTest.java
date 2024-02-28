package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import ru.el59.office.critery.PriceCritery;
import ru.el59.office.critery.TovarCritery;
import ru.el59.office.db.Price;
import ru.el59.office.iproviders.IPriceProvider;

public class PriceDaoTest extends DaoTest<Price,Long>{

	@Override
	protected String getNameDao() {
		return "priceDao";
	}

	@Override
	public void read() {
		IPriceProvider dao = (IPriceProvider) getDao();
		String p = dao.getNameDefaultPrice();
		assertTrue(p!=null);
	}

	@Test
	public void getByCritery() {
		PriceCritery priceCritery = new PriceCritery();
		priceCritery.tovarCritery=new TovarCritery();
		priceCritery.tovarCritery.nnum.add(71089682);
		priceCritery.namePriceType = ((IPriceProvider) getDao()).getNameDefaultPrice();
		List<Price> listPrice = getDao().getByCritery(priceCritery);
		System.out.println(listPrice.size());
		assertTrue(listPrice.size()==1);
		Price price = listPrice.get(0);
		System.out.println(String.format("cena0 %.2f", price.getTovar().getCena0()));
		System.out.println(String.format("cenainrub %.2f", price.getTovar().getCenainrub()));
/*		for (Price price : listPrice) {
			System.out.println("price.k="+price.getK());
		}
*/	}
	
	@Test
	public void updatePrice() {
		IPriceProvider priceProvider = (IPriceProvider) getDao();
		PriceCritery priceCritery = new PriceCritery();
		priceCritery.tovarCritery=new TovarCritery();
		priceCritery.tovarCritery.nnum.add(71089682);
		priceCritery.namePriceType = priceProvider.getNameDefaultPrice();
		List<Price> listPrice = priceProvider.getByCritery(priceCritery);
		assertTrue(listPrice.size()==1);
		Price price = listPrice.get(0);
		try {
			priceProvider.update(price);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
