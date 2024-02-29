package ru.perm.v.el59.office.util;

import java.util.logging.Logger; 
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.emailer.IEmailer;

public class SMSSenderThrowEmail implements ISMSSender {
	private IEmailer emailer;
	private String smsEmailGate = "";
	private String login = "";
	private String pass = "";

	@Override
	public String send(String message, String phone, Shop shop) {
		String sender = "el59.ru";
		// Для Лидера
		if (shop != null && shop.getCod().compareTo("07443") == 0) {
			sender = "Eldorado.";
		}
		// Для реала раскоментировать
		 String clearedPhone = clearPhone(phone);
		// Для тестов
//		String clearedPhone="79125831975";
		if (clearedPhone.length() != 11) {
			Logger.getLogger(this.getClass()).info(String.format("Ошибочный номер телефона.Длина % не 11 символов", phone));
		} else {
			String mesEmail = String.format("%s:%s:0:0:0,0,%s:%s:%s",
					getLogin(), getPass(), sender, clearedPhone, message);
			getEmailer().sendSimpleMail(getSmsEmailGate(), mesEmail, "");
		}
		return "";
	}

	/**
	 * Очистка от всяких разных символов
	 * 
	 * @param phone
	 * @return
	 */
	private String clearPhone(String phone) {
		phone = phone.replace("(", "");
		phone = phone.replace(")", "");
		phone = phone.replace(" ", "");
		phone = phone.replace("-", "");
		phone = phone.replace("+", "");
		return phone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public IEmailer getEmailer() {
		return emailer;
	}

	public void setEmailer(IEmailer emailer) {
		this.emailer = emailer;
	}

	public String getSmsEmailGate() {
		return smsEmailGate;
	}

	public void setSmsEmailGate(String smsEmailGate) {
		this.smsEmailGate = smsEmailGate;
	}

}
