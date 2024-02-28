package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import ru.el59.office.dao.impl.plan.CalculatorZPAcc;
import ru.el59.office.db.Move;
import ru.el59.office.db.Operation;
import ru.el59.office.db.plan.UserZP;

/**
 * Тест калькулятора ЗП для Аксессуаров
 * @author vasi
 *
 */
public class CalculatorZPAccTest {

	private static BigDecimal SUMMAIN = new BigDecimal("1.00");
	private static BigDecimal SUMMAOUT = new BigDecimal("11.00");
	private static BigDecimal PLAN_SUMMAOUT = new BigDecimal("100.00");
	private static BigDecimal DECREASE_K = new BigDecimal("0.50");

	@Test
	public void testAddSummaInOut() {
		Move m = new Move();
		m.setSummain(SUMMAIN);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());
		UserZP u = new UserZP();
		CalculatorZPAcc calculator = new CalculatorZPAcc();
		u=calculator.addSumInOut(u, m);
		assertTrue(u.getSummaOut().compareTo(SUMMAOUT)==0);
		assertTrue(u.getSummaAcc().compareTo(SUMMAOUT)==0);
	}
	
	private Operation getOperation() {
		Operation op = new Operation();
		op.setZnak(-1);
		return op;
	}

	// План по аксам выполнен больше чем на 100%
	// Наценка больше 35%
	private static BigDecimal BIG_K = new BigDecimal("0.11");
	@Test
	public void test_PlanOver100_NacenkaOver35() throws Exception {
		Move m = new Move();
		m.setSummain(SUMMAIN);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());

		UserZP u = new UserZP();
		u.setSummaOut(PLAN_SUMMAOUT);
		u.setSummaAcc(PLAN_SUMMAOUT.multiply(new BigDecimal("0.11")));
		CalculatorZPAcc calculator = new CalculatorZPAcc();
		
		assertTrue(u.getSummaAccBonus().compareTo(BigDecimal.ZERO)==0);
		
		u=calculator.calcZP(u, m);
		
		BigDecimal zp = m.getSummaout().multiply(BIG_K).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaAccBonus().compareTo(zp)==0);
	}

	// План по аксам выполнен больше чем на 100%
	// Наценка меньше 35%
	@Test
	public void test_PlanOver100_NacenkaLess35() throws Exception {
/*		p.setSummaout(PLAN_SUMMAOUT);
		p.setAcc(SUMMAOUT);
		
*/		Move m = new Move();
		m.setSummain(SUMMAOUT);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());

		UserZP u = new UserZP();
		u.setSummaOut(PLAN_SUMMAOUT);
		u.setSummaAcc(SUMMAOUT);
		CalculatorZPAcc calculator = new CalculatorZPAcc();
		
		assertTrue(u.getSummaAccBonus().compareTo(BigDecimal.ZERO)==0);
		
		u=calculator.calcZP(u, m);
		
		BigDecimal zp = m.getSummaout().multiply(BIG_K.multiply(DECREASE_K)).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaAccBonus().compareTo(zp)==0);
	}

	// План по аксам выполнен от 70% до 100%
	// Наценка больше 35%
	private static BigDecimal MIDDLE_K = new BigDecimal("0.05");
	@Test
	public void test_PlanOver70_NacenkaOver35() throws Exception {
/*		p.setSummaout(PLAN_SUMMAOUT);
		p.setAcc(PLAN_SUMMAOUT.multiply(new BigDecimal("0.11")).multiply(new BigDecimal("0.70")));*/
		
		Move m = new Move();
		m.setSummain(SUMMAIN);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());

		UserZP u = new UserZP();
		u.setSummaOut(PLAN_SUMMAOUT);
		u.setSummaAcc(PLAN_SUMMAOUT.multiply(new BigDecimal("0.11")).multiply(new BigDecimal("0.70")));
		CalculatorZPAcc calculator = new CalculatorZPAcc();
		
		assertTrue(u.getSummaAccBonus().compareTo(BigDecimal.ZERO)==0);
		
		u=calculator.calcZP(u, m);

		BigDecimal zp = m.getSummaout().multiply(MIDDLE_K).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaAccBonus().compareTo(zp)==0);
	}

	// План по аксам выполнен от 70% до 100%
	// Наценка меньше 35%
	@Test
	public void test_PlanOver70_NacenkaLess35() throws Exception {
		Move m = new Move();
		m.setSummain(SUMMAOUT);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());

		UserZP u = new UserZP();
		u.setSummaOut(PLAN_SUMMAOUT);
		u.setSummaAcc(PLAN_SUMMAOUT.multiply(new BigDecimal("0.11")).multiply(new BigDecimal("0.70")));
		CalculatorZPAcc calculator = new CalculatorZPAcc();
		
		assertTrue(u.getSummaAccBonus().compareTo(BigDecimal.ZERO)==0);
		
		u=calculator.calcZP(u, m);
		
		BigDecimal zp = m.getSummaout().multiply(MIDDLE_K.multiply(DECREASE_K)).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaAccBonus().compareTo(zp)==0);
	}

	// План по аксам меньше 70%
	// Наценка больше 35%
	private static BigDecimal SMALL_K = new BigDecimal("0.02");
	@Test
	public void test_PlanLess70_NacenkaOver35() throws Exception {
/*		p.setSummaout(PLAN_SUMMAOUT);
		p.setAcc(PLAN_SUMMAOUT.multiply(new BigDecimal("0.11")).multiply(new BigDecimal("0.69")));*/
		
		Move m = new Move();
		m.setSummain(SUMMAIN);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());

		UserZP u = new UserZP();
		CalculatorZPAcc calculator = new CalculatorZPAcc();
		
		assertTrue(u.getSummaAcc().compareTo(BigDecimal.ZERO)==0);
		assertTrue(u.getSummaAccBonus().compareTo(BigDecimal.ZERO)==0);
		
		u=calculator.calcZP(u, m);
		
		BigDecimal zp = m.getSummaout().multiply(SMALL_K).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaAccBonus().compareTo(zp)==0);
	}

	// План по аксам меньше 70%
	// Наценка больше 35%
	@Test
	public void test_PlanLess70_NacenkaLess35() throws Exception {
/*		p.setSummaout(PLAN_SUMMAOUT);
		p.setAcc(PLAN_SUMMAOUT.multiply(new BigDecimal("0.11")).multiply(new BigDecimal("0.69")));*/
		
		Move m = new Move();
		m.setSummain(SUMMAOUT);
		m.setSummaout(SUMMAOUT);
		m.setOperation(getOperation());

		UserZP u = new UserZP();
		CalculatorZPAcc calculator = new CalculatorZPAcc();
		
		assertTrue(u.getSummaAcc().compareTo(BigDecimal.ZERO)==0);
		assertTrue(u.getSummaAccBonus().compareTo(BigDecimal.ZERO)==0);
		
		u=calculator.calcZP(u, m);
		
		BigDecimal zp = m.getSummaout().multiply(SMALL_K.multiply(DECREASE_K)).setScale(2, RoundingMode.HALF_UP);
		System.out.println(zp);
		assertTrue(u.getSummaAccBonus().compareTo(zp)==0);
	}
}
