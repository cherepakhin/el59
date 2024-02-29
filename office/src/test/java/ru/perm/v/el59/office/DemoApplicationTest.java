package ru.perm.v.el59.office;

import org.junit.jupiter.api.Test;
import ru.perm.v.el59.office.db.Contragent;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
class DemoApplicationTest {

//	@Autowired
//	private MyService myService;
//
//	@Test
//	void contextLoads() {
//		assertNotNull(myService.message());
//	}

    @Test
    void simple() {
        String s1 = "s1";
        String s2 = "s1";
        assertEquals(s1, s2);
        Contragent contragent = new Contragent();
    }

    class BaseA {
        private Long n = 0L;

        public Long getN() {
            return n;
        }

        public void setN(Long n) {
            this.n = n;
        }
    }

    class B extends BaseA {

    }

    @Test
    void testInheritN() {
        B b = new B();
        b.setN(1L);
    }
}
