package ru.perm.v.el59.office.test.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ru.el59.office.db.TovarInfo;
import ru.el59.office.parser.AParserSite;

public class AParserTest {

	class TestParser extends AParserSite {

		@Override
		public String getCHARSET_SITE() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getSampleLink() {
			return null;
		};
		
		public String testClearHTML(String s) {
			return clearHTML(s);
		}

		@Override
		protected TovarInfo fillInfo() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected TovarInfo fillPhoto() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Test
	public void clearHTML() {
		String testString = "15.6&quot;";
		String expectedString = "15.6 ";
		TestParser parser= new TestParser();
		String returnString = parser.testClearHTML(testString);
		System.out.println(returnString);
		assertTrue(returnString.equals(expectedString));
	}
}
