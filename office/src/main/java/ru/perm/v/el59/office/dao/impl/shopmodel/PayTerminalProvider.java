package ru.perm.v.el59.office.dao.impl.shopmodel;


import ru.el59.office.shopmodel.PayTerminal;
import ru.perm.v.el59.office.iproviders.shopmodel.IPayTerminalProvider;

public class PayTerminalProvider extends APaymentProvider<PayTerminal, Long>
		implements IPayTerminalProvider {

	public PayTerminalProvider(Class<PayTerminal> type) {
		super(type);
	}
}
