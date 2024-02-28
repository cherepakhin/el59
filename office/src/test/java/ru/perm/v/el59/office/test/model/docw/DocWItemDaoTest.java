package ru.perm.v.el59.office.test.model.docw;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ru.el59.office.db.web.DocW;
import ru.el59.office.db.web.DocWItem;
import ru.el59.office.db.web.DocWItemInfo;
import ru.el59.office.iproviders.web.DocWCritery;
import ru.el59.office.iproviders.web.DocWItemCritery;
import ru.el59.office.iproviders.web.IDocWItemProvider;
import ru.perm.v.el59.office.test.model.DaoTest;

public class DocWItemDaoTest extends DaoTest<DocWItem, Long> {

	@Override
	protected String getNameDao() {
		return "docWItemDao";
	}

	@Override
	@Ignore
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		DocWItem e = getDao().read(n);
		System.out.println("DocWItem:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

	@Test
	@Ignore
	public void getByCritery() {
		DocWItemCritery critery = new DocWItemCritery();
		DocW docW = new DocW();
		docW.setN(0L);
		critery.listDocW.add(docW);
		List<DocWItem> listDocWItem = getDao().getByCritery(critery);
		assertTrue(listDocWItem.size()==1);
		listDocWItem = getDao().getByCritery(critery);
		assertTrue(listDocWItem.size()==1);
	}

	@Test
	public void getDocWItemInfo() {
		DocWCritery critery = new DocWCritery();
		Calendar cal =Calendar.getInstance();
		cal.set(2015, Calendar.SEPTEMBER, 21);
		critery.fromdate=cal.getTime();
		cal.set(2015, Calendar.SEPTEMBER, 23);
		critery.todate=cal.getTime();
		IDocWItemProvider provider = context.getBean(IDocWItemProvider.class);
		List<DocWItemInfo> listDocWItemInfo = provider.getDocWItemInfo(critery);
		System.out.println(listDocWItemInfo.size());
		assertTrue(listDocWItemInfo.size()>0);
		assertTrue(listDocWItemInfo.size()==8);
	}
}
