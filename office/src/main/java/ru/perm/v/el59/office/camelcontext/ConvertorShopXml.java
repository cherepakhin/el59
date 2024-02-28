package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.dto.ShopDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;

import com.thoughtworks.xstream.XStream;

public class ConvertorShopXml extends ConvertorXML<Shop, ShopDTO> {

	@Override
	public String getXML(MessageEntity<Shop> m) {

		Shop s = m.getEntity();
		ShopDTO dto = getMapper().map(s, ShopDTO.class);
		MessageEntity<ShopDTO> mDTO = createMessageDTO(m, dto);

		m.setClassName(Shop.class.getSimpleName());
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(Shop.class.getSimpleName(), m.getEntity().getClass());

		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
