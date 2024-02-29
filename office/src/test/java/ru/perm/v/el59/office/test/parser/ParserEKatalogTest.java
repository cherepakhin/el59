package ru.perm.v.el59.office.test.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ru.el59.office.db.Feature;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.TovarInfo;
import ru.el59.office.iproviders.ITovarInfoProvider;
import ru.el59.office.iproviders.ITovarProvider;
import ru.el59.office.iproviders.subs.IMainFeatureProvider;
import ru.el59.office.iproviders.web.ISubsFeatureProvider;
import ru.el59.office.parser.ParserEKatalog;

public class ParserEKatalogTest {

	private final static Integer NNUM=1099648;
	private ParserEKatalog parser;
	private ITovarProvider tovarProvider;
	private ITovarInfoProvider tovarInfoProvider;
	private IMainFeatureProvider mainFeatureProvider;
	private ISubsFeatureProvider subsFeatureProvider;

	@Before
	public void setUp() throws Exception {
		parser = new ParserEKatalog();

		Tovar tovar = new Tovar();
		tovar.setNnum(NNUM);
		TovarInfo tovarInfo = new TovarInfo();
		tovarInfo.setTovar(tovar);
		
		parser.setTovarInfo(tovarInfo);

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
			// tovar = parser.parseTovar(1099648, "http://www.e-katalog.ru/APPLE-IPAD-PRO-32GB.htm",null,true,true);
			// Без вкладки Характеристики
//			tovar = parser.parseTovar(1099648, "http://www.e-katalog.ru/CANON-C-EXV14-0384B006.htm",null,true,true);
			// С вкладкой Характеристики
//			tovar = parser.parseTovar(1099648, "http://www.e-katalog.ru/GORENJE-EC-55-CL.htm",null,true,true);
//			tovar = parser.parseTovar(1099648, "http://www.e-katalog.ru/LG-32LH530V.htm",null,true,true);
			// Полные характеристики на главной странице
//			tovar = parser.parseTovar(1099648, "http://www.e-katalog.ru/ACER-DT-SXRER-021.htm",null,true,true);
			// Еще один какой-то левый вариант отображения
			tovar = parser.parseTovar(1099648, "http://www.e-katalog.ru/TUAREX-ALTA-1002.htm",null,true,true);
						
//			tovar = parser.parseTovar(1099648, "file:///home/vasi/temp/a.html",null,true,true);
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
		assertTrue(parser.getTovarInfo().getListFeature().size()>0);
		for (Feature f : parser.getTovarInfo().getListFeature()) {
			Logger.getLogger(this.getClass().getName()).info(
					String.format("%s:%s:%s", f.getGrp(),
							f.getName(), f.getVal()));
		}
	}

}
