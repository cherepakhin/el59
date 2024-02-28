package ru.perm.v.el59.office.dao.impl.shopmodel;

import java.util.List;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypeCashProvider;
import ru.perm.v.el59.office.shopmodel.TypeCash;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypeCashProvider extends GenericDaoMessageImpl<TypeCash, Long>
		implements ITypeCashProvider {

	public TypeCashProvider(Class<TypeCash> type) {
		super(type);
	}

	@Override
	public List<TypeCash> getAll() {
		List<TypeCash> ret = getByCritery(new CommonCritery(""));
		return ret;
	}
}
