package ru.perm.v.el59.office.dao.impl.shopmodel;


import ru.el59.office.shopmodel.PayBonusCard;
import ru.perm.v.el59.office.iproviders.shopmodel.IPayBonusCardProvider;

public class PayBonusCardProvider extends APaymentProvider<PayBonusCard, Long>
		implements IPayBonusCardProvider {
	public PayBonusCardProvider(Class<PayBonusCard> type) {
		super(type);
	}

}
