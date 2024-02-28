package ru.perm.v.el59.office.dao.impl.service;

import java.util.List;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.service.Complaint;
import ru.perm.v.el59.office.iproviders.service.IComplaintProvider;

public class ComplaintProvider extends GenericDaoHibernateImpl<Complaint, Long>
		implements IComplaintProvider {

	public ComplaintProvider(Class<Complaint> type) {
		super(type);
	}

	public List<Complaint> getAll() {
		CommonCritery critery = new CommonCritery("");
		return getByCritery(critery);
	}

}
