package ru.perm.v.el59.office.camelcontext.receiver;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.dto.SMSDTO;
import ru.perm.v.el59.office.dto.message.MessageSMSDTO;
import ru.perm.v.el59.office.iproviders.shopmodel.ISMSProvider;
import ru.perm.v.el59.office.shopmodel.SMS;
import ru.perm.v.el59.office.util.ISMSSender;

import java.util.Date;

public class ConvertorXmlSMS extends ConvertorFromXML<SMSDTO, SMS> {

	private ISMSSender smsSender;
	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessageSMSDTO.class);
		xstream.aliasField("command", MessageSMSDTO.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			SMSDTO dto = message.getEntity();
			Logger.getLogger(this.getClass().getName()).info(dto.toString());
			SMS sms = fillFromDTO(dto, message.getShopCod());
			if (message.getShopCod() == null) {
				Logger.getLogger(this.getClass().getName()).error(
						"ShopCod in message is null");
			}
			if (message.getTypeCommand() == null) {
				Logger.getLogger(this.getClass().getName()).error(
						"TypeCommand in message is null");
			}
			if (message.getTypeCommand() == null) {
				Logger.getLogger(this.getClass().getName()).error(
						"TypeCommand in message is null");
			}
			if(sms.getPhone()==null || sms.getPhone().isEmpty()) {
				Logger.getLogger(this.getClass().getName()).error(
						"Phone is empty in SMS");
			}
			if(sms.getMessage()==null || sms.getMessage().isEmpty()) {
				Logger.getLogger(this.getClass().getName()).error(
						"Message is empty in SMS");
			}
			Logger.getLogger(this.getClass().getName())
					.info(String
							.format("Shop: %s;Command %s;Nn: %s,phone: %s, message: %s  ",
									message.getShopCod(),
									message.getTypeCommand(), sms.getNn(),
									sms.getPhone(), sms.getMessage()));
			doMessage(sms);
			String ret = getSmsSender().send(sms.getMessage(), sms.getPhone(),sms.getShop());
			if(ret.isEmpty()) {
				sms.setDateSend(new Date());
			} else {
				sms.setErr(ret);
			}
			getDao().update(sms);
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private SMS fillFromDTO(SMSDTO dto, String shopCod) {
		SMS sms = ((ISMSProvider) getDao()).getByDTO(dto, shopCod);
		return sms;
	}

	public ISMSSender getSmsSender() {
		return smsSender;
	}

	public void setSmsSender(ISMSSender smsSender) {
		this.smsSender = smsSender;
	}

}
