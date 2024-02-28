package ru.perm.v.el59.office.test.loader;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.el59.office.iproviders.ILoaderMdocm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springContext.xml")
public class LoaderMdocmTest {

	@Autowired
	protected ApplicationContext context;

	@Test
	public void test() {
		String shopCod = "07220";
		
		
		String tmpDir = FileUtils.getTempDirectoryPath();
		System.out.println(tmpDir);
		tmpDir = tmpDir + File.separator+ shopCod;
		System.out.println(tmpDir);
		File dir = new File(tmpDir);
		if(dir.exists()) {
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File tmpFile;
		byte[] filedata ={'1','2'};
		try {
			boolean ret = dir.mkdir();
			if(!ret) {
				fail();
			}
			tmpFile = File.createTempFile("out", ".zip",dir);
			FileUtils.writeByteArrayToFile(tmpFile, filedata);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void load() throws IOException {
		ILoaderMdocm loader = (ILoaderMdocm) context.getBean("loaderMdocm");
		String zip = getClass().getResource("/testdata/07258.zip").getFile();
		byte[] filedata = FileUtils.readFileToByteArray(new File(zip));
		try {
			loader.load("07258", filedata, 10);
		} catch (InstantiationException e) {
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
