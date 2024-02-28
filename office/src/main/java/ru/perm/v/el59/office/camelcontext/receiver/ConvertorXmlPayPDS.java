package ru.perm.v.el59.office.camelcontext.receiver;

import org.apache.log4j.Logger;

import ru.perm.v.el59.office.dto.PayPDSDTO;
import ru.perm.v.el59.office.dto.message.MessagePayPDSDTO;
import ru.perm.v.el59.office.shopmodel.PayPDS;

import com.thoughtworks.xstream.XStream;

public class ConvertorXmlPayPDS extends ConvertorXmlPayment<PayPDSDTO, PayPDS> {
	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayPDSDTO.class);
		xstream.aliasField("command", MessagePayPDSDTO.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayPDSDTO dto = message.getEntity();
			Logger.getLogger(this.getClass()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayPDS payPDS = new PayPDS();
			payPDS = super.fillFromDTO(dto, payPDS, message.getShopCod());
			doMessage(payPDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

}
