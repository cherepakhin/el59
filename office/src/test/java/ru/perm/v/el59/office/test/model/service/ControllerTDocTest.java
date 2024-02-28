package ru.perm.v.el59.office.test.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.db.Manager;
import ru.el59.office.db.service.LOPT;
import ru.el59.office.iproviders.IManagerProvider;
import ru.el59.office.iproviders.RequestItem;
import ru.el59.office.iproviders.RequestMessage;
import ru.el59.office.iproviders.service.IControllerTDoc;
import ru.el59.office.iproviders.service.ILOPTDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext-test.xml")

public class ControllerTDocTest {

	@Autowired
	protected ApplicationContext context;
	private IControllerTDoc controllerTDoc;
	private ILOPTDao loptDao;
	private Manager manager;

	@Before
	public void  getControllerTDoc() {
		controllerTDoc = context.getBean(IControllerTDoc.class);
		loptDao = context.getBean(ILOPTDao.class);
		IManagerProvider managerDao = context.getBean(IManagerProvider.class);
		manager = managerDao.getByEqName("Черепахин В.Г.");
	}
	
	@Test
	public void createTDoc() {
		Long n = loptDao.getMax();
		LOPT lopt = loptDao.read(n-1);
		assertNotNull(lopt);
		RequestMessage req1 = null;
		try {
			req1 = controllerTDoc.getRequestMessage(lopt, null, manager.getName());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Не получил первый ответ по lopt");
		}
		assertNotNull(req1);
		assertTrue(req1.listRequestItem.size()>0);
		RequestItem ans = req1.listRequestItem.get(0);
		RequestMessage req2 = null;
		ans.request=true;
		try {
			req2 = controllerTDoc.getRequestMessage(lopt, req1, manager.getName());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Не получил второй ответ");
		}
		assertNotNull(req2);
	}

}
