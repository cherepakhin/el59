package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.el59.office.iproviders.shopmodel.ITypeSertProvider;
import ru.el59.office.shopmodel.TypeSert;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypeSertProvider extends GenericDaoMessageImpl<TypeSert, Long>
		implements ITypeSertProvider {

	public TypeSertProvider(Class<TypeSert> type) {
		super(type);
	}
}
