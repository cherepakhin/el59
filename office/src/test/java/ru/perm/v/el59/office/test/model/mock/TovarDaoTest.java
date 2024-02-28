package ru.perm.v.el59.office.test.model.mock;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import ru.el59.office.dao.impl.TovarProvider;
import ru.el59.office.db.Tovar;

public class TovarDaoTest {

//	private TovarProvider tovarProvider;

	private class TovarProviderStub extends TovarProvider {

		public TovarProviderStub(Class<Tovar> type) {
			super(type);
		}

		@Override
		public void update(Tovar o) throws Exception {
		}
		
		
		
	}
/*	@Before
	public void setUp() {
		tovarProvider = spy(new TovarProvider(Tovar.class));
	}
*/
	@Test
	public void testUpdateIfCenaSupplierIsNull() throws Exception {
		TovarProviderStub tovarProviderStub = spy(new TovarProviderStub(Tovar.class));
		Tovar tovar = new Tovar();
		tovar.setCenaSupplier(null);
		Date date = new Date();
		Tovar tovarRet = tovarProviderStub.setCenaSupplier(tovar, new BigDecimal("1.00"), date);
		assertTrue(tovarRet.getCenaSupplier().compareTo(new BigDecimal("1.00"))==0);
		assertTrue(tovarRet.getDateCenaSupplier().compareTo(date)==0);
		verify(tovarProviderStub, times(1)).update(tovar);
	}
	
	@Test
	public void testUpdateIfCenaSupplierLargerCenaSupplierTovar() throws Exception {
		TovarProviderStub tovarProviderStub = spy(new TovarProviderStub(Tovar.class));
		Date date = new Date();

		Tovar tovar = new Tovar();
		tovar.setCenaSupplier(new BigDecimal("1.00"));
		tovar.setDateCenaSupplier(date);
		
		Tovar tovarRet = tovarProviderStub.setCenaSupplier(tovar, new BigDecimal("2.00"), date);
		assertTrue(tovarRet.getCenaSupplier().compareTo(new BigDecimal("1.00"))==0);
		assertTrue(tovarRet.getDateCenaSupplier().compareTo(date)==0);
		verify(tovarProviderStub, times(0)).update(tovar);
	}

	@Test
	public void testUpdateIfCenaSupplierLessCenaSupplierTovar() throws Exception {
		TovarProviderStub tovarProviderStub = spy(new TovarProviderStub(Tovar.class));
		Date date = new Date();

		Tovar tovar = new Tovar();
		tovar.setCenaSupplier(new BigDecimal("2.00"));
		tovar.setDateCenaSupplier(date);
		
		Tovar tovarRet = tovarProviderStub.setCenaSupplier(tovar, new BigDecimal("1.00"), date);
		assertTrue(tovarRet.getCenaSupplier().compareTo(new BigDecimal("1.00"))==0);
		assertTrue(tovarRet.getDateCenaSupplier().compareTo(date)==0);
		verify(tovarProviderStub, times(1)).update(tovar);
	}

	@Test
	public void testUpdateIfDateCenaSupplierLessDateCenaSupplierTovar() throws Exception {
		TovarProviderStub tovarProviderStub = spy(new TovarProviderStub(Tovar.class));
		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Tovar tovar = new Tovar();
		tovar.setCenaSupplier(new BigDecimal("1.00"));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date beforeDate = cal.getTime();
		tovar.setDateCenaSupplier(beforeDate);
		
		Tovar tovarRet = tovarProviderStub.setCenaSupplier(tovar, new BigDecimal("2.00"), date);
		assertTrue(tovarRet.getCenaSupplier().compareTo(new BigDecimal("2.00"))==0);
		assertTrue(tovarRet.getDateCenaSupplier().compareTo(date)==0);
		verify(tovarProviderStub, times(1)).update(tovar);
	}

	@Test
	public void testUpdateIfDateCenaSupplierLargerDateCenaSupplierTovar() throws Exception {
		TovarProviderStub tovarProviderStub = spy(new TovarProviderStub(Tovar.class));
		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Tovar tovar = new Tovar();
		tovar.setCenaSupplier(new BigDecimal("2.00"));
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date afterDate = cal.getTime();
		tovar.setDateCenaSupplier(afterDate);
		
		Tovar tovarRet = tovarProviderStub.setCenaSupplier(tovar, new BigDecimal("1.00"), date);
		assertTrue(tovarRet.getCenaSupplier().compareTo(new BigDecimal("2.00"))==0);
		assertTrue(tovarRet.getDateCenaSupplier().compareTo(afterDate)==0);
		verify(tovarProviderStub, times(0)).update(tovar);
	}

}
