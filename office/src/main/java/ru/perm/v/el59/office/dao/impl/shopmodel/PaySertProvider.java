package ru.perm.v.el59.office.dao.impl.shopmodel;


import ru.el59.office.shopmodel.PaySert;
import ru.perm.v.el59.office.iproviders.shopmodel.IPaySertProvider;

public class PaySertProvider extends APaymentProvider<PaySert, Long> implements
		IPaySertProvider {

	public PaySertProvider(Class<PaySert> type) {
		super(type);
	}
}
