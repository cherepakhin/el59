package ru.perm.v.el59.office.commerceml.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("РасчетныйСчет")
public class ROrder {
	@XStreamAlias("НомерСчета")
	private String number;
	@XStreamAlias("Банк")
	private Bank bank;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
}
