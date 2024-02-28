package ru.perm.v.el59.office.test.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.db.Tovar;
import ru.el59.office.parser.ParserMailRu;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class ParserMailRuTest1 {
	
	@Autowired
	protected ApplicationContext context;
	private ParserMailRu parser;

	@Before
	public void setUp() {
		parser = context.getBean(ParserMailRu.class);
	}
	
	@Test
	public void parse() {
		String ver = System.getProperty("java.version");
		System.out.println(ver);
		Tovar tovar = null;
		try {
			tovar = parser.parseTovar(1099648, "http://torg.mail.ru/tv/sony-kdl-32w705b-id14155553",null,true,true);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(tovar);
		assertTrue(tovar.getNnum().equals(1099648));
	}

}
