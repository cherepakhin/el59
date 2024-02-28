package ru.perm.v.el59.office.camelcontext;

import ru.perm.v.el59.office.dto.RewardCreditDTO;
import ru.perm.v.el59.office.dto.message.MessageEntity;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.shopmodel.RewardCredit;

import com.thoughtworks.xstream.XStream;

public class ConvertorRewardCreditXml extends
		ConvertorXML<RewardCredit, RewardCreditDTO> {
	
	private IShopProvider shopProvider;
	
	@Override
	public String getXML(MessageEntity<RewardCredit> m) throws Exception {
		RewardCredit rewardCredit = m.getEntity();
		RewardCreditDTO dto = getMapper().map(rewardCredit,
				RewardCreditDTO.class);
		MessageEntity<RewardCreditDTO> mDTO = createMessageDTO(m, dto);
		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(RewardCreditDTO.class.getSimpleName(), RewardCreditDTO.class);
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
