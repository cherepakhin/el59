package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.db.Dolgnost;
import ru.perm.v.el59.office.dto.DolgnostDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.iproviders.IDolgnostProvider;

import com.thoughtworks.xstream.XStream;

public class ConvertorDolgnostXml extends ConvertorXML<Dolgnost, DolgnostDTO> {
	private IDolgnostProvider dolgnostProvider;

	@Override
	public String getXML(MessageEntity<Dolgnost> m) {
		Dolgnost dolgnost = m.getEntity();
		dolgnost = (Dolgnost) getDolgnostProvider().initialize(dolgnost.getN());
		DolgnostDTO dto = getMapper().map(dolgnost, DolgnostDTO.class);

		MessageEntity<DolgnostDTO> mDTO = createMessageDTO(m, dto);

		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(Dolgnost.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

	public IDolgnostProvider getDolgnostProvider() {
		return dolgnostProvider;
	}

	public void setDolgnostProvider(IDolgnostProvider dolgnostProvider) {
		this.dolgnostProvider = dolgnostProvider;
	}

}
