package ru.perm.v.el59.office.dao.impl.routedoc;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.DocFile;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.routedoc.ReestrDoc;
import ru.perm.v.el59.office.iproviders.IDocFileProvider;
import ru.perm.v.el59.office.iproviders.IManagerProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IReestrDocProvider;
import ru.perm.v.el59.office.iproviders.routedoc.ReestrDocCritery;
import ru.perm.v.el59.office.util.Helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReestrDocProvider extends GenericDaoHibernateImpl<ReestrDoc, Long>
		implements IReestrDocProvider {

	private IDocFileProvider docFileProvider;
	private IManagerProvider managerProvider;

	public ReestrDocProvider(Class<ReestrDoc> type) {
		super(type);
	}

	@Override
	public List<ReestrDoc> getByCritery(Object critery) {
		ReestrDocCritery c = (ReestrDocCritery) critery;
		Criteria criteria = getSession().createCriteria(ReestrDoc.class);
		if (c.fromDate != null && c.toDate != null) {
			criteria.add(Restrictions.ge("ddate", c.fromDate));
			criteria.add(Restrictions.le("ddate", c.toDate));
		}
		if (c.manager != null) {
			criteria.add(Restrictions.eq("manager", c.manager));
		}
		criteria.addOrder(Order.asc("n"));
		List<ReestrDoc> list = criteria.list();
		return list;
	}

	@Override
	public String delete(ReestrDoc reestr, Manager manager) throws Exception {
		if (!reestr.getManager().equals(manager)) {
			return "Вы не можете удалить этот реестр.Он не ваш.";
		}
		if (reestr.getAgreed()) {
			return "Вы не можете удалить этот реестр.Он согласован.\n"
					+ "Перед удалением необходимо отменить согласование";
		}
		super.delete(reestr);
		return "";
	}

	@Override
	public String agree(ReestrDoc reestr, Boolean agree, Manager managerAgreed)
			throws Exception {
		Date dateAgreed = new Date();
		if (!agree) {
			managerAgreed = getManagerProvider().getNullManager();
			dateAgreed = Helper.getNullDate();
		}
		reestr.setManagerAgreed(managerAgreed);
		reestr.setAgreed(agree);
		reestr.setDateAgreed(dateAgreed);
		update(reestr);

		String sql = "update DocFile set "
				+ "managerAgreed=:managerAgreed,dateAgreed=:dateAgreed,agreed=:agreed "
				+ "where n in (:list)";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("managerAgreed", managerAgreed);
		q1.setParameter("dateAgreed", dateAgreed);
		q1.setParameter("agreed", agree);
		ArrayList<Long> list = new ArrayList<Long>();
		for (DocFile docFile : reestr.getListDocFile()) {
			list.add(docFile.getN());
		}
		q1.setParameterList("list", list);
		int ret = q1.executeUpdate();
		return "";
	}

	@Override
	public ReestrDoc addDocFile(ReestrDoc reestrDoc, DocFile docFile)
			throws Exception {
		reestrDoc = initListDocFile(reestrDoc);
		reestrDoc.getListDocFile().add(docFile);
		reestrDoc.setSumma(reestrDoc.getSumma().add(docFile.getSumma()));
		update(reestrDoc);

		return reestrDoc;
	}

	@Override
	public void removeDocFile(DocFile docFile) throws Exception {
		String sql = "select r from ReestrDoc r join r.listDocFile docfile where docfile.n=:n";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("n", docFile.getN());
		List<ReestrDoc> listReestr = q1.list();
		for (ReestrDoc reestrDoc : listReestr) {
			removeDocFile(reestrDoc, docFile);
		}
	}

	@Override
	public ReestrDoc removeDocFile(ReestrDoc reestrDoc, DocFile docFile)
			throws Exception {
		reestrDoc = initListDocFile(reestrDoc);
		reestrDoc.getListDocFile().remove(docFile);
		reestrDoc.setSumma(reestrDoc.getSumma().subtract(docFile.getSumma()));
		update(reestrDoc);
		getSession().flush();
		return reestrDoc;
	}

	@Override
	public String pay(ReestrDoc reestr, Boolean pay, Manager manager)
			throws Exception {
		reestr.setPaid(pay);
		update(reestr);
		return "";
	}

	@Override
	public Long create(ReestrDoc o) throws Exception {
		if (o.getDateAgreed() == null) {
			o.setDateAgreed(Helper.getNullDate());
		}
		if (o.getManagerAgreed() == null) {
			Manager m = getManagerProvider().getNullManager();
			o.setManagerAgreed(m);
		}
		return super.create(o);
	}

	public IManagerProvider getManagerProvider() {
		return managerProvider;
	}

	public void setManagerProvider(IManagerProvider managerProvider) {
		this.managerProvider = managerProvider;
	}

	@Override
	public ReestrDoc initListDocFile(ReestrDoc reestrDoc) {
		reestrDoc = read(reestrDoc.getN());
		Hibernate.initialize(reestrDoc.getListDocFile());
		return reestrDoc;
	}

	public IDocFileProvider getDocFileProvider() {
		return docFileProvider;
	}

	public void setDocFileProvider(IDocFileProvider docFileProvider) {
		this.docFileProvider = docFileProvider;
	}

	@Override
	public void update(ReestrDoc o) throws Exception {
		BigDecimal sum = new BigDecimal("0.00");
		for (DocFile docFile : o.getListDocFile()) {
			sum = sum.add(docFile.getSumma());
		}
		o.setSumma(sum);
		super.update(o);
	}

}
