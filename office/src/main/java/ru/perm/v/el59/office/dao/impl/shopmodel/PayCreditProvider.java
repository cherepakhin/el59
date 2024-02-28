package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.office.iproviders.shopmodel.IPayCreditProvider;
import ru.perm.v.el59.office.shopmodel.PayCredit;

public class PayCreditProvider extends APaymentProvider<PayCredit, Long>
		implements IPayCreditProvider {

	public PayCreditProvider(Class<PayCredit> type) {
		super(type);
	}
}
