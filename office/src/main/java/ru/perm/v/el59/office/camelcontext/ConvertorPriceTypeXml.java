package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.PriceTypeDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.db.PriceType;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.IShopProvider;

public class ConvertorPriceTypeXml extends
		ConvertorXML<PriceType, PriceTypeDTO> {
	
	private IShopProvider shopProvider;
	
	@Override
	public String getXML(MessageEntity<PriceType> m) throws Exception {
		PriceType priceType = m.getEntity();
		PriceTypeDTO dto = getMapper().map(priceType,
				PriceTypeDTO.class);
		MessageEntity<PriceTypeDTO> mDTO = createMessageDTO(m, dto);
		Shop nullShop = getShopProvider().getNullShop();
		if(!priceType.getShop().equals(nullShop)) {
			mDTO.setShopCod(priceType.getShop().getCod());
		}
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(PriceTypeDTO.class.getSimpleName(), PriceTypeDTO.class);
		String xml = xstream.toXML(mDTO);
		return xml;
	}
	public IShopProvider getShopProvider() {
		return shopProvider;
	}
	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}
}
