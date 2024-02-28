package ru.perm.v.el59.office.camelcontext.receiver;

import org.apache.camel.Body;
import org.apache.log4j.Logger;

import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.wscommand.ICommander;

import com.thoughtworks.xstream.XStream;

public class ConvertorFromXmlDefault extends ConvertorFromXML {
	
	public String fromXmlToMessageEntity(@Body Object body) throws Exception {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessageEntity.class);
		xstream.aliasField("command", MessageEntity.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		message = (MessageEntity) xstream.fromXML(xml);
		Object t = message.getEntity();
		Logger.getLogger(this.getClass()).info(
				String.format("%s %s %s", message.getShopCod(),
						message.getTypeCommand(), message.getClassName()));
		doMessage(t);
		return xml;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public ICommander getCommander() {
		return commander;
	}

	public void setCommander(ICommander commander) {
		this.commander = commander;
	}

}
