package ru.perm.v.el59.office.test.web;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.db.web.RestWeb;
import ru.el59.office.db.web.TypeSite;
import ru.el59.office.iproviders.web.IRestWebProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class RestWebProviderTest {

	@Autowired
	protected ApplicationContext context;
	private IRestWebProvider restWebProvider;;

	@Before
	public void setup() {
		restWebProvider = context.getBean(IRestWebProvider.class);
	}
	
	@Test
	public void upload() {
		List<RestWeb> listRest = restWebProvider.getListForSite(TypeSite.INNER);
		assertTrue(listRest.size()>0);
		for (RestWeb restWeb : listRest) {
			if(restWeb.getTovar().getNnum().compareTo(76000310)==0) {
				fail("Двойник существует");
			}
		}
	}
	
}
