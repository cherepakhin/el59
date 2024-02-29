package ru.perm.v.el59.office.dao.impl.shopmodel;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.BonusCardMoveDTO;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.critery.BonusCardCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.IBonusCardMoveProvider;
import ru.perm.v.el59.office.iproviders.shopmodel.IBonusCardProvider;
import ru.perm.v.el59.office.iproviders.shopmodel.IDocTitleProvider;
import ru.perm.v.el59.office.shopmodel.BonusCard;
import ru.perm.v.el59.office.shopmodel.BonusCardMove;
import ru.perm.v.el59.office.shopmodel.DocTitle;

import java.util.List;

public class BonusCardMoveProvider extends
		GenericDaoHibernateImpl<BonusCardMove, Long> implements
		IBonusCardMoveProvider {

	private IBonusCardProvider bonusCardProvider;
	private IShopProvider shopProvider;
	private IDocTitleProvider docTitleProvider;

	public BonusCardMoveProvider(Class<BonusCardMove> type) {
		super(type);
	}

	@Override
	public List<BonusCardMove> getByCritery(Object critery) {
		BonusCardCritery c = (BonusCardCritery) critery;
		Criteria bonusCardMoveCriteria = getSession().createCriteria(
				BonusCardMove.class);
		Criteria bonusCardCriteria = bonusCardMoveCriteria
				.createCriteria("bonusCard");
		if (c.nn != null) {
			bonusCardMoveCriteria.add(Restrictions.eq("nn", c.nn));
		}
		if (c.shopCod != null) {
			bonusCardMoveCriteria.add(Restrictions.eq("shop.cod", c.shopCod));
		}
		if (c.n != null) {
			bonusCardMoveCriteria.add(Restrictions.eq("n", c.n));
		}
		if (c.fromdate != null && c.todate != null) {
			bonusCardMoveCriteria.add(Restrictions.ge("ddate", c.fromdate));
			bonusCardMoveCriteria.add(Restrictions.le("ddate", c.todate));
		}
		if (c.name != null) {
			bonusCardCriteria.add(Restrictions.like("name", c.name.trim(),
					MatchMode.ANYWHERE).ignoreCase());
		}
		List<BonusCardMove> list = bonusCardMoveCriteria.list();
		return list;
	}

	@Override
	public BonusCardMove getByDTO(BonusCardMoveDTO dto, String shopCod)
			throws Exception {
		BonusCardCritery bonusCardCritery = new BonusCardCritery();
		bonusCardCritery.nn = dto.getN();
		bonusCardCritery.shopCod = shopCod;
		List<BonusCardMove> list = getByCritery(bonusCardCritery);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			BonusCardMove bonusCardMove = new BonusCardMove();
			bonusCardMove.setActive(dto.getActive());
			bonusCardMove.setBonusCard((BonusCard) getBonusCardProvider()
					.initialize(dto.getBonusCard().getN()));
			bonusCardMove.setComment(dto.getComment());
			bonusCardMove.setDdate(dto.getDdate());
			bonusCardMove.setDeleted(dto.getDeleted());
			bonusCardMove.setName(dto.getName());
			bonusCardMove.setNn(dto.getN());
			Shop shop = (Shop) getShopProvider().initialize(shopCod);
			bonusCardMove.setShop(shop);
			bonusCardMove.setSumma(dto.getSumma());
			DocTitle docTitle = getDocTitleProvider().getByDTO(
					dto.getDocTitle(), shop);
			bonusCardMove.setDocTitle(docTitle);
			Long n = (Long) create(bonusCardMove);
			bonusCardMove.setN(n);
			return bonusCardMove;
		}
	}

	public IBonusCardProvider getBonusCardProvider() {
		return bonusCardProvider;
	}

	public void setBonusCardProvider(IBonusCardProvider bonusCardProvider) {
		this.bonusCardProvider = bonusCardProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public IDocTitleProvider getDocTitleProvider() {
		return docTitleProvider;
	}

	public void setDocTitleProvider(IDocTitleProvider docTitleProvider) {
		this.docTitleProvider = docTitleProvider;
	}
}
