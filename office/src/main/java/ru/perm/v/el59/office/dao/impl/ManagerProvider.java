package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.Manager;
import ru.el59.office.iproviders.IManagerProvider;
import ru.perm.v.el59.office.iproviders.critery.ManagerCritery;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.util.ArrayList;
import java.util.List;

public class ManagerProvider extends GenericDaoHibernateImpl<Manager, Long>
		implements IManagerProvider {

	private Manager nullManager;

	public ManagerProvider(Class<Manager> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Manager> getByCritery(Object critery) {
		if (critery.getClass().equals(ManagerCritery.class)) {
			ManagerCritery managerCritery = (ManagerCritery) critery;
			Criteria managerCriteria = getSession().createCriteria(
					Manager.class);
			if (managerCritery.getWorked() != null)
				managerCriteria.add(Restrictions.eq("worked",
						managerCritery.getWorked()));
			if ((managerCritery.getName() != null)
					&& (!managerCritery.getName().equals("")))
				managerCriteria.add(Restrictions.like("name",
						managerCritery.getName(), MatchMode.ANYWHERE)
						.ignoreCase());
			managerCriteria.addOrder(Order.asc("name"));
			ArrayList<Manager> list = (ArrayList<Manager>) managerCriteria
					.list();
			return list;
		} else {
			CommonCritery managerCritery = (CommonCritery) critery;
			Criteria managerCriteria = getSession().createCriteria(
					Manager.class);
			managerCriteria.add(Restrictions.eq("worked", true));
			if ((managerCritery.getName() != null)
					&& (!managerCritery.getName().equals("")))
				managerCriteria.add(Restrictions.like("name",
						managerCritery.getName(), MatchMode.ANYWHERE)
						.ignoreCase());
			managerCriteria.addOrder(Order.asc("name"));
			ArrayList<Manager> list = (ArrayList<Manager>) managerCriteria
					.list();
			return list;
		}
	}

	@Override
	public List<String> getUsers() {
		ArrayList<String> ret = new ArrayList<String>();
		for (Manager manager : getWorked()) {
			ret.add(manager.getName());
		}
		return ret;
	}

	@Override
	public Manager checkPassword(String name, String password) {
		ManagerCritery critery = new ManagerCritery();
		critery.setWorked(true);
		critery.setName(name);
		List<Manager> managers = getByCritery(critery);
		if (managers.size() == 0)
			return null;
		if ((managers.get(0).getPassword().equals(password))
				&& (managers.get(0).getName().equals(name))) {
			return managers.get(0);
		}
		return null;
	}

	@Override
	public Manager getNullManager() {
		if (nullManager == null) {
			nullManager = read(0L);
			if (nullManager == null) {
				nullManager = new Manager();
				nullManager.setN(0L);
				nullManager.setName("-");
				nullManager.setWorked(true);
				try {
					create(nullManager);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return nullManager;
	}

	@Override
	public List<Manager> getWorked() {
		ManagerCritery critery = new ManagerCritery();
		critery.setWorked(true);
		List<Manager> managers = getByCritery(critery);
		return managers;
	}
}
