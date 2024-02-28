package ru.perm.v.el59.office.calc;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import bsh.EvalError;
import bsh.Interpreter;

public class BshCalc {

	@Test
	public void testInteger() {
		Integer newCena=0;
		Interpreter i = new Interpreter();
		try {
			i.set("k", 2);
			newCena = (Integer) i.eval("return 1*k;");
		} catch (EvalError e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(newCena==2);
	}

	/*
import java.math.BigDecimal;

if(nnum.equals(71049743)) return cena;
_cena=cena.doubleValue();
rub=(_cena*1.12);
kop=_cena-_cena.intValue();
if(rub<=100) {
	return new BigDecimal(rub.intValue()+kop);
} 
if(rub<1000) {
	return new BigDecimal((rub/10+0.5).intValue()*10-1+kop);
} 
return new BigDecimal((rub/100+0.5).intValue()*100-10+kop);
	 */
	@Test
	public void testBigDecimal() {
		BigDecimal newCena=new BigDecimal("0.00");
		newCena.multiply(new BigDecimal(new Double(2)));
		Interpreter i = new Interpreter();
		try {
			i.set("k", new BigDecimal("2.00"));
			newCena = (BigDecimal) i.eval("import java.math.BigDecimal;" +
					"i=k.doubleValue();" +
					"ret=i*4;" +
					"return new BigDecimal(ret);");
		} catch (EvalError e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(newCena.compareTo(new BigDecimal("8.00"))==0);
	}
	
	@Test
	public void testFormula() {
		BigDecimal newCena=new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
		Interpreter i = new Interpreter();
		try {
			i.set("cena", new BigDecimal("10.50"));
			i.set("nnum", new Integer(100000));
			newCena = (BigDecimal) i.eval("" +
					"import java.math.BigDecimal;" +
					"if(nnum.equals(71049743)) return cena;" +
					"double _cena=cena.doubleValue();" +
					"double rub=(_cena*1.12);" +
					"double kop=_cena-_cena.intValue();" +
					"" +
					"if(rub<=100) { return new BigDecimal(rub.intValue()+kop);}" +
					"if(rub<1000) {return new BigDecimal((rub/10+0.5).intValue()*10-1+kop);}" +
					"return new BigDecimal((rub/100+0.5).intValue()*100-10+kop);");
		} catch (EvalError e) {
			e.printStackTrace();
			fail();
		}
		System.out.println(newCena);
		assertTrue(newCena.compareTo(new BigDecimal("11.50"))==0);
	}
	
}
