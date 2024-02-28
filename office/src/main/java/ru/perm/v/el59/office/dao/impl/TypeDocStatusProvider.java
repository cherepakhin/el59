package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.db.TypeDocStatus;
import ru.perm.v.el59.office.iproviders.ITypeDocStatusProvider;

public class TypeDocStatusProvider extends
		GenericDaoHibernateImpl<TypeDocStatus, Long> implements
		ITypeDocStatusProvider {

	public TypeDocStatusProvider(Class<TypeDocStatus> type) {
		super(type);
	}

}
