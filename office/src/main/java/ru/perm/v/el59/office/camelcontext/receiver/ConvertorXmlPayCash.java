package ru.perm.v.el59.office.camelcontext.receiver;

import org.apache.log4j.Logger;

import ru.perm.v.el59.office.dto.PayCashDTO;
import ru.perm.v.el59.office.dto.message.MessagePayCashDTO;
import ru.perm.v.el59.office.shopmodel.PayCash;

import com.thoughtworks.xstream.XStream;

public class ConvertorXmlPayCash extends
		ConvertorXmlPayment<PayCashDTO, PayCash> {
	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayCashDTO.class);
		xstream.aliasField("command", MessagePayCashDTO.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayCashDTO dto = message.getEntity();
			Logger.getLogger(this.getClass()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayCash payCash = new PayCash();
			payCash = super.fillFromDTO(dto, payCash, message.getShopCod());
			doMessage(payCash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

}
