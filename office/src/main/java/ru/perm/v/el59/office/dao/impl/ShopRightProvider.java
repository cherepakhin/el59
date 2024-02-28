package ru.perm.v.el59.office.dao.impl;

import ru.el59.office.shopmodel.ShopRight;
import ru.perm.v.el59.office.iproviders.shopmodel.IShopRightProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.List;

//TODO: fill methods
public class ShopRightProvider extends GenericDaoMessageImpl<ShopRight, Long>
		implements IShopRightProvider {

	public ShopRightProvider(Class<ShopRight> type) {
		super(type);
	}

	@Override
	public Long create(ShopRight shopRight) throws Exception {
		return null;
	}

	@Override
	public ShopRight read(Long aLong) {
		return null;
	}

	@Override
	public void update(ShopRight shopRight) throws Exception {

	}

	@Override
	public void delete(ShopRight shopRight) throws Exception {

	}

	@Override
	public List<ShopRight> getByCritery(Object o) {
		return null;
	}

	@Override
	public ShopRight initialize(Long aLong) {
		return null;
	}

	@Override
	public ShopRight getByEqName(String s) {
		return null;
	}

	@Override
	public List<ShopRight> getByLikeName(String s) {
		return null;
	}

	@Override
	public Class<ShopRight> getType() {
		return null;
	}

	@Override
	public Long getMax() {
		return null;
	}
}
