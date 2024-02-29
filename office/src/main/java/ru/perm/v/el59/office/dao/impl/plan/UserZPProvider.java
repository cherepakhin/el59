package ru.perm.v.el59.office.dao.impl.plan;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.plan.Plan;
import ru.perm.v.el59.office.db.plan.Smena;
import ru.perm.v.el59.office.db.plan.Tabel;
import ru.perm.v.el59.office.db.plan.UserZP;
import ru.perm.v.el59.office.iproviders.critery.PlanCritery;
import ru.perm.v.el59.office.iproviders.plan.ITabelProvider;
import ru.perm.v.el59.office.iproviders.plan.IUserZPProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserZPProvider extends GenericDaoHibernateImpl<UserZP, Long>
		implements IUserZPProvider {

	private ITabelProvider tabelProvider;

	@SuppressWarnings("unchecked")
	public UserZPProvider(Class<UserZP> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getByCritery(Object critery) {
		PlanCritery c = (PlanCritery) critery;
		Criteria q = getSession().createCriteria(UserZP.class);
		Criteria qplan = q.createCriteria("plan");
		if (c.shop != null)
			qplan.add(Restrictions.eq("shop", c.shop));
		if (c.year != null)
			qplan.add(Restrictions.eq("year", c.year));
		if (c.month != null)
			qplan.add(Restrictions.eq("month", c.month));
		q.addOrder(Order.asc("name"));
		qplan.addOrder(Order.asc("shop"));
		qplan.addOrder(Order.asc("year"));
		qplan.addOrder(Order.asc("month"));
		List list = q.list();
		return list;
	}

	@Override
	public List<UserZP> getByNameAndPlan(String name, Plan plan) {
		Criteria q = getSession().createCriteria(UserZP.class);
		Criteria qplan = q.createCriteria("plan");
		if (plan.getShop() != null)
			qplan.add(Restrictions.eq("shop", plan.getShop()));
		if (plan.getYear() != null)
			qplan.add(Restrictions.eq("year", plan.getYear()));
		if (plan.getMonth() != null)
			qplan.add(Restrictions.eq("month", plan.getMonth()));
		if (name != null && !name.isEmpty()) {
			q.add(Restrictions.eq("name", name));
		}
		q.addOrder(Order.asc("name"));
		qplan.addOrder(Order.asc("shop"));
		qplan.addOrder(Order.asc("year"));
		qplan.addOrder(Order.asc("month"));
		List<UserZP> list = q.list();
/*		HashMap<String, UserZP> mapUsersZP = new HashMap<String,UserZP>(); 
		for (UserZP userZP : list) {
			mapUsersZP.put(userZP.getName(), userZP);
		}
		if(name==null && plan!=null) {
			UserShopCritery userShopCritery = new UserShopCritery();
			userShopCritery.setShopname(plan.getShop().getName());
			userShopCritery.setWorked(true);
			List<UserShop> users = getUserShopProvider().getByCritery(userShopCritery);
			for (UserShop userShop : users) {
				if(!mapUsersZP.containsKey(userShop.getNamebest())) {
					UserZP u = new UserZP();
					u.setName(user)
				}
			}
		}
*/		
		return list;
	}

	@Override
	public UserZP initialize(Long id) {
		UserZP o = (UserZP) getSession().get(UserZP.class, id);
		Hibernate.initialize(o.getTabel());
		return o;
	}

	@Override
	public UserZP loadTabel(Serializable id) {
		UserZP o = (UserZP) getSession().get(UserZP.class, id);
		Hibernate.initialize(o.getTabel());
		for (Tabel t : o.getTabel()) {
			Hibernate.initialize(t.getSmena());
			for (Smena smena : t.getSmena().values()) {
				Hibernate.initialize(smena.getTdoctabel());
			}
		}
		return o;
	}

	@Override
	public List<UserZP> loadTabel(List<UserZP> listUserZP) {
		ArrayList<UserZP> ret = new ArrayList<UserZP>();
		for (UserZP userZP : listUserZP) {
			UserZP u = loadTabel(userZP.getN());
			ret.add(u);
		}
		return ret;
	}

	public ITabelProvider getTabelProvider() {
		return tabelProvider;
	}

	public void setTabelProvider(ITabelProvider tabelProvider) {
		this.tabelProvider = tabelProvider;
	}

}
