package ru.perm.v.el59.office.test.routedoc;

import ru.el59.office.db.routedoc.Dispatcher;
import ru.perm.v.el59.office.test.model.DaoTest;

public class DispatcherDaoTest extends DaoTest<Dispatcher,Long> {

	@Override
	protected String getNameDao() {
		return "dispatcherDao";
	}

}
