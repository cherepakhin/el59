package ru.perm.v.el59.office.test.analisebest;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {

	@Test
	public void test() {
		String name="aaa07258ddd5555.zip";
		Pattern p = Pattern.compile("(\\d{5})");  
        Matcher m = p.matcher(name);  
		if(m.find()) {
			System.out.println(m.group(0));
		}
		assertTrue(true);
	}

}
