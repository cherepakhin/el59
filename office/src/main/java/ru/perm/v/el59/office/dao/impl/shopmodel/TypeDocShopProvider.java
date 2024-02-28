package ru.perm.v.el59.office.dao.impl.shopmodel;

import java.util.List;

import ru.perm.v.el59.dto.dao.CommonCritery;
import ru.perm.v.el59..office.iproviders.shopmodel.ITypeDocShopProvider;
import ru.el59.office.shopmodel.TypeDocShop;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypeDocShopProvider extends
		GenericDaoMessageImpl<TypeDocShop, Long> implements
		ITypeDocShopProvider {

	public TypeDocShopProvider(Class<TypeDocShop> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public List<TypeDocShop> getAll() {
		List<TypeDocShop> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

}
