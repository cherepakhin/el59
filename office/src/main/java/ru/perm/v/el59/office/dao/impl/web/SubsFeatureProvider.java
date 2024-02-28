package ru.perm.v.el59.office.dao.impl.web;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.critery.FeatureCritery;
import ru.perm.v.el59.office.critery.SubsFeatureCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.db.web.SubsFeature;
import ru.perm.v.el59.office.db.web.TypeProperty;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.iproviders.web.ISubsFeatureProvider;

public class SubsFeatureProvider extends
		GenericDaoHibernateImpl<SubsFeature, Long> implements
		ISubsFeatureProvider {

	private ITovarInfoProvider tovarInfoProvider;

	public SubsFeatureProvider(Class<SubsFeature> type) {
		super(type);
	}

	@Override
	public List<SubsFeature> getByCrritery(Object o) {
		SubsFeatureCritery c = (SubsFeatureCritery) o;
		Criteria q = getSession().createCriteria(SubsFeature.class);
		if (c.typeProperty != null) {
			q.add(Restrictions.eq("typeFeature", c.typeProperty));
		}
		if (c.seekPhrase != null) {
			q.add(Restrictions.eq("seekPhrase", c.seekPhrase));
		}
		q.addOrder(Order.asc("typeProp"));
		q.addOrder(Order.asc("seekPhrase"));
		q.addOrder(Order.asc("newValue"));
		List<SubsFeature> list = q.list();
		return list;
	}

	@Override
	public List<SubsFeature> getAll() {
		Criteria q = getSession().createCriteria(SubsFeature.class);
		q.addOrder(Order.asc("typeProp"));
		q.addOrder(Order.asc("seekPhrase"));
		q.addOrder(Order.asc("newValue"));
		List<SubsFeature> list = q.list();
		return list;
	}

	@Override
	public int process(List<SubsFeature> list) {
		int ret = 0;
		for (SubsFeature subsFeature : list) {
			String sql = "";
			if (subsFeature.getTypeProp().equals(TypeProperty.NAME)) {
				sql = "update db.tovarinfo_listfeature set name=:newvalue where name=:oldvalue";
			}
			if (subsFeature.getTypeProp().equals(TypeProperty.VAL)) {
				sql = "update db.tovarinfo_listfeature set val=:newvalue where val=:oldvalue";
			}
			Query q1 = getSession().createSQLQuery(sql);
			q1.setString("newvalue", subsFeature.getNewValue());
			q1.setString("oldvalue", subsFeature.getSeekPhrase());
			int i = q1.executeUpdate();
			ret = ret + i;
		}
		return ret;
	}

	@Override
	public int processByAll() {
		List<SubsFeature> l = getAll();
		int ret = process(l);
		return ret;
	}

	/*
	 * @Override public Long create(SubsFeature o) { SubsFeatureCritery c = new
	 * SubsFeatureCritery(); c.typeProperty=o.getTypeProp();
	 * c.seekPhrase=o.getSeekPhrase(); List<SubsFeature> list = getByCritery(c);
	 * if(list.size()>0) { SubsFeature s = list.get(0);
	 * if(!s.getNewValue().equals(o.getNewValue())) {
	 * s.setNewValue(o.getNewValue()); update(s); } return s.getN(); } else {
	 * return super.create(o); } }
	 */
	@Override
	public int processTovarInfo(TovarInfo tovarInfo) {
		int ret = 0;
		for (SubsFeature subsFeature : getAll()) {
			String sql = "";
			if (subsFeature.getTypeProp().equals(TypeProperty.NAME)) {
				sql = "update db.tovarinfo_listfeature set name=:newvalue where name=:oldvalue ";
			}
			if (subsFeature.getTypeProp().equals(TypeProperty.VAL)) {
				sql = "update db.tovarinfo_listfeature set val=:newvalue where val=:oldvalue ";
			}
			sql = sql + " and tovarinfo_nnum=:nnum";
			Query q1 = getSession().createSQLQuery(sql);
			q1.setString("newvalue", subsFeature.getNewValue());
			q1.setString("oldvalue", subsFeature.getSeekPhrase());
			q1.setInteger("nnum", tovarInfo.getNnum());
			int i = q1.executeUpdate();
			ret = ret + i;
		}
		return ret;
	}

	@Override
	public List<Feature> getFeaturesBySubsFeature(
			List<SubsFeature> listSubsFeature) {
		ArrayList<Feature> ret = new ArrayList<Feature>();
		for (SubsFeature subsFeature : listSubsFeature) {
			ret.addAll(getFeaturesBySubsFeature(subsFeature));
		}
		return ret;
	}

	@Override
	public List<Feature> getFeaturesBySubsFeature(SubsFeature subsFeature) {
		FeatureCritery featureCritery = new FeatureCritery();
		if (subsFeature.getTypeProp().equals(TypeProperty.NAME)) {
			featureCritery.setName(subsFeature.getSeekPhrase());
		}
		if (subsFeature.getTypeProp().equals(TypeProperty.VAL)) {
			featureCritery.val = subsFeature.getSeekPhrase();
		}
		return getTovarInfoProvider().getFeatureByCritery(featureCritery);
	}

	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

}
