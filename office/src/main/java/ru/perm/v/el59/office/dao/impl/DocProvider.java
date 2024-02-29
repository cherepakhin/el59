package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.office.db.dto.DocItemDTO;
import ru.perm.v.el59.office.iproviders.*;
import ru.perm.v.el59.office.iproviders.critery.DocCritery;
import ru.perm.v.el59.office.iproviders.critery.DocItemCritery;
import ru.perm.v.el59.office.iproviders.critery.ShopCritery;
import ru.perm.v.el59.office.iproviders.critery.TovarCritery;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;
import ru.perm.v.el59.office.iproviders.routedoc.IPathPageProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IPlanDownloadSumProvider;
import ru.perm.v.el59.office.iproviders.web.IDocWItemProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DocProvider extends GenericDaoHibernateImpl<Doc, Long> implements
		IDocProvider {
	private IShopProvider shopProvider;
	private ITypeDocProvider typeDocProvider;
	private IContragentProvider contragentProvider;
	private ITovarProvider tovarProvider;
	private IDocItemProvider docItemProvider;
	private IDocFileProvider docFileProvider;
	private IDocWItemProvider docWItemProvider;
	private IManagerProvider managerProvider;
	private ITypeFileProvider typeFileProvider;
	private IPathPageProvider pathPageProvider;
	private IPlanDownloadSumProvider planDownloadSumProvider;

	private String VIPISKA;
	private String NET;
	private String ORDER;

	private TypeDoc vipiska;
	private Contragent supplier;
	// private Long numberNullDocWItem=0L;
	private String nameTypeDocOrderSupplier;
	private TypeDoc typeDocOrderSupplier;
	private Long nORDER;

	public DocProvider(Class<Doc> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Doc> getByCritery(Object critery) {
		DocCritery c = (DocCritery) critery;
		Criteria criteria = getSession().createCriteria(Doc.class, "d");
		if ((c.fromdate != null) && (c.todate != null)) {
			criteria.add(Restrictions.ge("d.ddate", c.fromdate));
			criteria.add(Restrictions.le("d.ddate", c.todate));
		}
		if (c.shops.size() > 0)
			criteria.add(Restrictions.in("d.shop", c.shops));
		if (!c.numdoc.equals(""))
			criteria.add(Restrictions.eq("d.numdoc", c.numdoc.toUpperCase()));
		if (!c.typedocname.equals("")) {
			Criteria typedocCritery = criteria.createCriteria("typedoc");
			typedocCritery.add(Restrictions.like("name", c.typedocname,
					MatchMode.ANYWHERE).ignoreCase());
		}
		if (c.typedoc != null) {
			criteria.add(Restrictions.eq("d.typedoc", c.typedoc));
		}
		if (c.manager != null) {
			criteria.add(Restrictions.eq("d.manager", c.manager));
		}

		if (c.supplier != null && !c.supplier.getName().equals("Нет")
				&& !c.supplier.getName().equals("")) {
			criteria.add(Restrictions.eq("d.contragent", c.supplier));
		}
		if (c.listN.size() > 0) {
			criteria.add(Restrictions.in("d.n", c.listN));
		}
		if (c.numberReestDoc != null || c.noReestr != null) {
			Criteria reestrOrderDocCritery = criteria
					.createCriteria("reestrDoc");
			if (c.numberReestDoc != null) {
				reestrOrderDocCritery.add(Restrictions.eq("n", c.numberReestDoc));
			}
			if (c.noReestr != null) {
				reestrOrderDocCritery.add(Restrictions.eq("n", 0L));
			}
		}

		if (c.notAgree != null) {
			criteria.add(Restrictions.eq("d.agreed", false));
		}
		if (c.withOrder != null) {
			criteria.add(Restrictions
					.sqlRestriction("{alias}.n in (select df.doc_n from db.DocFile df where df.typefile_n="
							+ getNOrder() + ")"));
		}
		if (c.notInPathPage != null) {
			criteria.add(Restrictions
					.sqlRestriction("{alias}.n not in (select doc_n from db.pathpage_listroutejob)"));
		}
		criteria.addOrder(Order.asc("d.n"));
		ArrayList<Doc> list = (ArrayList<Doc>) criteria.list();
		return list;
	}

	private String getNOrder() {
		if (nORDER == null) {
			TypeFile typeFileOrder = getTypeFileProvider().getByEqName(ORDER);
			nORDER = typeFileOrder.getN();
		}
		return nORDER.toString();
	}

	@Override
	public void delete(Doc o) throws Exception {
		Doc doc = (Doc) o;
		DocItemCritery docItemCritery = new DocItemCritery();
		docItemCritery.doc = doc;
		List<DocItem> listDocItem = getDocItemProvider().getByCritery(
				docItemCritery);
		if (listDocItem.size() > 0) {
			getDocWItemProvider().unLinkDocWItem(listDocItem);
		}
		getDocItemProvider().deleteByDoc(doc);
		getDocFileProvider().deleteByDoc(doc);
		getPathPageProvider().delete(doc);
		getPlanDownloadSumProvider().deleteDoc(doc);
		// getSession().merge(doc);
		getSession().clear();
		/*
		 * String sql = "delete from Doc where n=:n"; Query
		 * q1=getSession().createQuery(sql); q1.setParameter("n", doc.getN());
		 * q1.executeUpdate();
		 */
		super.delete(doc);
	}

	@Override
	public void recalc(Doc doc) throws Exception {
		DocItemCritery critery = new DocItemCritery();
		critery.doc = doc;
		List<DocItem> list = getDocItemProvider().getByCritery(critery);
		BigDecimal sum = new BigDecimal("0.00");
		BigDecimal volume = new BigDecimal("0.00");
		doc = (Doc) initialize(doc.getN());
		for (DocItem docItem : list) {
			sum = sum.add(docItem.getQty().multiply(docItem.getCena()));
/*			if (docItem.getTovar().getGroup() != null) {
				volume = volume.add(docItem.getTovar().getGroup().getVolume());
			}
*/		}
		doc.setSumma(sum.setScale(2, RoundingMode.HALF_UP));
		doc.setVolume(volume);
		update(doc);
	}

	@Override
	public DocFile addDocFile(DocFile docFile) throws Exception {
		/*
		 * if(!doc.getManager().equals(manager)) { return
		 * "Вы не можете изменить этот документ.Он не ваш."; }
		 */
		if (docFile.getTypeFile() == null) {
			docFile.setTypeFile(getTypeFileProvider().getNullTypeFile());
		}
		/*
		 * List<DocFile> listDocFile = getListDocFile(docFile.getDoc()); for
		 * (DocFile _docFile : listDocFile) {
		 * if(_docFile.getTypeFile().equals(docFile.getTypeFile())) {
		 * _docFile.setBu(true); getDocFileProvider().update(_docFile); } }
		 */
		Long n = getDocFileProvider().create(docFile);
		/*
		 * if(!docFile.getSumma().equals(new BigDecimal("0.00"))) { Doc doc =
		 * docFile.getDoc(); doc.setSumma(docFile.getSumma()); update(doc); }
		 */return docFile;
	}

	@Override
	public DocFile addDocDbfFile(DocFile docFile) throws Exception {
		docFile = addDocFile(docFile);
		List<DocItem> list = getDocFileProvider().loadDbf(docFile);
		BigDecimal sum=new BigDecimal("0.00");
		for (DocItem docItem : list) {
			getDocItemProvider().create(docItem);
			sum=sum.add(docItem.getQty().multiply(docItem.getCena()));
		}
		docFile=getDocFileProvider().read(docFile.getN());
		docFile.setSumma(sum);
		getDocFileProvider().update(docFile);
		return docFile;
	}

	@Override
	public String deleteDocFile(DocFile docFile, Manager manager)
			throws Exception {
		if (!docFile.getManager().equals(manager)) {
			return "Вы не можете изменить этот документ.Он не ваш.";
		}

		if (docFile.getSumma().compareTo(BigDecimal.ZERO)!=0) {
			Doc doc = docFile.getDoc();
			doc.setSumma(new BigDecimal("0.00"));
			update(doc);
		}
		getDocFileProvider().delete(docFile);
		return "";
	}

	@Override
	public byte[] getBody(DocFile docFile) throws IOException {
		return getDocFileProvider().getBody(docFile);
	}

	@Override
	public Long create(String nameTypedoc, String nameShop,
			String nameSupplier, Manager manager) throws Exception {
		Doc doc = new Doc();
		doc.setManager(manager);
		Shop shop = (Shop) getShopProvider().getByEqName(nameShop);
		TypeDoc typedoc = (TypeDoc) getTypeDocProvider().getByEqName(
				nameTypedoc);
		Contragent supplier = getContragentProvider().getByEqName(nameSupplier);
		doc.setShop(shop);
		doc.setTypedoc(typedoc);
		doc.setContragent(supplier);
		Long n = create(doc);
		return n;
	}

	@Override
	public void checkDocItem(DocItemDTO docItemDTO) throws Exception {
		if (!docItemDTO.getShopcod().isEmpty()) {
			ShopCritery shopCritery = new ShopCritery();
			shopCritery.setCod(docItemDTO.getShopcod());
			List listShop = getShopProvider().getByCritery(shopCritery);
			if (listShop.size() == 1) {
				Shop shop = (Shop) listShop.get(0);
				// Проверка на существование документа
				DocCritery docCritery = new DocCritery();
//				docCritery.shops.add(shop);
				docCritery.numdoc = docItemDTO.getNumdoc();
				docCritery.fromdate = docItemDTO.getDdate();
				docCritery.todate = docItemDTO.getDdate();
				docCritery.typedoc = getVipiska();
				List<Doc> listDoc = getByCritery(docCritery);
				if (listDoc.size() <= 1) {
					if (listDoc.size() == 1) {
						updateDocItem(docItemDTO, shop, listDoc.get(0));
					} else {
						createDocByItem(docItemDTO, shop);
					}
				} else {
					printlog("Документов магазина " + shop.getCod()
							+ " сномером " + docItemDTO.getNumdoc()
							+ " больше одного");
				}
			} else {
				printlog("Не верно указан магазин с кодом "
						+ docItemDTO.getShopcod());
			}
		} else {
			printlog("Не указан магазин");
		}

	}

	private void createDocByItem(DocItemDTO docItemDTO, Shop shop)
			throws Exception {
		Doc doc = new Doc();
		doc.setDdate(docItemDTO.getDdate());
		doc.setShop(shop);
		doc.setSumma(new BigDecimal("0.00"));
		doc.setTypedoc(getVipiska());
		doc.setContragent(getSupplier());
		doc.setComment("");
		doc.setNumdoc(docItemDTO.getNumdoc());
		create(doc);
		createDocItem(doc, docItemDTO);
	}

	private DocItem createDocItem(Doc doc, DocItemDTO docItemDTO)
			throws Exception {
		DocItem docItem = new DocItem();
		docItem.setDoc(doc);
		docItem.setQty(docItemDTO.getQtyout());
		docItem.setQtyZakaz(docItemDTO.getQtyzakaz());
		docItem.setCena(docItemDTO.getSummaout());
		docItem.setTovar(getTovar(docItemDTO.getNnum()));
		getDocItemProvider().create(docItem);

		doc.setSumma(doc.getSumma().add(docItemDTO.getSummaout()));
		update(doc);
		return docItem;
	}

	private Tovar getTovar(Integer nnum) {
		TovarCritery tovarCritery = new TovarCritery();
		tovarCritery.nnum.add(nnum);
		List<Tovar> list = getTovarProvider().getByCritery(tovarCritery);
		return list.get(0);
	}

	private void updateDocItem(DocItemDTO docItemDTO, Shop shop, Doc doc)
			throws Exception {
		DocItemCritery c = new DocItemCritery();
		c.doc = doc;
		c.nnum = docItemDTO.getNnum();
		List<DocItem> list = getDocItemProvider().getByCritery(c);
		if (list.size() != 1) {
			// если нет , то создать
			createDocItem(doc, docItemDTO);
		} else {
			DocItem docItem = list.get(0);
			if (docItem.getQty().equals(docItemDTO.getQtyout())) {
				docItem.setQty(docItemDTO.getQtyout());
				getDocItemProvider().update(docItem);
			}
		}
	}

	@Override
	public List<DocFile> getListDocFile(Doc doc) {
		return getDocFileProvider().getListDocFile(doc);
	}

	@Override
	public void setDateSending(DocFile docFile) throws Exception {
		docFile=getDocFileProvider().read(docFile.getN());
		docFile.setDateSending(new Date());
		getDocFileProvider().update(docFile);
	}

	/*
	 * @Override public String agree(Doc selectedDoc, Boolean agree, Manager
	 * currentUser) { if(selectedDoc.getAgreed() && agree) { return
	 * "Документ уже согласован"; }
	 * if(!selectedDoc.getManagerAgreed().equals(getManagerProvider
	 * ().getNullManager())) {
	 * if(!selectedDoc.getManagerAgreed().equals(currentUser)) { return
	 * "Документ согласован "+selectedDoc.getManagerAgreed()+".\n" +
	 * "Только он может согласовать или отменить согласование."; } } if(!agree)
	 * { if(!selectedDoc.getManagerAgreed().equals(currentUser)) { return
	 * "Документ согласован "+selectedDoc.getManagerAgreed()+".\n" +
	 * "Только он может отменить согласование.";
	 * 
	 * } } selectedDoc.setAgreed(agree); update(selectedDoc); return "";
	 * 
	 * }
	 */
	@Override
	public Long create(Doc doc) throws Exception {
		if (doc.getManager() == null) {
			doc.setManager(getManagerProvider().getNullManager());
		}
		Long n = super.create(doc);
		doc.setN(n);
		// getPathPageProvider().createInCurrentPathPage(doc);
		return n;
	}

	@Override
	public String update(Doc doc, Manager manager) throws Exception {
		if (!doc.getManager().equals(manager)) {
			return "Вы не можете изменить этот документ.Он не ваш.";
		}
		update(doc);
		return "";
	}

	@Override
	public TypeDoc getTypeDocOrderSupplier() {
		if (typeDocOrderSupplier == null) {
			typeDocOrderSupplier = getTypeDocProvider().getByEqName(
					nameTypeDocOrderSupplier);
		}
		return typeDocOrderSupplier;
	}

	private void printlog(String string) {
		System.out.println(string);
	}

	public TypeDoc getVipiska() {
		if (vipiska == null) {
			List<TypeDoc> list = getTypeDocProvider().getByCritery(
					new CommonCritery(getVIPISKA()));
			vipiska = list.get(0);
		}
		return vipiska;
	}

	public Contragent getSupplier() {
		if (supplier == null) {
			supplier = getContragentProvider().getByEqName(getNET());
		}
		return supplier;
	}

	public List<Doc> getFreeForPathPage(DocCritery critery) {
		Calendar calFromDate = Calendar.getInstance();
		calFromDate.set(Calendar.YEAR, 2013);
		calFromDate.set(Calendar.MONTH, 7);
		calFromDate.set(Calendar.DAY_OF_MONTH, 19);
		if (critery.fromdate.before(calFromDate.getTime())) {
			critery.fromdate = calFromDate.getTime();
		}
		String sql = "from Doc d "
				+ "where d.ddate>=:fromDate and d.ddate<=:toDate  and "
				+ "d.n not in (select r.doc.n from RouteJob r)";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("fromDate", critery.fromdate);
		q1.setParameter("toDate", critery.todate);

		List<Doc> list = q1.list();
		if (critery.shops.size() > 0) {
			List<Doc> ret = new ArrayList<Doc>();
			for (Doc doc : list) {
				if (critery.shops.contains(doc.getShop())) {
					ret.add(doc);
				}
			}
			return ret;
		} else {
			return list;
		}
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public IDocItemProvider getDocItemProvider() {
		return docItemProvider;
	}

	public void setDocItemProvider(IDocItemProvider docItemProvider) {
		this.docItemProvider = docItemProvider;
	}

	public String getVIPISKA() {
		return VIPISKA;
	}

	public void setVIPISKA(String vIPISKA) {
		VIPISKA = vIPISKA;
	}

	public String getNET() {
		return NET;
	}

	public void setNET(String nET) {
		NET = nET;
	}

	public IContragentProvider getContragentProvider() {
		return contragentProvider;
	}

	public void setContragentProvider(IContragentProvider contragentProvider) {
		this.contragentProvider = contragentProvider;
	}

	public IManagerProvider getManagerProvider() {
		return managerProvider;
	}

	public void setManagerProvider(IManagerProvider managerProvider) {
		this.managerProvider = managerProvider;
	}

	public IDocWItemProvider getDocWItemProvider() {
		return docWItemProvider;
	}

	public void setDocWItemProvider(IDocWItemProvider docWItemProvider) {
		this.docWItemProvider = docWItemProvider;
	}

	public ITypeDocProvider getTypeDocProvider() {
		return typeDocProvider;
	}

	public void setTypeDocProvider(ITypeDocProvider typeDocProvider) {
		this.typeDocProvider = typeDocProvider;
	}

	public String getNameTypeDocOrderSupplier() {
		return nameTypeDocOrderSupplier;
	}

	public void setNameTypeDocOrderSupplier(String nameTypeDocOrderSupplier) {
		this.nameTypeDocOrderSupplier = nameTypeDocOrderSupplier;
	}

	public ITypeFileProvider getTypeFileProvider() {
		return typeFileProvider;
	}

	public void setTypeFileProvider(ITypeFileProvider typeFileProvider) {
		this.typeFileProvider = typeFileProvider;
	}

	public IDocFileProvider getDocFileProvider() {
		return docFileProvider;
	}

	public void setDocFileProvider(IDocFileProvider docFileProvider) {
		this.docFileProvider = docFileProvider;
	}

	public String getORDER() {
		return ORDER;
	}

	public void setORDER(String oRDER) {
		ORDER = oRDER;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public IPathPageProvider getPathPageProvider() {
		return pathPageProvider;
	}

	public void setPathPageProvider(IPathPageProvider pathPageProvider) {
		this.pathPageProvider = pathPageProvider;
	}

	public IPlanDownloadSumProvider getPlanDownloadSumProvider() {
		return planDownloadSumProvider;
	}

	public void setPlanDownloadSumProvider(IPlanDownloadSumProvider planDownloadSumProvider) {
		this.planDownloadSumProvider = planDownloadSumProvider;
	}

}
