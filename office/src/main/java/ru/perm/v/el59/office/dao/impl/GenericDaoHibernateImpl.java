package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.perm.v.el59.dao.AEntity;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Transactional(propagation = Propagation.REQUIRED)
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements IGenericDao<T, PK> {

	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> type;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public GenericDaoHibernateImpl(Class<T> type) {
		this.type = type;
	}

	public PK create(T o) throws Exception {
		PK n = null;
		if (AEntity.class.isAssignableFrom(o.getClass())) {
			AEntity a = (AEntity) o;
			if ((a.getN() == null) || (a.getN().equals(-1L))) {
				n = (PK) getMax();
				a.setN((Long) n);
			}
			getSession().merge(a);
		} else {
			n = (PK) getSession().save(o);
		}
		return n;
	}

	@Override
	public PK getMax() {
		Long n = (Long) getSession().createQuery("select max(n)+1 from " + type.getSimpleName()).uniqueResult();
		if (n == null)
			n = 0L;
		return (PK) n;
	}

	public T read(PK id) {
		T o = null;
		try {
			o = (T) getSession().get(type, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public T initialize(PK id) {
		T o = (T) getSession().get(type, id);
		Hibernate.initialize(o);
		return o;
	}

	public void update(T o) throws Exception {
		getSession().saveOrUpdate(o);
	}

	public void delete(T o) throws Exception {
		getSession().delete(o);
	}

	public List<T> getByCritery(Object critery) {
		CommonCritery c = (CommonCritery) critery;
		Criteria q = getSession().createCriteria(type);
		if (c.getName() != null) {
			q.add(Restrictions.like("name", c.getName(), MatchMode.ANYWHERE).ignoreCase());
		}
		q.addOrder(Order.asc("name"));
		List<T> list = q.list();
		return list;
	}

	public T getByEqName(String name) {
		CommonCritery c = new CommonCritery();
		c.setName(name);
		Criteria q = getSession().createCriteria(type);
		q.add(Restrictions.eq("name", c.getName()));
/*		q.add(Restrictions.eq("name", c.getName()).ignoreCase());*/
		List<T> list = q.list();
		if (list.size() == 0) {
			Logger.getLogger(this.getClass().getName()).info("Не найден объект с именем " + name);
			return null;
		}
		return (T) list.get(0);
	}

	public List<T> getByLikeName(String name) {
		CommonCritery c = new CommonCritery();
		c.setName(name);
		Criteria q = getSession().createCriteria(type);
		q.add(Restrictions.like("name", c.getName(), MatchMode.ANYWHERE).ignoreCase());
		List<T> list = q.list();
		return list;
	}

	public Class<T> getType() {
		return type;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
