package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.TovarBonusDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.shopmodel.TovarBonus;

public class ConvertorTovarBonusXml extends
		ConvertorXML<TovarBonus, TovarBonusDTO> {
	@Override
	public String getXML(MessageEntity<TovarBonus> m) {
		TovarBonus tovarBonus = m.getEntity();
		TovarBonusDTO dto = getMapper().map(tovarBonus, TovarBonusDTO.class);
		MessageEntity<TovarBonusDTO> mDTO = createMessageDTO(m, dto);

		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(TovarBonus.class.getSimpleName(), m.getEntity()
				.getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}
}
