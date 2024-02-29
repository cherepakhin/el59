package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.el59.office.shopmodel.PayCredit;
import ru.perm.v.el59.office.iproviders.shopmodel.IPayCreditProvider;

public class PayCreditProvider extends APaymentProvider<PayCredit, Long>
		implements IPayCreditProvider {

	public PayCreditProvider(Class<PayCredit> type) {
		super(type);
	}
}
