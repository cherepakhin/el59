package ru.perm.v.el59.office.dao.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.critery.RestCritery;
import ru.perm.v.el59.office.critery.TovarCritery;
import ru.perm.v.el59.office.db.DiffRest;
import ru.perm.v.el59.office.db.MasterLevel;
import ru.perm.v.el59.office.db.Rest;
import ru.perm.v.el59.office.db.RestCur;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TypeStock;
import ru.perm.v.el59.office.db.Vars;
import ru.perm.v.el59.office.iproviders.IRestCurProvider;
import ru.perm.v.el59.office.iproviders.IRestProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;

/**
 * 
 * @author vasi Провайдер остатков
 * 
 */
public class RestProvider extends GenericDaoHibernateImpl<Rest, Long> implements
		IRestProvider {
	private final static Logger LOG = Logger.getLogger(RestProvider.class);
	private ITovarProvider tovarProvider;
	private IRestCurProvider restCurProvider;

	public RestProvider(Class<Rest> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Rest> getByCritery(Object o) {
		RestCritery critery = (RestCritery) o;
		String sql = "select r.shop.cod, r.tovar.nnum,r.typeStock.name,"
				+ "max(r.ddate)  from Rest r "
				+ "group by r.shop.cod, r.tovar.nnum,r.typeStock.name";
		Iterator iter = getSession().createQuery(sql).list().iterator();
		while (iter.hasNext()) {
			Object[] row = (Object[]) iter.next();
			// System.out.println(row[0]);
		}
		Criteria restCritery = getSession().createCriteria(Rest.class);
		Criteria shopCritery = restCritery.createCriteria("shop");
		Criteria stockCritery = restCritery.createCriteria("typeStock");
		Criteria tovarCritery = restCritery.createCriteria("tovar");
		if (critery.tovarCritery.name.equals(""))
			tovarCritery.add(Restrictions.like("name",
					critery.tovarCritery.name, MatchMode.ANYWHERE));
		if (critery.tovarCritery.nnum.size() > 0)
			tovarCritery
					.add(Restrictions.in("nnum", critery.tovarCritery.nnum));
		if (critery.tovarCritery.isRest) {
			if (critery.tovarCritery.arrshops.size() > 0)
				restCritery.add(Restrictions.in("shop",
						critery.tovarCritery.arrshops));
			if (critery.arrtypestock.size() > 0)
				restCritery.add(Restrictions.in("typestock",
						critery.arrtypestock));
		}
		// restCritery.add(Restrictions.eq("ddate", critery.ddate));
		restCritery.addOrder(Order.asc("shop"));
		// restCritery.addOrder(Order.asc("shop"));
		ArrayList<Rest> list = (ArrayList<Rest>) restCritery.list();

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iprovider.IRestProvider#oldgetOnDate(db.CriteryRest)
	 */
	public ArrayList<Rest> oldgetOnDate(RestCritery critery) {
		String sql = "select r.shop.cod, r.tovar.nnum,"
				+ "max(r.ddate)  from Rest r where ";
		if (critery.tovarCritery.abcd.equals(""))
			sql = sql + " r.tovar.abcd=:abcd and ";
		if (critery.tovarCritery.name.equals(""))
			sql = sql + " r.tovar.name=:name and ";
		if (critery.tovarCritery.nnum.size() > 0)
			sql = sql + " r.tovar.nnum=:nnum and ";
		sql = sql + " r.ddate<=:ddate " + "group by r.shop.cod, r.tovar.nnum";
		Query q1 = getSession().createQuery(sql);

		if (critery.tovarCritery.abcd.equals(""))
			q1.setParameter("abcd", critery.tovarCritery.abcd);
		if (critery.tovarCritery.name.equals(""))
			q1.setParameter("name", critery.tovarCritery.name);
		if (critery.tovarCritery.nnum.size() > 0)
			q1.setParameter("nnum", critery.tovarCritery.nnum.get(0));
		q1.setParameter("ddate", critery.ddate);
		Iterator iter = q1.list().iterator();
		ArrayList criter = new ArrayList();
		ArrayList<Rest> ret = new ArrayList<Rest>();
		Object[] row;
		while (iter.hasNext()) {
			row = (Object[]) iter.next();
			// System.out.println(row[0]);
			criter.add(row);
		}
		String sqlQuery = "from Rest r where r.shop.cod=:shop and r.tovar.nnum=:nnum "
				+ " and r.ddate=:ddate and r.qty>0";

		Query query = getSession().createQuery(sqlQuery);
		for (int i = 0; i < criter.size(); i++) {
			row = (Object[]) criter.get(i);
			query.setParameter("shop", row[0]);
			query.setParameter("nnum", row[1]);
			query.setParameter("ddate", row[2]);

			ArrayList<Rest> list = (ArrayList<Rest>) query.list();
			ret.addAll(list);
			/*
			 * if (list.size()>0) for(int j=0;j<list.size();j++) {
			 * System.out.println
			 * (list.get(j).getShop().getName()+"|"+list.get(j)
			 * .getTovar().getNnum().toString()); }
			 */}
		return ret;
	}

	@Override
	public ArrayList<Rest> getOnDate(RestCritery critery) {
		HashMap<Integer, Tovar> cache = new HashMap<Integer, Tovar>();
		ArrayList<Rest> list;
		Connection conn = getSession().connection();
		try {
			String sqlQuery = "select "
					+ "0 as n, "
					+ // 1
					"r.cenain as cenain, "
					+ // 2
					"r.ddate as ddate, "
					+ // 3
					"ddate as ddatecena, "
					+ // 4
					"r.qty as qty, "
					+ // 5
					"s.cod , "
					+ // 6
					"s.name,"
					+ // 7
					"r.tovar_nnum as tovar_nnum, "
					+ // 8
					"t.name,"
					+ // 9
					"r.typestock_n as typestock_n ,"
					+ // 10
					"ts.name, "
					+ // 11
					""
					+ // 12
					"t.category "
					+ // 13
					"from db.getrestbu(?,?) r, db.tovar t,"
					+ "db.shop s,db.typestock ts "
					+ "where r.tovar_nnum=t.nnum and "
					+ "r.shop_cod=s.cod and " + "r.typestock_n=ts.n and "
					+ "r.qty!=0 and " + "lower(t.name) like lower(?)";
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			stmt.setDate(1, new java.sql.Date(critery.ddate.getTime()));
			if (critery.tovarCritery.arrshops.size() > 0)
				stmt.setString(2, critery.tovarCritery.arrshops.get(0).getCod());
			else
				stmt.setString(2, "");
			stmt.setString(3, "%" + critery.tovarCritery.name + "%");

			ResultSet rs = stmt.executeQuery();
			list = new ArrayList<Rest>();
			while (rs.next()) {
				Rest r = new Rest();
				r.setN(rs.getLong(1));
				r.setCenain(rs.getBigDecimal(2));
				r.setDdate(rs.getDate(3));
				r.setDdatecena(rs.getDate(4));
				r.setQty(rs.getBigDecimal(5));
				Shop s = new Shop();
				s.setCod(rs.getString(6));
				s.setName(rs.getString(7));
				r.setShop(s);
				Integer nnum = rs.getInt(8);
				Tovar t;
				if (cache.containsKey(nnum))
					t = cache.get(nnum);
				else {
					TovarCritery c = new TovarCritery();
					c.nnum.add(nnum);
					c.withoutDuplicates=false;
					
					ArrayList<Tovar> tt = (ArrayList<Tovar>) getTovarProvider()
							.getByCritery(c);
					if (tt == null || tt.size() == 0) {
						LOG.error("Нет товара с номером" + nnum);
					}
					t = tt.get(0);
					cache.put(nnum, t);
				}
				r.setTovar(t);
				TypeStock ts = new TypeStock();
				ts.setN(rs.getLong(10));
				ts.setName(rs.getString(11));
				r.setTypeStock(ts);
				if (critery.arrtypestock.size() > 0) {
					if (critery.arrtypestock.contains(ts))
						list.add(r);
				} else {
					list.add(r);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		ArrayList<Rest> results = new ArrayList<Rest>();
		if (critery.arrtypestock.size() > 0) {
			for (Rest r : list) {
				if (critery.arrtypestock.contains(r.getTypeStock())) {
					results.add(r);
				}
			}
		} else {
			results = list;
		}
		return results;
	}

	/*
	 * public void recalc() throws Exception { Calendar calendar =
	 * Calendar.getInstance(); calendar.set(2009, 0, 1); for(Shop shop
	 * :ShopProvider.get().getAll()) { // Shop shop =
	 * ShopProvider.get().getByName("Кунгур"); for(TypeStock typestock :
	 * TypeStockProvider.get().getAll()) { CriteryMove critery = new
	 * CriteryMove(); critery.fromDate=calendar.getTime(); critery.toDate=new
	 * Date(); critery.sort.add("shop"); critery.sort.add("fromstock");
	 * critery.sort.add("tovar"); critery.sort.add("ddate");
	 * critery.sort.add("vid"); critery.arrshops.add(shop);
	 * critery.arrtypestock.add(typestock);
	 * System.out.println(shop.getName()+"-"+typestock.getName()); //
	 * critery.nnum = new Integer[] {57099850}; ArrayList<Move> moves =
	 * MoveProvider.get().getByCritery(critery); Rest currentRest = new Rest();
	 * for(Move m : moves) { if ( (!m.getShop().equals(currentRest.getShop()))
	 * || (!m.getFromstock().equals(currentRest.getTypeStock())) ||
	 * (!m.getTovar().equals(currentRest.getTovar())) ) {
	 * currentRest.setShop(m.getShop()); currentRest.setTovar(m.getTovar());
	 * currentRest.setTypeStock(m.getFromstock()); currentRest.setQty(new
	 * BigDecimal("0.00")); currentRest.setCenain(new BigDecimal("0.00")); }
	 * Rest r = new Rest(); r.setShop(m.getShop());
	 * r.setTypeStock(m.getFromstock()); r.setTovar(m.getTovar());
	 * r.setDdate(m.getDdate()); r.setDdatecena(m.getDdate()); if(m.isPrihod())
	 * { r.setQty(currentRest.getQty()+m.getQty());
	 * r.setCenain(currentRest.getCenain()+m.getSummain()); } else {
	 * r.setQty(currentRest.getQty()-m.getQty());
	 * r.setCenain(currentRest.getCenain()-m.getSummain()); }
	 * currentRest.setQty(r.getQty()); currentRest.setCenain(r.getCenain());
	 * save(r); } } } }
	 */public ArrayList<DiffRest> calcDifferent(String mkartFile, Shop shop,
			Date ddate) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		HashMap<String, DiffRest> hash = new HashMap<String, DiffRest>();
		File file = new File(mkartFile);
		Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
		Connection conn = DriverManager.getConnection(
				"jdbc:dbf:/" + file.getParent() + "?charSet=Cp866", "", "");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select sclad,nnum,koltek from \"mkart.dbf\" where koltek>0");
		while (rs.next()) {
			RestCur rest = new RestCur();
			rest.setShop(shop);
			Tovar tovar = new Tovar();
			tovar.setNnum(rs.getInt(2));
			rest.setTovar(tovar);

			rest.setQty(rs.getBigDecimal(3));

			TypeStock typestock = new TypeStock();
			String ts = rs.getString(1);
			Long its = Long.parseLong(ts.substring(3));
			typestock.setN(its);
			rest.setTypeStock(typestock);
			DiffRest diffrest = new DiffRest();
			diffrest.setBest(rest);
			String key = rest.getKey();
			hash.put(key, diffrest);
		}
		conn.commit();
		conn.close();

		RestCritery critery = new RestCritery();
		critery.tovarCritery.arrshops.add(shop);
		critery.ddate = ddate;
		ArrayList<RestCur> arrrestdb = new ArrayList<RestCur>();
		if (ddate != null) {
			ArrayList<Rest> _arrrestdb = getOnDate(critery);
			for (int i = 0; i < _arrrestdb.size(); i++)
				arrrestdb.add(_arrrestdb.get(i).getRestCur());
		} else {
			arrrestdb = (ArrayList<RestCur>) getRestCurProvider().getByCritery(
					critery);
		}

		for (int i = 0; i < arrrestdb.size(); i++) {
			String key = arrrestdb.get(i).getKey();
			if (hash.containsKey(key)) {
				hash.get(key).setDb(arrrestdb.get(i));
			} else {
				DiffRest diffrest = new DiffRest();
				diffrest.setDb(arrrestdb.get(i));
				hash.put(key, diffrest);
			}
		}
		ArrayList<DiffRest> ret = new ArrayList<DiffRest>();
		Iterator iter = hash.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			DiffRest diffrest = hash.get(key);
			if (diffrest.getBest() == null) {
				RestCur rest0 = new RestCur();
				rest0.setShop(shop);
				rest0.setTovar(diffrest.getDb().getTovar());
				rest0.setQty(new BigDecimal("0.00"));
				rest0.setTypeStock(diffrest.getDb().getTypeStock());
				rest0.setCenain(new BigDecimal("0.00"));
				diffrest.setBest(rest0);
			}
			if (diffrest.getDb() == null) {
				RestCur rest0 = new RestCur();
				rest0.setShop(shop);
				rest0.setTovar(diffrest.getBest().getTovar());
				rest0.setQty(new BigDecimal("0.00"));
				rest0.setTypeStock(diffrest.getBest().getTypeStock());
				rest0.setCenain(new BigDecimal("0.00"));
				diffrest.setDb(rest0);
			}

			ret.add(diffrest);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iprovider.IRestProvider#getMaster(java.util.Date)
	 */
	public ArrayList<MasterLevel> getMaster(Date todate) throws SQLException {
		ArrayList<MasterLevel> ret = new ArrayList<MasterLevel>();
		Connection conn = getSession().connection();
		String sqlQuery = "select "
				+ "bg.name,"
				+ // 13
				"s.name,"
				+ // 7
				"sum(r.qty) "
				+ // 5
					// indate text, innum integer, inbg text, gname text, tname
					// text, tsname text, inshop text, intypetovar integer
				"from db.calcrestondate(?,?,?,?,?,?,?,?,?) r, db.tovar t,"
				+ "db.shop s,db.typestock ts,db.grup g, db.biggrup bg "
				+ "where r.tovar_nnum=t.nnum and " + "r.shop_cod=s.cod and "
				+ "r.typestock_n=ts.n and " + "t.group_cod=g.cod and "
				+ "g.biggrup_n=bg.n and r.qty!=0" + "group by bg.name,s.name";
		PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		stmt.setString(1, Vars.getDateFormat().format(todate));
		stmt.setInt(2, -1);
		stmt.setString(3, "");
		stmt.setString(4, "");
		stmt.setString(5, "");
		stmt.setString(6, "");
		stmt.setString(7, "");
		stmt.setInt(8, -1);
		stmt.setString(9, "");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			MasterLevel m = new MasterLevel();
			m.level0 = rs.getString(1);
			m.level1 = rs.getString(2);
			m.sum.add(rs.getBigDecimal(3));
			ret.add(m);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iprovider.IRestProvider#execrest(java.lang.String)
	 */
	public void execrest(String procedure) throws SQLException {
		Connection conn = getSession().connection();
		String sqlQuery = "select " + procedure;
		PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		ResultSet rs = stmt.executeQuery();
	}

	public IRestCurProvider getRestCurProvider() {
		return restCurProvider;
	}

	public void setRestCurProvider(IRestCurProvider restCurProvider) {
		this.restCurProvider = restCurProvider;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

}
