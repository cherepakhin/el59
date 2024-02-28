package ru.perm.v.el59.office.test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import ru.el59.office.critery.DocCritery;
import ru.el59.office.critery.DocItemCritery;
import ru.el59.office.db.Doc;
import ru.el59.office.db.DocItem;
import ru.el59.office.db.Tovar;
import ru.el59.office.iproviders.IDocItemProvider;
import ru.el59.office.iproviders.IDocProvider;

public class DocItemDaoTest extends DaoTest<DocItem, Long> {

	private static final String TEMPL_DBF_NACL = "/home/vasi/temp/nacl.dbf";
	private static final String DBF_NACL = "/home/vasi/temp/nacl1.dbf";
	private IDocProvider docDao;

	@Before
	public void setUp(){
		docDao = context.getBean(IDocProvider.class);
	}
	
	@Override
	protected String getNameDao() {
		return "docItemDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax()-1;
		System.out.println("n:"+n);
		DocItem e = getDao().read(n);
		System.out.println("Doc:"+e.getN());
		assertTrue(e.getN().compareTo(n)==0);
	}
	
	@Test
	public void getNullDocItem() {
		DocItem nullDocItem = ((IDocItemProvider) getDao()).getNullDocItem();
		assertNotNull(nullDocItem);
	}
	
	@Test
	public void createDBF(){
		try {
			FileUtils.copyFile(new File(TEMPL_DBF_NACL), new File(DBF_NACL));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Нет заготовки файла накладной");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Ошибка копирования заготовки в файл накладной");
		}
		
		try {
			Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			fail("Ошибка InstantiationException при регистрации драйвера DBF");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("Ошибка IllegalAccessException при регистрации драйвера DBF");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("Драйвера DBF не найден");
		}
		
		File file = new File(DBF_NACL);
		String prefixForLinux = "";
		if (DBF_NACL.startsWith("/")) {
			prefixForLinux = "//";
		}
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:dbf:/" + prefixForLinux + file.getParent() + "?charSet=Cp866", "",
					"");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Ошибка соединения с DBF файлом");
		}
		
		PreparedStatement ps=null;
		try {
			ps = conn
					.prepareStatement("insert into \""
							+ file.getName()
							+ "\" ("
							+ "grup,nnum,codpart_i,ed_i,kol_i,cena_i,numdoc,cena_iv,valcode,name,	info,kol_ned"
							+ ") values (" + "?,?,?,?,?,?,?,?,?,?,?,0" + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Ошибка подготовки insert запроса");
		}
		
		DocItemCritery critery = new DocItemCritery();
		critery.doc = getDoc();
		List<DocItem> listDocItem = getDao().getByCritery(critery);
		assertTrue(listDocItem.size()>0);
		for (int i = 0; i < listDocItem.size(); i++) {
			Tovar t = listDocItem.get(i).getTovar();
			String _nnum = t.getNnum().toString();
			if (_nnum.length() != 8) {
				while (_nnum.length() < 8)
					_nnum = "0" + _nnum;
			}
			try {
				System.out.println("grup:"+t.getGroup().getBestAsString());
				ps.setString(1, t.getGroup().getBestAsString());
				System.out.println("nnum:"+_nnum);
				ps.setString(2, _nnum);
				ps.setString(3, "");
				ps.setString(4, "шт.");
				System.out.println("qty"+listDocItem.get(i).getQty());
				ps.setBigDecimal(5, listDocItem.get(i).getQty());
				BigDecimal cena = listDocItem.get(i).getCena().setScale(2, BigDecimal.ROUND_HALF_UP);
				ps.setBigDecimal(6, cena);
				ps.setString(7, "");
				ps.setBigDecimal(8, cena);
				ps.setString(9, "РУБ");
				System.out.println("name:"+t.getName());
				ps.setString(10, t.getName());
				ps.setString(11, "");
			} catch (Exception e) {
				e.printStackTrace();
				fail("Ошибка при подстановки значений");
			}
			try {
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				e.getNextException().printStackTrace();
				fail("Ошибка вставки");
			}
		}
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Ошибка закрытия соединений");
		}
		assertTrue(true);
	}

	private Doc getDoc() {
		DocCritery docCritery = new DocCritery();
		docCritery.listN.add(new Long(44164));
		List<Doc> list = docDao.getByCritery(docCritery);
		if(list.size()==0) {
			fail("Список документов для теста пуст");
		}
		return list.get(0);
	}

}
