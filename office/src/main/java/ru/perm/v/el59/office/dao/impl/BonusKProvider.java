package ru.perm.v.el59.office.dao.impl;

import java.util.List;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.db.BonusK;
import ru.perm.v.el59.office.iproviders.IBonuskProvider;

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
