package ru.perm.v.el59.office.test.web;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ReaderRest {

	@Test
	public void readXls() {
		BufferedInputStream inputXML = null;
		try {
			inputXML = new BufferedInputStream(new FileInputStream(
					"/home/vasi/temp/rest.xml"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail("Не найден шаблон testdata/rest.xml");
		}
		
		XLSReader mainReader = null;
		try {
			mainReader = ReaderBuilder.buildFromXML(inputXML);
		} catch (IOException e1) {
			e1.printStackTrace();
			fail("Ошибка при открытии файла шаблона");
		} catch (SAXException e1) {
			e1.printStackTrace();
			fail("Файл шаблона не имеет правильной структуры");
		}
		
		InputStream inputXLS = null;
		try {
			inputXLS = new BufferedInputStream(
					new FileInputStream("/home/vasi/temp/rest.xls"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail("Файл остатков не найден");
		}
		Map<String, List<?>> beans = new HashMap<String, List<?>>();
		final List listRestXls = new ArrayList();
		beans.put("arrrestxls", listRestXls);
		try {
			mainReader.read(inputXLS, beans);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Файл остатков не распознан.");
		}
/*		for (RestXls restXls : listRestXls) {
			System.out.println(restXls);
		}
*/		assertTrue(true);
	}

}
