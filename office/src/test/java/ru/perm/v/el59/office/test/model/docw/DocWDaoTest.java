package ru.perm.v.el59.office.test.model.docw;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ru.el59.office.db.Shop;
import ru.el59.office.db.web.DocW;
import ru.el59.office.db.web.DocWInfo;
import ru.el59.office.iproviders.IShopProvider;
import ru.el59.office.iproviders.web.IDocWProvider;
import ru.perm.v.el59.office.test.model.DaoTest;

public class DocWDaoTest extends DaoTest<DocW, Long> {

	@Override
	protected String getNameDao() {
		return "docWDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		DocW e = getDao().read(n);
		System.out.println("DocW:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

	@Test
	public void getOnlyOpen() {
		IDocWProvider docWProvider =(IDocWProvider) getDao();
		IShopProvider shopDao = (IShopProvider) context.getBean("shopDao");
		List<Shop> workedShops = shopDao.getWorkedShop();
		List<DocWInfo> listDocWInfo = docWProvider.getOnlyOpen(workedShops);
		assertNotNull(listDocWInfo);
		
	}
}
