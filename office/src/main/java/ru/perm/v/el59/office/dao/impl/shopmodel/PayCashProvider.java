package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.office.iproviders.shopmodel.IPayCashProvider;
import ru.perm.v.el59.office.shopmodel.PayCash;

public class PayCashProvider extends APaymentProvider<PayCash, Long> implements
		IPayCashProvider {

	public PayCashProvider(Class<PayCash> type) {
		super(type);
	}
}
