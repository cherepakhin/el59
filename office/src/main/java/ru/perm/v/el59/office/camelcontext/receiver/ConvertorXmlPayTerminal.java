package ru.perm.v.el59.office.camelcontext.receiver;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.dto.PayTerminalDTO;
import ru.perm.v.el59.office.dto.message.MessagePayTerminalDTO;
import ru.perm.v.el59.office.shopmodel.PayTerminal;

public class ConvertorXmlPayTerminal extends
		ConvertorXmlPayment<PayTerminalDTO, PayTerminal> {
	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayTerminalDTO.class);
		xstream.aliasField("command", MessagePayTerminalDTO.class,
				"typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayTerminalDTO dto = message.getEntity();
			Logger.getLogger(this.getClass().getName()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayTerminal payTerminal = new PayTerminal();
			payTerminal = super.fillFromDTO(dto, payTerminal,
					message.getShopCod());
			doMessage(payTerminal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

}
