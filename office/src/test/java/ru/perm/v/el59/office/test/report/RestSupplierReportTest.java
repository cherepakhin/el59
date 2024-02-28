package ru.perm.v.el59.office.test.report;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.camel.StringSource;
import org.junit.Before;
import org.junit.Test;

import ru.el59.office.db.Contragent;
import ru.el59.office.db.RestSupplier;
import ru.el59.office.db.Tovar;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * Получение сводной таблицы из списка бинов<br>
 * Вид таблицы:<br>
 * <pre>
 * 			Supplier1	Supplier2
 * Tovar1	cena11		cena12
 * Tovar2	cena21		cena22
 * </pre>
 * @author vasi
 *
 */
public class RestSupplierReportTest {

	@Before
	public void setUp() {
		
	}
	
	private Contragent createContragent(Long n,String name) {
		Contragent c = new Contragent();
		c.setN(n);
		c.setName(name);
		return c;
	}
	
	private Tovar createTovar(Integer nnum,String name) {
		Tovar tovar = new Tovar();
		tovar.setNnum(nnum);
		tovar .setName(name);
		return tovar;
	}

	// Создание списка дял тестирования
	private List<RestSupplier> getRestsSupplier() {
		List<RestSupplier> ret = new ArrayList<RestSupplier>();
		Tovar tovar1 = createTovar(1,"Tovar1");
		Contragent supplier1 = createContragent(new Long(1),"Supplier1");

		Tovar tovar2 = createTovar(2,"Tovar2");
		Contragent supplier2 = createContragent(new Long(2),"Supplier2");
		
		RestSupplier r = new RestSupplier();
		r.setTovar(tovar1);
		r.setContragent(supplier1);
		r.setCena(new BigDecimal("1.10"));
		ret.add(r);
		
		r = new RestSupplier();
		r.setTovar(tovar2);
		r.setContragent(supplier1);
		r.setCena(new BigDecimal("2.10"));
		ret.add(r);
		
		r = new RestSupplier();
		r.setTovar(tovar1);
		r.setContragent(supplier2);
		r.setCena(new BigDecimal("1.20"));
		ret.add(r);

		r = new RestSupplier();
		r.setTovar(tovar2);
		r.setContragent(supplier2);
		r.setCena(new BigDecimal("2.20"));
		ret.add(r);

		return ret;
	}
	@Test
	public void restToXmlTest() throws IOException, TemplateException, TransformerException {
		// Получение тестового массива
		List<RestSupplier> rests = getRestsSupplier();
		assertTrue(rests.size()>0);
		
		// Настройка FreeMarker
		String directory = this.getClass().getResource("/").getFile();
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setDirectoryForTemplateLoading(new File(directory));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(true);
		Template template = cfg.getTemplate("restsupplier.ftl");
		
		// Преобразование List<RestSupplier> в xml
		Map<String, Object> beans = new HashMap<>();
		beans.put("rests", rests);
		StringWriter s = new StringWriter();
		template.process(beans, s);
		s.close();
//		System.out.println(s.toString());
		
		// XSLT преобразование
		Source xmlSource = new StringSource(s.toString());
		Source xsltSource = new StreamSource(new File(this.getClass().getResource("/restsupplier.xslt").getFile()));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsltSource);
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		transformer.transform(xmlSource, new StreamResult(bOut));
		bOut.close();
		String returnXML = bOut.toString();
		System.out.println(returnXML);
	}

}
