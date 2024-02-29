package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.GroupTovarDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.db.GroupTovar;

public class ConvertorGroupTovarXml extends
		ConvertorXML<GroupTovar, GroupTovarDTO> {

	@Override
	public String getXML(MessageEntity<GroupTovar> m) {
		GroupTovar g = m.getEntity();
		GroupTovarDTO dto_g = new GroupTovarDTO();
		dto_g.setCod(g.getCod());
		dto_g.setBestcod(g.getBest());
		// dto_g.setM_level(g.getM_level());
		dto_g.setName(g.getName());
		dto_g.setParent(g.getParent().getCod());
		MessageEntity<GroupTovarDTO> mDTO = createMessageDTO(m, dto_g);
		XStream xstream = getXStream();
		xstream.aliasField("level", GroupTovarDTO.class, "m_level");
		m.setShopCod("*");
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
