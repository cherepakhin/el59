package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.iproviders.shopmodel.ITypePriceProvider;
import ru.perm.v.el59.office.shopmodel.TypePrice;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypePriceProvider extends GenericDaoMessageImpl<TypePrice, Long>
		implements ITypePriceProvider {

	public TypePriceProvider(Class<TypePrice> type) {
		super(type);
	}
}
