package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.db.SetTovar;
import ru.perm.v.el59.office.dto.SetTovarDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.iproviders.ISetTovarProvider;

import com.thoughtworks.xstream.XStream;

public class ConvertorSetTovarXml extends ConvertorXML<SetTovar, SetTovarDTO> {
	private ISetTovarProvider setTovarProvider;

	@Override
	public String getXML(MessageEntity<SetTovar> m) {
		SetTovar o = m.getEntity();
		o = getSetTovarProvider().initialize(o.getN());

		SetTovarDTO dto = getMapper().map(o, SetTovarDTO.class);
		MessageEntity<SetTovarDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(SetTovar.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

	public ISetTovarProvider getSetTovarProvider() {
		return setTovarProvider;
	}

	public void setSetTovarProvider(ISetTovarProvider setTovarProvider) {
		this.setTovarProvider = setTovarProvider;
	}
}
