package ru.perm.v.el59.office.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ru.el59.office.util.CalculatorQtyThread;

public class CalculatorQtyThreadTester {

	@Test
	public void test_10_4_4() {
		CalculatorQtyThread c = new CalculatorQtyThread();
		c.calc(10, 4, 4);
		int qThread = c.getQtyThread();
		assertTrue(qThread==3);
		int qTTovar = c.getQtyTTovarByThread();
		assertTrue(qTTovar==4);
	}

	@Test
	public void test_3_4_4() {
		CalculatorQtyThread c = new CalculatorQtyThread();
		c.calc(3, 4, 4);
		int qThread = c.getQtyThread();
		assertTrue(qThread==1);
		int qTTovar = c.getQtyTTovarByThread();
		assertTrue(qTTovar==4);
	}

	@Test
	public void test_16_4_4() {
		CalculatorQtyThread c = new CalculatorQtyThread();
		c.calc(16, 4, 4);
		int qThread = c.getQtyThread();
		assertTrue(qThread==4);
		int qTTovar = c.getQtyTTovarByThread();
		assertTrue(qTTovar==4);
	}

	@Test
	public void test_17_4_4() {
		CalculatorQtyThread c = new CalculatorQtyThread();
		c.calc(17, 4, 4);
		int qThread = c.getQtyThread();
		System.out.println(qThread);
		assertTrue(qThread==4);
		int qTTovar = c.getQtyTTovarByThread();
		assertTrue(qTTovar==4);
	}
}
