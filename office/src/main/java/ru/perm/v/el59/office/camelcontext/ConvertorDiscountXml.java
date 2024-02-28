package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.dto.DiscountDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.shopmodel.Discount;

import com.thoughtworks.xstream.XStream;

public class ConvertorDiscountXml extends ConvertorXML<Discount, DiscountDTO> {
	@Override
	public String getXML(MessageEntity<Discount> m) {
		Discount s = m.getEntity();
		DiscountDTO dto = getMapper().map(s, DiscountDTO.class);
		MessageEntity<DiscountDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(Discount.class.getSimpleName(), m.getEntity().getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
