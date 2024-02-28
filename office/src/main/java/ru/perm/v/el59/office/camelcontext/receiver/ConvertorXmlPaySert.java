package ru.perm.v.el59.office.camelcontext.receiver;

import org.apache.log4j.Logger;

import ru.perm.v.el59.office.dto.PaySertDTO;
import ru.perm.v.el59.office.dto.message.MessagePaySertDTO;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypeSertProvider;
import ru.perm.v.el59.office.shopmodel.PaySert;
import ru.perm.v.el59.office.shopmodel.TypeSert;

import com.thoughtworks.xstream.XStream;

public class ConvertorXmlPaySert extends
		ConvertorXmlPayment<PaySertDTO, PaySert> {
	private ITypeSertProvider typeSertProvider;

	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePaySertDTO.class);
		xstream.aliasField("command", MessagePaySertDTO.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PaySertDTO dto = message.getEntity();
			Logger.getLogger(this.getClass()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PaySert paySert = new PaySert();
			paySert = super.fillFromDTO(dto, paySert, message.getShopCod());
			paySert.setTypeSert((TypeSert) getTypeSertProvider().initialize(
					dto.getTypeSert().getN()));
			doMessage(paySert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	public ITypeSertProvider getTypeSertProvider() {
		return typeSertProvider;
	}

	public void setTypeSertProvider(ITypeSertProvider typeSertProvider) {
		this.typeSertProvider = typeSertProvider;
	}

}
