package ru.perm.v.el59.office.camelcontext;

import org.apache.camel.Body;
import ru.perm.v.el59.dto.message.MessageEntity;

import java.util.Map;
import java.util.logging.Logger;

public class ConvertorFromOfficeDB {
	private IConvertorXML defaultConvertor;
	private Map<String, IConvertorXML> mapConvertor;

	public String getXML(@Body Object body) throws Exception {
		MessageEntity m = (MessageEntity) body;
		if (getMapConvertor().containsKey(m.getClassName())) {
			IConvertorXML conv = getMapConvertor().get(m.getClassName());
			return conv.getXML(m);
		} else {
			try {
				String s = defaultConvertor.getXML(m);
				return s;
			} catch (Exception e) {
				Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
				return null;
			}
		}
	}

	public Map<String, IConvertorXML> getMapConvertor() {
		return mapConvertor;
	}

	public void setMapConvertor(Map<String, IConvertorXML> mapConvertor) {
		this.mapConvertor = mapConvertor;
	}

	public IConvertorXML getDefaultConvertor() {
		return defaultConvertor;
	}

	public void setDefaultConvertor(IConvertorXML defaultConvertor) {
		this.defaultConvertor = defaultConvertor;
	}
}
