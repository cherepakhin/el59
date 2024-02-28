package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import ru.el59.office.db.ConstAnnotFeature;
import ru.el59.office.db.FeaturePrice;
import ru.el59.office.db.TovarInfo;
import ru.el59.office.iproviders.ITovarInfoProvider;

public class TovarInfoDaoTest extends DaoTest<TovarInfo,Integer>{

	
	@Override
	public void read() {
		TovarInfo t = getDao().read(78096177);
		assertNotNull(t);
	}

	@Override
	protected String getNameDao() {
		return "tovarInfoDao";
	}

	@Test
	public void init() {
		TovarInfo tovarInfo = (TovarInfo) getDao().initialize(71095415);
		assertTrue(tovarInfo.getNnum().equals(71095415));
		System.out.println(tovarInfo.getNnum());
		assertNotNull(tovarInfo);
		assertTrue(tovarInfo.getListPhoto().size()>0);
	}

	@Test
	public void update() {
		TovarInfo tovarInfo = (TovarInfo) getDao().initialize(71095415);
		assertTrue(tovarInfo.getNnum().equals(71095415));
		System.out.println(tovarInfo.getNnum());
		assertNotNull(tovarInfo);
		assertTrue(tovarInfo.getListPhoto().size()>0);
		ITovarInfoProvider tovarInfoProvider=(ITovarInfoProvider) getDao();
		try {
			tovarInfoProvider.update(tovarInfo);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	@Test
	public void generatePriceTag() throws Exception {
		ITovarInfoProvider tovarInfoProvider=(ITovarInfoProvider) getDao();
		TovarInfo tovarInfo = tovarInfoProvider.initialize(71040887);
		tovarInfo=tovarInfoProvider.generateTagPrice(tovarInfo);
		FeaturePrice f = tovarInfo.getFeaturePriceValByCod(ConstAnnotFeature.ZPROPERTIES);
		assertNotNull(f);
	}
}
