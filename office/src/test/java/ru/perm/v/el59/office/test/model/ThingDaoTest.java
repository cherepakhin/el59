package ru.perm.v.el59.office.test.model;

import ru.el59.office.db.Thing;

public class ThingDaoTest extends DaoTest<Thing, String> {

	@Override
	protected String getNameDao() {
		return "thingDao";
	}


}
