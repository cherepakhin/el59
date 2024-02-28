package ru.perm.v.el59.office.test.analisebest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import ru.el59.office.analisebest.BestTagToPriceConvertor;
import ru.el59.office.dto.BestTag;

public class BestTagToPriceConvertorTest {

	@Test
	public void test() {
		BestTagToPriceConvertor convertor = new BestTagToPriceConvertor();
		convertor.setTemplatePriceDBF(getClass().getResource("/price.dbf").getFile());
		List<BestTag> bestTags = new ArrayList<BestTag>();
		bestTags.add(new BestTag("", 1, "1", "1", new BigDecimal("1.10"),
				new BigDecimal("1.00"),""));
		bestTags.add(new BestTag("", 2, "2", "2", new BigDecimal("2.20"),
				new BigDecimal("2.00"),""));
		bestTags.add(new BestTag("", 1, "11", "11", new BigDecimal("11.11"),
				new BigDecimal("1.00"),""));
		bestTags.add(new BestTag("", 2, "21", "21", new BigDecimal("2.21"),
				new BigDecimal("2.00"),""));
		try {
			byte[] data = convertor.createZip(bestTags);
			FileUtils.writeByteArrayToFile(new File("/tmp/p.zip"), data);
			assertTrue(data.length>0);
		} catch (InstantiationException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
