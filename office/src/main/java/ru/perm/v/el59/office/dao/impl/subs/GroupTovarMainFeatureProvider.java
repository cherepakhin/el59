package ru.perm.v.el59.office.dao.impl.subs;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.subs.GroupTovarMainFeature;
import ru.perm.v.el59.office.db.subs.MainFeature;
import ru.perm.v.el59.office.iproviders.IGroupTovarProvider;
import ru.perm.v.el59.office.iproviders.subs.IGroupTovarMainFeatureProvider;
import ru.perm.v.el59.office.iproviders.subs.IMainFeatureProvider;

import java.util.List;

public class GroupTovarMainFeatureProvider extends GenericDaoHibernateImpl<GroupTovarMainFeature, Long>
		implements IGroupTovarMainFeatureProvider {

	private IMainFeatureProvider mainFeatureProvider;
	private IGroupTovarProvider groupTovarProvider;

	public GroupTovarMainFeatureProvider(Class<GroupTovarMainFeature> type) {
		super(type);
	}

	public GroupTovarMainFeature getByNameGroupTovar(String nameGroupTovar) {
		Criteria criteria = getSession().createCriteria(GroupTovarMainFeature.class);
		Criteria groupTovarcriteria = criteria.createCriteria("groupTovar");
		groupTovarcriteria.add(Restrictions.like("name", nameGroupTovar, MatchMode.ANYWHERE).ignoreCase());
		groupTovarcriteria.addOrder(Order.asc("name"));
		List<GroupTovarMainFeature> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	public GroupTovarMainFeature init(GroupTovarMainFeature groupTovarMainFeature) {
		groupTovarMainFeature = read(groupTovarMainFeature.getN());
		Hibernate.initialize(groupTovarMainFeature.getMainFeatures());
		return groupTovarMainFeature;
	}

	public GroupTovarMainFeature createForGroupTovar(GroupTovar groupTovar) throws Exception {
		if (groupTovar == null) {
			throw new Exception("GroupTovar is null");
		}
		groupTovar = getGroupTovarProvider().read(groupTovar.getCod());
		if (groupTovar == null) {
			throw new Exception("GroupTovar is null after create.");
		}
		GroupTovarMainFeature g = getByNameGroupTovar(groupTovar.getName());
		if (g != null) {
			return g;
//			throw new Exception("GroupTovar is with cod " + groupTovar.getCod() + "already exist");
		}
		g = new GroupTovarMainFeature();
		g.setGroupTovar(groupTovar);
		Long n = super.create(g);
		g.setN(n);
		return g;
	}

	@Override
	public Long create(GroupTovarMainFeature o) throws Exception {
		return createForGroupTovar(o.getGroupTovar()).getN();
	}

	@Override
	public void delete(GroupTovarMainFeature g) throws Exception {
		g = init(g);
		for (MainFeature m : g.getMainFeatures()) {
			getMainFeatureProvider().delete(m);
		}
		super.delete(g);
	}

	public GroupTovarMainFeature createMainfeature(GroupTovar groupTovar, String nameMainFeature)
			throws Exception {
		GroupTovarMainFeature g = getByNameGroupTovar(groupTovar.getName());
		if (g == null) {
			g = createForGroupTovar(groupTovar);
		}
		MainFeature m = new MainFeature();
		m.setGroupTovarMainFeature(g);
		m.setName(nameMainFeature);
		if (g.getMainFeatures().contains(m)) {
			throw new Exception(nameMainFeature + " already exist in " + groupTovar.getName());
		}
		g.getMainFeatures().add(m);
		update(g);
		return g;
	}

	@Override
	public GroupTovarMainFeature getGroupTovarMainFeatureByGroupTovar(GroupTovar groupTovar) {
/*		groupTovar=getGroupTovarProvider().initialize(groupTovar.getCod());
		GroupT groupt = groupTovar.getGroupT();
*/		
		GroupTovarMainFeature groupTovarMainFeature = getByGroupTovar(groupTovar);
		return groupTovarMainFeature;
	}

/*	@Override
	public Map<GroupTovar, GroupTMainFeature> getMapGroupTovarGroupTMainFeature() {
		Criteria criteria = getSession().createCriteria(GroupTMainFeature.class);
		List<GroupTMainFeature> allGroupTMainFeature = criteria.list();
		HashMap<GroupT, GroupTMainFeature> mapGroupTMainFeature = new HashMap<GroupT, GroupTMainFeature>();
		for (GroupTMainFeature groupTMainFeature : allGroupTMainFeature) {
			mapGroupTMainFeature.put(groupTMainFeature.getGroupT(), groupTMainFeature);
		}

		ArrayList<GroupTovar> allGroupTovar = getGroupTovarProvider().getAll();
		HashMap<GroupTovar, GroupTMainFeature> map = new HashMap<GroupTovar, GroupTMainFeature>();
		for (GroupTovar groupTovar : allGroupTovar) {
			GroupTovar gt = getGroupTovarProvider().initialize(groupTovar.getCod());
			if (!gt.hasChids()) {
				GroupTMainFeature g = getGroupTMainFeatureFromMap(gt.getGroupT(),
						mapGroupTMainFeature);
				if (g == null) {
					map.remove(gt);
				} else {
					map.put(gt, g);
				}
			}
		}
		return map;

	}
*/
/*	private GroupTovarMainFeature getGroupTMainFeatureFromMap(GroupTovar groupTovar,
			HashMap<GroupTovar, GroupTovarMainFeature> mapGroupTovarMainFeature) {
		if (groupTovar == null) {
			return null;
		}
		if (groupTovar.getParent() == null) {
			return null;
		}
		if (mapGroupTovarMainFeature.containsKey(groupTovar)) {
			return mapGroupTovarMainFeature.get(groupTovar);
		} else {
			return getGroupTMainFeatureFromMap(groupTovar.getParent(), mapGroupTovarMainFeature);
		}
	}
*/
	@Override
	public GroupTovarMainFeature getByGroupTovar(GroupTovar groupTovar) {
		Criteria criteria = getSession().createCriteria(GroupTovarMainFeature.class);
		criteria.add(Restrictions.eq("groupTovar", groupTovar));
		List<GroupTovarMainFeature> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public IGroupTovarProvider getGroupTovarProvider() {
		return groupTovarProvider;
	}

	public void setGroupTovarProvider(IGroupTovarProvider groupTovarProvider) {
		this.groupTovarProvider = groupTovarProvider;
	}
	
	public IMainFeatureProvider getMainFeatureProvider() {
		return mainFeatureProvider;
	}

	public void setMainFeatureProvider(IMainFeatureProvider mainFeatureProvider) {
		this.mainFeatureProvider = mainFeatureProvider;
	}

}