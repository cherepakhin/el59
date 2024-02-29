package ru.perm.v.el59.office.dao.impl.routedoc;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.routedoc.Dispatcher;
import ru.perm.v.el59.office.iproviders.routedoc.IDispatcherProvider;

import java.util.List;

public class DispatcherProvider extends
		GenericDaoHibernateImpl<Dispatcher, Long> implements
		IDispatcherProvider {

	private Long nDefaultDispatcher;
	private Dispatcher defaultDispatcher;

	public DispatcherProvider(Class<Dispatcher> type) {
		super(type);
	}

	@Override
	public List<Dispatcher> getAll() {
		List<Dispatcher> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

	@Override
	public Dispatcher getDefaultDispatcher() {
		if (defaultDispatcher == null) {
			defaultDispatcher = read(getnDefaultDispatcher());
		}
		return defaultDispatcher;
	}

	public Long getnDefaultDispatcher() {
		return nDefaultDispatcher;
	}

	public void setnDefaultDispatcher(Long nDefaultDispatcher) {
		this.nDefaultDispatcher = nDefaultDispatcher;
	}

}
