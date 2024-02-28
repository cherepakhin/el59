package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ru.el59.office.db.GroupT;
import ru.el59.office.iproviders.IGroupTProvider;

public class GroupTDaoTest extends DaoTest<GroupT,String>{

	
	@Override
	public void read() {
		IGroupTProvider dao=(IGroupTProvider) getDao();
		List<GroupT> list = dao.getAll();
		assertTrue(list.size()>0);
	}
	
/*	@Test*/
	public void chekSort() {
		IGroupTProvider dao=(IGroupTProvider) getDao();
		GroupT g = dao.initialize("0000000000");
		assertTrue(g.getChilds().size()>0);
		for(int i=0;i<g.getChilds().size();i++) {
			System.out.println(g.getChilds().get(i));
		}
	}

	@Override
	protected String getNameDao() {
		return "groupTDao";
	}
	
	@Test
	public void getRootGroupT() {
		IGroupTProvider dao=(IGroupTProvider) getDao();
		GroupT root = new GroupT();
		root.setCod("");
		root = dao.getTree(root);
		assertNotNull(root);
	}
	

}
