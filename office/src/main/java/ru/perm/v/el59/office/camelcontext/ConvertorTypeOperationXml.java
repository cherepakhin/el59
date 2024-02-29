package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.office.dto.TypeOperationDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.shopmodel.TypeOperation;

public class ConvertorTypeOperationXml extends
		ConvertorXML<TypeOperation, TypeOperationDTO> {
	@Override
	public String getXML(MessageEntity<TypeOperation> m) {
		TypeOperation typeOperation = m.getEntity();
		TypeOperationDTO dto = getMapper().map(typeOperation,
				TypeOperationDTO.class);
		MessageEntity<TypeOperationDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(TypeOperation.class.getSimpleName(), m.getEntity()
				.getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}
}
