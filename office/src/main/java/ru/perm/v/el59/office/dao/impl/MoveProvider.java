package ru.perm.v.el59.office.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.critery.MoveCritery;
import ru.perm.v.el59.office.critery.RestCritery;
import ru.perm.v.el59.office.critery.TovarCritery;
import ru.perm.v.el59.office.db.Agent;
import ru.perm.v.el59.office.db.GroupTovar;
import ru.perm.v.el59.office.db.MasterLevel;
import ru.perm.v.el59.office.db.Move;
import ru.perm.v.el59.office.db.MoveSummary;
import ru.perm.v.el59.office.db.OpGroup;
import ru.perm.v.el59.office.db.Operation;
import ru.perm.v.el59.office.db.PriceType;
import ru.perm.v.el59.office.db.Rest;
import ru.perm.v.el59.office.db.SetTovar;
import ru.perm.v.el59.office.db.SetTypeStock;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.StringSummaInOut;
import ru.perm.v.el59.office.db.SummaInOut;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TypeStock;
import ru.perm.v.el59.office.db.dto.MoveOperationSum;
import ru.perm.v.el59.office.iproviders.IGroupTovarProvider;
import ru.perm.v.el59.office.iproviders.IMoveProvider;
import ru.perm.v.el59.office.iproviders.IOperationProvider;
import ru.perm.v.el59.office.iproviders.IRestProvider;
import ru.perm.v.el59.office.iproviders.ISetGroupTovarProvider;
import ru.perm.v.el59.office.iproviders.ISetTovarProvider;
import ru.perm.v.el59.office.iproviders.ISetTypeStockProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.iproviders.shopmodel.ITypePDSProvider;
import ru.perm.v.el59.office.util.Helper;

public class MoveProvider extends GenericDaoHibernateImpl<Move, Long> implements
		IMoveProvider, Serializable {

	private static final long serialVersionUID = 4010349270310802243L;
	private final static Logger LOGGER = Logger.getLogger(MoveProvider.class);
	private ITovarProvider tovarprovider;
	private ISetGroupTovarProvider setgrouptovarprovider;
	private IShopProvider shopprovider;
	private IGenericDao<Agent, Long> agentprovider;
	private IOperationProvider operationprovider;
	private ISetTypeStockProvider settypestockprovider;
	private IGroupTovarProvider groupTovarProvider;
	private IRestProvider restProvider;
	private ISetTovarProvider setTovarProvider;
	private ITypePDSProvider typePDSProvider;
	// private List<Integer> excludeNnumFromZP = new ArrayList<Integer>();
	private String nameReal = "";
	private String namePrihod = "";
	private Integer nnumBonusCard=71049743; 
	// private List<String> excludeFromFactMoviesSellerForZP = new
	// ArrayList<String>();

	private List<String> competitionCodGroupTovar;
	private List<String> competitionNameOpGroup;
	private String competitionNameTovar;
	private String realOperationName="Реализация";

	public MoveProvider(Class<Move> type) {
		super(type);
	}

	@Override
	public List<Move> getByCritery(Object c) {
		MoveCritery critery = (MoveCritery) c;

		Criteria moveCritery = getSession().createCriteria(Move.class);
		Criteria tovarCritery = moveCritery.createCriteria("tovar");
		Criteria fromstockCritery = moveCritery.createCriteria("fromstock");
		Criteria shopCritery = moveCritery.createCriteria("shop");
		Criteria operationCritery = moveCritery.createCriteria("operation");
		Criteria groupCritery = tovarCritery.createCriteria("group");
		Criteria bonuskCritery = groupCritery.createCriteria("bonusk");

		if (critery.listIncludeSetTovar.size() > 0) {
			for (SetTovar setTovar : critery.listIncludeSetTovar) {
				setTovar = (SetTovar) getSetTovarProvider().initialize(
						setTovar.getN());
				for (Tovar tovar : setTovar.getTovars()) {
					critery.nnum.add(tovar.getNnum());
				}
			}
		}
		if (critery.listExcludeSetTovar.size() > 0) {
			for (SetTovar setTovar : critery.listExcludeSetTovar) {
				setTovar = (SetTovar) getSetTovarProvider().initialize(
						setTovar.getN());
				for (Tovar tovar : setTovar.getTovars()) {
					critery.excludennum.add(tovar.getNnum());
				}
			}
		}

		if (critery.groups.size() > 0) {
			Disjunction gc = Restrictions.disjunction();
			for (int i = 0; i < critery.groups.size(); i++) {
				String s = critery.groups.get(i).getCod();
				s = Helper.clear00(s);
				gc.add(Restrictions.like("cod", s, MatchMode.START)
						.ignoreCase());
			}
			groupCritery.add(gc);
		}
		if ((critery.fromDate != null) && (critery.toDate != null)) {
			moveCritery.add(Restrictions.between("ddate", critery.fromDate,
					critery.toDate));
		}
		if (!critery.numdoc.equals("")) {
			moveCritery.add(Restrictions.eq("numdoc", critery.numdoc));
		}
		if (!critery.abcd.equals("")) {
			tovarCritery.add(Restrictions.like("abcd", critery.abcd,
					MatchMode.ANYWHERE));

		}
		if (!critery.name.equals("")) {
			tovarCritery.add(Restrictions.like("name", critery.name,
					MatchMode.ANYWHERE).ignoreCase());
		}
		if (critery.nnum.size() > 0) {
			tovarCritery.add(Restrictions.in("nnum", critery.nnum));
		}
		if (critery.excludennum.size() > 0) {
			tovarCritery.add(Restrictions.not(Restrictions.in("nnum",
					critery.excludennum)));
		}
		if (critery.qty != null) {
			moveCritery.add(Restrictions.eq("qty", critery.qty));
		}
		if (critery.vid != null) {
			moveCritery.add(Restrictions.eq("vid", critery.vid));
		}
		if (critery.fromstock_n != null) {
			fromstockCritery.add(Restrictions.eq("n", critery.fromstock_n));
		}
		if (critery.arrshops.size() > 0) {
			moveCritery.add(Restrictions.in("shop", critery.arrshops));
		}
		if (critery.arrtypestock.size() > 0) {
			moveCritery.add(Restrictions.in("fromstock", critery.arrtypestock));
		}
		if (critery.arrOpgroup.size() > 0) {
			// Criteria opgroupCritery =
			// operationCritery.createCriteria("opgroup");
			operationCritery
					.add(Restrictions.in("opgroup", critery.arrOpgroup));
		}
		if (critery.seller != null) {
			moveCritery.add(Restrictions.eq("seller", critery.seller));
		}
		if (critery.bonusk != null) {
			if (groupCritery == null) {
				groupCritery = tovarCritery.createCriteria("group");
			}
			bonuskCritery.add(Restrictions.eq("name", critery.bonusk));
		}
		if (critery.sort.size() == 0) {
			moveCritery.addOrder(Order.asc("tovar.nnum"));
		} else {
			for (String sortOrder : critery.sort)
				if(sortOrder.contains("tovar.")) {
					tovarCritery.addOrder(Order.asc(sortOrder.replace("tovar.", "")));
				}  else {
					moveCritery.addOrder(Order.asc(sortOrder));
				}
		}
		List<Move> list = moveCritery.list();
		return list;
	}

	@Override
	public List<Move> getResultCompetition(Date fromDate, Date toDate) {
		String sql = "select m.seller,m.shop.cod,sum(m.summaout)  from Move m where  ";
		if (getCompetitionCodGroupTovar() != null
				&& getCompetitionCodGroupTovar().size() > 0) {
			sql = sql + " m.operation.opgroup.name in (:listNameOpGroup) and ";
		}
		if (getCompetitionCodGroupTovar() != null
				&& getCompetitionCodGroupTovar().size() > 0) {
			sql = sql + " m.tovar.group.cod in (:listCodGroupTovar) and ";
		}
		sql = sql
				+ " m.shop in (:shops) and m.ddate >=:fromdate and "
				+ " m.ddate<:todate and m.seller is not null and lower(m.tovar.name) like lower(:name) ";
		sql = sql + "group by m.seller,m.shop.cod ";

		Query q1 = getSession().createQuery(sql);

		q1.setParameter("fromdate", fromDate);
		q1.setParameter("todate", toDate);
		List<Shop> listShop = getShopprovider().getWorkedShop();
		q1.setParameterList("shops", listShop);
		q1.setParameterList("listNameOpGroup", getCompetitionNameOpGroup());
		q1.setParameterList("listCodGroupTovar", getCompetitionCodGroupTovar());
		q1.setParameter("name", getCompetitionNameTovar());

		Iterator iter = q1.list().iterator();
		Object[] row;
		ArrayList<Move> ret = new ArrayList<Move>();
		while (iter.hasNext()) {
			row = (Object[]) iter.next();
			Move m = new Move();
			m.setSeller((String) row[0]);
			Shop shop = getShopprovider().read((String) row[1]);
			m.setShop(shop);
			m.setSummaout((BigDecimal) row[2]);
			ret.add(m);
		}

		Collections.sort(ret, new Comparator<Move>() {

			@Override
			public int compare(Move o1, Move o2) {
				return o2.getSummaout().compareTo(o1.getSummaout());
			}
		});
		return ret;
	}

	public Iterator<Move> getAllIterator() {
		Criteria moveCritery = getSession().createCriteria(Move.class);
		Criteria tovarCritery = moveCritery.createCriteria("tovar");
		moveCritery.addOrder(Order.asc("shop"));
		moveCritery.addOrder(Order.asc("fromstock"));
		tovarCritery.addOrder(Order.asc("nnum"));
		moveCritery.addOrder(Order.asc("ddate"));
		Iterator<Move> list = moveCritery.list().iterator();
		return list;
	}

	/*
	 * @Override public List<Move> getUserZPByPlan(Plan plan, ArrayList<OpGroup>
	 * arrOpgroup, ArrayList<GroupTovar> groups) { String sql =
	 * "select m.seller,sum(m.cenaInOnDate * m.qty),sum(m.summaout) " +
	 * "  from Move m where "; if (arrOpgroup.size() > 0) { sql = sql +
	 * " m.operation.opgroup in (:arrOpgroup) and"; } if (groups != null &&
	 * groups.size() > 0) { sql = sql + " m.tovar.group in (:groups) and "; } if
	 * (excludeNnumFromZP.size() > 0) { sql = sql +
	 * " m.tovar.nnum not in (:excludeNnumFromZP) and "; } sql = sql +
	 * " m.fromstock in (:typestocks) and "; sql = sql +
	 * " m.shop in (:shops) and m.ddate >=:fromdate and m.ddate<:todate "; sql =
	 * sql + "group by m.seller ";
	 * 
	 * Query q1 = getSession().createQuery(sql); Date d = plan.getFromDate(); d
	 * = plan.getToDate();
	 * 
	 * q1.setParameter("fromdate", plan.getFromDate());
	 * q1.setParameter("todate", plan.getToDate()); ArrayList<Shop> listShop =
	 * getShopAndParther(plan.getShop()); q1.setParameterList("shops",
	 * listShop); if (groups != null && groups.size() > 0) {
	 * q1.setParameterList("groups", groups); } if (excludeNnumFromZP.size() >
	 * 0) { q1.setParameterList("excludeNnumFromZP", excludeNnumFromZP); } if
	 * (arrOpgroup.size() > 0) { q1.setParameterList("arrOpgroup", arrOpgroup);
	 * } SetTypeStock settypestock = getSettypestockprovider().getByCritery( new
	 * CommonCritery("Без клиентской")).get(0);
	 * q1.setParameterList("typestocks", settypestock.getTypeStocks());
	 * ArrayList q1list = (ArrayList) q1.list(); Iterator iter =
	 * q1.list().iterator(); Object[] row; ArrayList<Move> ret = new
	 * ArrayList<Move>(); while (iter.hasNext()) { row = (Object[]) iter.next();
	 * Move m = new Move(); m.setSeller((String) row[0]);
	 * m.setSummain((BigDecimal) row[1]); m.setSummaout((BigDecimal) row[2]);
	 * ret.add(m); } return ret; }
	 */
	@Override
	public ArrayList<Shop> getShopAndParther(Shop shop) {
		ArrayList<Shop> listShop = new ArrayList<Shop>();
		listShop.add(shop);
		if (!shop.getPartnerShops().equals("")) {
			String[] arrCodShop = shop.getPartnerShops().split(";");
			for (String cod : arrCodShop) {
				Shop _shop = getShopprovider().read(cod);
				if (_shop != null) {
					listShop.add(_shop);
				}
			}
		}
		return listShop;
	}

	public List<StringSummaInOut> getSumByOperation(MoveCritery critery) {
		String sql = "select m.operation.name,sum(m.summain),sum(m.summaout) "
				+ "  from Move m where ";
		/*
		 * if (plan.getShop()!=null) { sql=sql+" m.shop.cod in (";
		 * sql=sql+"'"+plan.getShop().getCod()+"',"; sql=sql+"'') and "; }
		 */
		if (critery.arrOpgroup.size() > 0) {
			sql = sql + " m.operation.opgroup.name in (";
			for (OpGroup opgroup : critery.arrOpgroup)
				sql = sql + "'" + opgroup.getName() + "',";
			sql = sql + "'') and ";
		}
		if (critery.groups != null) {
			if (critery.groups.size() > 0) {
				String setgroupscod = "";
				for (GroupTovar groupTovar : critery.groups) {
					setgroupscod = setgroupscod + "'" + groupTovar.getCod()
							+ "',";
				}
				setgroupscod = setgroupscod + "''";
				sql = sql + " m.tovar.group.cod in (" + setgroupscod + ") and ";
			}
		}
		SetTypeStock settypestock = getSettypestockprovider().getByCritery(
				new CommonCritery("Без клиентской")).get(0);
		sql = sql + " m.fromstock.n in (";
		for (TypeStock ts : settypestock.getTypeStocks())
			sql = sql + ts.getN() + ",";
		sql = sql + "0) and ";

		sql = sql
				+ " m.shop=:shop and m.ddate >=:fromdate and m.ddate<=:todate "
				+ "group by m.operation.znak,m.operation.name "
				+ "order by m.operation.znak desc,m.operation.name";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("fromdate", critery.fromDate);
		q1.setParameter("todate", critery.toDate);
		q1.setParameter("shop", critery.arrshops.get(0));
		ArrayList q1list = (ArrayList) q1.list();
		Iterator iter = q1.list().iterator();
		Object[] row;
		List<StringSummaInOut> ret = new ArrayList<StringSummaInOut>();
		while (iter.hasNext()) {
			row = (Object[]) iter.next();
			SummaInOut summaInOut = new SummaInOut((BigDecimal) row[1],(BigDecimal) row[2]);
			StringSummaInOut stringSummaInOut = new StringSummaInOut(
					(String) row[0], summaInOut);
			ret.add(stringSummaInOut);
		}
		return ret;
	}

	public ArrayList<MoveSummary> getSumByCritery(MoveCritery critery) {
		String stocks = "";
		if (critery.arrtypestock.size() > 0) {
			for (TypeStock ts : critery.arrtypestock)
				stocks = stocks + ts.getN() + ",";
		}
		stocks = stocks + "-1";
		String sql = "select r.shop.name,r.tovar.group.cod,r.tovar.nnum, sum(r.qty),sum(r.summain),sum(r.summaout),min(r.ddate),max(r.ddate) "
				+ "  from Move r where ";
		if (!critery.abcd.equals(""))
			sql = sql + " r.tovar.abcd=:abcd and ";
		if (!critery.name.equals(""))
			sql = sql + " r.tovar.name=:name and ";
		if (critery.nnum.size() > 0)
			sql = sql + " r.tovar.nnum=:nnum and ";
		if (critery.arrOpgroup.size() > 0) {
			sql = sql + " r.operation.opgroup.name in (";
			for (OpGroup opgroup : critery.arrOpgroup)
				sql = sql + "'" + opgroup.getName() + "',";
			sql = sql + "'') and ";
		}
		if (critery.arrshops.size() > 0) {
			sql = sql + " r.shop.cod in (";
			for (Shop shop : critery.arrshops)
				sql = sql + "'" + shop.getCod() + "',";
			sql = sql + "'') and ";
		}
		if (critery.groups.size() > 0) {
			String setgroupscod = getSetgrouptovarprovider().getByCodesCritery(
					critery.groups);
			sql = sql + " r.tovar.group.cod in (" + setgroupscod + ") and ";
		}
		if (critery.arrtypestock.size() > 0) {
			sql = sql + " r.fromstock.n in (" + stocks + ") and ";
		}
		sql = sql + " r.ddate >=:fromdate and r.ddate<=:todate "
				+ "group by r.shop.name,r.tovar.group.cod, r.tovar.nnum "
				+ "order by r.shop.name, r.tovar.nnum,r.tovar.group.cod ";
		Query q1 = getSession().createQuery(sql);

		if (!critery.abcd.equals(""))
			q1.setParameter("abcd", critery.abcd);
		if (!critery.name.equals(""))
			q1.setParameter("name", critery.name);
		if (critery.nnum.size() > 0)
			q1.setParameter("nnum", critery.nnum.get(0));
		// if (critery.opgroup!="")
		// q1.setParameter("opgroup",critery.opgroup);
		// if (critery.setGroupTovarName!="")
		// 0503010100;0503010200;0301030200;0301030100;0301020100;0301020200;0301010500;0301010400;0301010100;0301010200;0301010300
		// q1.setParameter("setgroupscod","0503010100;0503010200;0301030200;0301030100;0301020100;0301020200;0301010500;0301010400;0301010100;0301010200;0301010300");
		// q1.setParameter("setgroupscod",setgroupscod);
		q1.setParameter("fromdate", critery.fromDate);
		q1.setParameter("todate", critery.toDate);
		ArrayList q1list = (ArrayList) q1.list();
		Iterator iter = q1.list().iterator();
		ArrayList<MoveSummary> ret = new ArrayList<MoveSummary>();
		Object[] row;
		while (iter.hasNext()) {
			row = (Object[]) iter.next();
			MoveSummary m = new MoveSummary();
			m.setShopname((String) row[0]);
			Tovar t = new Tovar();
			TovarCritery c = new TovarCritery();
			c.withoutDuplicates=false;
			c.nnum.add((Integer) row[2]);
			ArrayList<Tovar> listTovars = (ArrayList<Tovar>) getTovarprovider()
					.getByCritery(c);
			if(listTovars!=null && listTovars.size()>0) {
				m.setTovar(listTovars.get(0));
				m.setSumQty((BigDecimal) row[3]);
				m.setSumSummain((BigDecimal) row[4]);
				m.setSumSummaout((BigDecimal) row[5]);
				m.setFromdate((Date) row[6]);
				m.setTodate((Date) row[7]);
				ret.add(m);
			}
		}
		return ret;
	}

	public ArrayList<Move> getNumDocByCritery(MoveCritery critery) {
		Criteria moveCritery = getSession().createCriteria(Move.class);
		Criteria tovarCritery = moveCritery.createCriteria("tovar");
		Criteria groupCritery = tovarCritery.createCriteria("group");
		Criteria shopCritery = moveCritery.createCriteria("shop");
		shopCritery.addOrder(Order.asc("name"));
		moveCritery.addOrder(Order.asc("ddate"));
		moveCritery.addOrder(Order.asc("numdoc"));
		Criteria operationCritery = moveCritery.createCriteria("operation");
		Criteria fromstockCritery = moveCritery.createCriteria("fromstock");
		Criteria opgroupCritery = operationCritery.createCriteria("opgroup");
		if (critery.groups.size() > 0) {
			Disjunction gc = Restrictions.disjunction();
			for (int i = 0; i < critery.groups.size(); i++) {
				String s = critery.groups.get(i).getCod();
				s = Helper.clear00(s);
				gc.add(Restrictions.like("cod", s, MatchMode.START)
						.ignoreCase());
			}
			groupCritery.add(gc);
		}
		if ((critery.fromDate != null) && (critery.toDate != null))
			moveCritery.add(Restrictions.between("ddate", critery.fromDate,
					critery.toDate));
		if (critery.nnum.size() > 0) {
			moveCritery
					.add(Restrictions
							.sqlRestriction(
									"numdoc in (select numdoc from db.move where ddate>=? and ddate<=? and tovar_nnum = ?)",
									new Object[] { critery.fromDate,
											critery.toDate, critery.nnum.get(0) },
									new Type[] { Hibernate.DATE,
											Hibernate.DATE, Hibernate.INTEGER }));
		}
		if (!critery.abcd.equals(""))
			tovarCritery.add(Restrictions.like("abcd", critery.abcd,
					MatchMode.ANYWHERE));
		if (!critery.name.equals(""))
			tovarCritery.add(Restrictions.like("name", critery.name,
					MatchMode.ANYWHERE));
		if (critery.qty != null)
			moveCritery.add(Restrictions.eq("qty", critery.qty));
		if (critery.fromstock_n != null)
			fromstockCritery.add(Restrictions.eq("n", critery.fromstock_n));
		ArrayList<Move> list = (ArrayList<Move>) moveCritery.list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iprovider.IMoveProvider#getMaster(java.util.Date, java.util.Date)
	 */
	public ArrayList<MasterLevel> getMaster(Date fromdate, Date todate) {
		ArrayList<MasterLevel> ret = new ArrayList<MasterLevel>();
		String sql = "select s.tovar.group,s.shop.name,sum(s.qty),sum(s.summain),sum(s.summaout) "
				+ "from Move s where "
				+ "s.ddate>=:fromdate and s.ddate<=:todate "
				+ " and s.operation.opgroup.name=:opgroup "
				+ "group by s.tovar.group,s.shop.name "
				+ "order by s.tovar.group,s.shop.name";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("fromdate", fromdate);
		q1.setParameter("todate", todate);
		q1.setParameter("opgroup", "Реализация");
		Iterator iter = q1.list().iterator();
		while (iter.hasNext()) {
			Object[] row = (Object[]) iter.next();
			MasterLevel m = new MasterLevel();
			m.level0 = (String) row[0];
			m.level1 = (String) row[1];
			m.sum.add((BigDecimal) row[2]);
			BigDecimal sumin = (BigDecimal) row[3];
			BigDecimal sumout = (BigDecimal) row[4];
			if (sumin.compareTo(BigDecimal.ZERO) == 0
					|| sumout.compareTo(BigDecimal.ZERO) == 0) {

			} else {
				// m.sum.add(100 * (1 - sumin / sumout));
				BigDecimal s = new BigDecimal("1.00").subtract(
						sumin.divide(sumout, RoundingMode.HALF_UP)).multiply(
						new BigDecimal("100.00"));
				m.sum.add(s);
			}
			ret.add(m);
		}
		return ret;
	}

	/*
	 * 
	 * @see iprovider.IMoveProvider#deleteMoveShopInPeriod(java.lang.String,
	 * java.util.Date, java.util.Date)
	 */
	@Override
	public void deleteMoveShopInPeriod(String cod, Date mindate, Date maxdate)
			throws Exception {
		String sql = "delete from db.move m where "
				+ "m.shop_cod=:cod and m.ddate>=:fromdate and m.ddate<=:todate and tovar_nnum not in (:listPDS)";

		// Было Query q1 = getSession().createSQLQuery(sql,"m",Move.class);
		Query q1 = getSession().createSQLQuery(sql);
		q1.setParameter("fromdate", mindate);
		q1.setParameter("todate", maxdate);
		q1.setParameter("cod", cod);
		// Не удадять защиты и СК
		ArrayList<Integer> notDelete = new ArrayList<Integer>();
		notDelete.addAll(getTypePDSProvider().getListNnumPDS());
		notDelete.add(getNnumBonusCard());
		q1.setParameterList("listPDS", notDelete);
		q1.executeUpdate();
	}

	/*
	 * @Override public ArrayList getByExample(Object o) throws Exception { Move
	 * move =(Move) o; Session session = sessionFactory.openSession();
	 * session.beginTransaction(); Criteria moveCritery =
	 * session.createCriteria(Move.class); if(move.getDdate()!=null)
	 * moveCritery.add(Restrictions.eq("ddate", move.getDdate()));
	 * if(move.getTovar()!=null) moveCritery.add(Restrictions.eq("tovar",
	 * move.getTovar())); if (move.getNumdoc()!="")
	 * moveCritery.add(Restrictions.eq("numdoc", move.getNumdoc())); if
	 * (move.getShop()!=null) moveCritery.add(Restrictions.eq("shop",
	 * move.getShop())); if (move.getOperation()!=null)
	 * moveCritery.add(Restrictions.eq("operation", move.getOperation())); if
	 * (move.getQty()!=null) moveCritery.add(Restrictions.eq("qty",
	 * move.getQty())); if(move.getFromstock()!=null)
	 * moveCritery.add(Restrictions.eq("fromstock", move.getFromstock()));
	 * ArrayList<Move> list = (ArrayList<Move>) moveCritery.list();
	 * session.getTransaction().commit(); session.close(); return list; }
	 */

	/*
	 * @Override public SummaInOut getFactByPlan(Plan plan, ArrayList<OpGroup>
	 * arrOpgroup, List<GroupTovar> groups) { MoveCritery critery = new
	 * MoveCritery(); critery.arrOpgroup = arrOpgroup; critery.fromDate =
	 * plan.getFromDate(); critery.toDate = plan.getToDate(); critery.arrshops =
	 * getShopAndParther(plan.getShop()); critery.groups =
	 * (ArrayList<GroupTovar>) groups; return getSumFact(critery); }
	 */
	@Override
	public List<MoveOperationSum> getSumByOperationWithRest(MoveCritery critery) {
		ArrayList<MoveOperationSum> listMove = new ArrayList<MoveOperationSum>();
		RestCritery criteryRest = new RestCritery();
		criteryRest.tovarCritery.arrshops.addAll(critery.arrshops);
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(critery.fromDate);
		cal.roll(Calendar.DAY_OF_YEAR, -1);
		criteryRest.ddate = cal.getTime();
		MoveOperationSum m = new MoveOperationSum();
		Operation op = new Operation();
		op.setName("Остаток на начало");
		m.setOperation(op);
		m.setShop(critery.arrshops.get(0));
		m.setSummaout(new BigDecimal("0.00"));
		ArrayList<Rest> listRest = getRestProvider().getOnDate(criteryRest);
		for (Rest rest : listRest) {
			m.setSummain(m.getSummain().add(rest.getCenain()));
		}
		listMove.add(m);

		List<StringSummaInOut> listStringSummaInOut = getSumByOperation(critery);
		for (StringSummaInOut stringSummaInOut : listStringSummaInOut) {
			m = new MoveOperationSum();
			if (critery.arrshops.size() == 1) {
				m.setShop(critery.arrshops.get(0));
			} else {
				Shop s = new Shop();
				s.setName("");
				m.setShop(s);
			}
			List<Operation> listOperation = getOperationprovider()
					.getByCritery(new CommonCritery(stringSummaInOut.content));
			if (listOperation.size() > 0) {
				m.setOperation(listOperation.get(0));
			} else {
				Operation o = new Operation();
				o.setName("-");
				m.setOperation(o);
			}
			m.setSummain(stringSummaInOut.summaInOut.getSummain());
			m.setSummaout(stringSummaInOut.summaInOut.getSummaout());
			listMove.add(m);
		}

		criteryRest = new RestCritery();
		criteryRest.tovarCritery.arrshops.addAll(critery.arrshops);
		criteryRest.ddate = critery.toDate;
		m = new MoveOperationSum();
		op = new Operation();
		op.setName("Остаток на конец");
		m.setOperation(op);
		m.setSummaout(new BigDecimal("0.00"));
		m.setShop(critery.arrshops.get(0));
		listRest = getRestProvider().getOnDate(criteryRest);
		for (Rest rest : listRest) {
			m.setSummain(m.getSummain().add(rest.getCenain()));
		}
		listMove.add(m);
		return listMove;
	}

	@Override
	public List<Move> getControlPrice(MoveCritery critery, PriceType pricetype) {
		String sql = "from Move m  "
				+ "where m.ddate>=:fromDate and m.ddate<=:toDate "
				+ "and m.shop in (:shops) and m.operation.opgroup.name =:operation";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("fromDate", critery.fromDate);
		q1.setParameter("toDate", critery.toDate);
		q1.setParameter("operation", getNameReal());
		q1.setParameterList("shops", critery.arrshops);
		List list = q1.list();
		List<Move> ret = new ArrayList<Move>();
		for (Object object : list) {
			Object[] o = (Object[]) object;
			Move m = (Move) o[0];
			ret.add(m);
		}
		return ret;
	}

	/**
	 * @see ru.perm.v.el59.office.iproviders.IMoveProvider#getCenaInOnDate
	 */
	@Override
	public BigDecimal getCenaInOnDate(Integer nnum, Date ddate) {
		Criteria moveCriteria = getSession().createCriteria(Move.class);
		Criteria tovarCriteria = moveCriteria.createCriteria("tovar");
		Criteria operationCriteria = moveCriteria.createCriteria("operation");
		Criteria opgroupCriteria = operationCriteria.createCriteria("opgroup");
		moveCriteria.add(Restrictions.le("ddate", ddate));
		tovarCriteria.add(Restrictions.eq("nnum", nnum));
		opgroupCriteria.add(Restrictions.eq("name", getNamePrihod()));
		moveCriteria.setFirstResult(0);
		moveCriteria.setMaxResults(1);
		moveCriteria.addOrder(Order.desc("ddate"));
		List<Move> list = moveCriteria.list();
		if (list.size() == 0) {
			LOGGER.error(String.format(
					"Не найдена цена прихода %d на дату %tF", nnum, ddate));
			return BigDecimal.ZERO;
		}
		Move move = list.get(0);
		return move.getSummain().divide(move.getQty(),RoundingMode.HALF_UP).setScale(2,RoundingMode.HALF_UP);
	}

	public ITovarProvider getTovarprovider() {
		return tovarprovider;
	}

	public void setTovarprovider(ITovarProvider tovarprovider) {
		this.tovarprovider = tovarprovider;
	}

	public ISetGroupTovarProvider getSetgrouptovarprovider() {
		return setgrouptovarprovider;
	}

	public void setSetgrouptovarprovider(
			ISetGroupTovarProvider setgrouptovarprovider) {
		this.setgrouptovarprovider = setgrouptovarprovider;
	}

	public IGenericDao getAgentprovider() {
		return agentprovider;
	}

	public void setAgentprovider(IGenericDao agentprovider) {
		this.agentprovider = agentprovider;
	}

	public IGroupTovarProvider getGroupTovarProvider() {
		return groupTovarProvider;
	}

	public void setGroupTovarProvider(IGroupTovarProvider groupTovarProvider) {
		this.groupTovarProvider = groupTovarProvider;
	}

	public IRestProvider getRestProvider() {
		return restProvider;
	}

	public void setRestProvider(IRestProvider restProvider) {
		this.restProvider = restProvider;
	}

	public ISetTovarProvider getSetTovarProvider() {
		return setTovarProvider;
	}

	public void setSetTovarProvider(ISetTovarProvider setTovarProvider) {
		this.setTovarProvider = setTovarProvider;
	}

	/*
	 * @Override public List<Integer> getExcludeNnumFromZP() { return
	 * excludeNnumFromZP; }
	 * 
	 * public void setExcludeNnumFromZP(List<Integer> excludeNnumFromZP) {
	 * this.excludeNnumFromZP = excludeNnumFromZP; }
	 */
	public IOperationProvider getOperationprovider() {
		return operationprovider;
	}

	public void setOperationprovider(IOperationProvider operationprovider) {
		this.operationprovider = operationprovider;
	}

	public IShopProvider getShopprovider() {
		return shopprovider;
	}

	public void setShopprovider(IShopProvider shopprovider) {
		this.shopprovider = shopprovider;
	}

	public ISetTypeStockProvider getSettypestockprovider() {
		return settypestockprovider;
	}

	public void setSettypestockprovider(
			ISetTypeStockProvider settypestockprovider) {
		this.settypestockprovider = settypestockprovider;
	}

	public String getNameReal() {
		return nameReal;
	}

	public void setNameReal(String nameReal) {
		this.nameReal = nameReal;
	}

	/*
	 * public List<String> getExcludeFromFactMoviesSellerForZP() { return
	 * excludeFromFactMoviesSellerForZP; }
	 * 
	 * public void setExcludeFromFactMoviesSellerForZP( List<String>
	 * excludeFromFactMoviesSellerForZP) { this.excludeFromFactMoviesSellerForZP
	 * = excludeFromFactMoviesSellerForZP; }
	 */
	public ITypePDSProvider getTypePDSProvider() {
		return typePDSProvider;
	}

	public void setTypePDSProvider(ITypePDSProvider typePDSProvider) {
		this.typePDSProvider = typePDSProvider;
	}

	public List<String> getCompetitionNameOpGroup() {
		return competitionNameOpGroup;
	}

	public void setCompetitionNameOpGroup(List<String> competitionNameOpGroup) {
		this.competitionNameOpGroup = competitionNameOpGroup;
	}

	public List<String> getCompetitionCodGroupTovar() {
		return competitionCodGroupTovar;
	}

	public void setCompetitionCodGroupTovar(
			List<String> competitionCodGroupTovar) {
		this.competitionCodGroupTovar = competitionCodGroupTovar;
	}

	public void setCompetitionNameTovar(String competitionNameTovar) {
		this.competitionNameTovar = competitionNameTovar;
	}

	public String getCompetitionNameTovar() {
		return competitionNameTovar;
	}

	public String getNamePrihod() {
		return namePrihod;
	}

	public void setNamePrihod(String namePrihod) {
		this.namePrihod = namePrihod;
	}

	public String getRealOperationName() {
		return realOperationName;
	}

	public void setRealOperationName(String realOperationName) {
		this.realOperationName = realOperationName;
	}

	public Integer getNnumBonusCard() {
		return nnumBonusCard;
	}

	public void setNnumBonusCard(Integer nnumBonusCard) {
		this.nnumBonusCard = nnumBonusCard;
	}
}
