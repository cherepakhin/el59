package ru.perm.v.el59.office.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.critery.TovarCritery;
import ru.el59.office.db.Tovar;
import ru.el59.office.iproviders.ITovarProvider;
import ru.el59.office.util.ILuceneSearcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class LuceneSearcherTest {

	@Autowired
	protected ApplicationContext context;
	private ILuceneSearcher searcher;
	private ITovarProvider tovarProvider;

	@Before
	public void setUp() {
		searcher = context.getBean(ILuceneSearcher.class);
		tovarProvider=context.getBean(ITovarProvider.class);
	}
	
	@Ignore
	@Test
	public void testCreateIndex() {
		assertTrue(searcher!=null);
		assertTrue(tovarProvider!=null);
		TovarCritery critery = new TovarCritery();
		List<Tovar> listTovar = tovarProvider.getByCritery(critery);
		int i=0;
		for (Tovar tovar : listTovar) {
			System.out.println(String.format("%d %s", i++, tovar.getName()));
		}

		try {
			searcher.addListTovar(listTovar);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Ошибка при создании индекса");
		}
		assertTrue(true);
	}

//	@Ignore
	@Test
	public void testGetAnalog() {
		List<Integer> listNnum = null;
		System.out.println("testGetAnalog()==================");
		try {
			listNnum = searcher.getAnalog("LG 24LH450U=");
			TovarCritery tovarCritery = new TovarCritery();
			tovarCritery.nnum.addAll(listNnum);
			List<Tovar> listTovar = tovarProvider.getByCritery(tovarCritery);
			for (Tovar tovar : listTovar) {
				System.out.println(tovar.getName());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("Ошибка IO");
		} catch (ParseException e) {
			e.printStackTrace();
			fail("Ошибка Parse");
		}
		assertNotNull(listNnum);
		assertTrue(listNnum.size()>0);
	}
}
