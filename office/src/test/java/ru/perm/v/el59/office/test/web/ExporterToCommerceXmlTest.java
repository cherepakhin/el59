package ru.perm.v.el59.office.test.web;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.critery.TovarCritery;
import ru.el59.office.db.TovarInfo;
import ru.el59.office.iproviders.ITovarInfoProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext-test.xml")
public class ExporterToCommerceXmlTest {

	@Autowired
	protected ApplicationContext context;
	private ITovarInfoProvider tovarInfoProvider;

	@Before
	public void setup() {
		tovarInfoProvider = context.getBean(ITovarInfoProvider.class);
	}
	
	@Ignore
	@Test
	public void getXmlCommerceMLByListTovarInfo() {
		TovarCritery tovarCritery = new TovarCritery();
		tovarCritery.name="555";
		List<TovarInfo> listTovarInfo = tovarInfoProvider.getByCritery(tovarCritery);
		assertTrue(listTovarInfo.size()>0);
		String xml = "";
		try {
			xml = tovarInfoProvider.getXmlCommerceMLByListTovarInfo(listTovarInfo);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(xml);
		assertTrue(!xml.isEmpty());
	}
	
	@Test
	public void getXmlCommerceByTovarCritery() throws Exception {
		String xml = "";
		try {
			xml = tovarInfoProvider.getXmlCommerceByTovarCritery();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println(xml.length());
		assertTrue(!xml.isEmpty());
	}
}
