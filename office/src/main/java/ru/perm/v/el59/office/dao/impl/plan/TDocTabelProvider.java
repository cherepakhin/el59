package ru.perm.v.el59.office.dao.impl.plan;

import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.TypeDoc;
import ru.perm.v.el59.office.db.plan.*;
import ru.perm.v.el59.office.db.service.TDoc;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;
import ru.perm.v.el59.office.iproviders.plan.ITDocTabelProvider;
import ru.perm.v.el59.office.iproviders.plan.IUserZPProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TDocTabelProvider extends GenericDaoHibernateImpl<TDocTabel, Long>
		implements ITDocTabelProvider {

	private IUserZPProvider userZPProvider;
	private IGenericDao typedocProvider;
	private IGenericDao tdocProvider;

	@SuppressWarnings("unchecked")
	public TDocTabelProvider(Class<TDocTabel> type) {
		super(type);
	}

	@Override
	public List<TDocTabel> getByPlan(Plan plan) {
		List<TDocTabel> ret = new ArrayList<TDocTabel>();
		List<UserZP> users = getUserZPProvider().getByNameAndPlan(null, plan);
		Long l = null;
		for (UserZP userZP : users) {
			userZP = (UserZP) getUserZPProvider().initialize(userZP.getN());
			userZP = getUserZPProvider().loadTabel(userZP.getN());
			for (Tabel tabel : userZP.getTabel()) {
				for (Smena smena : tabel.getSmena().values()) {
					for (TDocTabel tdoc : smena.getTdoctabel()) {
						ret.add(tdoc);
					}
				}
			}
		}
		return ret;
	}

	public IUserZPProvider getUserZPProvider() {
		return userZPProvider;
	}

	public void setUserZPProvider(IUserZPProvider userZPProvider) {
		this.userZPProvider = userZPProvider;
	}

	@Override
	public TDocTabel create(TDocTabel tdocTabel, Manager currentUser)
			throws Exception {
		TDoc tdoc = new TDoc();
		tdoc.setAutor(currentUser);
		tdoc.setDdate(new Date());
		TypeDoc typedoc = (TypeDoc) getTypedocProvider().getByCritery(
				new CommonCritery(tdocTabel.getDescriptionClass())).get(0);
		tdoc.setTypeDoc(typedoc);
		TDoc rootparent = (TDoc) getTdocProvider().read(0L);
		tdoc.setParent(rootparent);
		tdoc.setRootDoc(rootparent);
		getTdocProvider().create(tdoc);
		tdoc.setLastTDoc(tdoc);
		tdocTabel.setTdoc(tdoc);
		tdocTabel.setN(tdoc.getN());
//		getSession().save(tdocTabel);
		return tdocTabel;
	}

	public IGenericDao getTypedocProvider() {
		return typedocProvider;
	}

	public void setTypedocProvider(IGenericDao typedocProvider) {
		this.typedocProvider = typedocProvider;
	}

	public IGenericDao getTdocProvider() {
		return tdocProvider;
	}

	public void setTdocProvider(IGenericDao tdocProvider) {
		this.tdocProvider = tdocProvider;
	}
}
