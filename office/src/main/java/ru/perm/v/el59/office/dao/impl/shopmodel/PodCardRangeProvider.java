package ru.perm.v.el59.office.dao.impl.shopmodel;


import ru.perm.v.el59.dto.dao.CommonCritery;
import ru.perm.v.el59.dto.office.iproviders.shopmodel.IPodCardRangeProvider;
import ru.el59.office.shopmodel.PodCardRange;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.List;

public class PodCardRangeProvider extends
		GenericDaoMessageImpl<PodCardRange, Long> implements
		IPodCardRangeProvider {

	public PodCardRangeProvider(Class<PodCardRange> type) {
		super(type);
	}

	@Override
	public List<PodCardRange> getAll() {
		List<PodCardRange> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

}
