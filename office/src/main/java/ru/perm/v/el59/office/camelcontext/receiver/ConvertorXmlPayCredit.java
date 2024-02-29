package ru.perm.v.el59.office.camelcontext.receiver;

import com.thoughtworks.xstream.XStream;
import ru.perm.v.el59.dto.PayCreditDTO;
import ru.perm.v.el59.dto.message.MessagePayCreditDTO;
import ru.perm.v.el59.office.iproviders.shopmodel.IBankActionProvider;
import ru.perm.v.el59.office.shopmodel.BankAction;
import ru.perm.v.el59.office.shopmodel.PayCredit;

import java.util.logging.Logger;

public class ConvertorXmlPayCredit extends
		ConvertorXmlPayment<PayCreditDTO, PayCredit> {
	private IBankActionProvider bankActionProvider;

	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayCreditDTO.class);
		xstream.aliasField("command", MessagePayCreditDTO.class, "typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayCreditDTO dto = message.getEntity();
			Logger.getLogger(this.getClass()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayCredit payCredit = new PayCredit();
			payCredit = fillFromDTO(dto, payCredit, message.getShopCod());
			payCredit.setFirstPay(dto.getFirstPay());
			payCredit.setSummaCredit(dto.getSummaCredit());
			payCredit.setContract(dto.getContract());
			payCredit.setBankAction((BankAction) getBankActionProvider()
					.initialize(dto.getBankAction().getN()));
			doMessage(payCredit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	public IBankActionProvider getBankActionProvider() {
		return bankActionProvider;
	}

	public void setBankActionProvider(IBankActionProvider bankActionProvider) {
		this.bankActionProvider = bankActionProvider;
	}

}
