package ru.perm.v.el59.office.dao.impl;

import java.util.Collections;
import java.util.List;

import ru.perm.v.el59.office.db.TypeDoc;
import ru.perm.v.el59.office.iproviders.ITypeDocProvider;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

public class TypeDocProvider extends GenericDaoHibernateImpl<TypeDoc, Long>
		implements ITypeDocProvider {

	public TypeDocProvider(Class<TypeDoc> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TypeDoc> getAll() {
		List<TypeDoc> ret = getByCritery(new CommonCritery(""));
		Collections.sort(ret);
		return ret;
	}

}
