package ru.perm.v.el59.office.dao.impl.routedoc;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.routedoc.Procuratory;
import ru.perm.v.el59.office.iproviders.IManagerProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IProcuratoryProvider;
import ru.perm.v.el59.office.iproviders.routedoc.ProcuratoryCritery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProcuratoryProvider extends
		GenericDaoHibernateImpl<Procuratory, Long> implements
		IProcuratoryProvider {

	private Integer defaultLimitationDays;
	private IManagerProvider managerProvider;

	public ProcuratoryProvider(Class<Procuratory> type) {
		super(type);
	}

	@Override
	public List<Procuratory> getByCritery(Object critery) {
		ProcuratoryCritery c = (ProcuratoryCritery) critery;
		Criteria criteria = getSession().createCriteria(Procuratory.class);
		if (c.fromDate != null && c.toDate != null) {
			criteria.add(Restrictions.ge("ddate", c.fromDate));
			criteria.add(Restrictions.le("ddate", c.toDate));
		}
		if (c.shop != null) {
			criteria.add(Restrictions.eq("shop", c.shop));
		}
		if (c.supplier != null) {
			criteria.add(Restrictions.eq("supplier", c.supplier));
		}
		if (c.person != null) {
			criteria.add(Restrictions.eq("person", c.person));
		}
		if (c.numdoc != null) {
			criteria.add(Restrictions.eq("numdoc", c.numdoc));
		}
		criteria.addOrder(Order.asc("ddate"));
		criteria.addOrder(Order.asc("shop"));
		criteria.addOrder(Order.asc("numdoc"));
		ArrayList<Procuratory> list = (ArrayList<Procuratory>) criteria.list();
		return list;
	}

	@Override
	public Integer getNextNumberForShop(Shop shop) {
		String sql = "select max(numdoc)+1 "
				+ "from Procuratory  where shop=:shop";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("shop", shop);
		Object o = q1.uniqueResult();
		if (o != null) {
			return (Integer) o;
		}

		return 1;
	}

	@Override
	public Procuratory create(Shop shop, Contragent supplier,
			Contragent person, String numberOrder, Integer limitationDays,
			Manager manager) throws Exception {
		Procuratory procuratory = new Procuratory();
		procuratory.setShop(shop);
		procuratory.setNumberOrder(numberOrder);
		procuratory.setPerson(person);
		procuratory.setSupplier(supplier);
		procuratory.setNumdoc(getNextNumberForShop(shop));
		if (manager == null) {
			manager = getManagerProvider().getNullManager();
		}
		procuratory.setAutor(manager);
		Long n = create(procuratory);
		Calendar cal = Calendar.getInstance();
		cal.setTime(procuratory.getDdate());
		cal.add(Calendar.DAY_OF_YEAR, limitationDays);
		procuratory.setEndDdate(cal.getTime());
		procuratory.setN(n);
		return procuratory;
	}

	@Override
	public Integer getDefaultLimitationDays() {
		return defaultLimitationDays;
	}

	public void setDefaultLimitationDays(Integer defaultLimitationDays) {
		this.defaultLimitationDays = defaultLimitationDays;
	}

	public IManagerProvider getManagerProvider() {
		return managerProvider;
	}

	public void setManagerProvider(IManagerProvider managerProvider) {
		this.managerProvider = managerProvider;
	}

}
