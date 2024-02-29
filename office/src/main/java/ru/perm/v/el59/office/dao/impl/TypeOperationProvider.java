package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypeOperationProvider;
import ru.perm.v.el59.office.shopmodel.TypeOperation;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.List;

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
