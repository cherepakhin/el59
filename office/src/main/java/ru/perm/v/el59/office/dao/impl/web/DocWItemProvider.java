package ru.perm.v.el59.office.dao.impl.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.el59.office.db.DocItem;
import ru.el59.office.db.Shop;
import ru.el59.office.db.web.DocW;
import ru.el59.office.db.web.DocWItem;
import ru.el59.office.db.web.DocWItemInfo;
import ru.perm.v.el59.dto.office.iproviders.IDocItemProvider;
import ru.perm.v.el59.dto.office.iproviders.IShopProvider;
import ru.perm.v.el59.dto.office.iproviders.ITovarProvider;
import ru.perm.v.el59.dto.office.iproviders.shopmodel.IDocTitleProvider;
import ru.perm.v.el59.dto.office.iproviders.web.DocWCritery;
import ru.perm.v.el59.dto.office.iproviders.web.DocWItemCritery;
import ru.perm.v.el59.dto.office.iproviders.web.IDocWItemProvider;
import ru.perm.v.el59.dto.office.iproviders.web.IDocWProvider;
import ru.el59.office.shopmodel.DocDetail;
import ru.el59.office.shopmodel.DocTitle;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class DocWItemProvider extends GenericDaoMessageImpl<DocWItem, Long>
		implements IDocWItemProvider {

	private IDocWProvider docWProvider;
	private ITovarProvider tovarProvider;
	private IDocItemProvider docItemProvider;
	private IDocTitleProvider docTitleProvider;
	private IShopProvider shopProvider;
	private Long numberNullDocWItem = 0L;
	private DocWItem nullDocWItem;
	private Long numberNullDocDetail = 0L;
	private DocDetail nullDocDetail;

	public DocWItemProvider(Class<DocWItem> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocWItem> getByCritery(Object critery) {
		DocWItemCritery c = (DocWItemCritery) critery;
		Criteria criteria = getSession().createCriteria(DocWItem.class);
		if (c.listDocW.size() > 0) {
			criteria.add(Restrictions.in("docw", c.listDocW));
		}
		if (c.nnum != null || !c.nameTovar.equals("")) {
			Criteria tovarCritery = criteria.createCriteria("tovar");
			if (c.nnum != null) {
				tovarCritery.add(Restrictions.eq("nnum", c.nnum));
			}
			if (!c.nameTovar.equals("")) {
				tovarCritery.add(Restrictions.like("name", c.nameTovar,
						MatchMode.ANYWHERE).ignoreCase());
			}
		}
		if (c.listDocItem.size() > 0) {
			Criteria docItemCritery = criteria.createCriteria("docItem");
			ArrayList<Long> listn = new ArrayList<Long>();
			for (DocItem docItem : c.listDocItem) {
				listn.add(docItem.getN());
			}
			docItemCritery.add(Restrictions.in("n", listn));
		}
		if (c.deleted = false) {
			criteria.add(Restrictions.eq("deleted", false));
		}
		criteria.addOrder(Order.asc("docw"));
		List<DocWItem> list = criteria.list();
		return list;
	}

	@Override
	public void deleteByDocW(DocW docw) {
		String sql = "update DocWItem set deleted=true where docw=:docw";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("docw", docw);
		q1.executeUpdate();
	}

	@Override
	public Long create(DocWItem o) throws Exception {
		if (o.getDocItem() == null) {
			o.setDocItem(getDocItemProvider().getNullDocItem());
		}
		if (o.getDocDetailInput() == null) {
			o.setDocDetailInput(getNullDocDetail());
		}
		if (o.getDocDetailOut() == null) {
			o.setDocDetailOut(getNullDocDetail());
		}
		/*
		 * RouteDoc routeDoc=new RouteDoc(); routeDoc.setN(0L);
		 * o.setRouteDoc(routeDoc);
		 */Long n = super.create(o);
		DocWItem di = (DocWItem) o;
		getDocWProvider().recalc(di.getDocw());
		return n;
	}

	@Override
	public void delete(DocWItem o) throws Exception {
		super.delete(o);
		DocWItem di = (DocWItem) o;
		getDocWProvider().recalc(di.getDocw());
	}

	@Override
	public void update(DocWItem o) throws Exception {
		super.update(o);
		DocWItem di = (DocWItem) o;
		getDocWProvider().recalc(di.getDocw());
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public IDocWProvider getDocWProvider() {
		return docWProvider;
	}

	public void setDocWProvider(IDocWProvider docWProvider) {
		this.docWProvider = docWProvider;
	}

	public IDocItemProvider getDocItemProvider() {
		return docItemProvider;
	}

	public void setDocItemProvider(IDocItemProvider docItemProvider) {
		this.docItemProvider = docItemProvider;
	}

	@Override
	public List<DocWItemInfo> getDocWItemInfo(DocWCritery critery) {
		Criteria criteria = getSession().createCriteria(DocWItem.class)
				.setFetchMode("docItem", FetchMode.JOIN)
				.setFetchMode("docItem.doc", FetchMode.JOIN)
				.setFetchMode("docDetailInput", FetchMode.JOIN)
				.setFetchMode("docDetailInput.docTitle", FetchMode.JOIN)
				.setFetchMode("docDetailOut", FetchMode.JOIN)
				.setFetchMode("docDetailOut.docTitle", FetchMode.JOIN)
				.setFetchMode("docw", FetchMode.JOIN)
				;
		if (critery.deleted == false) {
			criteria.add(Restrictions.eq("deleted", false));
		}
		Criteria docwCritery = criteria.createCriteria("docw");
		if (critery.fromdate != null && critery.todate != null) {
			docwCritery.add(Restrictions.ge("ddate", critery.fromdate));
			docwCritery.add(Restrictions.le("ddate", critery.todate));
			// }

			if (critery.shops.size() > 0) {
				docwCritery.add(Restrictions.in("shop", critery.shops));
			}
			if (critery.noRouteDoc != null) {
				Criteria routeDocCritery = criteria.createCriteria("routeDoc");
				routeDocCritery.add(Restrictions.eq("n", 0L));
				criteria.add(Restrictions.ge("ddatePlanOut", critery.fromdate));
				criteria.add(Restrictions.le("ddatePlanOut", critery.todate));
			}
			docwCritery.addOrder(Order.asc("numdoc"));
			List<DocWItem> listDocWItem = criteria.list();
			for (DocWItem docWItem : listDocWItem) {
				init(docWItem);
			}
			return getListDocWItemInfo(listDocWItem);
		}
		ArrayList<DocWItemInfo> ret = new ArrayList<DocWItemInfo>();
		return ret;
/*	Второй вариант	
 	String sql = "select {t.*},{dwi.*},{dw.*},{c.*},{s.*},{td.*},{m.*},{gc.*},{di.*},{d.*} " +
				"from db.Tovar t, " +
				"db.DocWItem dwi join db.DocItem di on dwi.docItem_n=di.n ," +
				"db.Doc d , " +
				"db.DocW dw,db.Contragent c, " +
				"db.Shop s,db.TypeDoc td,db.Manager m,db.GroupContragent gc  " +
				"where t.nnum=dwi.tovar_nnum and " +
				"t.manager_n=m.n and " +
				"dw.n=dwi.docw_n and " +
				"dw.typedoc_n=td.n and " +
				"dw.contragent_n=c.n and " +
				"c.groupcontragent_n=gc.n and "+
				"dw.shop_cod=s.cod and " +
				"dwi.deleted=false and " +
				"dw.deleted=false and " +
				"di.doc_n=d.n and ";
		if (critery.fromdate != null && critery.todate != null) {
			sql=sql+"dw.ddate>=:fromdate and dw.ddate<=:todate and ";
		}
		sql=sql+"1=1";
		Query q1 = getSession().createSQLQuery(sql)
				.addEntity("t",Tovar.class)
				.addEntity("dwi",DocWItem.class)
				.addEntity("dw",DocW.class)
				.addEntity("c",Contragent.class)
				.addEntity("s",Shop.class)
				.addEntity("td",TypeDoc.class)
				.addEntity("m",Manager.class)
				.addEntity("gc",GroupContragent.class)
				.addJoin("di", "dwi.docItem")
				.addEntity("d", Doc.class)
				;
		
		if (critery.fromdate != null && critery.todate != null) {
//			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			q1.setParameter("fromdate", critery.fromdate);
			q1.setParameter("todate", critery.todate);
		}
		List list = q1.list();
		List<DocWItemInfo> docWItemInfos = new ArrayList<DocWItemInfo>();
		for (Object obj : list) {
			Object[] arr=(Object[]) obj;
			Tovar t = (Tovar) arr[0];
			DocWItem dwi = (DocWItem) arr[1];
			DocW dw = (DocW) arr[2];
			Contragent c = (Contragent) arr[3];
			Shop s = (Shop) arr[4];
			TypeDoc td = (TypeDoc) arr[5];
			dw.setTypeDoc(td);
			Manager m = (Manager) arr[6];
			GroupContragent gc = (GroupContragent) arr[7];
			c.setGroupContragent(gc);
			t.setManager(m);
			dwi.setDocw(dw);
			dwi.setTovar(t);
			dw.setShop(s);
			dw.setContragent(c);
			DocItem di = (DocItem) arr[8];
			Doc d= (Doc) arr[9];
			di.setDoc(d);
			dwi.setDocItem(di);
			DocWItemInfo docWItemInfo = new DocWItemInfo();
			docWItemInfo.setDocwItem(dwi);
			docWItemInfos.add(docWItemInfo);
		}
		return docWItemInfos;
*/	}

	@Override
	public List<DocWItemInfo> getListDocWItemInfo(List<DocWItem> listDocWItem) {
		ArrayList<DocWItemInfo> ret = new ArrayList<DocWItemInfo>();
		DocTitle nullDocTitle = getDocTitleProvider().getNullDocTitle();
		for (DocWItem docWItem : listDocWItem) {
			DocWItemInfo docWItemInfo = new DocWItemInfo();
			docWItemInfo.setDocwItem(docWItem);
			// docWItemInfo.setSumPay(getDocWProvider().getPay(docWItem.getDocw()));
			docWItemInfo.setSumPay(new BigDecimal("0.00"));
			ret.add(docWItemInfo);
		}
		return ret;
	}

	/**
	 * Получение оплаты
	 * 
	 * @param docWItem
	 * @return
	 */
	private BigDecimal getPay(DocWItem docWItem) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Получение документов отгрузки
	 * 
	 * @return
	 */
	private DocTitle getDelivered(DocWItem docWItem) {
		DocTitle doc = getDocWProvider().getDocTitleByDocW(docWItem.getDocw());
		if (doc == null)
			return null;

		if (doc.getParent() != null
				&& !doc.getParent().getN().equals(0L)) {
			// Выписка бэст
			DocTitle docOrder = doc.getParent();

			String sql = "from DocDetail  " + "where "
					+ "docTitle.parent=:docOrder and "
					+ "docTitle.typedoc.name=:real and " + "tovar = :tovar";

			Query q1 = getSession().createQuery(sql);
			q1.setParameter("docOrder", docOrder);
			q1.setParameter("real", getDocTitleProvider().getNameReal());
			q1.setParameter("tovar", docWItem.getTovar());

			List<DocDetail> lisDocDetail = q1.list();

			if (lisDocDetail.size() == 0) {
				return null;
			} else {
				return lisDocDetail.get(0).getDocTitle();
			}
		}
		return null;
	}

	public IDocTitleProvider getDocTitleProvider() {
		return docTitleProvider;
	}

	public void setDocTitleProvider(IDocTitleProvider docTitleProvider) {
		this.docTitleProvider = docTitleProvider;
	}

	@Override
	public void linkToDocWItem(DocDetail docDetail) throws Exception {
		if (docDetail.getDocTitle().getTypeDocShop().getName()
				.equals(getDocTitleProvider().getNameInInvoice())) {
			String sql = "from DocWItem  " + "where " + "ddate<=:ddate and "
					+ "docDetailInput=0 and " + "tovar = :tovar and "
					+ "docw.shop=:shop";

			Query q1 = getSession().createQuery(sql);
			q1.setParameter("ddate", docDetail.getDocTitle().getDdate());
			q1.setParameter("tovar", docDetail.getTovar());
			q1.setParameter("shop", docDetail.getDocTitle().getShop());
			List<DocWItem> list = q1.list();
			if (list.size() > 0) {
				DocWItem docWItem = list.get(0);
				docWItem.setDocDetailInput(docDetail);
				update(docWItem);
			}
		}
	}

	@Override
	public DocWItem getNullDocWItem() {
		if (nullDocWItem == null) {
			nullDocWItem = read(numberNullDocWItem);
			if (nullDocWItem == null) {
				nullDocWItem = new DocWItem();
				nullDocWItem.setN(numberNullDocWItem);
				try {
					create(nullDocWItem);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return nullDocWItem;
	}

	public DocDetail getNullDocDetail() {
		if (nullDocDetail == null) {
			nullDocDetail = new DocDetail();
			nullDocDetail.setN(numberNullDocDetail);
		}
		return nullDocDetail;
	}

	@Override
	public void unLinkDocWItem(List<DocItem> listDocItem) {
		DocItem nullDocItem = getDocItemProvider().getNullDocItem();
		String sql = "update DocWItem set  docItem=:nullDocItem "
				+ "where docItem in (:listDocItem) ";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("nullDocItem", nullDocItem);
		q1.setParameterList("listDocItem", listDocItem);
		q1.executeUpdate();
	}

	@Override
	public List<DocWItemInfo> getOnlyOpen(Shop shop) {
		DocWCritery critery = new DocWCritery();
		Calendar cal = Calendar.getInstance();
		// 01.10.2013- начало внедрения
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		critery.fromdate = cal.getTime();
		critery.todate = new Date();
		if (shop != null) {
			critery.shops.add(shop);
		} else {
			critery.shops=getShopProvider().getWorkedShop();
		}
		List<DocWItemInfo> list = getDocWItemInfo(critery);
		ArrayList<DocWItemInfo> ret = new ArrayList<DocWItemInfo>();
		for (DocWItemInfo docWItemInfo : list) {
			// System.out.println("docWItemInfo.getDocwItem().getN() "+docWItemInfo.getDocwItem().getN());
			// System.out.println("docWItemInfo.getDocwItem().getDocDetailOut().getN() "+docWItemInfo.getDocwItem().getDocDetailOut().getN());
			if (docWItemInfo.getDocwItem().getDocDetailOut() != null
					&& docWItemInfo.getDocwItem().getDocDetailOut()
							.equals(getNullDocDetail())) {
				ret.add(docWItemInfo);
			}
		}
		return ret;
	}

	@Override
	public List<DocWItem> getInitializedByCritery(
			DocWItemCritery docWItemCritery) {
		List<DocWItem> listDocWItem = getByCritery(docWItemCritery);
		for (DocWItem docWItem : listDocWItem) {
			init(docWItem);
		}
		return listDocWItem;
	}
	
	private void init(DocWItem docWItem) {
		Hibernate.initialize(docWItem.getDocItem());
		Hibernate.initialize(docWItem.getDocDetailInput());
		Hibernate.initialize(docWItem.getDocDetailOut());
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}


}
