package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.dao.IGenericDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public abstract class DaoTest<T, PK extends Serializable> {
	
	@Autowired
	protected ApplicationContext context;
	private IGenericDao<T, PK> dao;
	
	@Test
	public void read() {
		List<T> list = getDao().getByLikeName("");
/*		for (T entity: list) {
			System.out.println(entity);
		}
*/		assertTrue(list.size()>0);
	}

	public IGenericDao<T, PK> getDao() {
		if(dao==null) {
			dao = (IGenericDao<T, PK>) context.getBean(getNameDao());
		}
		return dao;
	}

	protected abstract String getNameDao();

}
