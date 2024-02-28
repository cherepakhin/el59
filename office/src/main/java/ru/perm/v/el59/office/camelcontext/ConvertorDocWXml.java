package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.db.web.DocW;
import ru.perm.v.el59.office.dto.DocWDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.iproviders.web.IDocWProvider;

import com.thoughtworks.xstream.XStream;

public class ConvertorDocWXml extends ConvertorXML<DocW, DocWDTO> {
	private IDocWProvider docWProvider;

	@Override
	public String getXML(MessageEntity<DocW> m) {
		DocW docW = m.getEntity();
		docW = (DocW) getDocWProvider().initialize(docW.getN());
		DocWDTO dto = getMapper().map(docW, DocWDTO.class);

		MessageEntity<DocWDTO> mDTO = createMessageDTO(m, dto);
		mDTO.setShopCod(docW.getShop().getCod());
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(DocW.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

	public IDocWProvider getDocWProvider() {
		return docWProvider;
	}

	public void setDocWProvider(IDocWProvider docWProvider) {
		this.docWProvider = docWProvider;
	}

}
