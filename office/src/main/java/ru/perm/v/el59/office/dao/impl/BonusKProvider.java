package ru.perm.v.el59.office.dao.impl;

import ru.el59.dao.CommonCritery;
import ru.el59.office.db.BonusK;
import ru.el59.office.iproviders.IBonuskProvider;

import java.util.List;

public class BonusKProvider extends GenericDaoHibernateImpl<BonusK, Long>
		implements IBonuskProvider {

	public BonusKProvider(Class<BonusK> type) {
		super(type);
	}

	@Override
	public BonusK initialize(Long id) {
		BonusK o = (BonusK) getSession().get(BonusK.class, id);
		return o;
	}

	@Override
	public List<BonusK> getAll() {
		return getByCritery(new CommonCritery(""));
	}

}
