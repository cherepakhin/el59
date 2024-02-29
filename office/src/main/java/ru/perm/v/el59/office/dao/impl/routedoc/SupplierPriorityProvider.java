package ru.perm.v.el59.office.dao.impl.routedoc;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.routedoc.SupplierPriority;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.routedoc.ISupplierPriorityProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierPriorityProvider extends
		GenericDaoHibernateImpl<SupplierPriority, Long> implements
		ISupplierPriorityProvider {

	private IShopProvider shopProvider;
	private List<SupplierPriority> listAll;
	private Map<Contragent, SupplierPriority> hash;

	public SupplierPriorityProvider(Class<SupplierPriority> type) {
		super(type);
	}

	@Override
	public List<SupplierPriority> getAll() {
		if (listAll == null) {
			Criteria q = getSession().createCriteria(SupplierPriority.class);
			q.addOrder(Order.asc("priority"));
			listAll = q.list();
		}
		hash = new HashMap<Contragent, SupplierPriority>();
		for (SupplierPriority s : listAll) {
			hash.put(s.getContragent(), s);
		}

		return listAll;
	}

	@Override
	public Long create(SupplierPriority o) throws Exception {
		listAll = null;
		return super.create(o);
	}

	@Override
	public void update(SupplierPriority o) throws Exception {
		listAll = null;
		super.update(o);
	}

	@Override
	public void delete(SupplierPriority o) throws Exception {
		listAll = null;
		super.delete(o);
	}

	@Override
	public Map<Contragent, SupplierPriority> getHashAll() {
		getAll();
		return hash;
	}

	@Override
	public List<Contragent> getContragents(
			List<Contragent> listExcludeContragent) {
		List<Contragent> contragents = new ArrayList<Contragent>();
		for (SupplierPriority s : getAll()) {
			contragents.add(s.getContragent());
		}
		contragents.removeAll(listExcludeContragent);
		List<Shop> shops = getShopProvider().getWorkedShop();
		List<Contragent> ret = new ArrayList<Contragent>();
		for (Contragent contragent : contragents) {
			boolean found = false;
			for (Shop shop : shops) {
				if (contragent.getName().equals(shop.getFullname())) {
					found = true;
				}
			}
			if (!found) {
				ret.add(contragent);
			}
		}

		return ret;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

}
