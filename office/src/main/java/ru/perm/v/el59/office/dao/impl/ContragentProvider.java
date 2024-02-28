package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.office.critery.ContragentCritery;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.GroupContragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.dto.ContragentDTO;
import ru.perm.v.el59.office.iproviders.IContragentProvider;
import ru.perm.v.el59.office.iproviders.IGroupContragentProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;

public class ContragentProvider extends
		GenericDaoHibernateImpl<Contragent, Long> implements
		IContragentProvider {

	private IShopProvider shopProvider;
	private IGroupContragentProvider groupContragentProvider;
	private String nameOrderW;
	private String nameGroupSupplier;
	private String defaultGroupContragent;

	public ContragentProvider(Class<Contragent> type) {
		super(type);
	}

	@Override
	public List<Contragent> getAll(GroupContragent groupContragent) {
		Criteria criteria = getSession().createCriteria(Contragent.class);
		if (groupContragent != null) {
			criteria.add(Restrictions.eq("groupContragent", groupContragent));
			criteria.addOrder(Order.asc("name"));
			return criteria.list();
		} else {
			return new ArrayList<Contragent>();
		}
	}

	String getShopCod(Object o) throws Exception {
		Contragent contragent = (Contragent) o;
		if (contragent.getShop() == null) {
			Shop shop = getShopProvider().getNullShop();
			contragent.setShop(shop);
		}
		String shopCod = contragent.getShop().getCod();
		if (contragent.getShop().equals(getShopProvider().getNullShop())) {
			shopCod = "*";
		}
		return shopCod;
	}

	@Override
	public Long create(Contragent contragent) throws Exception {
		if(contragent.getGroupContragent()==null) {
			contragent.setGroupContragent(getNullGroupContragent());
		}
		if(contragent.getShop()==null) {
			contragent.setShop(getShopProvider().getNullShop());
		}
		Long n = super.create(contragent);
/*		if (contragent.getShop() != null) {
			getCommander().create(contragent, getShopCod(contragent));
		}
*/		return n;
	}

	private GroupContragent getNullGroupContragent() {
		return getGroupContragentProvider().getByEqName(getDefaultGroupContragent());
	}

	@Override
	public void delete(Contragent o) throws Exception {
		super.delete(o);
	}

	@Override
	public void update(Contragent o) throws Exception {
		super.update(o);
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	@Override
	public Contragent getByDTO(ContragentDTO dto, Shop shop) throws Exception {

		ContragentCritery c = new ContragentCritery();
		c.nn = dto.getN();
		c.shopcod = shop.getCod();
		List<Contragent> list = getByCritery(c);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			Contragent contragent = new Contragent();
			contragent.setAddress(dto.getAddress());
			contragent.setInfo(dto.getInfo());
			contragent.setName(dto.getName());
			contragent.setNn(dto.getN());
			contragent.setShop(shop);
			Long n = (Long) create(contragent);
			contragent.setN(n);
			return contragent;
		}
	}

	@Override
	public List<Contragent> getByCritery(Object critery) {
		if (critery.getClass().equals(ContragentCritery.class)) {
			ContragentCritery c = (ContragentCritery) critery;
			Criteria criteria = getSession().createCriteria(Contragent.class);
			if (c.getName() != null) {
				criteria.add(Restrictions.like("name", c.getName(),
						MatchMode.ANYWHERE).ignoreCase());
			}
			if (c.nn != null) {
				criteria.add(Restrictions.eq("nn", c.nn));
			}
			if (c.shopcod != null) {
				criteria.add(Restrictions.eq("shop.cod", c.shopcod));
			}
			if (c.groupContragentName != null) {
				Criteria criteriaGroupContragent = criteria
						.createCriteria("groupContragent");
				criteriaGroupContragent.add(Restrictions.eq("name",
						c.groupContragentName).ignoreCase());
			}
			criteria.addOrder(Order.asc("name"));
			List<Contragent> list = criteria.list();
			return list;
		}
		if (critery.getClass().equals(CommonCritery.class)) {
			return super.getByCritery(critery);
		}
		return new ArrayList<Contragent>();
	}

	@Override
	public List<Contragent> getSuppliers() {
		ContragentCritery c = new ContragentCritery();
		c.groupContragentName = getNameGroupSupplier();
		List<Contragent> list = getByCritery(c);
		return list;
	}

	public String getNameOrderW() {
		return nameOrderW;
	}

	public void setNameOrderW(String nameOrderW) {
		this.nameOrderW = nameOrderW;
	}

	@Override
	public List<Contragent> getSupplier(String findName) {
		ContragentCritery c = new ContragentCritery();
		c.setName(findName);
		c.groupContragentName = getNameGroupSupplier();
		List<Contragent> list = getByCritery(c);
		return list;
	}

	@Override
	public String getNameGroupSupplier() {
		return nameGroupSupplier;
	}

	public void setNameGroupSupplier(String nameGroupSupplier) {
		this.nameGroupSupplier = nameGroupSupplier;
	}

	@Override
	public void addToGroupContragent(Contragent contragent,
			String nameGroupSupplier) throws Exception {
		GroupContragent g = (GroupContragent) getGroupContragentProvider()
				.getByEqName(getNameGroupSupplier());
		contragent.setGroupContragent(g);
		update(contragent);
	}

	@Override
	public void removeFromGroupContragent(Contragent contragent,
			String nameGroupSupplier) throws Exception {
		GroupContragent g = (GroupContragent) getGroupContragentProvider()
				.getByEqName(getDefaultGroupContragent());
		contragent.setGroupContragent(g);
		update(contragent);
	}

	public IGroupContragentProvider getGroupContragentProvider() {
		return groupContragentProvider;
	}

	public void setGroupContragentProvider(
			IGroupContragentProvider groupContragentProvider) {
		this.groupContragentProvider = groupContragentProvider;
	}

	@Override
	public String getDefaultGroupContragent() {
		return defaultGroupContragent;
	}

	public void setDefaultGroupContragent(String defaultGroupContragent) {
		this.defaultGroupContragent = defaultGroupContragent;
	}

}
