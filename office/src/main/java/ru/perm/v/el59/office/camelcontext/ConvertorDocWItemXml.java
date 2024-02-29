package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.DocWItemDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.db.web.DocWItem;
import ru.perm.v.el59.office.iproviders.web.IDocWItemProvider;

public class ConvertorDocWItemXml extends ConvertorXML<DocWItem, DocWItemDTO> {
	private IDocWItemProvider docWItemProvider;

	@Override
	public String getXML(MessageEntity<DocWItem> m) {
		DocWItem docWItem = m.getEntity();
		DocWItemDTO dto = getMapper().map(docWItem, DocWItemDTO.class);

		MessageEntity<DocWItemDTO> mDTO = createMessageDTO(m, dto);
		mDTO.setShopCod(docWItem.getDocw().getShop().getCod());
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(DocWItem.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

	public IDocWItemProvider getDocWItemProvider() {
		return docWItemProvider;
	}

	public void setDocWItemProvider(IDocWItemProvider docWItemProvider) {
		this.docWItemProvider = docWItemProvider;
	}
}
