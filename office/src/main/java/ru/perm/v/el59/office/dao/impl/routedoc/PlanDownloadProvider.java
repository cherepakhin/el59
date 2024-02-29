package ru.perm.v.el59.office.dao.impl.routedoc;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.Contragent;
import ru.el59.office.db.GroupContragent;
import ru.el59.office.db.Manager;
import ru.el59.office.db.Shop;
import ru.el59.office.db.routedoc.CrossPlanDownload;
import ru.el59.office.db.routedoc.PlanDownload;
import ru.el59.office.db.routedoc.PlanDownloadSum;
import ru.perm.v.el59.dto.office.critery.PlanDownloadCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.iproviders.IGroupContragentProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.ITypeFileProvider;
import ru.perm.v.el59.office.iproviders.routedoc.CrossPlanDownloadSum;
import ru.perm.v.el59.office.iproviders.routedoc.IPathPageProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IPlanDownloadProvider;
import ru.perm.v.el59.office.iproviders.routedoc.IPlanDownloadSumProvider;
import ru.perm.v.el59.office.util.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PlanDownloadProvider extends
		GenericDaoHibernateImpl<PlanDownload, Long> implements
		IPlanDownloadProvider {

	private IGroupContragentProvider groupContragentProvider;
	private List<String> listNameGroupContragent;
	private List<GroupContragent> listGroupContragent;
	private IShopProvider shopProvider;
	private IPlanDownloadSumProvider planDownloadSumProvider;
	private IPathPageProvider pathPageProvider;
	private ITypeFileProvider typeFileProvider;

	private CharSequence nameEldorado = "Эльдорадо";

	public PlanDownloadProvider(Class<PlanDownload> type) {
		super(type);
	}

	@Override
	public List<PlanDownload> getByCritery(Object critery) {
		Criteria criteria = getSession().createCriteria(PlanDownload.class);
		PlanDownloadCritery c = (PlanDownloadCritery) critery;
		if (c.ddate != null) {
			criteria.add(Restrictions.eq("ddate", c.ddate));
		}
		if (c.listShop.size() > 0) {
			criteria.add(Restrictions.in("shop", c.listShop));
		}
		criteria.addOrder(Order.asc("n"));
		List<PlanDownload> ret = criteria.list();
		return ret;
	}

	@Override
	public List<PlanDownload> getInPeriod(Date ddate, List<Shop> listShop) {
		PlanDownloadCritery critery = new PlanDownloadCritery();
		critery.ddate = ddate;
		if (listShop != null && listShop.size() > 0) {
			critery.listShop = listShop;
		}
		return getByCritery(critery);
	}

	@Override
	public List<GroupContragent> getListGroupContragent() {
		listGroupContragent = new ArrayList<GroupContragent>();
		for (String nameGroupContragent : listNameGroupContragent) {
			GroupContragent g = (GroupContragent) getGroupContragentProvider()
					.getByEqName(nameGroupContragent);
			if (g != null) {
				listGroupContragent.add(g);
			}
		}
		return listGroupContragent;
	}

	@Override
	public void createForAllShop(Date ddate, Manager manager) throws Exception {
		// Проверить на существование перед созданием
		ddate = Helper.getNullHour(ddate);
		List<Date> listDdate = getLastDate(1);
		Date lastDdate = listDdate.get(0);
		if (ddate.equals(lastDdate)) {
			throw new Exception("План погрузок на "
					+ Helper.getDateFornmatter().format(ddate)
					+ " уже существует");
		}
		List<Shop> shops = getShopProvider().getWorkedShop();
		getListGroupContragent();
		for (Shop shop : shops) {
			if (!shop.getCod().equals("00000")) {
				PlanDownload plan = new PlanDownload();
				for (GroupContragent g : listGroupContragent) {
					plan = new PlanDownload();
					plan.setShop(shop);
					plan.setGroupContragent(g);
					plan.setDdate(ddate);
					create(plan);
					// getPlanDownloadSumProvider().createForPlan(plan,
					// manager);
				}
			}
		}
		// Создание маршрутных листов
		// getPathPageProvider().createByTemplateWorked(manager);
	}

	@Override
	public void delete(List<CrossPlanDownload> listPlanDownload)
			throws Exception {

		for (CrossPlanDownload crossPlanDownload : listPlanDownload) {
			getPlanDownloadSumProvider().deleteForPlanDownload(
					crossPlanDownload.getPlanDownload1());
			PlanDownload p1 = read(crossPlanDownload.getPlanDownload1().getN());
			if (p1 != null) {
				delete(p1);
			}
			getPlanDownloadSumProvider().deleteForPlanDownload(
					crossPlanDownload.getPlanDownload2());
			PlanDownload p2 = read(crossPlanDownload.getPlanDownload2().getN());
			if (p2 != null) {
				delete(p2);
			}
		}
	}

	@Override
	public Map<Shop, Map<GroupContragent, PlanDownload>> getPivot(
			PlanDownloadCritery critery) throws Exception {
		getListGroupContragent();
		List<PlanDownload> listPlanDownload = getByCritery(critery);
		HashMap<Shop, Map<GroupContragent, PlanDownload>> mapShop = new HashMap<Shop, Map<GroupContragent, PlanDownload>>();
		for (PlanDownload planDownload : listPlanDownload) {
			Shop shop = planDownload.getShop();
			if (!mapShop.containsKey(shop)) {
				HashMap<GroupContragent, PlanDownload> mapGroupContragent = new HashMap<GroupContragent, PlanDownload>();
				mapShop.put(shop, mapGroupContragent);
			}
			mapShop.get(shop).put(planDownload.getGroupContragent(),
					planDownload);
		}
		List<CrossPlanDownloadSum> listCrossPlanDownloadSum = getPlanDownloadSumProvider()
				.getByPlanDownload(critery);

		for (CrossPlanDownloadSum crossPlanDownloadSum : listCrossPlanDownloadSum) {
			for (PlanDownloadSum planDownloadSum : crossPlanDownloadSum
					.getListPlanDownloadSum()) {
				if (listPlanDownload
						.contains(planDownloadSum.getPlanDownload())) {
					int i = listPlanDownload.indexOf(planDownloadSum
							.getPlanDownload());
					PlanDownload p = listPlanDownload.get(i);
					p.setPlanUsed(p.getPlanUsed()
							.add(planDownloadSum.getPlan()));
					if(!p.getMapTypeFileSum().containsKey(planDownloadSum.getOrder().getTypeFile())) {
						p.getMapTypeFileSum().put(planDownloadSum.getOrder().getTypeFile(), planDownloadSum.getOrder().getSumma());
					} else {
						BigDecimal s = p.getMapTypeFileSum().get(planDownloadSum.getOrder().getTypeFile());
						s=s.add(planDownloadSum.getOrder().getSumma()).setScale(2, RoundingMode.HALF_UP);
						p.getMapTypeFileSum().put(planDownloadSum.getOrder().getTypeFile(), s);
					}

					if(!p.getMapTypeFileSum().containsKey(planDownloadSum.getInvoice().getTypeFile())) {
						p.getMapTypeFileSum().put(planDownloadSum.getInvoice().getTypeFile(), planDownloadSum.getInvoice().getSumma());
					} else {
						BigDecimal s = p.getMapTypeFileSum().get(planDownloadSum.getInvoice().getTypeFile());
						s=s.add(planDownloadSum.getInvoice().getSumma()).setScale(2, RoundingMode.HALF_UP);
						p.getMapTypeFileSum().put(planDownloadSum.getInvoice().getTypeFile(), s);
					}
					
					if(!p.getMapTypeFileSum().containsKey(planDownloadSum.getReceive().getTypeFile())) {
						p.getMapTypeFileSum().put(planDownloadSum.getReceive().getTypeFile(), planDownloadSum.getReceive().getSumma());
					} else {
						BigDecimal s = p.getMapTypeFileSum().get(planDownloadSum.getReceive().getTypeFile());
						s=s.add(planDownloadSum.getReceive().getSumma()).setScale(2, RoundingMode.HALF_UP);
						p.getMapTypeFileSum().put(planDownloadSum.getReceive().getTypeFile(), s);
					}
				}
			}
		}
		return mapShop;
	}

	/*
	 * @Override public List<CrossPlanDownload> getCrossPlanDownload(
	 * PlanDownloadCritery critery) { getListGroupContragent();
	 * List<PlanDownload> list = getByCritery(critery);
	 * ArrayList<CrossPlanDownload> ret = new ArrayList<CrossPlanDownload>();
	 * HashMap<Date, HashMap<Shop, CrossPlanDownload>> hash1 = new HashMap<Date,
	 * HashMap<Shop, CrossPlanDownload>>(); for (PlanDownload plan : list) { if
	 * (!hash1.containsKey(plan.getDdate())) { HashMap<Shop, CrossPlanDownload>
	 * hash2 = new HashMap<Shop, CrossPlanDownload>(); hash2.put(plan.getShop(),
	 * createCrossPlanDownload(plan)); hash1.put(plan.getDdate(), hash2); } else
	 * { HashMap<Shop, CrossPlanDownload> hash2 = hash1.get(plan .getDdate());
	 * if (!hash2.containsKey(plan.getShop())) { hash2.put(plan.getShop(),
	 * createCrossPlanDownload(plan)); } else { CrossPlanDownload c =
	 * hash2.get(plan.getShop()); setSumCrossDownLoad(c, plan); } } }
	 * Collection<HashMap<Shop, CrossPlanDownload>> listHash2 = hash1.values();
	 * for (HashMap<Shop, CrossPlanDownload> hash2 : listHash2) {
	 * ret.addAll(hash2.values()); } for (CrossPlanDownload c : ret) {
	 * BigDecimal plan1 = new BigDecimal("0.00"); BigDecimal plan2 = new
	 * BigDecimal("0.00"); if (c.getPlanDownload1() != null &&
	 * c.getPlanDownload1().getN() != null) { plan1 =
	 * getFact(c.getPlanDownload1()); if (plan1 == null) { plan1 = new
	 * BigDecimal("0.00"); } } if (c.getPlanDownload2() != null &&
	 * c.getPlanDownload2().getN() != null) { plan2 =
	 * getFact(c.getPlanDownload2()); if (plan2 == null) { plan2 = new
	 * BigDecimal("0.00"); } } c.setSum1Fact(plan1); c.setSum2Fact(plan2);
	 * c.setSumAllFact(plan1.add(plan2)); }
	 * 
	 * Collections.sort(ret); return ret; }
	 */
	/*
	 * private BigDecimal getFact(PlanDownload planDownload) { String sql =
	 * "select sum(plan) " + "from PlanDownloadSum  where PlanDownload.n=:n";
	 * Query q1 = getSession().createQuery(sql); q1.setParameter("n",
	 * planDownload.getN()); Object o = q1.uniqueResult(); if (o != null) {
	 * return (BigDecimal) o; } return new BigDecimal("0.00"); }
	 */
	/*
	 * private CrossPlanDownload createCrossPlanDownload(PlanDownload plan) {
	 * CrossPlanDownload c = new CrossPlanDownload();
	 * c.setDdate(plan.getDdate()); c.setShop(" " + plan.getShop().getName());
	 * setSumCrossDownLoad(c, plan); return c; }
	 */
	/*
	 * private void setSumCrossDownLoad(CrossPlanDownload c, PlanDownload plan)
	 * { if (plan.getGroupContragent().equals(listGroupContragent.get(0))) {
	 * c.setSum1(plan.getSumma()); c.setPlanDownload1(plan); } else {
	 * c.setSum2(plan.getSumma()); c.setPlanDownload2(plan); }
	 * c.setSumAll(c.getSum1().add(c.getSum2())); }
	 */
	public List<String> getListNameGroupContragent() {
		return listNameGroupContragent;
	}

	public void setListNameGroupContragent(List<String> listNameGroupContragent) {
		this.listNameGroupContragent = listNameGroupContragent;
	}

	public IGroupContragentProvider getGroupContragentProvider() {
		return groupContragentProvider;
	}

	public void setGroupContragentProvider(
			IGroupContragentProvider groupContragentProvider) {
		this.groupContragentProvider = groupContragentProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	@Override
	public boolean isExist(PlanDownload plan) {
		PlanDownloadCritery c = new PlanDownloadCritery();
		c.ddate = plan.getDdate();
		c.listShop.add(plan.getShop());
		List<PlanDownload> list = getByCritery(c);
		return list.size() > 0;
	}

	private PlanDownload getMaxPlanDownload(Long n) {
		String sql = "select max(n) " + "from PlanDownload  where n<:n";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("n", n);
		Object o = q1.uniqueResult();
		if (o != null) {
			PlanDownload p1 = read((Long) o);
			return p1;
		}
		return null;
	}

	@Override
	public List<Date> getLastDate(Integer n) {
		String sql = "select distinct ddate from PlanDownload  order by  ddate desc";
		Query q1 = getSession().createQuery(sql);
		// q1.setFirstResult(1);
		q1.setMaxResults(n);
		List<Date> o = q1.list();

		return o;
	}

	@Override
	public List<PlanDownload> getLastPlanDownload() {
		return getLessMaxPlanDownload(1000000000L);
	}

	public List<PlanDownload> getLessMaxPlanDownload(Long max) {
		ArrayList<PlanDownload> ret = new ArrayList<PlanDownload>();
		PlanDownload lastPlan = getMaxPlanDownload(max);
		ret.add(lastPlan);
		PlanDownloadCritery c = new PlanDownloadCritery();
		c.ddate = lastPlan.getDdate();
		List<PlanDownload> list = getByCritery(c);
		ret.addAll(list);
		return ret;
	}

	@Override
	public List<PlanDownload> getPrevLastPlanDownload(
			List<PlanDownload> lastPlans) {
		Long max = 0L;
		for (PlanDownload planDownload : lastPlans) {
			if (planDownload.getN() > max) {
				max = planDownload.getN();
			}
		}
		return getLessMaxPlanDownload(max);
	}

	@Override
	public String checkDelete(List<CrossPlanDownload> listPlanDownload) {
		// FIXME Проверить на удаление
		return "";
	}

	public IPathPageProvider getPathPageProvider() {
		return pathPageProvider;
	}

	public void setPathPageProvider(IPathPageProvider pathPageProvider) {
		this.pathPageProvider = pathPageProvider;
	}

	@Override
	public PlanDownload getBy(Shop shop, Date ddate, Contragent contragent) {
		PlanDownloadCritery c = new PlanDownloadCritery();
		c.ddate = ddate;
		c.listShop.add(shop);
		List<PlanDownload> list = getByCritery(c);
		if (contragent.getName().contains(getNameEldorado())) {
			for (PlanDownload planDownload : list) {
				if (planDownload.getGroupContragent().getName()
						.contains(getNameEldorado())) {
					return planDownload;
				}
			}
		} else {
			for (PlanDownload planDownload : list) {
				if (!planDownload.getGroupContragent().getName()
						.contains(getNameEldorado())) {
					return planDownload;
				}
			}
		}
		return null;
	}

	public IPlanDownloadSumProvider getPlanDownloadSumProvider() {
		return planDownloadSumProvider;
	}

	public void setPlanDownloadSumProvider(
			IPlanDownloadSumProvider planDownloadSumProvider) {
		this.planDownloadSumProvider = planDownloadSumProvider;
	}

	public ITypeFileProvider getTypeFileProvider() {
		return typeFileProvider;
	}

	public void setTypeFileProvider(ITypeFileProvider typeFileProvider) {
		this.typeFileProvider = typeFileProvider;
	}

	public CharSequence getNameEldorado() {
		return nameEldorado;
	}

	public void setNameEldorado(CharSequence nameEldorado) {
		this.nameEldorado = nameEldorado;
	}

}
