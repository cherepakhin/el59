package ru.perm.v.el59.office.dao.impl.routedoc;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.routedoc.Machine;
import ru.perm.v.el59.office.iproviders.routedoc.IMachineProvider;

import java.util.List;

public class MachineProvider extends GenericDaoHibernateImpl<Machine, Long>
		implements IMachineProvider {

	private Long nDefaultMachine;
	private Machine defaultMachine;

	public MachineProvider(Class<Machine> type) {
		super(type);
	}

	@Override
	public List<Machine> getAll() {
		List<Machine> ret = getByCritery(new CommonCritery(""));
		return ret;
	}

	public Long getnDefaultMachine() {
		return nDefaultMachine;
	}

	public void setnDefaultMachine(Long nDefaultMachine) {
		this.nDefaultMachine = nDefaultMachine;
	}

	@Override
	public Machine getDefaultMachine() {
		if (defaultMachine == null) {
			defaultMachine = read(getnDefaultMachine());
		}
		return defaultMachine;
	}

}
