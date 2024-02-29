package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.el59.office.shopmodel.PayPBank;
import ru.perm.v.el59.office.iproviders.shopmodel.IPayPBankProvider;

public class PayPBankProvider extends APaymentProvider<PayPBank, Long>
		implements IPayPBankProvider {

	public PayPBankProvider(Class<PayPBank> type) {
		super(type);
	}
}
