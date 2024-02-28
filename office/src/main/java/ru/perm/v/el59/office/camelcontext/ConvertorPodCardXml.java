package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.dto.PodCardDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.shopmodel.PodCard;

import com.thoughtworks.xstream.XStream;

public class ConvertorPodCardXml extends ConvertorXML<PodCard, PodCardDTO> {
	@Override
	public String getXML(MessageEntity<PodCard> m) {
		PodCard podCard = m.getEntity();
		PodCardDTO dto = getMapper().map(podCard, PodCardDTO.class);

		MessageEntity<PodCardDTO> mDTO = createMessageDTO(m, dto);

		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(PodCard.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
