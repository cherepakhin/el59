package ru.perm.v.el59.office.test.analisebest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ru.el59.office.analisebest.ICreatotBestTag;
import ru.el59.office.analisebest.ProtocolForTag;
import ru.el59.office.dto.BestTag;

public class ProtocolForTagTest {

	private BestTag bestTag1;
	private BestTag bestTag2;
	private BestTag bestTag11;
	private BestTag bestTag21;
	private ProtocolForTag protocolForTag;

	@Before
	public void setUp() {
		protocolForTag = new ProtocolForTag();
		bestTag1 = new BestTag("", 1, "1", "1", new BigDecimal("1.10"),
				new BigDecimal("1.00"),"");
		bestTag2 = new BestTag("", 2, "2", "2", new BigDecimal("2.20"),
				new BigDecimal("2.00"),"");
		bestTag11 = new BestTag("", 1, "11", "11", new BigDecimal("11.11"),
				new BigDecimal("1.00"),"");
		bestTag21 = new BestTag("", 2, "21", "21", new BigDecimal("2.21"),
				new BigDecimal("2.00"),"");
	}


	// Выходной массив пуст
	@Test
	public void testAnaliseListEqual() {
		class CreatorBestTagForEqual implements ICreatotBestTag {

			@Override
			public BestTag getBestTag(Integer nnum,String shopCod) {
				if (nnum.equals(bestTag1.nnum)) {
					return bestTag1;
				}
				if (nnum.equals(bestTag2.nnum)) {
					return bestTag2;
				}
				return new BestTag();
			}
		}

		protocolForTag.setCreatorBestTag(new CreatorBestTagForEqual());
		ArrayList<BestTag> bestTags = new ArrayList<BestTag>();
		bestTags.add(bestTag1);
		bestTags.add(bestTag2);
		List<BestTag> ret = protocolForTag.analise(bestTags, "07258");
		assertTrue(ret.size() == 0);
	}


	// Выходной массив не пуст
	@Test
	public void testAnaliseListNotEqual() {
		class CreatorBestTagForNotEqual implements ICreatotBestTag{

			@Override
			public BestTag getBestTag(Integer nnum,String shopCod) {
				if (nnum.equals(bestTag1.nnum)) {
					return bestTag11;
				}
				if (nnum.equals(bestTag2.nnum)) {
					return bestTag2;
				}
				return new BestTag();
			}
		}

		protocolForTag.setCreatorBestTag(new CreatorBestTagForNotEqual());
		ArrayList<BestTag> bestTags = new ArrayList<BestTag>();
		bestTags.add(bestTag1);
		bestTags.add(bestTag2);
		List<BestTag> ret = protocolForTag.analise(bestTags, "07258");
		assertTrue(
				String.format("bestTags.size()=%d, ret.size()=%d",
						bestTags.size(), ret.size()), ret.size() == 1);
		assertTrue(ret.get(0).equals(bestTag11));
	}

}
