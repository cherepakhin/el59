package ru.perm.v.el59.office.test.analisebest;

import bsh.EvalError;
import bsh.Interpreter;
import ru.perm.v.el59.office.db.Price;
import ru.perm.v.el59.office.db.Tovar;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Тест формулы для расчета cena1 dbf-прайса для Бест
 * 
 * @author vasi
 * 
 */
public class FormulaArrayTest {

	private static BigDecimal LOW_MARGA = new BigDecimal("0.00");
	private static BigDecimal MIDDLE_MARGA = new BigDecimal("1.00");
	private static BigDecimal HIGH_MARGA = new BigDecimal("2.00");

//	private String formula = FileUtils.readAllText(new File(getClass().getClassLoader().getResource("testdata/formula_arr_suvs.bsh").getFile()));

	
	@Test
	public void calcArray() throws EvalError {
		ArrayList<Price> listPrice = new ArrayList<Price>();
		Tovar t1 = new Tovar();
		t1.setNnum(1);

		Tovar t2 = new Tovar();
		t2.setNnum(1);
		
		Price p = new Price();
		p.setTovar(t1);
		listPrice.add(p);
		
		p = new Price();
		p.setTovar(t2);
		listPrice.add(p);

		Interpreter i = new Interpreter();
//		i.set("listPrice", listPrice);
		i.set("listPrice", listPrice);
//		ArrayList<PriceForCalc> ret = (ArrayList<PriceForCalc>) i.eval(formula);
//		HashMap<Integer,BigDecimal> map =  (HashMap<Integer, BigDecimal>) i.eval(formula);
//		assertTrue(map.keySet().size()==2);
	}

}
