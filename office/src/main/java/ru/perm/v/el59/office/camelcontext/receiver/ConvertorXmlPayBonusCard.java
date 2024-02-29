package ru.perm.v.el59.office.camelcontext.receiver;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.dto.BonusCardMoveDTO;
import ru.perm.v.el59.office.dto.PayBonusCardDTO;
import ru.perm.v.el59.office.dto.message.MessagePayBonusCardDTO;
import ru.perm.v.el59.office.iproviders.shopmodel.IBonusCardMoveProvider;
import ru.perm.v.el59.office.shopmodel.BonusCardMove;
import ru.perm.v.el59.office.shopmodel.PayBonusCard;

public class ConvertorXmlPayBonusCard extends
		ConvertorXmlPayment<PayBonusCardDTO, PayBonusCard> {
	private IBonusCardMoveProvider bonusCardMoveProvider;

	@Override
	protected void get(Object o) {
	}

	@Override
	public String fromXmlToMessageEntity(Object body) {
		String xml = (String) body;
		XStream xstream = getXStream();
		xstream.alias("message", MessagePayBonusCardDTO.class);
		xstream.aliasField("command", MessagePayBonusCardDTO.class,
				"typeCommand");
		xstream.alias("entity", type);
		xstream.alias(getEntity(), type);
		try {
			message = getMessageFromXml(xml);
			PayBonusCardDTO dto = message.getEntity();
			Logger.getLogger(this.getClass().getName()).info(
					String.format("Shop %s;Command %s;N %d ",
							message.getShopCod(), message.getTypeCommand(),
							dto.getN()));
			PayBonusCard payBonusCard = new PayBonusCard();
			payBonusCard = super.fillFromDTO(dto, payBonusCard,
					message.getShopCod());
			BonusCardMoveDTO bonusCardMoveDTO = dto.getBonusCardMove();
			BonusCardMove bonusCardMove = getBonusCardMoveProvider().getByDTO(
					bonusCardMoveDTO, message.getShopCod());
			payBonusCard.setBonusCardMove(bonusCardMove);
			doMessage(payBonusCard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	public IBonusCardMoveProvider getBonusCardMoveProvider() {
		return bonusCardMoveProvider;
	}

	public void setBonusCardMoveProvider(
			IBonusCardMoveProvider bonusCardMoveProvider) {
		this.bonusCardMoveProvider = bonusCardMoveProvider;
	}

}
