package ru.perm.v.el59.office.calc;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class BigDecimalTest {

	@Test
	public void testScale() {
		BigDecimal b = new BigDecimal("10.4444444");
		BigDecimal c = b.setScale(0,RoundingMode.HALF_UP);

		assertTrue(c.compareTo(new BigDecimal(10))==0);
	}

	@Test
	public void testZero() {
		BigDecimal b = new BigDecimal("0.4444444");
		BigDecimal c = b.setScale(0,RoundingMode.HALF_UP);
		assertTrue(c.compareTo(BigDecimal.ZERO)==0);
	}
}
