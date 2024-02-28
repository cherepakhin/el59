package ru.perm.v.el59.office.dao.impl.subs;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.critery.MainFeatureCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.FeatureOld;
import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.db.subs.*;
import ru.perm.v.el59.office.iproviders.IFeatureOldProvider;
import ru.perm.v.el59.office.iproviders.IGroupTProvider;
import ru.perm.v.el59.office.iproviders.IGroupTovarProvider;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.iproviders.subs.ICorrectionNameProvider;
import ru.perm.v.el59.office.iproviders.subs.IGroupTovarMainFeatureProvider;
import ru.perm.v.el59.office.iproviders.subs.IMainFeatureProvider;
import ru.perm.v.el59.office.iproviders.subs.IValFeatureProvider;

import java.util.*;

public class MainFeatureProvider extends
		GenericDaoHibernateImpl<MainFeature, Long> implements
		IMainFeatureProvider {

	private static Logger LOG = Logger.getLogger(MainFeatureProvider.class);

	private static final String MAINGRP = "Основные характеристики";
	private IValFeatureProvider valFeatureProvider;
	private ICorrectionNameProvider correctionNameProvider;
	private IGroupTProvider groupTProvider;
	private IGroupTovarProvider groupTovarProvider;
	private IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider;
	private ITovarInfoProvider tovarInfoProvider;
	private IFeatureOldProvider featureOldProvider;

	public MainFeatureProvider(Class<MainFeature> type) {
		super(type);
	}

	@Override
	public List<MainFeature> getByCritery(Object critery) {
		MainFeatureCritery mainFeatureCritery = (MainFeatureCritery) critery;
		Criteria criteria = getSession().createCriteria(MainFeature.class);
		if (!mainFeatureCritery.nameMainFeature.equals("")) {
			criteria.add(Restrictions.eq("name",
					mainFeatureCritery.nameMainFeature));
		}
		if (!mainFeatureCritery.nameGroupT.equals("")) {
			Criteria criteriaGrouptMainFeature = criteria
					.createCriteria("groupTovarMainFeature");
			Criteria criteriaGroupT = criteriaGrouptMainFeature
					.createCriteria("groupTovar");
			criteriaGroupT.add(Restrictions.eq("name",
					mainFeatureCritery.nameGroupT));
		}
		if (!mainFeatureCritery.nameCorrectionName.equals("")) {
			criteria.setFetchMode("listCorrectionName", FetchMode.JOIN);
			Criteria criteriaCorrectionName = criteria
					.createCriteria("listCorrectionName");
			criteriaCorrectionName.add(Restrictions.eq("name",
					mainFeatureCritery.nameCorrectionName));
		}
		if (!mainFeatureCritery.nameCorrectionVal.equals("")) {
			criteria.setFetchMode("listValFeature", FetchMode.JOIN);
			Criteria criteriaValFeature = criteria
					.createCriteria("listValFeature");
			Criteria criteriaCorrectionVal = criteriaValFeature
					.createCriteria("listCorrectionVal");
			criteriaCorrectionVal.add(Restrictions.eq("name",
					mainFeatureCritery.nameCorrectionVal));
		}
		List<MainFeature> list = criteria.list();
		return list;
	}

	@Override
	public MainFeature init(MainFeature mainFeature) {
		mainFeature = read(mainFeature.getN());
		Hibernate.initialize(mainFeature);
		Hibernate.initialize(mainFeature.getListValFeature());
		Hibernate.initialize(mainFeature.getListCorrectionName());
		for (ValFeature valFeature : mainFeature.getListValFeature()) {
			Hibernate.initialize(valFeature.getListCorrectionVal());
		}
		return mainFeature;
	}

	public CorrectionName addCorrectionName(MainFeature mainFeature,
			String correction) throws Exception {
		if (mainFeature == null) {
			throw new Exception("mainfeature is null");
		}
		if (correction == null || correction.isEmpty()) {
			throw new Exception("correaction is null");
		}
		if (isCorrectionInMainFeature(mainFeature, correction)) {
			Iterator<CorrectionName> iter = mainFeature.getListCorrectionName()
					.iterator();
			while (iter.hasNext()) {
				CorrectionName c = iter.next();
				if (c.getName().equals(correction)) {
					return c;
				}
			}
		}
		mainFeature = init(mainFeature);
		CorrectionName c = new CorrectionName();
		c.setMainFeature(mainFeature);
		c.setName(correction.trim());
		getCorrectionNameProvider().create(c);
		mainFeature.getListCorrectionName().add(c);
		update(mainFeature);
		return c;
	}

	private boolean isCorrectionInMainFeature(MainFeature mainFeature,
			String correctionName) {
		mainFeature = init(mainFeature);
		CorrectionName c = new CorrectionName();
		c.setMainFeature(mainFeature);
		c.setName(correctionName.trim());
		return mainFeature.getListCorrectionName().contains(c);
	}

	public ValFeature addValFeature(MainFeature mainFeature, String val,
			String forTag) throws Exception {
		mainFeature = init(mainFeature);
		ValFeature valFeature = new ValFeature();
		valFeature.setMainFeature(mainFeature);
		valFeature.setName(val.trim());
		valFeature.setForTag(forTag.trim());
		if (mainFeature.getListValFeature().contains(valFeature)) {
			Iterator<ValFeature> iter = mainFeature.getListValFeature()
					.iterator();
			while (iter.hasNext()) {
				ValFeature v = iter.next();
				if (v.getName().equals(val)) {
					return v;
				}
			}
			/*
			 * throw new Exception("Значение " + val + " для хар-ки " +
			 * mainFeature.getName() + " уже существует");
			 */}
		getValFeatureProvider().create(valFeature);
		mainFeature.getListValFeature().add(valFeature);
		update(mainFeature);
		return valFeature;
	}

	public void delValFeature(ValFeature valFeature) throws Exception {
		getValFeatureProvider().delete(valFeature);
	}

	public CorrectionVal addCorrectionVal(ValFeature valFeature,
			String correction) throws Exception {
		CorrectionVal correctionVal = getValFeatureProvider().addCorrectionVal(
				valFeature, correction.trim());
		return correctionVal;
	}

	@Override
	public MainFeature createForGroupTovar(GroupTovar groupTovar,
			String nameFeature, String nameForTag) throws Exception {
		nameFeature = nameFeature.trim();
		Criteria criteria = getSession().createCriteria(MainFeature.class);
		Criteria criteriaGroupTovarMainFeature = criteria
				.createCriteria("groupTovarMainFeature");
		Criteria criteriaGroupT = criteriaGroupTovarMainFeature
				.createCriteria("groupTovar");
		criteriaGroupT.add(Restrictions.eq("name", groupTovar.getName()));
		criteria.add(Restrictions.eq("name", nameFeature));
		List<MainFeature> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		}
		criteriaGroupTovarMainFeature = getSession().createCriteria(
				GroupTovarMainFeature.class);
		criteriaGroupTovarMainFeature.add(Restrictions.eq("groupTovar",
				groupTovar));

		List<GroupTovarMainFeature> groupTovarMainFeatures = criteriaGroupTovarMainFeature
				.list();
		GroupTovarMainFeature groupTovarMainFeature;
		if (groupTovarMainFeatures.size() == 0) {
			groupTovarMainFeature = new GroupTovarMainFeature();
			groupTovarMainFeature.setGroupTovar(groupTovar);
			groupTovarMainFeature.setName(groupTovar.getName());
			Long n = getGroupTovarMainFeatureProvider().create(
					groupTovarMainFeature);
			groupTovarMainFeature.setN(n);
		} else {
			groupTovarMainFeature = groupTovarMainFeatures.get(0);
		}
		groupTovarMainFeature = (GroupTovarMainFeature) getSession().merge(
				groupTovarMainFeature);
		Hibernate.initialize(groupTovarMainFeature.getMainFeatures());
		MainFeature m = new MainFeature();
		m.setName(nameFeature.trim());
		m.setNameForTag(nameForTag.trim());
		// m.setGroupTovarMainFeature(groupTovarMainFeature);
		Long n = super.getMax();
		m.setN(n);
		groupTovarMainFeature.addMainFeature(m);
		getSession().save(m);
		// Long n = create(m);
		getGroupTovarMainFeatureProvider().update(groupTovarMainFeature);
		return m;
	}

	@Override
	public MainFeature createForGroupTovar(GroupTovar groupTovar,
			String nameMainFeature, String nameForTag, String correctionName,
			String nameValFeature, String forTagValFeature, String correctionVal)
			throws Exception {
		MainFeature mainFeature = getByGroupTovarAndManFeatureName(groupTovar,
				nameMainFeature);
		if (mainFeature == null) {
			mainFeature = createForGroupTovar(groupTovar, nameMainFeature,
					nameForTag);
		}
		if (!isCorrectionInMainFeature(mainFeature, correctionName)) {
			addCorrectionName(mainFeature, correctionName);
		}
		ValFeature valFeature = new ValFeature();
		valFeature.setMainFeature(mainFeature);
		valFeature.setName(nameValFeature);
		if (!existValFeature(mainFeature, nameValFeature)) {
			valFeature = addValFeature(mainFeature, nameValFeature,
					forTagValFeature);
		} else {
			Iterator<ValFeature> iter = mainFeature.getListValFeature()
					.iterator();
			while (iter.hasNext()) {
				ValFeature _val = iter.next();
				if (_val.equals(valFeature)) {
					valFeature = _val;
					break;
				}
			}
		}
		if (!getValFeatureProvider()
				.existCorrectName(valFeature, correctionVal)) {
			CorrectionVal _correctionVal = getValFeatureProvider()
					.addCorrectionVal(valFeature, correctionVal);
		}
		return mainFeature;
	}

	private boolean existValFeature(MainFeature mainFeature,
			String nameValFeature) {
		ValFeature valFeature = new ValFeature();
		valFeature.setMainFeature(mainFeature);
		valFeature.setName(nameValFeature);
		return mainFeature.getListValFeature().contains(valFeature);
		/*
		 * valFeature = getValFeatureProvider().init(valFeature.getN());
		 * CorrectionVal correctionVal = new CorrectionVal();
		 * correctionVal.setValFeature(valFeature);
		 * correctionVal.setName(nameCorrectionVal); return
		 * mainFeature.getListValFeature().contains(valFeature);
		 */}

	public MainFeature getByGroupTovarAndCorrectinName(GroupTovar groupTovar,
			String correctionName) {
		Criteria criteria = getSession().createCriteria(CorrectionName.class);
		criteria.add(Restrictions.eq("name", correctionName));
		// Criteria criteriaMainFeature =
		// criteria.createCriteria("mainFeature");
		Criteria criteriaGrouptMainFeature = criteria
				.createCriteria("groupTovarMainFeature");
		Criteria criteriaGroupT = criteriaGrouptMainFeature
				.createCriteria("groupTovar");
		criteriaGroupT.add(Restrictions.eq("name", groupTovar.getName()));
		List list = criteria.list();
		if (list.size() > 0) {
			list.get(0);
		}
		return null;
	}

	public MainFeature getByGroupTovarAndManFeatureName(GroupTovar groupTovar,
			String nameMainFeature) {
		Criteria criteria = getSession().createCriteria(MainFeature.class);
		criteria.add(Restrictions.eq("name", nameMainFeature));
		Criteria criteriaGrouptMainFeature = criteria
				.createCriteria("groupTovarMainFeature");
		Criteria criteriaGroupT = criteriaGrouptMainFeature
				.createCriteria("groupTovar");
		criteriaGroupT.add(Restrictions.eq("name", groupTovar.getName()));
		List<MainFeature> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public TovarInfo substituteMainFeature(TovarInfo tovarInfo)
			throws Exception {
		tovarInfo = getTovarInfoProvider().initialize(tovarInfo.getNnum());
		List<MainFeature> listMainFeature = getMainFeatureForGroupTovar(tovarInfo
				.getTovar().getGroup());
		if (listMainFeature.size() == 0) {
			return tovarInfo;
		}

		Hibernate.initialize(tovarInfo.getListFeature());

		// Делаю их все неосновными. Замену сделаю позже
		for (Feature feature : tovarInfo.getListFeature()) {
			feature.setPrmry(false);
			feature.setName(feature.getOldname());
			feature.setVal(feature.getOldval());
		}
		for (Feature feature : tovarInfo.getListFeature()) {
			for (MainFeature mainFeature : listMainFeature) {
				mainFeature = init(mainFeature);
				boolean isMainFeature = isMainFeature(mainFeature, feature);
				if (isMainFeature) {
					ValFeature foundedValFeature = findValFeature(mainFeature,
							feature);
					if (foundedValFeature != null) {
						FeatureOld featureOld = new FeatureOld(feature,
								tovarInfo);
						getFeatureOldProvider().create(featureOld);
						feature.setPrmry(true);
						feature.setName(mainFeature.getName());
						feature.setVal(foundedValFeature.getName());
					}
				}
			}
		}
		// Обновление даты и разных количеств (осн.хар-к, фото и т.п.)
		getTovarInfoProvider().update(tovarInfo);
		getSession().flush();
		getSession().clear();
		tovarInfo = getTovarInfoProvider().initialize(tovarInfo.getNnum());
		return tovarInfo;
	}

	private boolean isMainFeature(MainFeature mainFeature, Feature feature) {
		/*
		 * if (mainFeature.getName().toLowerCase().trim()
		 * .equals(feature.getName().toLowerCase().trim())) { return true; }
		 */
		for (CorrectionName correctionName : mainFeature
				.getListCorrectionName()) {
			if (feature.getName().toLowerCase().trim()
					.equals(correctionName.getName().toLowerCase().trim())
					|| feature.getName().toLowerCase().trim()
							.equals(mainFeature.getName().toLowerCase().trim())) {
				for (ValFeature valFeature : mainFeature.getListValFeature()) {
					if (valFeature.getName().trim().toLowerCase()
							.equals(feature.getVal().trim().toLowerCase())) {
						return true;
					}
					for (CorrectionVal correctionVal : valFeature
							.getListCorrectionVal()) {
						if (correctionVal.getName().trim().toLowerCase()
								.equals(feature.getVal().trim().toLowerCase())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private ValFeature findValFeature(MainFeature mainFeature, Feature feature) {
		for (ValFeature valFeature : mainFeature.getListValFeature()) {
			if (valFeature.getName().toLowerCase().trim()
					.equals(feature.getOldval().toLowerCase().trim())) {
				return valFeature;
			}
			for (CorrectionVal correctionVal : valFeature
					.getListCorrectionVal()) {
				if (feature.getOldval().trim().toLowerCase().trim()
						.equals(correctionVal.getName().toLowerCase().trim())) {
					return valFeature;
				}
			}
		}
		return null;
	}

	public List<MainFeature> getUncomleateSubstities(TovarInfo tovarInfo)
			throws Exception {
		List<MainFeature> listMainFeature = getMainFeatureForGroupTovar(tovarInfo
				.getTovar().getGroup());
		if (listMainFeature.size() == 0) {
			return listMainFeature;
		}
		Hibernate.initialize(tovarInfo.getListFeature());
		ArrayList<MainFeature> addListMainFeature = new ArrayList<MainFeature>();
		addListMainFeature.addAll(listMainFeature);
		for (MainFeature mainFeature : listMainFeature) {
			for (Feature feature : tovarInfo.getListFeature()) {
				if (feature.getPrmry()
						&& feature.getName().equals(mainFeature.getName())) {
					addListMainFeature.remove(mainFeature);
				}
			}
		}

		return addListMainFeature;
	}

	public List<MainFeature> getMainFeatureForGroupT(GroupTovar groupTovar)
			throws Exception {
		if (groupTovar == null) {
			throw new Exception("GroupTovar is null");
		}
		Hibernate.initialize(groupTovar);
		GroupTovarMainFeature groupTMainFeature = getGroupTovarMainFeatureProvider()
				.getByGroupTovar(groupTovar);
		if (groupTMainFeature != null) {
			Hibernate.initialize(groupTMainFeature.getMainFeatures());
			return groupTMainFeature.getMainFeatures();
		} else {
			return new ArrayList<MainFeature>();
		}
	}

	@Override
	public List<MainFeature> getMainFeatureForGroupTovar(GroupTovar groupTovar)
			throws Exception {
		if (groupTovar == null) {
			throw new Exception("GroupTovar is null");
		}

		GroupTovarMainFeature groupTovarMainFeature = getGroupTovarMainFeatureProvider()
				.getGroupTovarMainFeatureByGroupTovar(groupTovar);
		if (groupTovarMainFeature != null) {
			ArrayList<MainFeature> ret = new ArrayList<MainFeature>();
			for (MainFeature mainFeature : groupTovarMainFeature
					.getMainFeatures()) {
				mainFeature = init(mainFeature);
				ret.add(mainFeature);
			}
			return ret;
		} else {
			return new ArrayList<MainFeature>();
		}
	}

	@Override
	public void deleteCorrectionName(CorrectionName selectedCorrectionName)
			throws Exception {
		MainFeature mainFeature = init(selectedCorrectionName.getMainFeature());
		mainFeature.getListCorrectionName().remove(selectedCorrectionName);
		update(mainFeature);
		getCorrectionNameProvider().delete(selectedCorrectionName);
	}

	@Override
	public void delete(Set<MainFeature> _listMainFeature) throws Exception {
		Iterator<MainFeature> iterator = _listMainFeature.iterator();
		while (iterator.hasNext()) {
			MainFeature mainFeature = iterator.next();
			iterator.remove();
			delete(mainFeature);
		}
	}

	@Override
	public void deleteCorrectionName(Set<CorrectionName> _setCorrectionName)
			throws Exception {
		Iterator<CorrectionName> iterator = _setCorrectionName.iterator();
		while (iterator.hasNext()) {
			CorrectionName correctionName = iterator.next();
			iterator.remove();
			deleteCorrectionName(correctionName);
		}
	}

	@Override
	public void deleteValFeature(Set<ValFeature> _setValFeature)
			throws Exception {
		Iterator<ValFeature> iterator = _setValFeature.iterator();
		while (iterator.hasNext()) {
			ValFeature valFeature = iterator.next();
			iterator.remove();
			getValFeatureProvider().delete(valFeature);
		}
	}

	@Override
	public void delete(MainFeature o) throws Exception {
		MainFeature mf = init(o);
		deleteCorrectionName(mf.getListCorrectionName());
		deleteValFeature(mf.getListValFeature());
		mf.getListValFeature().clear();
		GroupTovarMainFeature groupTovarMainFeature = mf
				.getGroupTovarMainFeature();
		groupTovarMainFeature.removeMainFeature(mf);
		getGroupTovarMainFeatureProvider().update(groupTovarMainFeature);
		super.delete(mf);
	}

	@Override
	public Integer doSubsMainFeature(GroupTovar groupTovar) throws Exception {
		LOG.info(String.format("Начало замены группы %s", groupTovar.getName()));
		List<MainFeature> mainFeatures = getMainFeatureForGroupT(groupTovar);
		GroupTovar gtree = getGroupTovarProvider().getTree(groupTovar);

		HashSet<String> codsGroup = new HashSet<String>();
		HashSet<String> a = getCod(gtree, codsGroup);
		codsGroup.addAll(a);
		String sql = "update db.tovarinfo_listfeature set " + "name=oldname,"
				+ "prmry=false," + "val=oldval, " + "positionprmry=-1 "
				+ "from db.tovar where " + "tovarinfo_nnum=nnum and "
				+ "group_cod in (:cods) ";

		Query q1 = getSession().createSQLQuery(sql);
		q1.setParameterList("cods", codsGroup);
		q1.executeUpdate();
		Integer count = 0;
		int i = 0;
		for (MainFeature mf : mainFeatures) {
			LOG.info(String.format("Начало замены хар-ки %s (%d из %d)",
					mf.getName(), i, mainFeatures.size()));
			i++;
			Integer _count = doSubsMainFeature(mf);
			LOG.info(String.format("Конец замены хар-ки %s. Заменено %d.",
					mf.getName(), _count));
			count = count + _count;
		}
		LOG.info(String.format("Конец замены группы %s. Заменено %d.",
				groupTovar.getName(), count));
		return count;
	}

	@Override
	public Integer doSubsMainFeature(MainFeature mainFeature) {
		int positionPrmry = mainFeature.getGroupTovarMainFeature()
				.getMainFeatures().indexOf(mainFeature);
		int ret = 0;
		mainFeature = init(mainFeature);
		HashSet<String> codsGroup = new HashSet<String>();
		GroupTovar groupTovar = mainFeature.getGroupTovarMainFeature()
				.getGroupTovar();
		GroupTovar gtree = getGroupTovarProvider().getTree(groupTovar);
		HashSet<String> a = getCod(gtree, codsGroup);
		codsGroup.addAll(a);
		ArrayList<String> listValCorrectionVal = new ArrayList<String>();
		for (ValFeature valFeature : mainFeature.getListValFeature()) {
			for (CorrectionVal correctionVal : valFeature
					.getListCorrectionVal()) {
				listValCorrectionVal.add(correctionVal.getName());
			}
		}
		if (listValCorrectionVal.size() == 0) {
			return 0;
		}
		// Изменяемые хар-ки отправить в архив
		String sql = "insert into db.featureold(n,ddate,name,oldname,val,oldval,prmry,grp,tovarinfo_nnum) "
				+ "select nextval('db.featureold_n_seq'),now(),ti.name,ti.oldname,ti.val,ti.oldval,ti.prmry,ti.grp,tovarinfo_nnum "
				+ "from db.tovarinfo_listfeature ti, db.tovar t where "
				+ "lower(oldname)=lower(:correctionName) and "
				+ "lower(oldval)=lower(:correctionVal) and "
				+ "tovarinfo_nnum = nnum and " + "group_cod in (:cods)";

		Query q1 = getSession().createSQLQuery(sql);
		/*
		 * for (CorrectionName correctionName : mainFeature
		 * .getListCorrectionName()) { for (ValFeature valFeature :
		 * mainFeature.getListValFeature()) { for (CorrectionVal correctionVal :
		 * valFeature .getListCorrectionVal()) {
		 * q1.setParameter("correctionName", correctionName.getName());
		 * q1.setParameter("correctionVal", correctionVal.getName());
		 * q1.setParameterList("cods", codsGroup); q1.executeUpdate(); } } }
		 */

		// Определение списка изменяемых товаров listUpdatedNnumTovarInfo
		sql = "select tovarinfo_nnum from db.tovarinfo_listfeature ti, db.tovar t "
				+ "where "
				+ "ti.tovarinfo_nnum=t.nnum and "
				+ "t.group_cod in (:cods) and "
				+ "ti.oldname=:correctionName and "
				+ "ti.oldval in (:correctionVals)";

		q1 = getSession().createSQLQuery(sql);
		HashSet<Integer> listUpdatedNnumTovarInfo = new HashSet<Integer>();
		for (CorrectionName correctionName : mainFeature
				.getListCorrectionName()) {
			for (ValFeature valFeature : mainFeature.getListValFeature()) {
				ArrayList<String> correctionVals = new ArrayList<String>();
				correctionVals.add("********");
				for (CorrectionVal correctionVal : valFeature
						.getListCorrectionVal()) {
					// correctionVals.add(correctionVal.getName().toLowerCase());
					correctionVals.add(correctionVal.getName());
				}
				q1.setParameter("correctionName", correctionName.getName());
				q1.setParameterList("correctionVals", correctionVals);
				q1.setParameterList("cods", codsGroup);
				List<Integer> l = q1.list();
				listUpdatedNnumTovarInfo.addAll(l);
			}
		}
		LOG.info(String.format("Хар-ка %s, индекс %d", mainFeature.getName(),
				positionPrmry));
		// Обновление названия, типа хар-ки. И установка флага основная хар-ка
		sql = "update db.tovarinfo_listfeature set "
				+ "name=:mainfeature_name," + "prmry=true," + "grp=:grp,"
				+ "val=:valFeature, " + "positionPrmry=:positionPrmry "
				+ "from db.tovar where " + "tovarinfo_nnum=nnum and "
				+ "oldname=:correctionName and "
				+ "oldval in (:correctionVals) and " + "group_cod in (:cods) ";

		q1 = getSession().createSQLQuery(sql);

		for (CorrectionName correctionName : mainFeature
				.getListCorrectionName()) {
			for (ValFeature valFeature : mainFeature.getListValFeature()) {
				ArrayList<String> correctionVals = new ArrayList<String>();
				correctionVals.add("********");
				for (CorrectionVal correctionVal : valFeature
						.getListCorrectionVal()) {
					// correctionVals.add(correctionVal.getName().toLowerCase());
					correctionVals.add(correctionVal.getName());
				}
				q1.setParameter("mainfeature_name", mainFeature.getName());
				q1.setParameter("correctionName", correctionName.getName());
				q1.setParameterList("correctionVals", correctionVals);
				q1.setParameter("valFeature", valFeature.getName());
				q1.setParameter("positionPrmry", positionPrmry);

				q1.setParameter("grp", MAINGRP);

				q1.setParameterList("cods", codsGroup);

				int i = q1.executeUpdate();
				ret = ret + i;
			}
		}

		// Обновление даты
		if (listUpdatedNnumTovarInfo.size() > 0) {
			sql = "update db.tovar set dateChanged=:dateChanged where "
					+ "nnum in (:listUpdatedNnumTovarInfo)";
			q1 = getSession().createSQLQuery(sql);
			Date today = new Date();
			q1.setParameter("dateChanged", today);
			q1.setParameterList("listUpdatedNnumTovarInfo",
					listUpdatedNnumTovarInfo);
			q1.executeUpdate();
		}

		ArrayList<Integer> _listNnum = new ArrayList<Integer>();
		_listNnum.addAll(listUpdatedNnumTovarInfo);
		resortFeaturesByListNnnum(_listNnum);

		for (Integer nnum : listUpdatedNnumTovarInfo) {
			TovarInfo ti = getTovarInfoProvider().read(nnum);
			try {
				getTovarInfoProvider().calcMainFeature(ti);
			} catch (Exception e) {
				Logger.getLogger(this.getClass()).error(
						"Ошибка при расчете к-ва осн.хар-к.", e);
				e.printStackTrace();
			}
		}
		return ret;
	}

	private HashSet<String> getCod(GroupTovar groupTovar, HashSet<String> ret) {
		if (groupTovar.hasChids()) {
			for (GroupTovar _groupTovar : groupTovar.getChilds()) {
				getCod(_groupTovar, ret);
			}
		} else {
			if (!ret.contains(groupTovar.getName())) {
				ret.add(groupTovar.getCod());
			}
			// System.out.println(groupTovar.getCod()+";"+groupTovar.getName());
		}
		return ret;
	}

	/*
	 * private HashSet<GroupT> getListGroupT(GroupT treeGroupT, HashSet<GroupT>
	 * listGroupT) { if (treeGroupT.hasChids()) { for (GroupT groupT :
	 * treeGroupT.getChilds()) { getListGroupT(groupT, listGroupT); } } else {
	 * listGroupT.add(treeGroupT); } return listGroupT; }
	 */
	@Override
	public void up(MainFeature mainFeature) throws Exception {
		GroupTovarMainFeature groupTovarMainFeature = mainFeature
				.getGroupTovarMainFeature();
		groupTovarMainFeature = getGroupTovarMainFeatureProvider().read(
				groupTovarMainFeature.getN());
		Hibernate.initialize(groupTovarMainFeature.getMainFeatures());
		int i = groupTovarMainFeature.getMainFeatures().indexOf(mainFeature);
		if (i > 0) {
			MainFeature predMainFeature = groupTovarMainFeature
					.getMainFeatures().get(i - 1);
			groupTovarMainFeature.getMainFeatures().set(i - 1, mainFeature);
			groupTovarMainFeature.getMainFeatures().set(i, predMainFeature);
			getGroupTovarMainFeatureProvider().update(groupTovarMainFeature);
		}
	}

	@Override
	public void down(MainFeature mainFeature) throws Exception {
		GroupTovarMainFeature groupTovarMainFeature = mainFeature
				.getGroupTovarMainFeature();
		groupTovarMainFeature = getGroupTovarMainFeatureProvider().read(
				groupTovarMainFeature.getN());
		Hibernate.initialize(groupTovarMainFeature.getMainFeatures());
		int i = groupTovarMainFeature.getMainFeatures().indexOf(mainFeature);
		if (i < groupTovarMainFeature.getMainFeatures().size() - 1) {
			MainFeature nextMainFeature = groupTovarMainFeature
					.getMainFeatures().get(i + 1);
			groupTovarMainFeature.getMainFeatures().set(i + 1, mainFeature);
			groupTovarMainFeature.getMainFeatures().set(i, nextMainFeature);
			getGroupTovarMainFeatureProvider().update(groupTovarMainFeature);
		}
	}

	@Override
	public int resortFeaturesByListNnnum(List<Integer> listNnum) {
		/*
		 * String sql = "select db.reorder_feature_tovarinfo(ARRAY["; for
		 * (Integer nnum : listNnum) { sql = sql + nnum + ","; } sql = sql +
		 * "0])"; SQLQuery q = getSession().createSQLQuery(sql); List l =
		 * q.list();
		 */
		return listNnum.size();
	}

	@Override
	public int resortFeaturesByListTovarInfo(List<TovarInfo> listTovarInfo) {
		// TODO Убрал на время. Попробую не сортировать хар-ки. Т.е. не
		// перемещать основные вверх.
		return listTovarInfo.size();
		/*
		 * ArrayList<Integer> listNnum = new ArrayList<Integer>(); for
		 * (TovarInfo tovarInfo : listTovarInfo) {
		 * listNnum.add(tovarInfo.getNnum()); } return
		 * resortFeaturesByListNnnum(listNnum);
		 */
	}

	public IValFeatureProvider getValFeatureProvider() {
		return valFeatureProvider;
	}

	public void setValFeatureProvider(IValFeatureProvider valFeatureProvider) {
		this.valFeatureProvider = valFeatureProvider;
	}

	public ICorrectionNameProvider getCorrectionNameProvider() {
		return correctionNameProvider;
	}

	public void setCorrectionNameProvider(
			ICorrectionNameProvider correctionNameProvider) {
		this.correctionNameProvider = correctionNameProvider;
	}

	public IGroupTProvider getGroupTProvider() {
		return groupTProvider;
	}

	public void setGroupTProvider(IGroupTProvider groupTProvider) {
		this.groupTProvider = groupTProvider;
	}

	public IGroupTovarProvider getGroupTovarProvider() {
		return groupTovarProvider;
	}

	public void setGroupTovarProvider(IGroupTovarProvider groupTovarProvider) {
		this.groupTovarProvider = groupTovarProvider;
	}

	public IFeatureOldProvider getFeatureOldProvider() {
		return featureOldProvider;
	}

	public void setFeatureOldProvider(IFeatureOldProvider featureOldProvider) {
		this.featureOldProvider = featureOldProvider;
	}

	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

	public IGroupTovarMainFeatureProvider getGroupTovarMainFeatureProvider() {
		return groupTovarMainFeatureProvider;
	}

	public void setGroupTovarMainFeatureProvider(
			IGroupTovarMainFeatureProvider groupTovarMainFeatureProvider) {
		this.groupTovarMainFeatureProvider = groupTovarMainFeatureProvider;
	}

	@Override
	public boolean copyMainFeature(GroupTovar srcGroup, GroupTovar dstGroup,
			List<MainFeature> listMainFeature) throws Exception {
		// TODO Осн.хар-ки. Копирование шаблона подстановки между группами
		return false;
	}

}
