package ru.perm.v.el59.office.test.analisebest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import ru.el59.office.analisebest.DbfToBestTagConvertor;
import ru.el59.office.dto.BestTag;

public class DbfToBestTagConvertorTest {

	private DbfToBestTagConvertor convertor;

	@Before
	public void setUp() {
		convertor = new DbfToBestTagConvertor();
	}

	// Проверка длины имени zip-файла
	@Test(expected = Exception.class)
	public void testLengthNameZipFileFalse() throws Exception {
		File f = new File("aaaa.zip");
		convertor.getShopCodFromZip(f);
		assertTrue(false);
	}

	// Проверка что в имени zip-файла все цифры
	@Test(expected = Exception.class)
	public void testDigitNameZipFileFalse() throws Exception {
		File f = new File("07a58.zip");
		convertor.getShopCodFromZip(f);
		assertTrue(false);
	}

	// Проверка что в имени zip-файла все цифры
	@Test
	public void testDigitNameZipFileOk(){
		File f = new File("07258.zip");
		try {
			convertor.getShopCodFromZip(f);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	// Проверка что zip-файл не существует
	@Test(expected = Exception.class)
	public void testZipFileNotExist() throws Exception{
		File f = new File("07258.zip");
		convertor.unzip(f,"07258");
		assertTrue(true);
	}
	
	// Проверка что zip-файл существует
	@Test
	public void testZipFileExist(){
	    ClassLoader classLoader = getClass().getClassLoader();
	    File f = new File(classLoader.getResource("testdata/07258.zip").getFile());
	    
		String tmpDir="";
		try {
			tmpDir = convertor.unzip(f,"07258");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		Logger.getLogger(this.getClass()).info("Temp directory for zip "+tmpDir);
		assertTrue(true);
	}

	// Проверка подготовки данных из dbf
	@Test
	public void testprepareBestTagsFromDirectory(){
	    ClassLoader classLoader = getClass().getClassLoader();
	    File f = new File(classLoader.getResource("testdata/07258.zip").getFile());
	    
	    List<BestTag> bestTags=null;
	    try {
			bestTags = convertor.prepareBestTagsFromDirectory(f);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	    assertNotNull(bestTags);
//	    System.out.println(bestTags.size());
		assertTrue(bestTags.size()==1432);
/*		for (BestTag bestTag : bestTags) {
			System.out.println(bestTag.toString());
		}
*/	}
}
