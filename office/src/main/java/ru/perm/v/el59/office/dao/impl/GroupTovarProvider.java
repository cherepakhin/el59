package ru.perm.v.el59.office.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.dto.office.critery.GroupTovarCritery;
import ru.el59.office.db.BonusK;
import ru.el59.office.db.GroupTovar;
import ru.perm.v.el59..office.iproviders.IGroupTovarProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class GroupTovarProvider extends
		GenericDaoMessageImpl<GroupTovar, String> implements
		IGroupTovarProvider {

	ArrayList<GroupTovar> list;
	HashMap<String, GroupTovar> hash = new HashMap<String, GroupTovar>();
	private Long defaultNBonusK;
	private String defaultGroup;
	private GroupTovar root;

	@Override
	public GroupTovar initialize(String id) {
//		Logger.getLogger(this.getClass()).info("Initialize group_cod:" + id);
		GroupTovar o = (GroupTovar) getSession().get(GroupTovar.class, id);
		if (id.equals("0000000000")) {
			return o;
		}
		Hibernate.initialize(o.getChilds());
		Hibernate.initialize(o.getRoot());
		Hibernate.initialize(o.getGroupT());
		Hibernate.initialize(o.getBonusk());
		return o;
	}

	public GroupTovarProvider(Class<GroupTovar> type) {
		super(type);
	}

	public void updateChildBonusK(GroupTovar g, BonusK b) throws Exception {
		g.setBonusk(b);
		update(g);
		ArrayList<GroupTovar> listGT = getChilds(g);
		if (listGT.size() > 0) {
			for (GroupTovar grouptovar : listGT) {
				updateChildBonusK(grouptovar, b);
			}
		}
	}

	public ArrayList<GroupTovar> getByCritery(GroupTovarCritery critery) {
		Criteria criteryGroup = getSession().createCriteria(GroupTovar.class);
		Criteria parentGroup = criteryGroup.createCriteria("parent");
		Criteria bonuskCritery = criteryGroup.createCriteria("bonusk");
		criteryGroup.addOrder(Order.asc("cod"));
		criteryGroup.addOrder(Order.asc("name"));
		if (critery.cod != null) {
			criteryGroup.add(Restrictions.eq("cod", critery.cod));
		}
		if (critery.name != null) {
			criteryGroup.add(Restrictions.eq("name", critery.name));
		}
		if (critery.parent_cod != null) {
			parentGroup.add(Restrictions.eq("cod", critery.parent_cod));
		}
		if (critery.listNameGroupT.size() > 0) {
			Criteria groupTCritery = criteryGroup.createCriteria("groupT");
			groupTCritery.add(Restrictions.in("name", critery.listNameGroupT));
		}
		if (critery.bonusk != null) {
			bonuskCritery.add(Restrictions.eq("name", critery.bonusk));
		}
		ArrayList<GroupTovar> retList = (ArrayList<GroupTovar>) criteryGroup
				.list();
		return retList;
	}

	public ArrayList<GroupTovar> getByEnd(String suffix) {
		getAll();
		ArrayList<GroupTovar> retlist = new ArrayList<GroupTovar>();
		for (String key : hash.keySet()) {
			if (key.endsWith(suffix)) {
				if (!key.endsWith("00" + suffix))
					retlist.add(hash.get(key));
			}
		}
		return retlist;
	}

	public ArrayList<GroupTovar> getAll() {
		if (list == null) {
			list = new ArrayList<GroupTovar>();
			list = (ArrayList) getSession().createCriteria(GroupTovar.class)
			// .addOrder(Order.asc("parent"))
					.addOrder(Order.asc("name")).list();
			// Формирование дерева
			root = new GroupTovar();
			root.setCod("0000000000");
			root = read(root.getCod());
			root = initChilds(root);
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && list.get(i).getCod() != null) {
				hash.put(list.get(i).getCod(), list.get(i));
			}
		}
		return list;
	}

	/*
	 * @see iprovider.IGroupTovarProvider#getByCod(java.lang.String)
	 */
	public GroupTovar getByCod(String cod) {
		getAll();
		return hash.get(cod);
	}

	public GroupTovar getTree(GroupTovar parent) {
		// / Формирование через запросы
		getAll();
		// parent=getChildsOfParentFromList(parent);

		if (parent != null && parent.getCod() != null) {
			if (parent.getCod().equals("")) {
				parent = initialize("0000000000");
			}
			/*
			 * if (parent.getCod().equals("0000000000")) { return root; }
			 */
			parent = read(parent.getCod());
			parent = initChilds(parent);
		}

		return parent;
	}

	@Override
	public GroupTovar initChilds(GroupTovar parent) {
		// System.out.println("parent-"+parent.getCod());
		// Hibernate.initialize(parent.getChilds());
		// Hibernate.initialize(parent.getRelationTovar());
		initialize(parent.getCod());
		if (parent.getChilds().size() > 0) {
			for (GroupTovar g : parent.getChilds()) {
				/*
				 * if(g.getCod().equals("0910000000")) {
				 * System.out.println("1"); }
				 */
				if (g != null) {
					Hibernate.initialize(g.getChilds());
					Hibernate.initialize(g.getRelationTovar());
					initChilds(g);
					g.setCurrentParent(parent);
				} else {
					System.out.println(parent);
				}
			}
		}
		// System.out.println("end parent-"+parent.getCod());
		return parent;
	}

	@Override
	public GroupTovar getChildsOfParent(GroupTovar parent) {
		// Формирование через запросы
		Criteria critery = getSession().createCriteria(GroupTovar.class);
		critery.add(Restrictions.eq("parent.cod", parent.getCod()));
		critery.addOrder(Order.asc("name"));
		ArrayList<GroupTovar> childs = (ArrayList<GroupTovar>) critery.list();
		parent.setChilds(childs);
		if (childs.size() > 0) {
			for (int i = 0; i < parent.getChilds().size(); i++) {
				getChildsOfParent(parent.getChilds().get(i));
			}
		}
		return parent;
	}

	private GroupTovar getChildsOfParentFromList(GroupTovar parent) {
		// Формирование через перебор
		ArrayList<GroupTovar> childs = (ArrayList<GroupTovar>) getChilds(parent);
		parent.setChilds(childs);
		if (childs.size() > 0) {
			for (int i = 0; i < parent.getChilds().size(); i++) {
				getChildsOfParentFromList(parent.getChilds().get(i));
			}
		}
		return parent;
	}

	private ArrayList<GroupTovar> getChilds(GroupTovar parent) {
		ArrayList<GroupTovar> ret = new ArrayList<GroupTovar>();
		list = getAll();
		for (int i = 0; i < list.size(); i++) {
			GroupTovar g = list.get(i);
			if (g.getParent() != null) {
				if (parent.equals(g.getParent()))
					ret.add(g);
			}
		}
		return ret;
	}

	@Override
	public void deleteChilds(GroupTovar parent) {
		String sql = "delete from GroupTovar where parent_cod=:parent_cod";
		Query q1 = getSession().createQuery(sql);
		if (parent.hasChids()) {
			q1.setParameter("parent_cod", parent.getCod());
			q1.executeUpdate();
		}
		q1.setParameter("parent_cod", parent.getCod());
		q1.executeUpdate();
		parent.getChilds().clear();
		list = null;
	}

	private String getNewCod(String s) {
		if (s.endsWith("00")) {
			s = s.substring(0, s.lastIndexOf("00"));
			s = getNewCod(s);
			s = s + "00";
		} else {
			Long cod = Long.valueOf(s);
			cod++;
			s = cod.toString();
		}
		return s;
	}

	private String getBeginCod(String s) {
		if (s.endsWith("00")) {
			s = s.substring(0, s.lastIndexOf("00"));
			s = getBeginCod(s);
			s = s + "00";
		} else {
			s = s + "01";
		}
		return s;
	}

	public String getNewCodChildForParent(GroupTovar parent) {
		String cod = "";
		String sql = "select max(cod) from GroupTovar where parent_cod=:parent_cod";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("parent_cod", parent.getCod());
		ArrayList<String> _cod = (ArrayList<String>) q1.list();
		cod = _cod.get(0);
		if (cod == null) {
			cod = getBeginCod(parent.getCod());
			cod = cod.substring(0, cod.lastIndexOf("00"));
		}
		cod = getNewCod(cod);
		if (cod.length() < 10)
			cod = '0' + cod;
		return cod;
	}

	/*
	 * @Override public GroupTovar checkGroup(GroupTovar g) throws Exception {
	 * getAll(); if(!hash.containsKey(g.getCod()) ||
	 * (hash.get(g.getCod())==null)) { int pos=g.getCod().indexOf("00");
	 * if(pos>2) { String parentcod = g.getCod();
	 * parentcod=parentcod.substring(0, pos-2); for(int
	 * i=parentcod.length();i<g.getCod().length();i++) {
	 * parentcod=parentcod+"0"; }
	 * System.out.println("Check parent:          ,"+parentcod); GroupTovar
	 * parent = new GroupTovar(); parent.setCod(parentcod);
	 * parent=checkGroup(parent); g.setParent(parent);
	 * System.out.println("create:"+g+" parent:"+g.getParent()); create(g); //
	 * hash.put(g.getCod(), g); return g; } else { // create g
	 * g.setParent(hash.get("0000000000")); create(g);
	 * System.out.println("create:"+g+" parent:"+g.getParent());
	 * hash.put(g.getCod(), g); return g; } } else { Set<String> s =
	 * hash.keySet(); for (String string : s) { System.out.println(string); }
	 * return hash.get(g.getCod()); } }
	 */
	public Long getDefaultNBonusK() {
		return defaultNBonusK;
	}

	public void setDefaultNBonusK(Long defaultNBonusK) {
		this.defaultNBonusK = defaultNBonusK;
	}

	@Override
	public String create(GroupTovar o) throws Exception {
		GroupTovar g = (GroupTovar) o;
		if (g.getParent() != null) {
			g.setBonusk(g.getParent().getBonusk());
		}
		if (g.getBonusk() == null) {
			BonusK b = new BonusK();
			g.setBonusk(b);
		}
		if (g.getBonusk().getN() < 0) {
			g.getBonusk().setN(getDefaultNBonusK());
		}
		list = null;
		String cod = super.create(o);
		update(o.getParent());
		// getSession().flush();
		return cod;
	}

	@Override
	public void delete(GroupTovar o) throws Exception {
		GroupTovar parent = o.getParent();
		parent.getChilds().remove(o);
		update(parent);
		super.delete(o);
		list = null;
	}

	@Override
	public void update(GroupTovar o) throws Exception {
		list = null;
		super.update(o);
	}

	@Override
	public BigDecimal getMinNacenka(GroupTovar group) {
		Hibernate.initialize(group);
		return group.getMinNacenka();
	}

	@Override
	public boolean isParent(GroupTovar parent, GroupTovar child) {
		if (parent == null || child == null)
			return false;
		if (child.equals(parent))
			return true;
		if (child.getParent() == null)
			return false;
		if (child.getParent().equals(parent)) {
			return true;
		} else {
			return isParent(parent, child.getParent());
		}
	}

	@Override
	public GroupTovar getGroupByTrade(String mecat) {
		String cod = "";
		String sql = "select group_cod,group_cod_old,count(*) from db.tovar where group_cod_old like :trade_cod group by group_cod,group_cod_old order by  3 desc";
		Query q1 = getSession().createSQLQuery(sql);
		mecat = "0" + mecat;
		if(mecat.length()<7) {
			return read(getDefaultGroup());
		}
		mecat = mecat.substring(0, 7) + "%";
		q1.setParameter("trade_cod", mecat);
		List l = q1.list();
		if (l.size() > 0) {
			Object[] rr = (Object[]) l.get(0);
			cod = (String) rr[0];
			return read(cod);
		} else {
			return read(getDefaultGroup());
		}
	}

	public String getDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(String defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

}
