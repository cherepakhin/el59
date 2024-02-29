package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.UserShopDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.db.UserShop;

public class ConvertorUserShopXml extends ConvertorXML<UserShop, UserShopDTO> {

	@Override
	public String getXML(MessageEntity<UserShop> m) {

		UserShop u = m.getEntity();
		UserShopDTO dto = getMapper().map(u, UserShopDTO.class);
		MessageEntity<UserShopDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(UserShop.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
