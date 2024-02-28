package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.office.iproviders.shopmodel.IPayPDSProvider;
import ru.perm.v.el59.office.shopmodel.PayPDS;

public class PayPDSProvider extends APaymentProvider<PayPDS, Long> implements
		IPayPDSProvider {

	public PayPDSProvider(Class<PayPDS> type) {
		super(type);
	}
}
