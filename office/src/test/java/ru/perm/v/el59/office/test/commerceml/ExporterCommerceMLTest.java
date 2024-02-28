package ru.perm.v.el59.office.test.commerceml;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.commerceml.ExporterToCommerceML;
import ru.el59.office.iproviders.ITovarInfoProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class ExporterCommerceMLTest{
	
	@Autowired
	protected ApplicationContext context;

	ExporterToCommerceML exporter;

	private ITovarInfoProvider tovarInfoProvider;
	
	@Before
	public void setUp() {
		exporter = (ExporterToCommerceML) context.getBean("exporterCommerceML");
		tovarInfoProvider =  (ITovarInfoProvider) context.getBean("tovarInfoDao");
	}
	
	@Test
	public void export() throws Exception {
		try {
			String xml = tovarInfoProvider.getXmlCommerceByTovarCritery();
			FileUtils.writeStringToFile(new File("/home/vasi/temp/i.txt"), xml, false);
			assertTrue(xml.length()>0);
		} catch (IOException e) {
			e.printStackTrace();
			fail("export to xml failed");
		}
	}

}
