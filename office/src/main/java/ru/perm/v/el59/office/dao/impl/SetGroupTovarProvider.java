package ru.perm.v.el59.office.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.SetGroupTovar;
import ru.perm.v.el59.office.iproviders.IGroupTovarProvider;
import ru.perm.v.el59.office.iproviders.ISetGroupTovarProvider;

public class SetGroupTovarProvider extends
		GenericDaoHibernateImpl<SetGroupTovar, Long> implements
		ISetGroupTovarProvider {
	public SetGroupTovarProvider(Class<SetGroupTovar> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	private String arr[] = new String[] {};
	private HashMap<String, SetGroupTovar> hash = new HashMap<String, SetGroupTovar>();
	private List<SetGroupTovar> list;
	private IGroupTovarProvider groupTovarProvider;

	public List<SetGroupTovar> getAll() {
		if (list == null) {
			list = getSession().createCriteria(SetGroupTovar.class).list();
			hash = new HashMap<String, SetGroupTovar>();
			arr = new String[list.size() + 1];
			arr[0] = "";
			hash.put(arr[0], null);
			for (int i = 0; i < list.size(); i++) {
				hash.put(list.get(i).getName(), list.get(i));
				arr[i + 1] = list.get(i).getName();
			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iprovider.ISetGroupTovarProvider#load(java.lang.Long)
	 */
	@Override
	public SetGroupTovar load(Long id) {
		SetGroupTovar setgrouptovar = (SetGroupTovar) read(id);
		Hibernate.initialize(setgrouptovar.getGroups());
		return setgrouptovar;
	}

	public String[] getAsStringArray() {
		getAll();
		return arr;
	}

	public String getByCodesCritery(String name) {
		Criteria criteria = getSession().createCriteria(SetGroupTovar.class);
		criteria.add(Restrictions.eq("name", name));
		ArrayList<SetGroupTovar> list = (ArrayList<SetGroupTovar>) criteria
				.list();
		String retString = "";
		List<GroupTovar> ret = new ArrayList<GroupTovar>();
		if (list.size() > 0) {
			SetGroupTovar sgt = load(list.get(0).getN());

			for (GroupTovar g : sgt.getGroups()) {
				ret = loadChilds(g, ret);
			}
		}
		for (GroupTovar g : ret) {
			retString = retString + "'" + g.getCod() + "',";
		}
		retString = retString + "''";
		return retString;
	}

	public String getByCodesCritery(List<GroupTovar> ret) {
		String retString = "";
		ArrayList<GroupTovar> rettemp = new ArrayList<GroupTovar>();
		if (ret.size() > 0) {
			// SetGroupTovar sgt=load(list.get(0).getN());

			for (GroupTovar g : ret) {
				ArrayList<GroupTovar> cont = new ArrayList<GroupTovar>();
				List<GroupTovar> r = loadChilds(g, cont);
				rettemp.addAll(r);
			}
		}
		for (GroupTovar g : rettemp) {
			retString = retString + "'" + g.getCod() + "',";
		}

		retString = retString + "''";
		return retString;
	}

	public List<GroupTovar> getByName(String name) {
		Criteria criteria = getSession().createCriteria(SetGroupTovar.class);
		criteria.add(Restrictions.eq("name", name));
		List<SetGroupTovar> list = (ArrayList<SetGroupTovar>) criteria.list();
		List<GroupTovar> ret = new ArrayList<GroupTovar>();
		if (list.size() > 0) {
			SetGroupTovar sgt = load(list.get(0).getN());

			for (GroupTovar g : sgt.getGroups()) {
				// System.out.println("--"+g.getCod());
				ret = loadChilds(g, ret);
			}
		}
		return ret;
	}

	public List<GroupTovar> loadChilds(GroupTovar parent, List<GroupTovar> ret) {
		parent = getGroupTovarProvider().getTree(parent);
		ret.add(parent);
		for (GroupTovar g : parent.getChilds()) {
			ret.add(g);
			if (g.getChilds().size() > 0)
				ret = loadChilds(g, ret);
		}
		return ret;
	}

	@Override
	public SetGroupTovar getByEqName(String name) {
		SetGroupTovar sg = super.getByEqName(name);
		Hibernate.initialize(sg.getGroups());
		return sg;
	}

	@Override
	public Long create(SetGroupTovar o) throws Exception {
		Long n = super.create(o);
		list = null;
		getAll();
		return n;
	}

	@Override
	public void delete(SetGroupTovar o) throws Exception {
		super.delete(o);
		list = null;
		getAll();
	}

	@Override
	public void update(SetGroupTovar o) throws Exception {
		super.update(o);
		list = null;
		getAll();
	}

	public IGroupTovarProvider getGroupTovarProvider() {
		return groupTovarProvider;
	}

	public void setGroupTovarProvider(IGroupTovarProvider groupTovarProvider) {
		this.groupTovarProvider = groupTovarProvider;
	}
}
