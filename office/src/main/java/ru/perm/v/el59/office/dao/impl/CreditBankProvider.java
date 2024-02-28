package ru.perm.v.el59.office.dao.impl;

import ru.el59.dao.CommonCritery;
import ru.el59.office.db.CreditBank;
import ru.el59.office.iproviders.ICreditBankProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.List;


public class CreditBankProvider extends GenericDaoMessageImpl<CreditBank, Long>
		implements ICreditBankProvider {

	public CreditBankProvider(Class<CreditBank> type) {
		super(type);
	}

	@Override
	public List<CreditBank> getAll() {
		List<CreditBank> ret = getByCritery(new CommonCritery(""));
		return ret;
	}
}
