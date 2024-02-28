package ru.perm.v.el59.office.test.model;

import ru.el59.office.db.service.Complaint;

public class ComplaintDaoTest extends DaoTest<Complaint,Long>{

	@Override
	protected String getNameDao() {
		return "complaintDao";
	}

}
