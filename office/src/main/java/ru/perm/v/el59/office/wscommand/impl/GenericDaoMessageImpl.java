package ru.perm.v.el59.office.wscommand.impl;

import ru.perm.v.el59.dto.AEntityDTO;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.wscommand.ICommander;

import java.io.Serializable;

public class GenericDaoMessageImpl<T, PK extends Serializable> extends
		GenericDaoHibernateImpl<T, PK> {
	private ICommander commander;

	public GenericDaoMessageImpl(Class<T> type) {
		super(type);
	}

	public PK create(T o, String shopcod) throws Exception {
		PK n = (PK) super.create(o);
		if (getCommander() != null) {
			getCommander().create(o, shopcod);
		}
		return n;
	}

	public PK create(T o) throws Exception {
		PK n = (PK) super.create(o);
		if (getCommander() != null) {
			getCommander().create(o, "*");
		}
		return n;
	}

	@Override
	public void delete(T o) throws Exception {
		super.delete(o);
		if (getCommander() != null) {
			getCommander().delete(o, "*");
		}
	}

	@Override
	public void update(T o) throws Exception {
		super.update(o);
		if (getCommander() != null) {
			getCommander().update(o, "*");
		}
	}

	public void update(T o, String shopcod) throws Exception {
		super.update(o);
		if (getCommander() != null) {
			getCommander().update(o, shopcod);
		}
	}

	public ICommander getCommander() {
		return commander;
	}

	public void setCommander(ICommander commander) {
		this.commander = commander;
	}

	public T getByDTO(AEntityDTO dto, Shop shop) {
		T o = read((PK) dto.getN());
		return o;
	}
}
