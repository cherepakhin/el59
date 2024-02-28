package ru.perm.v.el59.office.dao.impl.shopmodel;

import ru.perm.v.el59.office.iproviders.shopmodel.IPayBonusCardProvider;
import ru.perm.v.el59.office.shopmodel.PayBonusCard;

public class PayBonusCardProvider extends APaymentProvider<PayBonusCard, Long>
		implements IPayBonusCardProvider {
	public PayBonusCardProvider(Class<PayBonusCard> type) {
		super(type);
	}

}
