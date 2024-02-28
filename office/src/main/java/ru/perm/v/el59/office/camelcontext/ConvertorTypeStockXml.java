package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.db.TypeStock;
import ru.perm.v.el59.office.dto.TypeStockDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;

import com.thoughtworks.xstream.XStream;

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
