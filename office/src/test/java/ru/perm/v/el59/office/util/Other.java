package ru.perm.v.el59.office.util;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class Other {

	@Test
	public void test() {
		Date d = new Date();
		System.out.println(String.format("date= %tF", d));
	}

}
