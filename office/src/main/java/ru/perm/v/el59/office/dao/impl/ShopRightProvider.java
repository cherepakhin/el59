package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.iproviders.shopmodel.IShopRightProvider;
import ru.perm.v.el59.office.shopmodel.ShopRight;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class ShopRightProvider extends GenericDaoMessageImpl<ShopRight, Long>
		implements IShopRightProvider {

	public ShopRightProvider(Class<ShopRight> type) {
		super(type);
	}
}
