package ru.perm.v.el59.office.test.model.plan;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import ru.el59.office.dao.impl.plan.CalculatorZPMainTovar;
import ru.el59.office.db.Move;
import ru.el59.office.db.Operation;
import ru.el59.office.db.plan.UserZP;

public class CalculatorZPMainTovarTest {

	private static BigDecimal SUMMA100 = new BigDecimal("100.00");
	private static BigDecimal SUMMA80 = new BigDecimal("80.00"); // для 25%
	private static BigDecimal SUMMA83 = new BigDecimal("83.33"); // для 20%

	@Test
	public void testAddSummaInOut() {
		Move m = new Move();
		m.setSummaout(SUMMA100);
		m.setOperation(getOperation());
		UserZP u = new UserZP();
		CalculatorZPMainTovar calculator = new CalculatorZPMainTovar();
		u = calculator.addSumInOut(u, m);
		assertTrue(u.getSummaOut().compareTo(SUMMA100) == 0);
		assertTrue(u.getSummaMainTovar().compareTo(SUMMA100) == 0);
	}

	private Operation getOperation() {
		Operation op = new Operation();
		op.setZnak(-1);
		return op;
	}

	@Test
	public void testMIN_K() throws Exception {
		CalculatorZPMainTovar calculator = new CalculatorZPMainTovar();

		Move m = new Move();
		m.setSummain(SUMMA83.add(BigDecimal.ONE));
		m.setOperation(getOperation());
		// Уменьшаю наценку меньше минимальной(20%)
		m.setSummaout(SUMMA100);

		UserZP u = new UserZP();

		u = calculator.calcZP(u, m);
		BigDecimal zp = m.getSummaout()
				.multiply(calculator.getPercentMin())
				.setScale(2, RoundingMode.HALF_UP);
		print("testMIN_K()", zp, u.getSummaMainTovarBonus());
		assertTrue(u.getSummaMainTovarBonus().compareTo(zp) == 0);
	}

	@Test
	public void testSTANDART_K() throws Exception {
		CalculatorZPMainTovar calculator = new CalculatorZPMainTovar();

		Move m = new Move();
		m.setOperation(getOperation());
		m.setSummain(SUMMA80);
		m.setQty(BigDecimal.ONE);
		// Уменьшаю наценку меньше максимальной
		m.setPrice(SUMMA100.subtract(BigDecimal.ONE));
		m.setSummaout(SUMMA100.subtract(BigDecimal.ONE));

		UserZP u = new UserZP();

		u = calculator.calcZP(u, m);
		BigDecimal zp = m.getSummaout()
				.multiply(calculator.getPercentStandart())
				.setScale(2, RoundingMode.HALF_UP);
		print("testSTANDART_K()", zp, u.getSummaMainTovarBonus());
		assertTrue(u.getSummaMainTovarBonus().compareTo(zp) == 0);
	}

	@Test
	public void testMAX_K() throws Exception {
		CalculatorZPMainTovar calculator = new CalculatorZPMainTovar();

		Move m = new Move();
		m.setOperation(getOperation());
		m.setQty(BigDecimal.ONE);
		m.setPrice(SUMMA100);
		m.setSummain(SUMMA80);
		// Увеличиваю наценку больше максимальной(25%)
		m.setSummaout(SUMMA100.add(BigDecimal.ONE));

		UserZP u = new UserZP();

		u = calculator.calcZP(u, m);
		BigDecimal zp = m.getSummaout()
				.multiply(calculator.getPercentMax())
				.setScale(2, RoundingMode.HALF_UP);
		print("testMAX_K()", zp, u.getSummaMainTovarBonus());
		assertTrue(u.getSummaMainTovarBonus().compareTo(zp) == 0);
	}
	
	@Test
	public void testMAX_K_WITH_DISCOUNT() throws Exception {
		CalculatorZPMainTovar calculator = new CalculatorZPMainTovar();

		Move m = new Move();
		m.setOperation(getOperation());
		m.setQty(BigDecimal.ONE);
		// Увеличиваю цену прайса на 1 
		m.setPrice(SUMMA100.add(BigDecimal.ONE));
		m.setSummain(SUMMA80);
		// Увеличиваю наценку больше максимальной(25%)
		// Но цена по прайсу больше. Имитация скидки
		m.setSummaout(SUMMA100);

		UserZP u = new UserZP();

		u = calculator.calcZP(u, m);
		BigDecimal zp = m.getSummaout()
				.multiply(calculator.getPercentStandart())
				.setScale(2, RoundingMode.HALF_UP);
		print("testMAX_K_WITH_DISCOUNT()", zp, u.getSummaMainTovarBonus());
		assertTrue(u.getSummaMainTovarBonus().compareTo(zp) == 0);
	}

	private void print(String method, BigDecimal expectedZP,BigDecimal calcZP) {
		System.out.println(String.format("%s expectedZP=%.2f calcZP=%.2f",method,expectedZP,calcZP));
	}
}
