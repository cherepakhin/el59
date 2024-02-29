package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.ContragentDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.db.Contragent;

public class ConvertorContragentXml extends
		ConvertorXML<Contragent, ContragentDTO> {

	@Override
	public String getXML(MessageEntity<Contragent> m) {
		Contragent contragent = m.getEntity();

		ContragentDTO dto = getMapper().map(contragent, ContragentDTO.class);

		dto.setAddress(contragent.getAddress());
		if (contragent.getGroupContragent() != null) {
			dto.setGroupContragent(contragent.getGroupContragent().getName());
		}
		dto.setInfo(contragent.getInfo());
		dto.setName(contragent.getName());

		MessageEntity<ContragentDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(Contragent.class.getSimpleName(), m.getEntity()
				.getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
