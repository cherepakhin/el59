package ru.perm.v.el59.office.dao.impl.shopmodel;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.web.DocW;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.critery.PaymentCritery;
import ru.perm.v.el59.office.iproviders.shopmodel.*;
import ru.perm.v.el59.office.shopmodel.Payment;
import ru.perm.v.el59.office.util.Helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public abstract class APaymentProvider<T extends Payment, PK extends Serializable>
		extends GenericDaoHibernateImpl implements IPaymentProvider {

	private ITypeDocShopProvider typeDocShopProvider;
	private IDocTitleProvider docTitleProvider;
	private ITypePaymentProvider typePaymentProvider;
	private IReasonProvider reasonProvider;
	private IShopProvider shopProvider;

	public APaymentProvider(Class<T> type) {
		super(type);
	}

	@Override
	public Long getMax() {
		Long n = (Long) getSession().createQuery(
				"select max(n)+1 from " + Payment.class.getSimpleName())
				.uniqueResult();
		if (n == null)
			n = 0L;
		return n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getByCritery(Object critery) {
		PaymentCritery c = (PaymentCritery) critery;
		Criteria paymentCriteria = getSession().createCriteria(Payment.class);
		if ((c.fromdate != null) && (c.todate != null)) {
			Calendar fromdate = Calendar.getInstance();
			fromdate.setTime(c.fromdate);
			int year = fromdate.get(Calendar.YEAR);
			int month = fromdate.get(Calendar.MONTH);
			int day = fromdate.get(Calendar.DAY_OF_MONTH);
			fromdate.clear();
			fromdate.set(year, month, day);

			Calendar todate = Calendar.getInstance();
			todate.setTime(c.todate);
			year = todate.get(Calendar.YEAR);
			month = todate.get(Calendar.MONTH);
			day = todate.get(Calendar.DAY_OF_MONTH);
			todate.clear();
			todate.set(year, month, day);
			paymentCriteria.add(Restrictions.between("ddate", fromdate.getTime(),
					todate.getTime()));
		}
		if (c.docTitle != null) {
			paymentCriteria.add(Restrictions.eq("docTitle", c.docTitle));
		}
		if (c.listTypePayment.size() > 0) {
			Criteria typepayment = paymentCriteria
					.createCriteria("typePayment");
			typepayment.add(Restrictions.in("name", c.listTypePayment));
		}
		if (c.listExpense.size() > 0) {
			Criteria expense = paymentCriteria.createCriteria("expense");
			expense.add(Restrictions.in("name", c.listExpense));
		}
		if (c.listReason.size() > 0) {
			Criteria reason = paymentCriteria.createCriteria("reason");
			reason.add(Restrictions.in("name", c.listReason));
		}
		if (c.listTypeCash.size() > 0) {
			Criteria typecash = paymentCriteria.createCriteria("typecash");
			typecash.add(Restrictions.in("name", c.listTypeCash));
		}
		if (c.nn != null) {
			paymentCriteria.add(Restrictions.eq("nn", c.nn));
		}
		if (c.listShop.size() > 0) {
			paymentCriteria.add(Restrictions.in("shop", c.listShop));
		}
		paymentCriteria.add(Restrictions.eq("deleted", c.deleted));
		List<Payment> list = paymentCriteria.list();
		return list;
	}

	public Map<Shop, BigDecimal> getSumPaymentReal(PaymentCritery critery) {
		String sql = "select p.shop.cod,sum(p.summa) from Payment p "
				+ "where  ";
		if (critery.listShop.size() > 0) {
			sql = sql + " p.shop in (:listShop) and ";
		}
		sql = sql
				+ "p.ddate>=:fromdate and p.ddate<=:todate and "
				+ "p.reason in (:listReasonReal) and deleted=false group by p.shop.cod";

		Query q1 = getSession().createQuery(sql);
		if (critery.listShop.size() > 0) {
			q1.setParameterList("listShop", critery.listShop);
		}
		q1.setParameterList("listReasonReal", getReasonProvider()
				.getRealReason());
		q1.setParameter("fromdate", Helper.getNullHour(critery.fromdate));
		q1.setParameter("todate", Helper.getNullHour(critery.todate));
		Iterator iter = q1.list().iterator();
		Object[] row;
		HashMap<Shop, BigDecimal> ret = new HashMap<Shop, BigDecimal>();
		while (iter.hasNext()) {
			row = (Object[]) iter.next();
			Shop shop = getShopProvider().read((String) row[0]);
			ret.put(shop, (BigDecimal) row[1]);
		}
		return ret;
	}

	/**
	 * Получение суммы платежей по выписке
	 */
	public BigDecimal getSumPay(DocW docW) {

		BigDecimal ret = new BigDecimal("0.00");
		List<Payment> listPayment = getListPayDocW(docW);
		for (Payment payment : listPayment) {
			ret = ret.add(payment.getSumma());
		}
		return ret;
	}

	public List<Payment> getListPayDocW(DocW docW) {
		String sql = "from Payment  "
				+ "where docTitle.n in (select dd.docTitle.n from DocDetail dd, DocWItem dwi where "
				+ "dd.n=dwi.docDetailOut.n and dwi.docw.n=:docw_n)";

		Query q1 = getSession().createQuery(sql);
		q1.setParameter("docw_n", docW.getN());
		List<Payment> listPayment = q1.list();
		return listPayment;

		/*
		 * DocTitleCritery docTitleCritery = new DocTitleCritery();
		 * docTitleCritery.numdoc=docW.getNumdoc();
		 * docTitleCritery.typedocs.add(
		 * getTypeDocShopProvider().getByEqName(docW.getTypedoc().getName()));
		 * List<DocTitle> listDocTitle =
		 * getDocTitleProvider().getByCritery(docTitleCritery);
		 * 
		 * if(listDocTitle.size()==0) return new ArrayList<Payment>(); DocTitle
		 * docTitleW = listDocTitle.get(0);
		 * 
		 * PaymentCritery paymentCritery = new PaymentCritery();
		 * paymentCritery.docTitle=docTitleW; List<Payment> listPayment =
		 * getByCritery(paymentCritery); ArrayList<Payment> listRezult = new
		 * ArrayList<Payment>(); listRezult.addAll(listPayment);
		 * 
		 * if(docTitleW.getParent()!=null &&
		 * !docTitleW.getParent().getN().equals(0L)) { // Выписка бэст
		 * DocTitle docOrder = docTitleW.getParent(); // Платежи paymentCritery
		 * = new PaymentCritery(); paymentCritery.docTitle=docOrder; listPayment
		 * = getByCritery(paymentCritery); listRezult.addAll(listPayment); }
		 */
	}

	public IDocTitleProvider getDocTitleProvider() {
		return docTitleProvider;
	}

	public void setDocTitleProvider(IDocTitleProvider docTitleProvider) {
		this.docTitleProvider = docTitleProvider;
	}

	public ITypeDocShopProvider getTypeDocShopProvider() {
		return typeDocShopProvider;
	}

	public void setTypeDocShopProvider(ITypeDocShopProvider typeDocShopProvider) {
		this.typeDocShopProvider = typeDocShopProvider;
	}

	public ITypePaymentProvider getTypePaymentProvider() {
		return typePaymentProvider;
	}

	public void setTypePaymentProvider(ITypePaymentProvider typePaymentProvider) {
		this.typePaymentProvider = typePaymentProvider;
	}

	public IReasonProvider getReasonProvider() {
		return reasonProvider;
	}

	public void setReasonProvider(IReasonProvider reasonProvider) {
		this.reasonProvider = reasonProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

}
