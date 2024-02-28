package ru.perm.v.el59.office.dao.impl;

import java.util.List;

import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypeOperationProvider extends
		GenericDaoMessageImpl<TypeOperation, Long> implements
		ITypeOperationProvider {

	public TypeOperationProvider(Class<TypeOperation> type) {
		super(type);
	}

	@Override
	public List<TypeOperation> getAll() {
		List<TypeOperation> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

}
