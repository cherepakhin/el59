package ru.perm.v.el59.office.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.perm.v.el59.office.db.TypeStock;
import ru.perm.v.el59.office.iproviders.ITypeStockProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class TypeStockProvider extends GenericDaoMessageImpl<TypeStock, Long>
		implements ITypeStockProvider {

	public TypeStockProvider(Class<TypeStock> type) {
		super(type);
	}

	@Override
	public List<TypeStock> getAll() {
		List<TypeStock> list = getSession().createCriteria(TypeStock.class)
				.list();
		return list;
	}

	@Override
	public Map<Long, TypeStock> getAllAsHashBestChr() {
		List<TypeStock> list = getAll();
		Map<Long, TypeStock> ret = new HashMap<Long, TypeStock>();
		for (TypeStock typestock : list) {
			ret.put(typestock.getN(), typestock);
		}
		return ret;
	}
}
