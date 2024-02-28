package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import ru.el59.office.dao.impl.plan.CalculatorZPMBT;
import ru.el59.office.db.BonusK;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.Move;
import ru.el59.office.db.Operation;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.plan.UserZP;

public class CalculatorZPMBTTest {

	private static BigDecimal SUMMAIN = new BigDecimal("1.00");
	private static BigDecimal SUMMAOUT = new BigDecimal("2.00");
	private static BigDecimal MIN_NACENKA = new BigDecimal("0.50");
	private static BigDecimal MAX_K_BONUS = new BigDecimal("0.05");
	private static BigDecimal MIN_K_BONUS = new BigDecimal("0.02");

	@Test 
	public void testAddSumInOut() {
		Move m = new Move();
		m.setSummain(SUMMAIN);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());
		UserZP u = new UserZP();
		CalculatorZPMBT calculator = new CalculatorZPMBT();
		u=calculator.addSumInOut(u, m);
		assertTrue(u.getSummaMainTovar().compareTo(SUMMAOUT)==0);
	}
	
	private Operation getOperation() {
		Operation op = new Operation();
		op.setZnak(-1);
		return op;
	}
	
	@Test
	public void testMaxK() throws Exception {
		Move m = new Move();
		m.setOperation(getOperation());
		m.setSummain(SUMMAIN);
		m.setSummaout(SUMMAOUT);
		Tovar t = new Tovar();
		m.setTovar(t);
		GroupTovar g =new GroupTovar();
		g.setMinNacenka(MIN_NACENKA);
		t.setGroup(g);
		BonusK b = new BonusK();
		b.setMinK(MIN_K_BONUS);
		b.setMaxK(MAX_K_BONUS);
		g.setBonusk(b);
		UserZP u = new UserZP();
		CalculatorZPMBT calculator = new CalculatorZPMBT();
		assertTrue(u.getSummaMainTovar().compareTo(BigDecimal.ZERO)==0);
		assertTrue(u.getSummaMainTovarBonus().compareTo(BigDecimal.ZERO)==0);
		
		u=calculator.calcZP(u, m);
		// Насенка больше MIN_NACENKA(50%)
		BigDecimal zp = m.getSummaout().multiply(MAX_K_BONUS).setScale(2, RoundingMode.HALF_UP);
		assertTrue(u.getSummaMainTovarBonus().compareTo(zp)==0);
	}

	@Test
	public void testMinK() throws Exception {
		Move m = new Move();
		m.setOperation(getOperation());
		m.setSummain(SUMMAIN);
		m.setSummaout(SUMMAIN);
		Tovar t = new Tovar();
		m.setTovar(t);
		
		GroupTovar g =new GroupTovar();
		g.setMinNacenka(MIN_NACENKA);
		t.setGroup(g);

		BonusK b = new BonusK();
		b.setMinK(MIN_K_BONUS);
		b.setMaxK(MAX_K_BONUS);
		g.setBonusk(b);
		
		UserZP u = new UserZP();
		CalculatorZPMBT calculator = new CalculatorZPMBT();
		assertTrue(u.getSummaMainTovarBonus().compareTo(BigDecimal.ZERO)==0);
		u=calculator.calcZP(u, m);
		assertTrue(u.getSummaMainTovar().compareTo(BigDecimal.ZERO)==0);
		// Насенка меньше MIN_NACENKA(50%)
		BigDecimal zp = m.getSummaout().multiply(MIN_K_BONUS).setScale(2, RoundingMode.HALF_UP);
		assertTrue(u.getSummaMainTovarBonus().compareTo(zp)==0);
		System.out.println(u.getSummaMainTovarBonus());
	}

	@Test
	public void testSumInZERO() throws Exception {
		Move m = new Move();
		m.setOperation(getOperation());
		m.setSummain(BigDecimal.ZERO);
		m.setSummaout(SUMMAOUT);
		Tovar t = new Tovar();
		m.setTovar(t);
		
		GroupTovar g =new GroupTovar();
		g.setMinNacenka(MIN_NACENKA);
		t.setGroup(g);

		BonusK b = new BonusK();
		b.setMinK(MIN_K_BONUS);
		b.setMaxK(MAX_K_BONUS);
		g.setBonusk(b);
		
		UserZP u = new UserZP();
		CalculatorZPMBT calculator = new CalculatorZPMBT();
		assertTrue(u.getSummaMainTovar().compareTo(BigDecimal.ZERO)==0);
		assertTrue(u.getSummaMainTovarBonus().compareTo(BigDecimal.ZERO)==0);
		u=calculator.calcZP(u, m);
		// Насенка больше MIN_NACENKA(50%)
		BigDecimal zp = m.getSummaout().multiply(MAX_K_BONUS).setScale(2, RoundingMode.HALF_UP);
		assertTrue(u.getSummaMainTovarBonus().compareTo(zp)==0);
		System.out.println(u.getSummaMainTovarBonus());
	}
}
