package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.TypeStockDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.db.TypeStock;

public class ConvertorTypeStockXml extends
		ConvertorXML<TypeStock, TypeStockDTO> {

	@Override
	public String getXML(MessageEntity<TypeStock> m) {
		m.setClassName("TypeStock");
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias("TypeStock", m.getEntity().getClass());
		String xml = xstream.toXML(m);
		return xml;
	}

}
