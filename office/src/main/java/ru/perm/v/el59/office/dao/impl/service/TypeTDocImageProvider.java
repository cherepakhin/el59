package ru.perm.v.el59.office.dao.impl.service;

import java.util.List;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.TypeTDocImage;
import ru.perm.v.el59.office.iproviders.service.ITypeTDocImageProvider;

public class TypeTDocImageProvider extends GenericDaoHibernateImpl<TypeTDocImage, Long>
		implements ITypeTDocImageProvider {

	public TypeTDocImageProvider(Class<TypeTDocImage> type) {
		super(type);
	}

	public List<TypeTDocImage> getAll() {
		CommonCritery critery = new CommonCritery("");
		return getByCritery(critery);
	}

}
