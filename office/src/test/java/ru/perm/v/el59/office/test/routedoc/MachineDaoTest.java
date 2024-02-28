package ru.perm.v.el59.office.test.routedoc;

import ru.el59.office.db.routedoc.Machine;
import ru.perm.v.el59.office.test.model.DaoTest;

public class MachineDaoTest extends DaoTest<Machine, Long> {

	@Override
	protected String getNameDao() {
		return "machineDao";
	}

}
