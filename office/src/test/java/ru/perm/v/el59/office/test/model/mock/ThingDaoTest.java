package ru.perm.v.el59.office.test.model.mock;

import org.junit.jupiter.api.Test;
import ru.perm.v.el59.office.dao.impl.ThingProvider;
import ru.perm.v.el59.office.db.Thing;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

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
