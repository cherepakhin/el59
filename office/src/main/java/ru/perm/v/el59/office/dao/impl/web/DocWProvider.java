package ru.perm.v.el59.office.dao.impl.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.dto.dao.CommonCritery;
import ru.perm.v.el59.dto.office.critery.DocTitleCritery;
import ru.el59.office.db.Doc;
import ru.el59.office.db.Shop;
import ru.el59.office.db.TypeDoc;
import ru.el59.office.db.web.DocW;
import ru.el59.office.db.web.DocWInfo;
import ru.el59.office.db.web.DocWItem;
import ru.perm.v.el59..office.iproviders.IDocItemProvider;
import ru.perm.v.el59..office.iproviders.IShopProvider;
import ru.perm.v.el59..office.iproviders.ITovarProvider;
import ru.perm.v.el59..office.iproviders.ITypeDocProvider;
import ru.perm.v.el59..office.iproviders.shopmodel.IDocTitleProvider;
import ru.perm.v.el59..office.iproviders.shopmodel.IPaymentProvider;
import ru.perm.v.el59..office.iproviders.shopmodel.ITypeDocShopProvider;
import ru.el59.office.shopmodel.DocTitle;
import ru.el59.office.shopmodel.TypeDocShop;
import ru.perm.v.el59..office.iproviders.web.DocWItemCritery;
import ru.perm.v.el59..office.iproviders.web.DocWCritery;
import ru.perm.v.el59..office.iproviders.web.ICommentDocWProvider;
import ru.perm.v.el59..office.iproviders.web.IDocWItemProvider;
import ru.perm.v.el59..office.iproviders.web.IDocWProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class DocWProvider extends GenericDaoMessageImpl<DocW, Long> implements
		IDocWProvider {
	private String VIPISKA = "Выписка";
	private IShopProvider shopProvider;
	private ITypeDocProvider typeDocProvider;
	private ITypeDocShopProvider typeDocShopProvider;
	private IDocTitleProvider docTitleProvider;
	private IPaymentProvider paymentProvider;
	private ITovarProvider tovarProvider;
	private IDocWItemProvider docWItemProvider;
	private IDocItemProvider docItemProvider;
	private ICommentDocWProvider commentDocWProvider;

	private TypeDoc vipiska;

	public DocWProvider(Class<DocW> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocW> getByCritery(Object critery) {
		DocWCritery c = (DocWCritery) critery;
		Criteria criteria = getSession().createCriteria(DocW.class);
		if (c.listN.size() > 0) {
			criteria.add(Restrictions.in("n", c.listN));
		}
		if ((c.fromdate != null) && (c.todate != null)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(c.todate);
			cal.add(Calendar.DAY_OF_YEAR, 1);

			criteria.add(Restrictions.between("ddate", c.fromdate, cal.getTime()));
			// criteria.add(Restrictions.le("ddate", c.todate));
		}
		if (c.shops.size() > 0)
			criteria.add(Restrictions.in("shop", c.shops));
		if (!c.numdoc.equals(""))
			criteria.add(Restrictions.eq("numdoc", c.numdoc.toUpperCase()));
		if (!c.typedocname.equals("")) {
			Criteria typedocCritery = criteria.createCriteria("typedoc");
			typedocCritery.add(Restrictions.like("name", c.typedocname,
					MatchMode.ANYWHERE).ignoreCase());
		}
		if (c.typedoc != null) {
			criteria.add(Restrictions.eq("typedoc", c.typedoc));
		}
		if (c.deleted == false) {
			criteria.add(Restrictions.eq("deleted", false));
		}

		criteria.addOrder(Order.asc("numdoc"));
		List<DocW> list = criteria.list();
		return list;
	}

	@Override
	public List<DocWInfo> getDocWInfo(DocWCritery critery) {
		Logger.getLogger(this.getClass().getName()).info("Получение выписок. Начало.");
		List<DocW> listDocW = getByCritery(critery);
		ArrayList<DocWInfo> ret = new ArrayList<DocWInfo>();
		Logger.getLogger(this.getClass().getName()).info("Получение содержимого выписок.");
		DocWItemCritery docWItemCritery = new DocWItemCritery();
		docWItemCritery.listDocW.addAll(listDocW);
		List<DocWItem> listDocWItem = getDocWItemProvider().getByCritery(docWItemCritery);
		Logger.getLogger(this.getClass().getName()).info("Построение кэша выписок.");
		HashMap<DocW, ArrayList<DocWItem>> hash = new HashMap<DocW, ArrayList<DocWItem>>();
		for (DocWItem docWItem : listDocWItem) {
			if (!hash.containsKey(docWItem.getDocw())) {
				ArrayList<DocWItem> l = new ArrayList<DocWItem>();
				hash.put(docWItem.getDocw(), l);
			}
			hash.get(docWItem.getDocw()).add(docWItem);
		}

		// Заказанное к-во покупателем
		String sql = "select dwi.docw.n ,sum(dwi.qty) from DocWItem dwi where dwi.docw in (:listDocW) group by dwi.docw.n";
		Query q1 = getSession().createQuery(sql);
		q1.setParameterList("listDocW", listDocW);
		HashMap<Long, Integer> hashQty = new HashMap<Long, Integer>();
		List l = q1.list();
		for (Object r : l) {
			Object[] rr = (Object[]) r;
			BigDecimal qty = (BigDecimal) rr[1];
			hashQty.put((Long) rr[0], qty.intValue());
		}
		
		// Выданное покупателю к-во
		sql = "select dwi.docw.n ,sum(dwi.docDetailOut.qty) from DocWItem dwi where dwi.docw in (:listDocW) group by dwi.docw.n";
		q1 = getSession().createQuery(sql);
		q1.setParameterList("listDocW", listDocW);
		HashMap<Long, Integer> hashQtyToCustomer = new HashMap<Long, Integer>();
		l = q1.list();
		for (Object r : l) {
			Object[] rr = (Object[]) r;
			BigDecimal qty = (BigDecimal) rr[1];
			hashQtyToCustomer.put((Long) rr[0], qty.intValue());
		}
		
		// Заказанное кол-во поставщику и себестоимость заказанного товара
		sql = "select dwi.docw.n ,sum(dwi.docItem.qty),sum(dwi.docItem.qty*dwi.docItem.cena) from DocWItem dwi where dwi.docw in (:listDocW) group by dwi.docw.n";
		q1 = getSession().createQuery(sql);
		q1.setParameterList("listDocW", listDocW);
		HashMap<Long, Integer> hashQtyOrderSupplier = new HashMap<Long, Integer>();
		HashMap<Long, BigDecimal> hashSumOrderSupplier = new HashMap<Long, BigDecimal>();
		l = q1.list();
		for (Object r : l) {
			Object[] rr = (Object[]) r;
			// Кол-во
			BigDecimal qty = (BigDecimal) rr[1];
			hashQtyOrderSupplier.put((Long) rr[0], qty.intValue());
			// Себестоимость
			BigDecimal sum = (BigDecimal) rr[2];
			hashSumOrderSupplier.put((Long) rr[0], sum);
		}

		// Пришло в магазин
		sql = "select dwi.docw.n ,sum(dwi.docDetailInput.qty) from DocWItem dwi where dwi.docw in (:listDocW) group by dwi.docw.n";
		q1 = getSession().createQuery(sql);
		q1.setParameterList("listDocW", listDocW);
		HashMap<Long, Integer> hashQtyInputShop = new HashMap<Long, Integer>();
		l = q1.list();
		for (Object r : l) {
			Object[] rr = (Object[]) r;
			BigDecimal qty = (BigDecimal) rr[1];
			hashQtyInputShop.put((Long) rr[0], qty.intValue());
		}

		sql = "select dwi.docw.n  from DocWItem dwi where dwi.docw in (:listDocW) group by dwi.docw.n having count(dwi.docItem.doc)>0";
		q1 = getSession().createQuery(sql);
		q1.setParameterList("listDocW", listDocW);
		List listIsLoadDocFile = q1.list();
/*		for (Object r : l) {
			Object[] rr = (Object[]) r;
			BigDecimal qty = (BigDecimal) rr[1];
			hashIsLoadDocFile.put((Long) rr[0], qty.intValue());
		}
*/
		for (DocW docW : listDocW) {
			DocWInfo d = new DocWInfo();
//			Logger.getLogger(this.getClass()).info("W-выписка "+docW.getNumdoc());
			d.setDocw(docW);
			// d.setSumPay(getPay(docW));
			d.setSumPay(new BigDecimal("0.00"));
/*			ArrayList<DocWItem> _listDocWItem = new ArrayList<DocWItem>();
			if (hash.containsKey(docW)) {
				_listDocWItem = hash.get(docW);
			}
*/
//			Logger.getLogger(this.getClass()).info("К-во в выписке");
			if(hashQty.containsKey(docW.getN())) {
				d.setQtyOrder(hashQty.get(docW.getN()));
			}
//			d.setQtyOrder(getQtyOrder(_listDocWItem));
			
//			Logger.getLogger(this.getClass()).info("Доставлено покупателю");
			if(hashQtyToCustomer.containsKey(docW.getN())) {
				d.setQtyToCustomer(hashQtyToCustomer.get(docW.getN()));
			}
//			d.setQtyToCustomer(getDelivered(docW, _listDocWItem));
			
//			Logger.getLogger(this.getClass()).info("Зарезервировано");
			if(hashQtyOrderSupplier.containsKey(docW.getN())) {
				d.setQtyOrderSupplier(hashQtyOrderSupplier.get(docW.getN()));
			}
//			if(hashSumOrderSupplier.containsKey(docW.getN())) {
//				d.setSumOrderSupplier(hashSumOrderSupplier.get(docW.getN()));
//			}
//			d.setQtyOrderSupplier(getQtySuppLier(_listDocWItem));
			
//			Logger.getLogger(this.getClass()).info("Приход в магазин");
			if(hashQtyInputShop.containsKey(docW.getN())) {
				d.setQtyInputShop(hashQtyInputShop.get(docW.getN()));
			}
//			d.setQtyInputShop(getQtyInvoice(_listDocWItem));
			
//			Logger.getLogger(this.getClass()).info("Счета загружены?");
			if(listIsLoadDocFile.contains(docW.getN())) {
				d.setIsLoadDocFile(true);
			}
//			d.setIsLoadDocFile(getLoadFile(_listDocWItem));
			ret.add(d);
		}
		Logger.getLogger(this.getClass().getName()).info("Получение выписок. Конец.");
		return ret;
	}

	/**
	 * Контроль загруженных счетов
	 */
	private Boolean getLoadFile(ArrayList<DocWItem> _listDocWItem) {
		List<Doc> listDoc = new ArrayList<Doc>();
		for (DocWItem docWItem : _listDocWItem) {
			if (docWItem.getDocItem().equals(
					getDocItemProvider().getNullDocItem())) {
				return false;
			}
			listDoc.add(docWItem.getDocItem().getDoc());
		}
		if(listDoc.size()==0) {
			return false;
		}
/*		String sql = "select count(*) from DocFile where doc in (:listDoc)";
		Query q = getSession().createQuery(sql);
		q.setParameterList("listDoc", listDoc);
		Long qtyDocFileInBase = (Long) q.uniqueResult();
		if (qtyDocFileInBase == 0) {
			return false;
		}
		if (qtyDocFileInBase < listDoc.size()) {
			return false;
		}
*/		return true;
	}

	/**
	 * Получение кол-ва товаров, доставленных в магазин
	 * 
	 * @param listDocWItem
	 * @return
	 */
	private Integer getQtyInvoice(List<DocWItem> listDocWItem) {
		Integer ret = 0;
		for (DocWItem docWItem : listDocWItem) {
			if (docWItem.getDocDetailInput() != null) {
				if (docWItem.getQty().intValue() < docWItem.getDocDetailInput()
						.getQty().intValue()) {
					ret = ret + docWItem.getQty().intValue();
				} else {
					ret = ret
							+ docWItem.getDocDetailInput().getQty().intValue();
				}
			}
		}
		return ret;
	}

	/**
	 * Получение кол-ва товаров в выписке
	 * 
	 * @return
	 */
	private Integer getQtyOrder(List<DocWItem> listDocWItem) {
		Integer ret = 0;
		for (DocWItem docWItem : listDocWItem) {
			// ПДС не считать
			if (!docWItem.getTovar().getName().contains("ПДС")) {
				ret = ret + docWItem.getQty().intValue();
			}
		}
		return ret;
	}

	/**
	 * Получение заказанного количества в счетах поставщика
	 * 
	 * @return
	 */
	private Integer getQtySuppLier(List<DocWItem> listDocWItem) {
		Integer ret = 0;
		for (DocWItem docWItem : listDocWItem) {
			if (!docWItem.getDocItem().equals(
					getDocItemProvider().getNullDocItem())) {
				ret = ret + docWItem.getDocItem().getQty().intValue();
			}
		}
		return ret;
	}

	@Override
	public DocTitle getDocTitleByDocW(DocW docW) {
		// Это сама W-выписка из магазина из таблицы DocTitle (не DocW)
		DocTitleCritery docTitleCritery = new DocTitleCritery();
		docTitleCritery.numdoc = docW.getNumdoc();
		TypeDocShop td = getTypeDocShopProvider().getByEqName(
				getDocTitleProvider().getNameOrderW());
		docTitleCritery.typedocs.add(td);
		List<DocTitle> listDocTitle = getDocTitleProvider().getByCritery(
				docTitleCritery);
		if (listDocTitle.size() > 0) {
			return listDocTitle.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Получение суммы отгрузки w-выписки
	 * 
	 * @param listDocWItem
	 */
	private Integer getDelivered(DocW docW, List<DocWItem> listDocWItem) {
		Integer ret = 0;
		for (DocWItem docWItem : listDocWItem) {
			if (docWItem.getDocDetailOut() != null) {
				ret = ret + docWItem.getDocDetailOut().getQty().intValue();
			}
		}
		return ret;
	}

	@Override
	public BigDecimal getPay(DocW docW) {
		return getPaymentProvider().getSumPay(docW);
	}

	public TypeDoc getVipiska() {
		if (vipiska == null) {
			List<TypeDoc> list = getTypeDocProvider().getByCritery(
					new CommonCritery(getVIPISKA()));
			vipiska = list.get(0);
		}
		return vipiska;
	}

	@Override
	public void delete(DocW o) throws Exception {
		DocW docw = (DocW) o;
		getDocWItemProvider().deleteByDocW(docw);
		getCommentDocWProvider().deleteByDocW(docw);
		docw = read(docw.getN());
		docw.setDeleted(true);
		update(docw);
		// super.delete(o);
	}

	@Override
	public void recalc(DocW docw) throws Exception {
		DocWItemCritery critery = new DocWItemCritery();
		critery.listDocW.add(docw);
		List<DocWItem> list = getDocWItemProvider().getByCritery(critery);
		BigDecimal sum = new BigDecimal("0.00");
		docw = (DocW) initialize(docw.getN());
		for (DocWItem docItem : list) {
			sum = sum.add(docItem.getSumma());
		}
		docw.setSumma(sum);
		update(docw);
	}

	public IDocWItemProvider getDocWItemProvider() {
		return docWItemProvider;
	}

	public void setDocWItemProvider(IDocWItemProvider docWItemProvider) {
		this.docWItemProvider = docWItemProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public String getVIPISKA() {
		return VIPISKA;
	}

	public void setVIPISKA(String vIPISKA) {
		VIPISKA = vIPISKA;
	}

	public ITypeDocProvider getTypeDocProvider() {
		return typeDocProvider;
	}

	public void setTypeDocProvider(ITypeDocProvider typeDocProvider) {
		this.typeDocProvider = typeDocProvider;
	}

	public IPaymentProvider getPaymentProvider() {
		return paymentProvider;
	}

	public void setPaymentProvider(IPaymentProvider paymentProvider) {
		this.paymentProvider = paymentProvider;
	}

	public ITypeDocShopProvider getTypeDocShopProvider() {
		return typeDocShopProvider;
	}

	public void setTypeDocShopProvider(ITypeDocShopProvider typeDocShopProvider) {
		this.typeDocShopProvider = typeDocShopProvider;
	}

	public IDocTitleProvider getDocTitleProvider() {
		return docTitleProvider;
	}

	public void setDocTitleProvider(IDocTitleProvider docTitleProvider) {
		this.docTitleProvider = docTitleProvider;
	}

	public IDocItemProvider getDocItemProvider() {
		return docItemProvider;
	}

	public void setDocItemProvider(IDocItemProvider docItemProvider) {
		this.docItemProvider = docItemProvider;
	}

	@Override
	public List<DocWInfo> getOnlyOpen(List<Shop> listShop) {
		DocWCritery critery = new DocWCritery();
		Calendar cal = Calendar.getInstance();
		// 01.10.2013- начало внедрения
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		critery.fromdate = cal.getTime();
		critery.todate = new Date();
		if (listShop.size() > 0) {
			critery.shops.addAll(listShop);
		}
		List<DocWInfo> list = getDocWInfo(critery);
		List<DocWInfo> ret = new ArrayList<DocWInfo>();
		for (DocWInfo docWInfo : list) {
			if (docWInfo.getQtyToCustomer() < docWInfo.getQtyOrder()) {
				ret.add(docWInfo);
			}
		}
		return ret;
	}

	public ICommentDocWProvider getCommentDocWProvider() {
		return commentDocWProvider;
	}

	public void setCommentDocWProvider(ICommentDocWProvider commentDocWProvider) {
		this.commentDocWProvider = commentDocWProvider;
	}

}
