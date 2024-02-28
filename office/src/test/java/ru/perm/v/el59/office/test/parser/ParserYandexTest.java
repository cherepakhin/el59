package ru.perm.v.el59.office.test.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.db.Tovar;
import ru.el59.office.db.TovarInfo;
import ru.el59.office.iproviders.ITovarInfoProvider;
import ru.el59.office.iproviders.ITovarProvider;
import ru.el59.office.iproviders.subs.IMainFeatureProvider;
import ru.el59.office.iproviders.web.ISubsFeatureProvider;
import ru.el59.office.parser.ParserEl;
import ru.el59.office.parser.ParserYandex;

public class ParserYandexTest {

	private final static Integer NNUM = 71173062;

	private ParserYandex parser;
	private ITovarProvider tovarProvider;
	private ITovarInfoProvider tovarInfoProvider;
	private IMainFeatureProvider mainFeatureProvider;
	private ISubsFeatureProvider subsFeatureProvider;

	@Before
	public void setUp() throws Exception {
		// parser = context.getBean(ParserEl.class);

		Tovar tovar = new Tovar();
		parser = new ParserYandex();
		tovar.setNnum(NNUM);

		TovarInfo tovarInfo = new TovarInfo();
		tovarInfo.setTovar(tovar);
		parser.setTovarInfo(tovarInfo);

		tovarInfoProvider = Mockito.mock(ITovarInfoProvider.class);
		parser.setTovarInfoProvider(tovarInfoProvider);
		when(tovarInfoProvider.initialize(NNUM)).thenReturn(tovarInfo);
		when(tovarInfoProvider.clearFeaturesWeb(tovarInfo)).thenReturn(
				tovarInfo);
		when(tovarInfoProvider.getDirForPhoto(NNUM)).thenReturn(
				"/home/vasi/temp/4");
		when(tovarInfoProvider.getDirForPhoto())
				.thenReturn("/home/vasi/temp/4");

		tovarProvider = Mockito.mock(ITovarProvider.class);
		parser.setTovarProvider(tovarProvider);

		mainFeatureProvider = Mockito.mock(IMainFeatureProvider.class);
		parser.setMainFeatureProvider(mainFeatureProvider);

		subsFeatureProvider = Mockito.mock(ISubsFeatureProvider.class);
		parser.setSubsFeatureProvider(subsFeatureProvider);
	}

	@Test
	public void parse() {
		String ver = System.getProperty("java.version");
		System.out.println(ver);
		Tovar tovar = null;
		try {
			String s = FileUtils.readFileToString(new File(
					"/home/vasi/temp/ya1.html"));
			// Парсинг из файла
			//System.out.println(s);
			tovar = parser
					.parseTovar(NNUM,
							s, null,
							true, true);
			// много картинок
/*			tovar = parser
					.parseTovar(1099648,
							"https://market.yandex.ru/product/107993", null,
							true, true);
*/			// одна картинка
			// tovar = parser.parseTovar(1099648,
			// "https://market.yandex.ru/product/7275466",null,true,true);
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
