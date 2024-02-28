package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.dto.office.iproviders.shopmodel.IPayPodCardEldoradoProvider;
import ru.el59.office.shopmodel.PayPodCardEldorado;

public class PayPodCardEldoradoProvider extends APaymentProvider<PayPodCardEldorado, Long>
		implements IPayPodCardEldoradoProvider {

	public PayPodCardEldoradoProvider(Class<PayPodCardEldorado> type) {
		super(type);
	}
}
