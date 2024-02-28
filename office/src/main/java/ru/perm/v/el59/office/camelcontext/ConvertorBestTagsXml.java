package ru.perm.v.el59.office.camelcontext;

import org.apache.camel.Body;
import org.apache.log4j.Logger;

import ru.perm.v.el59.office.dto.BestTag;
import ru.perm.v.el59.office.dto.BestTags;
import ru.perm.v.el59.office.dto.message.MessageBestTags;
import ru.perm.v.el59.office.dto.message.MessageEntity;

import com.thoughtworks.xstream.XStream;

public class ConvertorBestTagsXml implements IConvertorXML<BestTags> {

	@Override
	public String getXML(MessageEntity<BestTags> m) throws Exception {
		String xml = null;
		if (m.getEntity() != null && m.getEntity().getTags().size() > 0) {
			XStream xstream = ConvertorXML.getXStream();
			xstream.alias("message", MessageBestTags.class);
			xstream.alias(BestTags.class.getSimpleName(), BestTags.class);
			xstream.alias(BestTag.class.getSimpleName(), BestTag.class);

			xml = xstream.toXML(m);
		}
		return xml;
	}

	public Object process(@Body Object body) {
		String ret = null;
		try {
			ret = getXML((MessageEntity<BestTags>) body);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(this.getClass()).error(e);
		}
		return ret;
	}

}
