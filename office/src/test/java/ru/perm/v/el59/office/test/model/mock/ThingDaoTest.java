package ru.perm.v.el59.office.test.model.mock;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ru.el59.office.dao.impl.ThingProvider;
import ru.el59.office.dao.impl.TovarProvider;
import ru.el59.office.db.Thing;
import ru.el59.office.db.Tovar;

public class ThingDaoTest {

//	private TovarProvider tovarProvider;

	private class ThingProviderStub extends ThingProvider {

		public ThingProviderStub(Class<Thing> type) {
			super(type);
		}

		@Override
		public List<Thing> getAll() {
			ArrayList<Thing> ret = new ArrayList<Thing>();
			Thing t = new Thing();
			t.setName("с/ф");
			t.setNameFull("Смартфон");
			ret.add(t);
			
			t = new Thing();
			t.setName("м/т");
			t.setNameFull("Мобильный телефон");
			ret.add(t);

			return ret;
		}

	}
/*	@Before
	public void setUp() {
		tovarProvider = spy(new TovarProvider(Tovar.class));
	}
*/
	@Test
	public void testCreateFullDescription() throws Exception {
		ThingProviderStub thingProvider = spy(new ThingProviderStub(Thing.class));
		String newName = thingProvider.createFullDescription("с/ф Alcatel PIXI");
		System.out.println(newName);
		String testName = "Смартфон Alcatel PIXI";
		assertTrue(newName.equals(testName));
	}
}
