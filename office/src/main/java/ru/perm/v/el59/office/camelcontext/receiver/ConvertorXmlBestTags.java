package ru.perm.v.el59.office.camelcontext.receiver;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.BestTag;
import ru.perm.v.el59.dto.BestTags;
import ru.perm.v.el59.dto.message.MessageBestTags;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.dto.message.TypeCommand;
import ru.perm.v.el59.office.analisebest.ProtocolForTag;

import java.util.List;
import java.util.logging.Logger;

public class ConvertorXmlBestTags extends ConvertorFromXML<BestTags, BestTags> {

	private ProtocolForTag protocolForTag;
	
	@Override
	public String fromXmlToMessageEntity(Object body) {
/*		Exchange exch =(Exchange) body;
		Message in = exch.getIn();*/
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessageBestTags.class);
		xstream.alias("BestTag", BestTag.class);
		xstream.aliasField("command", MessageBestTags.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = (MessageEntity<BestTags>) xstream.fromXML(xml);
			message = getMessageFromXml(xml);
			BestTags dto = message.getEntity();
			List<BestTag> protocolBestTags = getProtocolForTag().analise(dto.getTags(), message.getShopCod());

			MessageBestTags messageSend = new MessageBestTags();
			messageSend.setShopCod(message.getShopCod());
			messageSend.setTypeCommand(TypeCommand.CREATE);
			messageSend.setClassName(BestTags.class.getSimpleName());
			messageSend.setEntity(new BestTags());
			if (protocolBestTags.size() > 0) {
				messageSend.getEntity().setTags(protocolBestTags);
			} else {
				Logger.getLogger(this.getClass()).info(
						String.format("Нет изменений цен для магазина %s",
								message.getShopCod()));
			}
			
			// Logger.getLogger(this.getClass()).info(dto.toString());
			// in.setBody(message);
			if(protocolBestTags.size()>0) {
				String xmlOut=xstream.toXML(messageSend);
				return xmlOut;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProtocolForTag getProtocolForTag() {
		return protocolForTag;
	}

	public void setProtocolForTag(ProtocolForTag protocolForTag) {
		this.protocolForTag = protocolForTag;
	}
}
