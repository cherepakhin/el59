package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.SetTypeStock;
import ru.el59.office.iproviders.ISetTypeStockProvider;

public class SetTypeStockDaoTest extends DaoTest<SetTypeStock,Long>{

	@Override
	protected String getNameDao() {
		return "setTypeStockDao";
	}

	@Override
	public void read() {
		ISetTypeStockProvider setTypeStockProvider = (ISetTypeStockProvider) getDao();
		SetTypeStock setTypeStock = setTypeStockProvider.getByEqName("Без клиентской");
		setTypeStock=setTypeStockProvider.initialize(setTypeStock.getN());
		System.out.println(setTypeStock.getTypeStocks());
		assertTrue(setTypeStock.getTypeStocks().size()>0);
	}
}
