package ru.perm.v.el59.office.dao.impl.routedoc;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.office.db.dto.FileAttach;
import ru.perm.v.el59.office.db.routedoc.PlanDownload;
import ru.perm.v.el59.office.db.routedoc.PlanDownloadSum;
import ru.perm.v.el59.office.db.routedoc.TypeFileSum;
import ru.perm.v.el59.office.iproviders.IDocProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.ITypeFileProvider;
import ru.perm.v.el59.office.iproviders.critery.PlanDownloadCritery;
import ru.perm.v.el59.office.iproviders.emailer.IEmailer;
import ru.perm.v.el59.office.iproviders.routedoc.CrossPlanDownloadSum;
import ru.perm.v.el59.office.iproviders.routedoc.IPathPageProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IPlanDownloadProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IPlanDownloadSumProvider;
import ru.perm.v.el59.office.util.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PlanDownloadSumProvider extends
		GenericDaoHibernateImpl<PlanDownloadSum, Long> implements
		IPlanDownloadSumProvider {

	private IPlanDownloadProvider planDownloadProvider;
	private IPathPageProvider pathPageProvider;
	private IShopProvider shopProvider;
	private ITypeFileProvider typeFileProvider;
	private IDocProvider docProvider;
	private IEmailer emailer;

	private String nameTypeFileOrder;
	private String nameTypeFileInvoice;
	private String nameTypeFileReceive;

	private TypeFile typeFileOrder;
	private TypeFile typeFileInvoice;
	private TypeFile typeFileReceive;

	public PlanDownloadSumProvider(Class<PlanDownloadSum> type) {
		super(type);
	}

	@Override
	public List<CrossPlanDownloadSum> getByPlanDownload(
			PlanDownloadCritery planDownloadCritery)
			throws Exception {
		Criteria criteria = getSession().createCriteria(PlanDownloadSum.class);
		Criteria criteriaPlanDownload = criteria.createCriteria("planDownload");
		criteriaPlanDownload.add(Restrictions.eq("ddate",
				planDownloadCritery.ddate));
		if (planDownloadCritery.listShop.size() > 0) {
			criteria.add(Restrictions.in("shop", planDownloadCritery.listShop));
		}
		List<PlanDownloadSum> listPlanDownloadSum = criteria.list();
		// Установка сумм заказов, счетов,отгрузок и размещения документов по
		// типам
		for (PlanDownloadSum planDownloadSum : listPlanDownloadSum) {
			fill(planDownloadSum);
			List<Doc> listDoc = planDownloadSum.getListdoc();
			for (Doc doc : listDoc) {
				List<DocFile> listDocFile = getDocProvider()
						.getListDocFile(doc);
				for (DocFile docFile : listDocFile) {
					if (docFile.getTypeFile().equals(
							planDownloadSum.getOrder().getTypeFile())) {
						planDownloadSum.getOrder().setSumma(
								planDownloadSum.getOrder().getSumma()
										.add(docFile.getSumma()).setScale(2, RoundingMode.HALF_UP));
						planDownloadSum.getOrder().getListDocFile()
								.add(docFile);
					}
					if (docFile.getTypeFile().equals(
							planDownloadSum.getInvoice().getTypeFile())) {
						planDownloadSum.getInvoice().setSumma(
								planDownloadSum.getInvoice().getSumma()
										.add(docFile.getSumma()).setScale(2, RoundingMode.HALF_UP));
						planDownloadSum.getInvoice().getListDocFile()
								.add(docFile);
					}
					if (docFile.getTypeFile().equals(
							planDownloadSum.getReceive().getTypeFile())) {
						planDownloadSum.getReceive().setSumma(
								planDownloadSum.getReceive().getSumma()
										.add(docFile.getSumma()).setScale(2, RoundingMode.HALF_UP));
						planDownloadSum.getReceive().getListDocFile()
								.add(docFile);
					}
				}
			}
		}
		// Установка меток отсылки
		// Если хотя-бы один файл не отослан, то общая метка не отослан
		for (PlanDownloadSum planDownloadSum : listPlanDownloadSum) {
			boolean sending = true;
			for (DocFile docFile : planDownloadSum.getOrder().getListDocFile()) {
				if (docFile.getDateSending().equals(Helper.getNullDate())) {
					sending = false;
				}
			}
			planDownloadSum.getOrder().setSending(sending);
			sending = true;
			for (DocFile docFile : planDownloadSum.getInvoice()
					.getListDocFile()) {
				System.out.println(docFile);
				if (docFile.getDateSending() == null
						|| docFile.getDateSending()
								.equals(Helper.getNullDate())) {
					sending = false;
				}
			}
			planDownloadSum.getInvoice().setSending(sending);
			sending = true;
			for (DocFile docFile : planDownloadSum.getReceive()
					.getListDocFile()) {
				if (docFile.getDateSending().equals(Helper.getNullDate())) {
					sending = false;
				}
			}
			planDownloadSum.getReceive().setSending(sending);
		}

		HashMap<Shop, CrossPlanDownloadSum> hash = new HashMap<Shop, CrossPlanDownloadSum>();
		for (PlanDownloadSum planDownloadSum : listPlanDownloadSum) {
			if (!isZero(planDownloadSum)) {
				if (!hash.containsKey(planDownloadSum.getShop())) {
					CrossPlanDownloadSum crossPlanDownloadSum = new CrossPlanDownloadSum();
					crossPlanDownloadSum.setShop(planDownloadSum.getShop());
					crossPlanDownloadSum.getListPlanDownloadSum().add(
							planDownloadSum);
					hash.put(planDownloadSum.getShop(), crossPlanDownloadSum);
				} else {
					hash.get(planDownloadSum.getShop())
							.getListPlanDownloadSum().add(planDownloadSum);
				}
			}
		}
		ArrayList<CrossPlanDownloadSum> ret = new ArrayList<CrossPlanDownloadSum>();
		ret.addAll(hash.values());
		Collections.sort(ret);
		return ret;
	}

	/**
	 * Проверка что суммы не раны нулю
	 * 
	 * @return
	 */
	private boolean isZero(PlanDownloadSum planDownloadSum) {
		boolean ret = true;
		if (planDownloadSum.getPlan().compareTo(BigDecimal.ZERO) != 0) {
			ret = false;
		}
		if (planDownloadSum.getOrder() != null
				&& planDownloadSum.getOrder().getSumma()
						.compareTo(BigDecimal.ZERO) != 0) {
			ret = false;
		}
		if (planDownloadSum.getInvoice() != null
				&& planDownloadSum.getInvoice().getSumma()
						.compareTo(BigDecimal.ZERO) != 0) {
			ret = false;
		}
		if (planDownloadSum.getReceive() != null
				&& planDownloadSum.getReceive().getSumma()
						.compareTo(BigDecimal.ZERO) != 0) {
			ret = false;
		}
		return ret;
	}

	private void fill(PlanDownloadSum planDownloadSum) {
		if (planDownloadSum.getOrder() == null) {
			planDownloadSum.setOrder(new TypeFileSum(getTypeFileOrder()));
		}
		if (planDownloadSum.getInvoice() == null) {
			planDownloadSum.setInvoice(new TypeFileSum(getTypeFileInvoice()));
		}
		if (planDownloadSum.getReceive() == null) {
			planDownloadSum.setReceive(new TypeFileSum(getTypeFileReceive()));
		}

	}

	@Override
	public PlanDownloadSum createPlanDownloadSum(Shop shop,
			Date datePlanDownload, Contragent contragent, Manager manager)
			throws Exception {
		PlanDownload planDownload = getPlanDownloadProvider().getBy(shop,
				datePlanDownload, contragent);
		if (planDownload != null) {
			return createPlanDownloadSum(shop, planDownload, contragent,
					manager);
		} else {
			throw new Exception("Общий план загрузок не создан"
					+ shop.getName() + ";"
					+ Helper.getDateFornmatter().format(datePlanDownload) + ";"
					+ contragent.getName());
		}
	}

	@Override
	public PlanDownloadSum createPlanDownloadSum(Shop shop, PlanDownload plan,
			Contragent contragent, Manager manager) throws Exception {
		if (isExist(shop, plan, contragent)) {
			throw new Exception("План уже существует " + shop.getName() + ";"
					+ contragent.getName() + ";"
					+ Helper.getDateFornmatter().format(plan.getDdate()));
		}
		PlanDownloadSum p = new PlanDownloadSum();
		p.setPlanDownload(plan);
		p.setShop(shop);
		p.setOrder(new TypeFileSum(getTypeFileOrder()));
		p.setInvoice(new TypeFileSum(getTypeFileInvoice()));
		p.setReceive(new TypeFileSum(getTypeFileReceive()));
		p.setManager(manager);
		p.setContragent(contragent);
		create(p);
		getSession().flush();
		return p;
	}

	@Override
	public PlanDownloadSum getPlanDownloadSum(Shop shop, PlanDownload plan,
			Contragent contragent) {
		Criteria criteria = getSession().createCriteria(PlanDownloadSum.class);
		criteria.add(Restrictions.eq("shop", shop));
		criteria.add(Restrictions.eq("planDownload", plan));
		criteria.add(Restrictions.eq("contragent", contragent));
		List list = criteria.list();
		if (list.size() > 0) {
			return (PlanDownloadSum) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void delete(Shop shop, PlanDownload plan, Contragent contragent)
			throws Exception {
		PlanDownloadSum p = getPlanDownloadSum(shop, plan, contragent);
		if (p != null) {
			delete(p);
		}
	}

	private boolean isExist(Shop shop, PlanDownload plan, Contragent contragent) {
		return getPlanDownloadSum(shop, plan, contragent) != null;
	}

	@Override
	public void delete(CrossPlanDownloadSum crossPlanDownloadSum,
			Contragent contragent) throws Exception {
		Date ddate = crossPlanDownloadSum.getListPlanDownloadSum().get(0)
				.getPlanDownload().getDdate();
		Criteria criteria = getSession().createCriteria(PlanDownloadSum.class);
		Criteria criteriaPlanDownload = criteria.createCriteria("planDownload");
		criteriaPlanDownload.add(Restrictions.eq("ddate", ddate));
		criteria.add(Restrictions.eq("contragent", contragent));
		List<PlanDownloadSum> listPlanDownloadSum = criteria.list();
		for (PlanDownloadSum planDownloadSum : listPlanDownloadSum) {
			for (Doc doc : planDownloadSum.getListdoc()) {
				getDocProvider().delete(doc);
			}
			delete(planDownloadSum);
		}
	}

	@Override
	public Long create(PlanDownloadSum o) throws Exception {
		if (o.getListdoc().size() == 0) {
			Doc doc = createDoc(o);
			o.getListdoc().add(doc);
			getPathPageProvider().createInCurrentPathPage(doc);
		}
		return super.create(o);
	}

	@Override
	public void update(PlanDownloadSum o) throws Exception {
		if (o.getN() < 0) {
			Long n = create(o);
			o.setN(n);
		} else {
			super.update(o);
		}
		if (o.getListdoc().size() == 0) {
			Doc doc = createDoc(o);
			o.getListdoc().add(doc);
			super.update(o);
			List<Doc> listDoc = new ArrayList<Doc>();
			listDoc.add(doc);
			getPathPageProvider().createInCurrentPathPage(doc);
		}
	}

	private Doc createDoc(PlanDownloadSum o) throws Exception {
		Doc doc = new Doc();
		doc.setContragent(o.getContragent());
		doc.setShop(o.getShop());
		doc.setTypedoc(getDocProvider().getTypeDocOrderSupplier());
		doc.setManager(o.getManager());
		doc.setSumma(o.getPlan());
		Long n = getDocProvider().create(doc);
		doc.setN(n);
		return doc;
	}

	@Override
	public PlanDownloadSum initialize(Long id) {
		PlanDownloadSum planDownloadSum = read(id);
		if (planDownloadSum != null && planDownloadSum.getListdoc() != null
				&& planDownloadSum.getListdoc().size() > 0) {
			Hibernate.initialize(planDownloadSum.getListdoc());
		}
		return planDownloadSum;
	}

	@Override
	public int deleteForPlanDownload(PlanDownload planDownload)
			throws Exception {
		String sql = "select ps from PlanDownloadSum ps where "
				+ "ps.planDownload.n=:n";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("n", planDownload.getN());
		List<PlanDownloadSum> list = q1.list();
		for (PlanDownloadSum planDownloadSum : list) {
			for (Doc doc : planDownloadSum.getListdoc()) {
				deleteDoc(doc);
			}
		}

		sql = "delete from PlanDownloadSum ps where " + "ps.planDownload.n=:n";
		q1 = getSession().createQuery(sql);
		q1.setParameter("n", planDownload.getN());
		int ret = q1.executeUpdate();
		return ret;
	}

	@Override
	public void deleteDoc(Doc doc) throws Exception {
		String sql = "select ps from PlanDownloadSum ps right outer join ps.listdoc doc where doc.n=:n";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("n", doc.getN());
		List<PlanDownloadSum> list = q1.list();
		for (PlanDownloadSum planDownloadSum : list) {
			if (planDownloadSum != null) {
				delete(planDownloadSum);
			}
		}
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public String getNameTypeFileOrder() {
		return nameTypeFileOrder;
	}

	public void setNameTypeFileOrder(String nameTypeFileOrder) {
		this.nameTypeFileOrder = nameTypeFileOrder;
	}

	public String getNameTypeFileInvoice() {
		return nameTypeFileInvoice;
	}

	public void setNameTypeFileInvoice(String nameTypeFileInvoice) {
		this.nameTypeFileInvoice = nameTypeFileInvoice;
	}

	public String getNameTypeFileReceive() {
		return nameTypeFileReceive;
	}

	public void setNameTypeFileReceive(String nameTypeFileReceive) {
		this.nameTypeFileReceive = nameTypeFileReceive;
	}

	public TypeFile getTypeFileOrder() {
		if (typeFileOrder == null) {
			typeFileOrder = getTypeFileProvider().getByEqName(
					getNameTypeFileOrder());
		}
		return typeFileOrder;
	}

	public TypeFile getTypeFileInvoice() {
		if (typeFileInvoice == null) {
			typeFileInvoice = getTypeFileProvider().getByEqName(
					getNameTypeFileInvoice());
		}
		return typeFileInvoice;
	}

	public TypeFile getTypeFileReceive() {
		if (typeFileReceive == null) {
			typeFileReceive = getTypeFileProvider().getByEqName(
					getNameTypeFileReceive());
		}
		return typeFileReceive;
	}

	public ITypeFileProvider getTypeFileProvider() {
		return typeFileProvider;
	}

	public void setTypeFileProvider(ITypeFileProvider typeFileProvider) {
		this.typeFileProvider = typeFileProvider;
	}

	public IDocProvider getDocProvider() {
		return docProvider;
	}

	public void setDocProvider(IDocProvider docProvider) {
		this.docProvider = docProvider;
	}

	public IPathPageProvider getPathPageProvider() {
		return pathPageProvider;
	}

	public void setPathPageProvider(IPathPageProvider pathPageProvider) {
		this.pathPageProvider = pathPageProvider;
	}

	@Override
	public DocFile addFile(Manager autor, Date datePlanDownload, Shop shop,
			Contragent contragent, boolean sendToContragent, String recipients,
			BigDecimal summa, String numdoc, String filename,
			TypeFile typeFile, byte[] data, boolean loadDbf) throws Exception {
		PlanDownload planDownload = getPlanDownloadProvider().getBy(shop,
				datePlanDownload, contragent);
		if (planDownload == null) {
			throw new Exception("Общий план загрузок не создан"
					+ shop.getName() + ";"
					+ Helper.getDateFornmatter().format(datePlanDownload) + ";"
					+ contragent.getName());
		}
		PlanDownloadSum planDownloadSum = getPlanDownloadSum(shop,
				planDownload, contragent);
		if (planDownloadSum == null) {
			planDownloadSum = createPlanDownloadSum(shop, planDownload,
					contragent, autor);
		}
		// Сохранение файла
		Doc doc = planDownloadSum.getListdoc().get(0);
		DocFile docFile = new DocFile();
		docFile.setName(filename);
		docFile.setBody(data);
		docFile.setManager(autor);
		docFile.setDoc(doc);
		docFile.setTypeFile(typeFile);
		docFile.setSumma(summa);
		docFile.setNumdoc(numdoc);
		if (loadDbf) {
			docFile = getDocProvider().addDocDbfFile(docFile);
		} else {
			docFile = getDocProvider().addDocFile(docFile);
		}

		// Отправка поставщику
		if (sendToContragent) {
			List<FileAttach> list = new ArrayList<FileAttach>();
			FileAttach fileAttach = new FileAttach();
			fileAttach.name = docFile.getName();
			fileAttach.body = data;
			list.add(fileAttach);
			String emailContragent = contragent.getEmail();
			String subj = shop.getName() + "/" + autor.getName() + "."
					+ doc.getTypedoc().getName() + " для "
					+ contragent.getName();
			String messageToContragent = "Это письмо создано программой-роботом.\nДля ответа используйте адрес "
					+ shop.getName() + " <" + autor.getEmail() + ">\n";
//	TODO: emailer
//			String ansSendContragent = emailer.send(autor, emailContragent,
//					messageToContragent, subj, list,true);
//			if (!ansSendContragent.equals("")) {
//				throw new Exception(ansSendContragent);
//			}
//			getDocProvider().setDateSending(docFile);
		}
		// Отправка уведомлений
		if (!recipients.equals("")) {
			String mes = doc.getShop().getName() + "/" + autor.getName()
					+ " загрузил " + doc.getTypedoc().getName() + " для "
					+ doc.getContragent().getName() + " к документу "
					+ doc.getN() + " от "
					+ Helper.getDateFornmatter().format(doc.getDdate());
// emailer
//			String ans = getEmailer().send(autor, recipients, "", mes, null,true);
//			if (!ans.equals("")) {
//				throw new Exception(ans);
//			}
		}

		return docFile;
	}

	public IEmailer getEmailer() {
		return emailer;
	}

	public void setEmailer(IEmailer emailer) {
		this.emailer = emailer;
	}

	public IPlanDownloadProvider getPlanDownloadProvider() {
		return planDownloadProvider;
	}

	public void setPlanDownloadProvider(IPlanDownloadProvider planDownloadProvider) {
		this.planDownloadProvider = planDownloadProvider;
	}

}
