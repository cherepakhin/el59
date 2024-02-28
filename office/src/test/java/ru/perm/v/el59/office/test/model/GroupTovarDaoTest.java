package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ru.el59.office.db.GroupTovar;
import ru.el59.office.iproviders.IGroupTovarProvider;

public class GroupTovarDaoTest extends DaoTest<GroupTovar,String>{

	
	@Override
	public void read() {
		IGroupTovarProvider dao=(IGroupTovarProvider) getDao();
		List<GroupTovar> list = dao.getAll();
		assertTrue(list.size()>0);
	}
	
	@Test
	public void chekSort() {
		IGroupTovarProvider dao=(IGroupTovarProvider) getDao();
		GroupTovar g = dao.initialize("0000000000");
		assertTrue(g.getChilds().size()>0);
		for(int i=0;i<g.getChilds().size();i++) {
			System.out.println(g.getChilds().get(i));
		}
	}

	@Test
	public void getTree() {
		IGroupTovarProvider dao=(IGroupTovarProvider) getDao();
		GroupTovar g = dao.initialize("0000000000");
		g=dao.getTree(g);
		assertTrue(g.getChilds().size()>0);
	}
	
	@Override
	protected String getNameDao() {
		return "groupTovarDao";
	}

}
