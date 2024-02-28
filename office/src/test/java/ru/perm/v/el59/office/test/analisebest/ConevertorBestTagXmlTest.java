package ru.perm.v.el59.office.test.analisebest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ru.el59.office.camelcontext.ConvertorBestTagsXml;
import ru.el59.office.dto.BestTag;
import ru.el59.office.dto.BestTags;
import ru.el59.office.dto.message.MessageBestTags;

public class ConevertorBestTagXmlTest {

	private BestTag bestTag1;
	private BestTag bestTag2;
	private List<BestTag> bestTags;

	@Before
	public void setUp() {
		bestTags = new ArrayList<BestTag>();
		bestTag1 = new BestTag("", 1, "1", "1", new BigDecimal("1.10"),
				new BigDecimal("1.00"),"Розничный");
		bestTags.add(bestTag1);
		bestTag2 = new BestTag("", 2, "2", "2", new BigDecimal("2.20"),
				new BigDecimal("2.00"),"Розничный");
		bestTags.add(bestTag2);
	}

	@Test
	public void test() throws Exception {
		ConvertorBestTagsXml convertorBestTagXml = new ConvertorBestTagsXml();
		MessageBestTags m = new MessageBestTags();
		m.setShopCod("07258");
		m.setEntity(new BestTags(bestTags));
		String xml = convertorBestTagXml.getXML(m);
		String[] lines = xml.split("\n");
		System.out.println(xml);
		// Проверяю кол-во строк
		// <message>
		// </message> 
		// 2 строки
		
		// <command>CREATE</command>
		// <className>BestTags</className>
		// <shopCod>07258</shopCod>
		// 3 строки
		
		// <entity type="BestTags">
	    // <tags>
		// </tags>
		// </entity>
		// 4
		
		// <BestTag>
        // 		<group></group>
        //		<nnum>1</nnum>
        //		<name>1</name>
		//		<annot>1</annot>
        //		<cena>1.10</cena>
        //		<label>1.00</label>
		//		<priceName>Розничный</priceName>
		//	</BestTag>
		// 9 * 2
		assertTrue(lines.length==2+3+4+9*2);
		assertTrue(lines[0].matches("\\s*<message>"));
		assertTrue(lines[1].matches("\\s*<command>CREATE</command>"));
		assertTrue(lines[2].matches("\\s*<className>BestTags</className>"));
		assertTrue(lines[3].matches("\\s*<shopCod>07258</shopCod>"));
		assertTrue(lines[4].matches("\\s*<entity>"));
		assertTrue(lines[5].matches("\\s*<tags>"));
		assertTrue(lines[6].matches("\\s*<BestTag>"));
		assertTrue(lines[7].matches("\\s*<group></group>"));
		assertTrue(lines[8].matches("\\s*<nnum>1</nnum>"));
		assertTrue(lines[9].matches("\\s*<name>1</name>"));
		assertTrue(lines[10].matches("\\s*<annot>1</annot>"));
		assertTrue(lines[11].matches("\\s*<cena>1.10</cena>"));
		assertTrue(lines[12].matches("\\s*<label>1.00</label>"));
		assertTrue(lines[13].matches("\\s*<priceName>Розничный</priceName>"));
		assertTrue(lines[14].matches("\\s*</BestTag>"));
	}

}
