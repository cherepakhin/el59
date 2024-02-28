package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.dto.BonusCardDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.shopmodel.BonusCard;

import com.thoughtworks.xstream.XStream;

public class ConvertorBonusCardXml extends
		ConvertorXML<BonusCard, BonusCardDTO> {
	@Override
	public String getXML(MessageEntity<BonusCard> m) {
		BonusCard bonusCard = m.getEntity();
		BonusCardDTO dto = getMapper().map(bonusCard, BonusCardDTO.class);

		MessageEntity<BonusCardDTO> mDTO = createMessageDTO(m, dto);

		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(BonusCard.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
