package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.office.shopmodel.PayPodCardEldorado;
import ru.perm.v.el59.office.iproviders.shopmodel.IPayPodCardEldoradoProvider;

public class PayPodCardEldoradoProvider extends APaymentProvider<PayPodCardEldorado, Long>
		implements IPayPodCardEldoradoProvider {

	public PayPodCardEldoradoProvider(Class<PayPodCardEldorado> type) {
		super(type);
	}
}
