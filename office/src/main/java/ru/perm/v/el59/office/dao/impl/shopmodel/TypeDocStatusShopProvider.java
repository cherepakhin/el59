package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.office.shopmodel.TypeDocStatusShop;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypeDocStatusShopProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypeDocStatusShopProvider extends
		GenericDaoMessageImpl<TypeDocStatusShop, Long> implements
		ITypeDocStatusShopProvider {

	public TypeDocStatusShopProvider(Class<TypeDocStatusShop> type) {
		super(type);
	}
}
