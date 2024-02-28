package ru.perm.v.el59.office.dao.impl.plan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.critery.MoveCritery;
import ru.perm.v.el59.office.critery.PlanCritery;
import ru.perm.v.el59.office.critery.UserShopCritery;
import ru.perm.v.el59.office.db.BonusK;
import ru.perm.v.el59.office.db.Dolgnost;
import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.OpGroup;
import ru.perm.v.el59.office.db.UserShop;
import ru.perm.v.el59.office.db.plan.Plan;
import ru.perm.v.el59.office.db.plan.Smena;
import ru.perm.v.el59.office.db.plan.Tabel;
import ru.perm.v.el59.office.db.plan.TypeTabel;
import ru.perm.v.el59.office.db.plan.UserZP;
import ru.perm.v.el59.office.iproviders.IDolgnostProvider;
import ru.perm.v.el59.office.iproviders.IMoveProvider;
import ru.perm.v.el59.office.iproviders.IOpGroupProvider;
import ru.perm.v.el59.office.iproviders.ISetTypeStockProvider;
import ru.perm.v.el59.office.iproviders.IUserShopProvider;
import ru.perm.v.el59.office.iproviders.plan.IControllerZP;
import ru.perm.v.el59.office.iproviders.plan.IPlanProvider;
import ru.perm.v.el59.office.iproviders.plan.ISmenaProvider;
import ru.perm.v.el59.office.iproviders.plan.ITypeTabelProvider;
import ru.perm.v.el59.office.iproviders.plan.IUserZPProvider;

public class ControllerZP implements Serializable, IControllerZP {

	private static final long serialVersionUID = -8892857483517333078L;

	private static final String PRODAVEC = "Продавец";
	private static Logger LOGGER = Logger.getLogger(ControllerZP.class);
	private Map<String, ICalculatorForBonusK> mapCalculators = new HashMap<String, ICalculatorForBonusK>();
	private IMoveProvider moveProvider;
	private IPlanProvider planProvider;
	private IUserZPProvider userZPProvider;
	private IUserShopProvider userShopProvider;
	private IOpGroupProvider opGroupProvider;
	private ITypeTabelProvider typeTabelProvider;
	private ISetTypeStockProvider setTypeStockProvider;
	private IGenericDao tabelProvider;
	private ISmenaProvider smenaProvider;
	private IDolgnostProvider dolgnostProvider;

	private static final String[] listTypeTabel = new String[] { "ФОВ", "УН",
			"Доплаты" };
	private String nameRealOpGroup = "Реализация";
	private String nameReaturnOpGroup = "Возвраты";
	private Dolgnost prodavec;

	public ControllerZP() {
		super();
	}

	public void calc(PlanCritery planCritery) throws Exception {
		// Расчет факта продаж
		Plan plan = getPlanProvider().fillFact(planCritery);

		// Расчет ЗП
		List<UserZP> listUserZP = calcZP(plan);

		/*
		 * calcZPSeller(listUserZP, plan); calcDirectorZP(plan);
		 * calcAccBonus(listUserZP, plan); calcRestForPlan(plan);
		 */
	}

	private List<UserZP> calcZP(Plan plan) throws Exception {
		List<UserShop> listUser = getUserShop(plan.getShop().getName(), true,
				null);
/*		HashMap<String, UserShop> hashNamebest = new HashMap<String, UserShop>();
		for (UserShop userShop : listUser) {
			if (userShop == null || userShop.getNamebest() == null) {
				throw new Exception("UserShop is null");
			}
			if (!userShop.getNamebest().isEmpty()) {
				hashNamebest.put(userShop.getNamebest(), userShop);
			}
		}
*/		// Продажи
		MoveCritery moveCritery = new MoveCritery();
		moveCritery.arrOpgroup.add(getOpGroupProvider().getByEqName(
				getNameRealOpGroup()));
		moveCritery.arrOpgroup.add(getOpGroupProvider().getByEqName(
				getNameReaturnOpGroup()));
		moveCritery.arrshops.add(plan.getShop());
		moveCritery.fromDate = plan.getFromDate();
		Date toDate = plan.getToDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDate);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		moveCritery.toDate = cal.getTime();
		moveCritery.excludennum
				.addAll(getPlanProvider().getExcludeNnumFromZP());
		List<Move> listMove = getMoveProvider().getByCritery(moveCritery);

		if (plan.getFond() == null)
			plan.setFond(new BigDecimal("0.00"));

		List<UserZP> listUserZP = getUserZPProvider().getByNameAndPlan(null,
				plan);

		HashMap<String, UserZP> mapUserZP = new HashMap<String, UserZP>();
		for (UserZP userZP : listUserZP) {
			userZP.setSummaAcc(new BigDecimal("0.00"));
			userZP.setSummaAccBonus(new BigDecimal("0.00"));
			userZP.setSummaAccBonusAll(new BigDecimal("0.00"));
			userZP.setSummaBonusForAccPDS(new BigDecimal("0.00"));
			userZP.setSummaIn(new BigDecimal("0.00"));
			userZP.setSummaMainTovar(new BigDecimal("0.00"));
			userZP.setSummaMainTovarBonus(new BigDecimal("0.00"));
			userZP.setSummaOut(new BigDecimal("0.00"));
			userZP.setSummaPDS(new BigDecimal("0.00"));
			userZP.setSummaPDSBonus(new BigDecimal("0.00"));
			mapUserZP.put(userZP.getNamebest(), userZP);
		}

		// Заполнение оборотов
		for (Move move : listMove) {
			if (!mapUserZP.containsKey(move.getSeller())) {
				UserZP u = new UserZP();
				// Если сотрудник не найден , то внести его в таблицу
				// сотрудников предприятия
/*				UserShop userShop = new UserShop();
				if (move.getSeller() == null) {
					move.setSeller("");
				}
*/				
				u.setName(move.getSeller());
				u.setNamebest(move.getSeller());
				u.setDolgnost(getProdavec());
//				u.setShop(plan.getShop());
//				u.setWorked(true);
				u.setPlan(plan);
				u.setDolgnost(getProdavec());
				createUser(u);
//				getUserZPProvider().create(u);
				mapUserZP.put(u.getName(), u);
//				getUserShopProvider().create(userShop);
//				hashNamebest.put(userShop.getNamebest(), userShop);
//				mapUserZP.put(move.getSeller(), u);
			}
			UserZP userZP = mapUserZP.get(move.getSeller());

			BonusK bonusK = move.getTovar().getGroup().getBonusk();
			if (!mapCalculators.containsKey(bonusK.getName())) {
				LOGGER.error(String.format(
						"Не найдена ЗП группа(BonusK)=%s, move.n=%d ", move
								.getTovar().getGroup().getBonusk().getName(),
						move.getN()));
				continue;
			}
			userZP = mapCalculators.get(bonusK.getName()).addSumInOut(userZP,
					move);
		}

		// Расчет ЗП
		for (Move move : listMove) {
			UserZP userZP = mapUserZP.get(move.getSeller());
			BonusK bonusK = move.getTovar().getGroup().getBonusk();
			if (!mapCalculators.containsKey(bonusK.getName())) {
				LOGGER.error(String.format(
						"Не найдена ЗП группа(BonusK)=%s, move.n=%d ", move
								.getTovar().getGroup().getBonusk().getName(),
						move.getN()));
				continue;
			}
			userZP = mapCalculators.get(bonusK.getName()).calcZP(userZP, move);
			getMoveProvider().update(move);
		}

		for (UserZP userZP : mapUserZP.values()) {
			if (userZP.getN() > 0) {
				getUserZPProvider().update(userZP);
			} else {
				createUser(userZP);
			}
		}
		List<UserZP> ret = new ArrayList<UserZP>();
		ret.addAll(mapUserZP.values());
		return ret;
	}

	/**
	 * Получение сотрудников магазина по критериям
	 * 
	 * @param shopname
	 *            - магазин
	 * @param worked
	 *            - работает? (Да/Нет)
	 * @param name
	 *            - имя сотрудника
	 * @return
	 */
	private List<UserShop> getUserShop(String shopname, Boolean worked,
			String name) {
		UserShopCritery c = new UserShopCritery();
		c.setWorked(worked);
		c.setShopname(shopname);
		c.setName(name);
		List<UserShop> listUser = getUserShopProvider().getByCritery(c);
		return listUser;
	}

	private void createUser(UserZP u) throws Exception {
		Long n = getUserZPProvider().create(u);
		u.setN(n);
		for (String sTypeTabel : listTypeTabel) {
			List<TypeTabel> list = getTypeTabelProvider().getByCritery(
					new CommonCritery(sTypeTabel));
			if (list.size() > 0) {
				Tabel t = new Tabel();
				Plan plan = u.getPlan();
				Calendar c = Calendar.getInstance();
				c.setTime(plan.getMinDate());
				c.add(Calendar.DAY_OF_YEAR, -1);
				t.setTypeTabel(list.get(0));
				t.setUserzp(u);
				n = (Long) getTabelProvider().create(t);
				t.setN(n);
				u.addTabel(t);
				getUserZPProvider().update(u);
				// getTabelProvider().update(t);
				t = (Tabel) getTabelProvider().initialize(t.getN());
				while (c.getTime().getTime() < plan.getMaxDate().getTime()) {
					c.add(Calendar.DAY_OF_YEAR, 1);
					Smena s = new Smena();
					n = getSmenaProvider().getMax();
					s.setN(n);
					s.setDdate(c.getTime());
					s.setName("");
					s.setTabel(t);
					t.addSmena(s);
					getSmenaProvider().create(s);
					getTabelProvider().update(t);
				}
			}
		}
		getUserZPProvider().update(u);
	}

	public IMoveProvider getMoveProvider() {
		return moveProvider;
	}

	public void setMoveProvider(IMoveProvider moveProvider) {
		this.moveProvider = moveProvider;
	}

	@Override
	public void get(Plan plan) {

	}

	public IUserZPProvider getUserZPProvider() {
		return userZPProvider;
	}

	public void setUserZPProvider(IUserZPProvider userZPProvider) {
		this.userZPProvider = userZPProvider;
	}

	public Dolgnost getProdavec() {
		if (prodavec == null) {
			prodavec = (Dolgnost) getDolgnostProvider().getByCritery(
					new CommonCritery(PRODAVEC)).get(0);
		}
		return prodavec;
	}

	public void setProdavec(Dolgnost prodavec) {
		this.prodavec = prodavec;
	}

	public IPlanProvider getPlanProvider() {
		return planProvider;
	}

	public void setPlanProvider(IPlanProvider planProvider) {
		this.planProvider = planProvider;
	}

	@Override
	public List<Move> getMoveUser(UserZP userZP) {
		return _getMoveUser(userZP, null);
	}

	public List<Move> _getMoveUser(UserZP userZP, String bonusk) {

		MoveCritery c = new MoveCritery();
		c.fromDate = userZP.getPlan().getFromDate();
		c.toDate = userZP.getPlan().getToDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(c.toDate);
		cal.add(Calendar.DAY_OF_YEAR, (int) -1);
		c.toDate = cal.getTime();
		c.arrshops = getMoveProvider().getShopAndParther(
				userZP.getPlan().getShop());

		OpGroup realOpGroup = getOpGroupProvider().getByEqName(nameRealOpGroup);
		c.arrOpgroup.add(realOpGroup);
		OpGroup returnOpGroup = getOpGroupProvider().getByEqName(
				nameReaturnOpGroup);
		c.arrOpgroup.add(returnOpGroup);
		// Исключение продаж с клинтского склада. Убрал до лучших времен
		// c.arrtypestock.addAll(getWorkStock());
		c.bonusk = bonusk;
		c.sort.add("ddate");
		c.sort.add("tovar");
		List<UserShop> listUserShop = getUserShop(userZP.getPlan().getShop()
				.getName(), true, userZP.getName());
		List<Move> listMove = new ArrayList<Move>();
		if (listUserShop.size() > 0) {
			c.seller = listUserShop.get(0).getNamebest();
			listMove = getMoveProvider().getByCritery(c);
		}
		// Удаление под.карт
		List<Move> listForRemove = new ArrayList<Move>();
		List<Integer> listExcludeNnum = getPlanProvider()
				.getExcludeNnumFromZP();
		for (Move move : listMove) {
			if (listExcludeNnum.contains(move.getTovar().getNnum())) {
				listForRemove.add(move);
			}
		}
		if (listForRemove.size() > 0) {
			listMove.removeAll(listForRemove);
		}
		return listMove;
	}

	@Override
	public List<UserZP> loadTabel(List<UserZP> listUserZP) {
		ArrayList<UserZP> ret = new ArrayList<UserZP>();
		for (UserZP userZP : listUserZP) {
			UserZP u = getUserZPProvider().loadTabel(userZP.getN());
			ret.add(u);
		}
		return ret;
	}

	public Map<String, ICalculatorForBonusK> getMapCalculators() {
		return mapCalculators;
	}

	public void setMapCalculators(
			Map<String, ICalculatorForBonusK> mapCalculators) {
		this.mapCalculators = mapCalculators;
	}

	public String getNameRealOpGroup() {
		return nameRealOpGroup;
	}

	public void setNameRealOpGroup(String nameRealOpGroup) {
		this.nameRealOpGroup = nameRealOpGroup;
	}

	public String getNameReaturnOpGroup() {
		return nameReaturnOpGroup;
	}

	public void setNameReaturnOpGroup(String nameReaturnOpGroup) {
		this.nameReaturnOpGroup = nameReaturnOpGroup;
	}

	public IUserShopProvider getUserShopProvider() {
		return userShopProvider;
	}

	public void setUserShopProvider(IUserShopProvider userShopProvider) {
		this.userShopProvider = userShopProvider;
	}

	public ITypeTabelProvider getTypeTabelProvider() {
		return typeTabelProvider;
	}

	public void setTypeTabelProvider(ITypeTabelProvider typeTabelProvider) {
		this.typeTabelProvider = typeTabelProvider;
	}

	public IGenericDao getTabelProvider() {
		return tabelProvider;
	}

	public void setTabelProvider(IGenericDao tabelProvider) {
		this.tabelProvider = tabelProvider;
	}

	public ISmenaProvider getSmenaProvider() {
		return smenaProvider;
	}

	public void setSmenaProvider(ISmenaProvider smenaProvider) {
		this.smenaProvider = smenaProvider;
	}

	public IDolgnostProvider getDolgnostProvider() {
		return dolgnostProvider;
	}

	public void setDolgnostProvider(IDolgnostProvider dolgnostProvider) {
		this.dolgnostProvider = dolgnostProvider;
	}

	public IOpGroupProvider getOpGroupProvider() {
		return opGroupProvider;
	}

	public void setOpGroupProvider(IOpGroupProvider opGroupProvider) {
		this.opGroupProvider = opGroupProvider;
	}

	public ISetTypeStockProvider getSetTypeStockProvider() {
		return setTypeStockProvider;
	}

	public void setSetTypeStockProvider(
			ISetTypeStockProvider setTypeStockProvider) {
		this.setTypeStockProvider = setTypeStockProvider;
	}
}
