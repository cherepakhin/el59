package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import ru.el59.office.dao.impl.plan.CalculatorZPProtection;
import ru.el59.office.db.Move;
import ru.el59.office.db.Operation;
import ru.el59.office.db.plan.UserZP;

public class CalculatorZPProtectionTest {

	private static BigDecimal SUMMAOUT = new BigDecimal("2.00");
	private static BigDecimal MIN_K_BONUS = new BigDecimal("0.10");
	private static BigDecimal MAX_K_BONUS = new BigDecimal("0.15");
	private static BigDecimal PLAN_PDS = new BigDecimal("0.03");

	@Test
	public void testAddSummaInOut() {
		Move m = new Move();
		m.setOperation(getOperation());
		m.setSummaout(SUMMAOUT);
		
		UserZP u = new UserZP();
		CalculatorZPProtection calculator = new CalculatorZPProtection();
		u=calculator.addSumInOut(u, m);
	
		assertTrue(u.getSummaOut().compareTo(SUMMAOUT)==0);
		assertTrue(u.getSummaPDS().compareTo(SUMMAOUT)==0);
	}

	private Operation getOperation() {
		Operation op = new Operation();
		op.setZnak(-1);
		return op;
	}
	
	// План выполнен больше 100%
	// В ЗП должно упасть 15%
	@Test
	public void testMaxK() throws Exception {
		Move m = new Move();
		m.setOperation(getOperation());
		m.setSummaout(SUMMAOUT);
		UserZP u = new UserZP();
		u.setSummaOut(SUMMAOUT);
		u.setSummaPDS(SUMMAOUT.multiply(PLAN_PDS));
		CalculatorZPProtection calculator = new CalculatorZPProtection();
		u=calculator.calcZP(u, m);
		// Насенка больше MIN_NACENKA(50%)
		BigDecimal zp = m.getSummaout().multiply(MAX_K_BONUS).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaPDSBonus().compareTo(zp)==0);
	}

	// План выполнен меньше 100%
	// В ЗП должно упасть 10%
	@Test
	public void testMinK() throws Exception {
		Move m = new Move();
		m.setOperation(getOperation());
		m.setSummaout(SUMMAOUT);
		UserZP u = new UserZP();
		u.setSummaOut(SUMMAOUT);
		// План для ЗАЩИТ 3%. Делаю выполнение меньше.
		u.setSummaPDS(SUMMAOUT.multiply(new BigDecimal("0.02")));
		CalculatorZPProtection calculator = new CalculatorZPProtection();
		u=calculator.calcZP(u, m);
		// Насенка больше MIN_NACENKA(50%)
		BigDecimal zp = m.getSummaout().multiply(MIN_K_BONUS).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaPDSBonus().compareTo(zp)==0);
	}
}
