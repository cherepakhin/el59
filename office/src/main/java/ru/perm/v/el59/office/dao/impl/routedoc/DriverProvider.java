package ru.perm.v.el59.office.dao.impl.routedoc;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.routedoc.Driver;
import ru.perm.v.el59.office.iproviders.routedoc.IDriverProvider;

import java.util.List;

public class DriverProvider extends GenericDaoHibernateImpl<Driver, Long>
		implements IDriverProvider {

	private Long nDefaultDriver;
	private Driver defaultDriver;

	public DriverProvider(Class<Driver> type) {
		super(type);
	}

	@Override
	public List<Driver> getAll() {
		List<Driver> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

	public Long getnDefaultDriver() {
		return nDefaultDriver;
	}

	public void setnDefaultDriver(Long nDefaultDriver) {
		this.nDefaultDriver = nDefaultDriver;
	}

	@Override
	public Driver getDefaultDriver() {
		if (defaultDriver == null) {
			defaultDriver = read(getnDefaultDriver());
		}
		return defaultDriver;
	}

}
