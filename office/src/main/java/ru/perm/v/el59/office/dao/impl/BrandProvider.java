package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.db.Brand;
import ru.perm.v.el59.office.iproviders.IBrandProvider;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.util.List;

public class BrandProvider extends GenericDaoHibernateImpl<Brand, Long>
		implements IBrandProvider {

	public BrandProvider(Class<Brand> type) {
		super(type);
	}

	public List<Brand> getAll() {
		CommonCritery critery = new CommonCritery("");
		return getByCritery(critery);
	}

}
