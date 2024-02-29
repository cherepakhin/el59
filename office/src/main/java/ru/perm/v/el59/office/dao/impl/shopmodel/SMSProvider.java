package ru.perm.v.el59.office.dao.impl.shopmodel;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.dto.SMSDTO;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.critery.SMSCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.ISMSProvider;
import ru.perm.v.el59.office.shopmodel.SMS;

import java.util.List;

public class SMSProvider extends GenericDaoHibernateImpl<SMS, Long>
		implements ISMSProvider {

	private IShopProvider shopProvider;
	
	public SMSProvider(Class<SMS> type) {
		super(type);
	}

	@Override
	public SMS getByDTO(SMSDTO dto, String shopCod) {
		SMS sms = new SMS();
		Shop shop=getShopProvider().read(shopCod);
		sms.setShop(shop);
		sms.setNn(dto.getN());
		sms.setPhone(dto.getPhone());
		sms.setMessage(dto.getMessage());
		return sms;
	}

	@Override
	public List<SMS> getByCritery(Object critery) {
		SMSCritery c = (SMSCritery) critery;
		Criteria criteria = getSession().createCriteria(SMS.class);
		if (c.fromDate != null) {
			criteria.add(Restrictions.ge("ddate", c.fromDate));
		}
		if (c.toDate != null) {
			criteria.add(Restrictions.le("ddate", c.toDate));
		}
		if (c.listShop.size() > 0) {
			criteria.add(Restrictions.in("shop", c.listShop));
		}
		if (!c.message.isEmpty()) {
			criteria.add(Restrictions.like("message", c.message,MatchMode.ANYWHERE).ignoreCase());
		}
		if (!c.phone.isEmpty()) {
			criteria.add(Restrictions.like("phone", c.phone));
		}
		criteria.addOrder(Order.asc("n"));
		List<SMS> list = criteria.list();
		return list;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

}
