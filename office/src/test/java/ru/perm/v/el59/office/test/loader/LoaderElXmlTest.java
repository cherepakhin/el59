package ru.perm.v.el59.office.test.loader;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ru.perm.v.el59.office.db.dto.elxml.Good;
import ru.perm.v.el59.office.loaders.LoaderElXml;

public class LoaderElXmlTest {

	@Test
	public void getGoodsEncode1251() {
		LoaderElXml loader = new LoaderElXml();
	    ClassLoader classLoader = getClass().getClassLoader();
	    File f = new File(classLoader.getResource("testdata/goods_1251.xml").getFile());
		List<Good> goods = loader.getGoods(f.getPath());
		assertTrue(goods.size()>0);
		assertTrue(goods.get(0).getName().equals("USB-флешка FD 16Gb TRANSCEND JetFlash 700"));
	}

	@Test
	public void getGoodsEncodeUTF8() {
		LoaderElXml loader = new LoaderElXml();
	    ClassLoader classLoader = getClass().getClassLoader();
	    File f = new File(classLoader.getResource("testdata/goods_utf8.xml").getFile());
		List<Good> goods = loader.getGoods(f.getPath());
		assertTrue(goods.size()>0);
		assertTrue(goods.get(0).getName().equals("USB-флешка FD 16Gb TRANSCEND JetFlash 700"));
	}

}
