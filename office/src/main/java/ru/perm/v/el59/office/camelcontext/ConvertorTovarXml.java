package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.dto.TovarDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;

public class ConvertorTovarXml extends ConvertorXML<Tovar, TovarDTO> {
	@Override
	public String getXML(MessageEntity<Tovar> m) {

		Tovar t = m.getEntity();
		TovarDTO dto = getMapper().map(t, TovarDTO.class);
		MessageEntity<TovarDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(Tovar.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
