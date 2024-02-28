package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.office.iproviders.shopmodel.IPaymentProvider;
import ru.perm.v.el59.office.shopmodel.Payment;

public class PaymentProvider extends APaymentProvider<Payment, Long> implements
		IPaymentProvider {

	public PaymentProvider(Class<Payment> type) {
		super(type);
	}
}
