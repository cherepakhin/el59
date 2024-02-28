package ru.perm.v.el59.office.dao.impl;

import ru.el59.office.db.Err;
import ru.el59.office.iproviders.IErrProvider;
import ru.el59.office.iproviders.IShopProvider;

public class ErrProvider extends GenericDaoHibernateImpl<Err, Long> implements
		IErrProvider {

	private IShopProvider shopProvider;
	private String defaultTypeErr;
	private String defaultNameShop;

	public ErrProvider(Class<Err> type) {
		super(type);
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public String getDefaultTypeErr() {
		return defaultTypeErr;
	}

	public void setDefaultTypeErr(String defaultTypeErr) {
		this.defaultTypeErr = defaultTypeErr;
	}

	public String getDefaultNameShop() {
		return defaultNameShop;
	}

	public void setDefaultNameShop(String defaultNameShop) {
		this.defaultNameShop = defaultNameShop;
	}
}
