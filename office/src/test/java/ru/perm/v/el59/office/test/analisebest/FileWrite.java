package ru.perm.v.el59.office.test.analisebest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;

public class FileWrite {

	@Test
	public void test() {
		File f = new File("/home/vasi/temp/dbf/pr.txt");
		if(f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
			FileUtils.writeStringToFile(f, String.format("%d;%.2f\n", new Integer(100),new BigDecimal("1.234")), true);
			FileUtils.writeStringToFile(f, String.format("%d;%.2f\n", new Integer(100),new BigDecimal("2.234")), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}
