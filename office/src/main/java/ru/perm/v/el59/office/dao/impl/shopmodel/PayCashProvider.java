package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.el59.office.shopmodel.PayCash;
import ru.perm.v.el59.office.iproviders.shopmodel.IPayCashProvider;

public class PayCashProvider extends APaymentProvider<PayCash, Long> implements
		IPayCashProvider {

	public PayCashProvider(Class<PayCash> type) {
		super(type);
	}
}
