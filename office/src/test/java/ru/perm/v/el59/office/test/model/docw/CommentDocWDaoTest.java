package ru.perm.v.el59.office.test.model.docw;

import static org.junit.Assert.assertTrue;
import ru.el59.office.db.web.CommentDocW;
import ru.perm.v.el59.office.test.model.DaoTest;

public class CommentDocWDaoTest extends DaoTest<CommentDocW, Long> {

	@Override
	protected String getNameDao() {
		return "commentDocWDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		CommentDocW e = getDao().read(n);
		System.out.println("CommentDocW:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

}
