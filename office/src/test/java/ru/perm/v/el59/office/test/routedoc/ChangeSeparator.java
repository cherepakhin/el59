package ru.perm.v.el59.office.test.routedoc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChangeSeparator {

	@Test
	public void test() {
		String s = "ss\\ss\\";
		s=s.replace("\\", "/");
		System.out.println(s);
		assertTrue(s.equals("ss/ss/"));
	}

}
