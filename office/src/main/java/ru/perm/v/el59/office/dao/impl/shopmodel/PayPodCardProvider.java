package ru.perm.v.el59.office.dao.impl.shopmodel;


import ru.el59.office.iproviders.shopmodel.IPayPodCardProvider;
import ru.el59.office.shopmodel.PayPodCard;

public class PayPodCardProvider extends APaymentProvider<PayPodCard, Long>
		implements IPayPodCardProvider {

	public PayPodCardProvider(Class<PayPodCard> type) {
		super(type);
	}
}
