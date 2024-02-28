package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.db.PriceType;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.IPriceTypeProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class PriceTypeProvider extends GenericDaoMessageImpl<PriceType, Long>
		implements IPriceTypeProvider {

	private IShopProvider shopProvider;

	public PriceTypeProvider(Class<PriceType> type) {
		super(type);
	}

	@Override
	public List<PriceType> getAll() {
		return getByCritery(new CommonCritery(""));
	}

	@Override
	public List<PriceType> getIsBase(Boolean isBase) {
		Criteria q = getSession().createCriteria(PriceType.class);
		q.add(Restrictions.eq("isBase", isBase));
		q.addOrder(Order.asc("name"));
		List<PriceType> list = q.list();
		return list;
	}

	@Override
	public void delete(PriceType pt) throws Exception {
		String sql = "delete from Price p where p.priceType=:pt";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("pt", pt);
		q1.executeUpdate();
		super.delete(pt);
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	@Override
	public Long create(PriceType o) throws Exception {
		if (o.getShop() == null) {
			o.setShop(getShopProvider().getNullShop());
		}
		return super.create(o);
	}

	@Override
	public void update(PriceType o) throws Exception {
		if (o.getShop() == null) {
			o.setShop(getShopProvider().getNullShop());
		}
		super.update(o);
	}

	@Override
	public PriceType init(PriceType selectedPriceType) {
		selectedPriceType = read(selectedPriceType.getN());
		Hibernate.initialize(selectedPriceType.getOwners());
		return selectedPriceType;
	}

	@Override
	public List<PriceType> getByShop(List<Shop> listShop) {
		Criteria q = getSession().createCriteria(PriceType.class);
		q.add(Restrictions.in("shop", listShop));
		Criteria shopCriteria = q.createCriteria("shop");
		shopCriteria.addOrder(Order.asc("name"));
		List<PriceType> list = q.list();
		return list;
	}

	@Override
	public List<PriceType> init(List<PriceType> listPriceType) {
		ArrayList<PriceType> ret = new ArrayList<PriceType>();
		for (PriceType pricetype : listPriceType) {
			ret.add(init(pricetype));
		}
		return ret;
	}
}
