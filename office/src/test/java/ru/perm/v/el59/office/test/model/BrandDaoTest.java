package ru.perm.v.el59.office.test.model;

import ru.el59.office.db.Brand;

public class BrandDaoTest extends DaoTest<Brand,Long>{

	@Override
	protected String getNameDao() {
		return "brandDao";
	}

}
