package ru.perm.v.el59.office.test.web;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.iproviders.web.IUploaderForSite;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class UploaderForSiteTest {

	@Autowired
	protected ApplicationContext context;
	private IUploaderForSite uploaderForSite;

	@Before
	public void setup() {
		uploaderForSite = context.getBean(IUploaderForSite.class);
	}
	
	@Test
	@Ignore
	public void upload() {
		try {
			uploaderForSite.upload();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		assertTrue(true);
	}
	
	@Test
	public void runScript() {
		try {
			uploaderForSite.runScript();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		assertTrue(true);
	}
}
