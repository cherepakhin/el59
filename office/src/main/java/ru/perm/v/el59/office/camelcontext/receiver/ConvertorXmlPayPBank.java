package ru.perm.v.el59.office.camelcontext.receiver;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.dto.PayPBankDTO;
import ru.perm.v.el59.office.dto.message.MessagePayPBankDTO;
import ru.perm.v.el59.office.shopmodel.PayPBank;

public class ConvertorXmlPayPBank extends
		ConvertorXmlPayment<PayPBankDTO, PayPBank> {
	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayPBankDTO.class);
		xstream.aliasField("command", MessagePayPBankDTO.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayPBankDTO dto = message.getEntity();
			Logger.getLogger(this.getClass().getName()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayPBank payPBank = new PayPBank();
			payPBank = super.fillFromDTO(dto, payPBank, message.getShopCod());
			payPBank.setPlatdoc(dto.getPlatdoc());
			doMessage(payPBank);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

}
