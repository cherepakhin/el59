package ru.perm.v.el59.office.test.model.service;

import ru.el59.office.db.service.VneshVid;
import ru.perm.v.el59.office.test.model.DaoTest;

public class VneshVidDaoTest extends DaoTest<VneshVid,Long> {

	@Override
	protected String getNameDao() {
		return "vneshVidDao";
	}

}
