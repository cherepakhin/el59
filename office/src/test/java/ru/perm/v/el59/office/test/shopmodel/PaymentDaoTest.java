package ru.perm.v.el59.office.test.shopmodel;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ru.el59.office.critery.PaymentCritery;
import ru.el59.office.iproviders.shopmodel.IPaymentProvider;
import ru.el59.office.shopmodel.Payment;
import ru.perm.v.el59.office.test.model.DaoTest;

public class PaymentDaoTest extends DaoTest<Payment, Long> {

	@Override
	protected String getNameDao() {
		return "paymentDao";
	}

	@Override
	public void read() {
		Long n = getDao().getMax() - 1;
		System.out.println("n:" + n);
		Payment e = getDao().read(n);
		System.out.println("Payment:" + e.getN());
		assertTrue(e.getN().compareTo(n) == 0);
	}

	@Test
	public void getByCritery() {
		IPaymentProvider paymentProvider =(IPaymentProvider) getDao();
		PaymentCritery critery = new PaymentCritery();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 8);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		critery.fromdate=cal.getTime();
		critery.todate=new Date();
		List list = paymentProvider.getByCritery(critery);
		assertTrue(list.size()>0);
	}
}
