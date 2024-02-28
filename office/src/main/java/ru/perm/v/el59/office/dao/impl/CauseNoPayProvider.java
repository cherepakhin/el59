package ru.perm.v.el59.office.dao.impl;

import ru.el59.dao.CommonCritery;
import ru.el59.office.db.CauseNoPay;
import ru.el59.office.iproviders.ICauseNoPayProvider;

import java.util.List;

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
