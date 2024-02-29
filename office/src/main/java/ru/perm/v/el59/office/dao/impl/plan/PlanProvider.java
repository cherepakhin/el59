package ru.perm.v.el59.office.dao.impl.plan;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.critery.MoveCritery;
import ru.perm.v.el59.office.critery.PlanCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.office.db.plan.Plan;
import ru.perm.v.el59.office.db.plan.UptRptAsp;
import ru.perm.v.el59.office.iproviders.IMoveProvider;
import ru.perm.v.el59.office.iproviders.IOpGroupProvider;
import ru.perm.v.el59.office.iproviders.ISetTypeStockProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.plan.IPlanProvider;

import java.math.BigDecimal;
import java.util.*;

public class PlanProvider extends GenericDaoHibernateImpl<Plan, Long> implements
		IPlanProvider {

	private static final String PDS = "ПДС";
	private static final String ACS = "Аксы";
	private IShopProvider shopProvider;
	private IOpGroupProvider opGroupProvider;
	private ISetTypeStockProvider setTypeStockProvider;
	private IMoveProvider moveProvider;
	private String nameRealOpGroup = "";
	private String nameReturnOpGroup = "";
	// Товары, почему-то исключенные из расчета зп по аксам и ПДС
	private List<String> excludeGroupTovarForAccPDS = new ArrayList<String>();
	// Исключить из факта продаж продажи этих продавцов(Ургачев)
	private List<String> excludeForSeller = new ArrayList<String>();
	// Товары, исключенные из расчета зп. Н.п. Подарочная карта
	private List<Integer> excludeNnumFromZP = new ArrayList<Integer>();

	public PlanProvider(Class<Plan> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Plan> getByCritery(Object critery) {
		PlanCritery c = (PlanCritery) critery;
		Criteria q = getSession().createCriteria(Plan.class);
		Criteria shopCriteria = q.createCriteria("shop");
		if (c.shop != null)
			q.add(Restrictions.eq("shop", c.shop));
		if (c.year != null)
			q.add(Restrictions.eq("year", c.year));
		if (c.month != null)
			q.add(Restrictions.eq("month", c.month));
		if ((c.fromDate != null) && (c.toDate != null)) {
			q.add(Restrictions.ge("fromDate", c.fromDate));
			q.add(Restrictions.le("toDate", c.toDate));
		}
		shopCriteria.addOrder(Order.asc("name"));
		List list = q.list();
		return list;
	}

	@Override
	public Plan initialize(Long id) {
		Plan o = (Plan) getSession().get(Plan.class, id);
		Hibernate.initialize(o.getDayplan());
		Hibernate.initialize(o.getRestforplan());
		return o;
	}

	@Override
	public List<Plan> createForAllShop(int year, int month) throws Exception {
		PlanCritery planCritery = new PlanCritery();
		planCritery.year = year;
		planCritery.month = month;
		List<Plan> listPlan = getByCritery(planCritery);
		if (listPlan.size() > 0) {
			throw new Exception("План на " + month + " " + year
					+ " уже создан.");
		}
		List<Shop> shops = getShopProvider().getWorkedShop();
		if (shops.size() == 0) {
			throw new Exception("Нет рабочих магазинов.");
		}
		int prevYear = year;
		int prevMonth = month - 1;
		// Если январь , то взять декабрь прерыдущего года
		if (month == 1) {
			prevYear--;
			prevMonth = 12;
		}
		planCritery = new PlanCritery();
		planCritery.year = prevYear;
		planCritery.month = prevMonth;
		List<Plan> listPrevPlan = getByCritery(planCritery);
		ArrayList<Plan> ret = new ArrayList<Plan>();
		for (Plan prevPlan : listPrevPlan) {
			if (shops.contains(prevPlan.getShop())) {
				Plan plan = new Plan();
				plan.setYear(year);
				plan.setMonth(month);
				plan.setShop(prevPlan.getShop());
				plan.setPlansummaout(prevPlan.getPlansummaout());
				plan.setPlanmarga(prevPlan.getPlanmarga());
				plan.setkTO(prevPlan.getkTO());
				plan.setkMarga(prevPlan.getkMarga());
				plan.setPlanPercentAcc(prevPlan.getPlanPercentAcc());
				plan.setPlanPercentPDS(prevPlan.getPlanPercentPDS());
				create(plan);
				ret.add(plan);
			}
		}
		return ret;
	}

	@Override
	public Plan fillFact(PlanCritery planCritery) throws Exception {
		Plan plan = getPlan(planCritery);
		if (plan == null) {
			throw new Exception("Расчет не выполнен. Не задан план на месяц.");
		}
		if (plan.getClosed()) {
			throw new Exception("Расчет не выполнен, т.к. закрыт.");
		}
		plan.setFond(new BigDecimal("0.00"));
		plan.setPds(new BigDecimal("0.00"));
		plan.setPdsin(new BigDecimal("0.00"));
		plan.setSummain(new BigDecimal("0.00"));
		plan.setSummaout(new BigDecimal("0.00"));
		plan.setAcc(new BigDecimal("0.00"));
		plan.setAccin(new BigDecimal("0.00"));
		plan.setDdate(new Date());
		// PLANACC = plan.getPlanPercentAcc();

		// Все продажи
		SummaInOut sio = calcFact(plan, null);
		plan.setSummain(sio.getSummain());
		plan.setSummaout(sio.getSummaout());

		// Продажи аксов
		ArrayList<String> listNameBonusKAcc = new ArrayList<String>();
		listNameBonusKAcc.add(ACS);
		sio = calcFact(plan, listNameBonusKAcc);
		plan.setAccin(sio.getSummain());
		plan.setAcc(sio.getSummaout());

		// Продажи ПДС
		ArrayList<String> listNameBonusKPDS = new ArrayList<String>();
		listNameBonusKPDS.add(PDS);
		sio = calcFact(plan, listNameBonusKPDS);
		plan.setPdsin(sio.getSummain());
		plan.setPds(sio.getSummaout());
		plan.calc();

		plan = calcDayPlan(plan);
		update(plan);
		return plan;
	}

	private Plan getPlan(PlanCritery planCritery) {
		List<Plan> listPlan = getByCritery(planCritery);
		if (listPlan != null && listPlan.size() > 0) {
			return listPlan.get(0);
		}
		return null;
	}

	/**
	 * Расчет факта продаж
	 * 
	 * @param plan
	 *            - в плане период продаж
	 * @param groups
	 *            - группы товаров(null если все)
	 * @return SummaInOut
	 * @throws Exception
	 */
	private SummaInOut calcFact(Plan plan, List<String> listNameBonusK)
			throws Exception {
		OpGroup realOpGroup = getByNameOpGroup(getNameRealOpGroup());
		OpGroup returnOpGroup = getByNameOpGroup(getNameReturnOpGroup());

		SummaInOut realSumInOut = getFactByPlan(plan, realOpGroup,
				listNameBonusK);

		Plan vozvrat = new Plan();
		vozvrat.setFromDate(plan.getFromDate());
		vozvrat.setToDate(plan.getToDate());
		vozvrat.setShop(plan.getShop());
		SummaInOut returnSumInOut = getFactByPlan(vozvrat, returnOpGroup,
				listNameBonusK);
		realSumInOut.setSummain(realSumInOut.getSummain().subtract(
				returnSumInOut.getSummain()));
		realSumInOut.setSummaout(realSumInOut.getSummaout().subtract(
				returnSumInOut.getSummaout()));
		return realSumInOut;
	}

	private OpGroup getByNameOpGroup(String nameOpGroup) throws Exception {
		OpGroup opGroup = getOpGroupProvider().getByEqName(nameOpGroup);
		if (opGroup == null) {
			throw new Exception("Не найдена группа операций " + nameOpGroup);
		}
		return opGroup;
	}

	/**
	 * Получение оборотов по группе операций для магазина во входных и выходных
	 * ценах
	 * 
	 * @param plan
	 *            - задает магазин и период
	 * @param opGroup
	 *            - группа операций
	 * @param listNameBonusK
	 *            - зарплатные группы товаров (если null, то все)
	 * @return
	 */
	public SummaInOut getFactByPlan(Plan plan, OpGroup opGroup,
			List<String> listNameBonusK) {
		List<Integer> excludeNnumFromZP = getExcludeNnumFromZP();
		String sql = "select new ru.perm.v.el59.office.db.SummaInOut(sum(m.summain),sum(m.summaout)) "
				+ "  from Move m where ";
		if (opGroup != null) {
			sql = sql + " m.operation.opgroup = :opGroup and ";
		}
		if (listNameBonusK != null && listNameBonusK.size() > 0) {
			sql = sql + " m.tovar.group.bonusk.name in (:listNameBonusK) and ";
		}

		if (excludeNnumFromZP.size() > 0) {
			sql = sql + " m.tovar.nnum not in (:excludeNnumFromZP) and ";
		}
		if (getExcludeForSeller().size() > 0) {
			sql = sql + " m.seller not in (:excludeForSeller) and ";
		}
		sql = sql + " m.fromstock in (:typestocks) and ";
		sql = sql
				+ " m.shop=:shop and m.ddate >=:fromdate and m.ddate<:todate ";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("fromdate", plan.getFromDate());
		q1.setParameter("todate", plan.getToDate());
		q1.setParameter("shop", plan.getShop());
		if (opGroup != null) {
			q1.setParameter("opGroup", opGroup);
		}
		if (listNameBonusK != null && listNameBonusK.size() > 0) {
			q1.setParameterList("listNameBonusK", listNameBonusK);
		}
		if (excludeNnumFromZP.size() > 0) {
			q1.setParameterList("excludeNnumFromZP", excludeNnumFromZP);
		}
		if (getExcludeForSeller().size() > 0) {
			q1.setParameterList("excludeForSeller", getExcludeForSeller());
		}
		SetTypeStock settypestock = getSetTypeStockProvider().getByEqName(
				"Без клиентской");
		q1.setParameterList("typestocks", settypestock.getTypeStocks());
		SummaInOut ret = (SummaInOut) q1.uniqueResult();
		return ret;
	}

	/**
	 * Расчет ежедневного выполнения
	 * 
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	private Plan calcDayPlan(Plan plan) throws Exception {
		plan = initialize(plan.getN());
		List<Plan> list = plan.getDayplan();
		plan.setDayplan(new ArrayList<Plan>());
		// Удаление ежедневных планов для пересоздания
		for (Plan p : list) {
			delete(p);
		}

		Calendar c = Calendar.getInstance();
		c.setTime(plan.getMinDate());
		c.add(Calendar.DAY_OF_YEAR, -1);
		while (c.getTime().getTime() < plan.getMaxDate().getTime()) {
			c.add(Calendar.DAY_OF_YEAR, 1);
			Plan dayplan = new Plan();
			// dayplan.setN(null);
			// Все продажи
			dayplan.setPlanPercentAcc(plan.getPlanPercentAcc());
			dayplan.setPlanPercentPDS(plan.getPlanPercentPDS());
			dayplan.setParent(plan);
			dayplan.setFromDate(c.getTime());
			Calendar todate = Calendar.getInstance();
			todate.setTime(c.getTime());
			todate.add(Calendar.DAY_OF_YEAR, 1);
			dayplan.setToDate(todate.getTime());
			dayplan.setShop(plan.getShop());
			SummaInOut sio = calcFact(dayplan, null);
			dayplan.setSummain(sio.getSummain());
			dayplan.setSummaout(sio.getSummaout());

			// Продажи аксов
			ArrayList<String> listNameBonusKAcc = new ArrayList<String>();
			listNameBonusKAcc.add(ACS);
			sio = calcFact(dayplan, listNameBonusKAcc);

			dayplan.setAccin(sio.getSummain());
			dayplan.setAcc(sio.getSummaout());

			// Продажи ПДС
			ArrayList<String> listNameBonusKPDS = new ArrayList<String>();
			listNameBonusKPDS.add(PDS);
			sio = calcFact(dayplan, listNameBonusKPDS);
			dayplan.setPdsin(sio.getSummain());
			dayplan.setPds(sio.getSummaout());
			dayplan.calc();
			create(dayplan);
			plan.addDayplan(dayplan);
			// getPlanProvider().update(dayplan);
		}
		update(plan);
		return plan;
	}


	public SummaInOut getSumFact(MoveCritery critery) {
		String sql = "select new ru.perm.v.el59.office.db.SummaInOut(sum(m.summain),sum(m.summaout)) "
				+ "  from Move m where ";
		if (critery.arrOpgroup.size() > 0) {
			sql = sql + " m.operation.opgroup in (:arrOpgroup) and ";
		}
		if (critery.groups != null && critery.groups.size() > 0) {
			sql = sql + " m.tovar.group in (:groups) and ";
		}
		if (excludeNnumFromZP.size() > 0) {
			sql = sql + " m.tovar.nnum not in (:excludeNnumFromZP) and ";
		}
		if (getExcludeForSeller().size() > 0) {
			sql = sql
					+ " m.seller not in (:excludeFromFactMoviesSellerForZP) and ";
		}
		sql = sql + " m.fromstock in (:typestocks) and ";
		sql = sql
				+ " m.shop in (:shops) and m.ddate >=:fromdate and m.ddate<:todate ";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("fromdate", critery.fromDate);
		q1.setParameter("todate", critery.toDate);
		q1.setParameterList("shops", critery.arrshops);
		if (critery.arrOpgroup.size() > 0) {
			q1.setParameterList("arrOpgroup", critery.arrOpgroup);
		}
		if (critery.groups != null && critery.groups.size() > 0) {
			q1.setParameterList("groups", critery.groups);
		}
		if (excludeNnumFromZP.size() > 0) {
			q1.setParameterList("excludeNnumFromZP", excludeNnumFromZP);
		}
		if (getExcludeForSeller().size() > 0) {
			q1.setParameterList("excludeFromFactMoviesSellerForZP",
					getExcludeForSeller());
		}
		SetTypeStock settypestock = getSetTypeStockProvider().getByEqName(
				"Без клиентской");
		q1.setParameterList("typestocks", settypestock.getTypeStocks());
		SummaInOut ret  = (SummaInOut) q1.uniqueResult();
		return ret;
	}

	@Override
	public List<Move> getMovePlan(Plan plan) throws Exception {
		OpGroup realOpGroup = getByNameOpGroup(getNameRealOpGroup());
		OpGroup returnOpGroup = getByNameOpGroup(getNameReturnOpGroup());

		MoveCritery c = new MoveCritery();
		c.fromDate = plan.getFromDate();
		c.toDate = plan.getToDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(c.toDate);
		cal.add(Calendar.DAY_OF_YEAR, (int) -1);
		c.toDate = cal.getTime();
		c.arrshops = getMoveProvider().getShopAndParther(plan.getShop());
		c.arrOpgroup.add(realOpGroup);
		c.arrOpgroup.add(returnOpGroup);
		SetTypeStock settypestock = getSetTypeStockProvider().getByEqName(
				"Без клиентской");
		c.arrtypestock.addAll(settypestock.getTypeStocks());
		List<Move> listMove = getMoveProvider().getByCritery(c);
		return listMove;
	}

	@Override
	public List<UptRptAsp> getUptRptAsp(Date fromDate, Date toDate,
			List<Shop> shops) {
		String sql = "select m.shop.cod,count(distinct m.numdoc),sum(m.qty),sum(m.summaout),sum(m.summain) "
				+ "from Move m where "
				+ "m.ddate>=:fromdate and m.ddate<=:todate and "
				+ "m.operation.opgroup.name=:operation and "
				+ "m.tovar.nnum not in (:excludeNnumFromZP) and "
				+ "m.seller not in (:excludeForSeller) and "
				+ "m.shop in (:shops) "
				+ "group by m.shop.cod "
				+ "order by m.shop.cod";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("fromdate", fromDate);
		q1.setParameter("todate", toDate);
		q1.setParameter("operation", getNameRealOpGroup());
		q1.setParameterList("excludeNnumFromZP", excludeNnumFromZP);
		q1.setParameterList("excludeForSeller", getExcludeForSeller());
		if (shops == null || shops.size() == 0) {
			shops = getShopProvider().getWorkedShop();
		}
		q1.setParameterList("shops", shops);
		ArrayList<UptRptAsp> ret = new ArrayList<UptRptAsp>();
		List list = q1.list();
		for (Object obj : list) {
			Object[] row =(Object[]) obj;
			ret.add(new UptRptAsp(getShopProvider().read((String) row[0]),
					(Long) row[1],
					(BigDecimal) row[2],
					(BigDecimal) row[3],
					(BigDecimal) row[4],
					BigDecimal.ZERO,
					BigDecimal.ZERO
					));
		}
		HashMap<Shop,UptRptAsp> map = new HashMap<Shop,UptRptAsp>();
		for (UptRptAsp uptRptAsp : ret) {
			map.put(uptRptAsp.getShop(), uptRptAsp);
		}

		// Расчет Аксов 
		String sqlBonusTovar = "select m.shop.cod,count(distinct m.numdoc),sum(m.qty),sum(m.summaout),sum(m.summain) "
				+ "from Move m where "
				+ "m.ddate>=:fromdate and m.ddate<=:todate and "
				+ "m.operation.opgroup.name=:operation and "
				+ "m.tovar.group.bonusk.name=:bonusK and "
				+ "m.tovar.nnum not in (:excludeNnumFromZP) and "
				+ "m.seller not in (:excludeForSeller) and "
				+ "m.shop in (:shops) "
				+ "group by m.shop.cod "
				+ "order by m.shop.cod";
		Query q1BonusTovar = getSession().createQuery(sqlBonusTovar);
		q1BonusTovar.setParameter("fromdate", fromDate);
		q1BonusTovar.setParameter("todate", toDate);
		q1BonusTovar.setParameter("operation", getNameRealOpGroup());
		q1BonusTovar.setParameterList("excludeNnumFromZP", excludeNnumFromZP);
		q1BonusTovar.setParameterList("excludeForSeller", getExcludeForSeller());
		q1BonusTovar.setParameterList("shops", shops);
		// Заполнение поля продаж аксов
		q1BonusTovar.setParameter("bonusK", ACS);
		List<UptRptAsp> accses = new ArrayList<UptRptAsp>();
		list = q1BonusTovar.list();
		for (Object obj : list) {
			Object[] row =(Object[]) obj;
			accses.add(new UptRptAsp(getShopProvider().read((String) row[0]),
					(Long) row[1],
					(BigDecimal) row[2],
					(BigDecimal) row[3],
					(BigDecimal) row[4],
					BigDecimal.ZERO,
					BigDecimal.ZERO
					));
		}
		for (UptRptAsp uptRptAsp : accses) {
			map.get(uptRptAsp.getShop()).setSummaOutAcc(uptRptAsp.getSummaOut());
		}
		// Заполнение поля продаж защит
		q1BonusTovar.setParameter("bonusK", PDS);
		List<UptRptAsp> protections = new ArrayList<UptRptAsp>();
		list = q1BonusTovar.list();
		for (Object obj : list) {
			Object[] row =(Object[]) obj;
			protections.add(new UptRptAsp(getShopProvider().read((String) row[0]),
					(Long) row[1],
					(BigDecimal) row[2],
					(BigDecimal) row[3],
					(BigDecimal) row[4],
					BigDecimal.ZERO,
					BigDecimal.ZERO
					));
		}
		for (UptRptAsp uptRptAsp : protections) {
			map.get(uptRptAsp.getShop()).setSummaOutPDS(uptRptAsp.getSummaOut());
		}
		return ret;
	}

	public IMoveProvider getMoveProvider() {
		return moveProvider;
	}

	public void setMoveProvider(IMoveProvider moveProvider) {
		this.moveProvider = moveProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public IOpGroupProvider getOpGroupProvider() {
		return opGroupProvider;
	}

	public void setOpGroupProvider(IOpGroupProvider opGroupProvider) {
		this.opGroupProvider = opGroupProvider;
	}

	public String getNameRealOpGroup() {
		return nameRealOpGroup;
	}

	public void setNameRealOpGroup(String nameRealOpGroup) {
		this.nameRealOpGroup = nameRealOpGroup;
	}

	public String getNameReturnOpGroup() {
		return nameReturnOpGroup;
	}

	public void setNameReturnOpGroup(String nameReturnOpGroup) {
		this.nameReturnOpGroup = nameReturnOpGroup;
	}

	@Override
	public List<String> getExcludeGroupTovarForAccPDS() {
		return excludeGroupTovarForAccPDS;
	}

	public void setExcludeGroupTovarForAccPDS(
			List<String> excludeGroupTovarForAccPDS) {
		this.excludeGroupTovarForAccPDS = excludeGroupTovarForAccPDS;
	}

	public ISetTypeStockProvider getSetTypeStockProvider() {
		return setTypeStockProvider;
	}

	public void setSetTypeStockProvider(
			ISetTypeStockProvider setTypeStockProvider) {
		this.setTypeStockProvider = setTypeStockProvider;
	}

	/**
	 * Исключить из факта продаж продажи этих продавцов(Ургачев)
	 */
	@Override
	public List<String> getExcludeForSeller() {
		return excludeForSeller;
	}

	/**
	 * Исключить из факта продаж продажи этих продавцов(Ургачев)
	 * 
	 * @param excludeForSeller
	 *            - имена продавцов
	 */
	public void setExcludeForSeller(List<String> excludeForSeller) {
		this.excludeForSeller = excludeForSeller;
	}

	@Override
	public List<Integer> getExcludeNnumFromZP() {
		return excludeNnumFromZP;
	}

	public void setExcludeNnumFromZP(List<Integer> excludeNnumFromZP) {
		this.excludeNnumFromZP = excludeNnumFromZP;
	}
}
