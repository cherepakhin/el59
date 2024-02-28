package ru.perm.v.el59.office.camelcontext.receiver;

 import org.apache.log4j.Logger;

import ru.perm.v.el59.office.dto.PayPodCardDTO;
import ru.perm.v.el59.office.dto.message.MessagePayPodCardDTO;
import ru.perm.v.el59.office.shopmodel.PayPodCard;

import com.thoughtworks.xstream.XStream;

public class ConvertorXmlPayPodCard extends
		ConvertorXmlPayment<PayPodCardDTO, PayPodCard> {
	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayPodCardDTO.class);
		xstream.aliasField("command", MessagePayPodCardDTO.class,
				"typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayPodCardDTO dto = message.getEntity();
			Logger.getLogger(this.getClass()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayPodCard pay = new PayPodCard();
			pay = super.fillFromDTO(dto, pay,
					message.getShopCod());
			doMessage(pay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

}
