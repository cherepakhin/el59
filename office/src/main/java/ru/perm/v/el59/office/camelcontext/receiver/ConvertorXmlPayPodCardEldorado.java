package ru.perm.v.el59.office.camelcontext.receiver;

 import org.apache.log4j.Logger;

import ru.perm.v.el59.office.dto.PayPodCardEldoradoDTO;
import ru.perm.v.el59.office.dto.message.MessagePayPodCardEldoradoDTO;
import ru.perm.v.el59.office.shopmodel.PayPodCardEldorado;

import com.thoughtworks.xstream.XStream;

public class ConvertorXmlPayPodCardEldorado extends
		ConvertorXmlPayment<PayPodCardEldoradoDTO, PayPodCardEldorado> {
	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayPodCardEldoradoDTO.class);
		xstream.aliasField("command", MessagePayPodCardEldoradoDTO.class,
				"typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayPodCardEldoradoDTO dto = message.getEntity();
			Logger.getLogger(this.getClass()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayPodCardEldorado pay = new PayPodCardEldorado();
			pay = super.fillFromDTO(dto, pay,
					message.getShopCod());
			doMessage(pay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

}
