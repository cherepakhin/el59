package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.office.db.Price;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.dto.PriceDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.iproviders.IShopProvider;

public class ConvertorPriceXml extends ConvertorXML<Price, PriceDTO> {

	private IShopProvider shopProvider;

	@Override
	public String getXML(MessageEntity<Price> m) throws Exception {
		Price price = m.getEntity();
		PriceDTO dto = getMapper().map(price, PriceDTO.class);
		MessageEntity<PriceDTO> mDTO = createMessageDTO(m, dto);
		Shop nullShop = getShopProvider().getNullShop();
		if (!price.getPriceType().getShop().equals(nullShop)) {
			mDTO.setShopCod(price.getPriceType().getShop().getCod());
		}
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(PriceDTO.class.getSimpleName(), PriceDTO.class);
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
