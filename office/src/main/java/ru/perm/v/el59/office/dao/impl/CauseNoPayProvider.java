package ru.perm.v.el59.office.dao.impl;

import java.util.List;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.db.CauseNoPay;
import ru.perm.v.el59.office.iproviders.ICauseNoPayProvider;

public class CauseNoPayProvider extends
		GenericDaoHibernateImpl<CauseNoPay, Long> implements
		ICauseNoPayProvider {

	public CauseNoPayProvider(Class<CauseNoPay> type) {
		super(type);
	}

	@Override
	public List<CauseNoPay> getCauseNoPayAll() {
		return getByCritery(new CommonCritery(""));
	}

}
