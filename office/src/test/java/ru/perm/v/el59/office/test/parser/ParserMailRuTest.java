package ru.perm.v.el59.office.test.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import ru.el59.office.db.Tovar;
import ru.el59.office.db.TovarInfo;
import ru.el59.office.iproviders.ITovarInfoProvider;
import ru.el59.office.iproviders.ITovarProvider;
import ru.el59.office.iproviders.subs.IMainFeatureProvider;
import ru.el59.office.iproviders.web.ISubsFeatureProvider;
import ru.el59.office.parser.ParserMailRu;

public class ParserMailRuTest {

	private final static Integer NNUM=1099648;
	@Autowired
	protected ApplicationContext context;
	private ParserMailRu parser;
	private ITovarProvider tovarProvider;
	private ITovarInfoProvider tovarInfoProvider;
	private IMainFeatureProvider mainFeatureProvider;
	private ISubsFeatureProvider subsFeatureProvider;

	@Before
	public void setUp() throws Exception {
		parser = new ParserMailRu();

		Tovar tovar = new Tovar();
		tovar.setNnum(NNUM);
		TovarInfo tovarInfo = new TovarInfo();
		tovarInfo.setTovar(tovar);

		tovarInfoProvider = Mockito.mock(ITovarInfoProvider.class);
		parser.setTovarInfoProvider(tovarInfoProvider);
		when(tovarInfoProvider.initialize(NNUM)).thenReturn(tovarInfo);
		when(tovarInfoProvider.getDirForPhoto(NNUM)).thenReturn("/home/vasi/temp/4");
		when(tovarInfoProvider.getDirForPhoto()).thenReturn("/home/vasi/temp/4");
		
		tovarProvider = Mockito.mock(ITovarProvider.class);
		parser.setTovarProvider(tovarProvider);
		
		mainFeatureProvider = Mockito.mock(IMainFeatureProvider.class);
		parser.setMainFeatureProvider(mainFeatureProvider);
		
		subsFeatureProvider = Mockito.mock(ISubsFeatureProvider.class);
		parser.setSubsFeatureProvider(subsFeatureProvider);
	}
	
	@Test
	public void parse() {
		Tovar tovar =null;
		try {
			tovar = parser.parseTovar(1099648, "http://torg.mail.ru/refrigerators/indesit-sb-167-id63129/",null,true,true);
//			tovar = parser.parseTovar(1099648, "file:///home/vasi/temp/hc1.html",null);
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
		assertTrue(tovar.getNnum().equals(NNUM));
	}

}
