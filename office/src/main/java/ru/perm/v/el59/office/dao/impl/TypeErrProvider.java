package ru.perm.v.el59.office.dao.impl;

import java.util.List;

import ru.perm.v.el59.office.db.TypeErr;
import ru.perm.v.el59.office.iproviders.ITypeErrProvider;

public class TypeErrProvider extends GenericDaoHibernateImpl<TypeErr, Long> {

	public TypeErrProvider(Class<TypeErr> type) {
		super(type);
	}

}
