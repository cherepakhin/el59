package ru.perm.v.el59.office.test.analisebest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import bsh.EvalError;
import bsh.Interpreter;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.Price;
import ru.el59.office.db.Tovar;

/**
 * Тест формулы для расчета cena1 dbf-прайса для Бест
 * 
 * @author vasi
 * 
 */
public class FormulaBestTagTest {

	private static BigDecimal LOW_MARGA = new BigDecimal("3.00");
	private static BigDecimal MIDDLE_MARGA = new BigDecimal("2.00");
	private static BigDecimal HIGH_MARGA = new BigDecimal("0.00");
	private static BigDecimal HIGH_MARGA_MAIN_TOVAR = new BigDecimal("1.00");

	private String formula = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource("testdata/formula_arr_suvs.bsh").getFile()));

    public FormulaBestTagTest() throws IOException {
    }

    public BigDecimal getCena1(
			Integer nnum, 
			BigDecimal cenain, 
			BigDecimal cena,
			String namePriceType, 
			String rootgroup,
			Integer category,
			String nameBonusK
			) throws EvalError {
		List<Price> prices = new ArrayList<Price>();
		Price p = new Price();
		Tovar tovar = new Tovar();
		tovar.setCenainrub(cenain);
		tovar.setNnum(nnum);
		GroupTovar groupTovar = new GroupTovar();
		tovar.setGroup(groupTovar);
		groupTovar.setName(rootgroup);
		BonusK bonusK = new BonusK();
		bonusK.setName(nameBonusK);
		groupTovar.setBonusk(bonusK);
		p.setCategory(category);
		p.setTovar(tovar);

		p.setCena(cena);
		
		PriceType priceType = new PriceType();
		priceType.setName(namePriceType);
		p.setPriceType(priceType);
		
		prices.add(p);
		Interpreter i = new Interpreter();
		i.set("listPrice", prices);
		Map<Integer,BigDecimal> ret = (Map<Integer, BigDecimal>) i.eval(formula);
		BigDecimal cena1 = new BigDecimal("-1.00");
		if(ret.containsKey(nnum)) {
			cena1=ret.get(nnum);
		}
		return cena1.setScale(0, RoundingMode.HALF_DOWN);
	}
	@Test
	public void testAksessuars() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					BigDecimal.ZERO,
					BigDecimal.ZERO,
					"",
					"Аксессуары",
					1,
					""
					);
			
			assertTrue(cena1.compareTo(HIGH_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testAksessuars");
		}
	}

	@Test
	public void testStockLow() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("1.00"),
					new BigDecimal("1.10"),
					"СТОКИ",
					"",
					1,
					""
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(MIDDLE_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testStockLow");
		}
	}

	@Test
	public void testStockHIGH() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("1.00"),
					new BigDecimal("1.26"),
					"Стоки",
					"",
					1,
					""
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(HIGH_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testStockHIGH");
		}
	}

	@Test
	public void testHIGH() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("1.00"),
					new BigDecimal("1.26"),
					"",
					"",
					1,
					""
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(HIGH_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testHIGH");
		}
	}

	@Test
	public void test15LOW() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("2.00"),
					new BigDecimal("2.10"),
					"",
					"",
					15,
					""
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(MIDDLE_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("test15LOW");
		}
	}

	@Test
	public void testLOW() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("1.00"),
					new BigDecimal("1.10"),
					"",
					"",
					1,
					""
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(LOW_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testHIGH");
		}
	}

	@Test
	public void testMIDDLE() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("1.00"),
					new BigDecimal("1.21"),
					"",
					"",
					1,
					""
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(MIDDLE_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testHIGH");
		}
	}

	@Test
	public void testGroupZP25() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("1.00"),
					new BigDecimal("1.10"),
					"",
					"",
					1,
					"Основной товар(5-2)"
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(HIGH_MARGA)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testHIGH");
		}
	}

	@Test
	public void testGroupMainTovar() {
		try {
			BigDecimal cena1 = getCena1(
					1,
					new BigDecimal("1.00"),
					new BigDecimal("1.30"),
					"",
					"",
					1,
					"Основной товар"
					);
			System.out.println(cena1);
			assertTrue(cena1.compareTo(HIGH_MARGA_MAIN_TOVAR)==0);
		} catch (EvalError e) {
			e.printStackTrace();
			fail("testHIGH");
		}
	}
}
