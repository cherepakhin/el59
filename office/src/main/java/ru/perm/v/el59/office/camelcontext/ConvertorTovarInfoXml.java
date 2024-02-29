package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.FeatureDTO;
import ru.perm.v.el59.dto.FeaturePriceDTO;
import ru.perm.v.el59.dto.TovarInfoDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.FeaturePrice;
import ru.perm.v.el59.office.db.TovarInfo;

public class ConvertorTovarInfoXml extends
		ConvertorXML<TovarInfo, TovarInfoDTO> {
	@Override
	public String getXML(MessageEntity<TovarInfo> m) {

		TovarInfo t = m.getEntity();
		TovarInfoDTO dto = getMapper().map(t, TovarInfoDTO.class);
		MessageEntity<TovarInfoDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(TovarInfo.class.getSimpleName(), TovarInfoDTO.class);
		xstream.alias(Feature.class.getSimpleName(), FeatureDTO.class);
		xstream.alias(FeaturePrice.class.getSimpleName(), FeaturePriceDTO.class);
		String xml = xstream.toXML(mDTO);
		return xml;
	}

}
