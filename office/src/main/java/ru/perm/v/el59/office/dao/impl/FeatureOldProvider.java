package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.office.db.FeatureOld;
import ru.perm.v.el59.office.iproviders.IFeatureOldProvider;

public class FeatureOldProvider extends
		GenericDaoHibernateImpl<FeatureOld, Long> implements
		IFeatureOldProvider {

	public FeatureOldProvider(Class<FeatureOld> type) {
		super(type);
	}
}
