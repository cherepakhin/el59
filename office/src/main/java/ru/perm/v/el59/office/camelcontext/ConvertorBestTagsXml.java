package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import org.apache.camel.Body;
import ru.perm.v.el59.dto.BestTag;
import ru.perm.v.el59.dto.BestTags;
import ru.perm.v.el59.dto.message.MessageBestTags;
import ru.perm.v.el59.dto.message.MessageEntity;

import java.util.logging.Logger;

public class ConvertorBestTagsXml implements IConvertorXML<BestTags> {

//	@Override
//	public String getXML(MessageEntity<BestTags> m) throws Exception {
//		String xml = null;
//		if (m.getEntity() != null && m.getEntity().getTags().size() > 0) {
//			XStream xstream = ConvertorXML.getXStream();
//			xstream.alias("message", MessageBestTags.class);
//			xstream.alias(BestTags.class.getSimpleName(), BestTags.class);
//			xstream.alias(BestTag.class.getSimpleName(), BestTag.class);
//
//			xml = xstream.toXML(m);
//		}
//		return xml;
//	}

	public Object process(@Body Object body) {
		String ret = null;
//TODO: to XML
//		try {
//
//			ret = getXML(body);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Logger.getLogger(this.getClass()).severe(e.getMessage());
//		}
		return ret;
	}

	@Override
	public String getXML(MessageEntity<BestTags> m) throws Exception {
		return null;
	}
}
