package ru.perm.v.el59.office.camelcontext;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.BankActionDTO;
import ru.perm.v.el59.dto.message.MessageEntity;
import ru.perm.v.el59.office.iproviders.shopmodel.IBankActionProvider;
import ru.perm.v.el59.office.shopmodel.BankAction;

public class ConvertorBankActionXml extends
		ConvertorXML<BankAction, BankActionDTO> {
	private IBankActionProvider bankActionProvider;

	@Override
	public String getXML(MessageEntity<BankAction> m) {
		BankAction bankAction = m.getEntity();
		bankAction = (BankAction) getBankActionProvider().initialize(
				bankAction.getN());
		BankActionDTO dto = getMapper().map(bankAction, BankActionDTO.class);

		MessageEntity<BankActionDTO> mDTO = createMessageDTO(m, dto);

		XStream xstream = getXStream();
		xstream.aliasSystemAttribute("type", "class");
		xstream.alias(BankAction.class.getSimpleName(), m.getEntity()
				.getClass());
		String xml = xstream.toXML(mDTO);
		return xml;
	}

	public IBankActionProvider getBankActionProvider() {
		return bankActionProvider;
	}

	public void setBankActionProvider(IBankActionProvider bankActionProvider) {
		this.bankActionProvider = bankActionProvider;
	}

}
