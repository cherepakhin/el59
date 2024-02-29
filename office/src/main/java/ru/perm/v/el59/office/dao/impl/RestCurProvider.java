package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.*;
import ru.perm.v.el59.office.iproviders.*;
import ru.perm.v.el59.office.iproviders.critery.PriceCritery;
import ru.perm.v.el59.office.iproviders.critery.RestCritery;
import ru.perm.v.el59.office.iproviders.dao.CommonCritery;
import ru.perm.v.el59.office.util.Helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class RestCurProvider extends GenericDaoHibernateImpl<RestCur, Long>
		implements IRestCurProvider {
	private final static Logger LOG = Logger.getLogger(RestProvider.class.getName());
	private IShopProvider shopProvider;
	private ITypeStockProvider typeStockProvider;
	private ITovarProvider tovarProvider;
	private ISetTypeStockProvider setTypeStockProvider;
	private IPriceProvider priceProvider;

	public RestCurProvider(Class<RestCur> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<RestCur> getByCritery(Object o) {
		RestCritery critery = (RestCritery) o;
		Criteria restCritery = getSession().createCriteria(RestCur.class);
		Criteria tovarCritery = restCritery.createCriteria("tovar");
		if (critery.tovarCritery.groups.size() > 0) {
			Criteria groupCritery = tovarCritery.createCriteria("group");
			Disjunction gc = Restrictions.disjunction();
			for (int i = 0; i < critery.tovarCritery.groups.size(); i++) {
				String s = critery.tovarCritery.groups.get(i).getCod();
				s = Helper.clear00(s);
				gc.add(Restrictions.like("cod", s, MatchMode.START).ignoreCase());
			}
			groupCritery.add(gc);
		}
		if (!critery.tovarCritery.name.isEmpty()) {
			tovarCritery.add(Restrictions.ilike("name",
					critery.tovarCritery.name, MatchMode.ANYWHERE));
		}
		if (critery.tovarCritery.nnum != null
				&& critery.tovarCritery.nnum.size() > 0) {
			tovarCritery.add(Restrictions.in("nnum", critery.tovarCritery.nnum));
		}
		if (critery.tovarCritery.fromDateInsert != null) {
			tovarCritery.add(Restrictions.eq("dateinsert",
					critery.tovarCritery.fromDateInsert));
		}
		if (critery.tovarCritery.arrshops.size() > 0) {
			Criteria shopCritery = restCritery.createCriteria("shop");
			shopCritery.add(Restrictions.eq("worked", true));
			restCritery.add(Restrictions
					.in("shop", critery.tovarCritery.arrshops));
		}
		if (critery.arrtypestock.size() > 0) {
			restCritery.add(Restrictions.in("typeStock", critery.arrtypestock));
		}
		restCritery.addOrder(Order.asc("shop"));
		restCritery.addOrder(Order.asc("tovar"));
		restCritery.addOrder(Order.asc("typeStock"));
		ArrayList<RestCur> list = (ArrayList<RestCur>) restCritery.list();
		if (critery.insertPrice != null && critery.insertPrice) {
			PriceCritery priceCritery = new PriceCritery();
			priceCritery.namePriceType = getPriceProvider()
					.getNameDefaultPrice();
			List<Price> listPrice = getPriceProvider().getByCritery(
					priceCritery);
			HashMap<Tovar, Price> hashPrice = new HashMap<Tovar, Price>();
			for (Price price : listPrice) {
				hashPrice.put(price.getTovar(), price);
			}
			Price price0 = new Price();
			PriceType pt = new PriceType();
			pt.setName(getPriceProvider().getNameDefaultPrice());
			price0.setPriceType(pt);
			for (RestCur r : list) {
				if (hashPrice.containsKey(r.getTovar())) {
					r.getTovar().setPrice(hashPrice.get(r.getTovar()));
				} else {
					r.getTovar().setPrice(price0);
				}
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see iprovider.IRestcurProvider#getMaster()
	 */
	public ArrayList<MasterLevel> getMaster() {
		ArrayList<MasterLevel> ret = new ArrayList<MasterLevel>();
		String sql = "select r.tovar.grup.biggrup.name,r.shop.name,"
				+ "sum(r.qty),count(r.tovar.nnum)  from RestCur r "
				+ "where r.qty>0 "
				+ "group by r.tovar.grup.biggrup.name,r.shop.name";
		Query q1 = getSession().createQuery(sql);
		Iterator iter = q1.list().iterator();
		while (iter.hasNext()) {
			Object[] row = (Object[]) iter.next();
			MasterLevel m = new MasterLevel();
			m.level0 = (String) row[0];
			m.level1 = (String) row[1];
			m.sum.add((BigDecimal) row[2]);
			m.sum.add(new BigDecimal((Long) row[3]));
			ret.add(m);
		}
		return ret;
	}

	public void calcByShop(String shop_cod) throws SQLException {
		String sql = "select * from db.calcrestcurbu(:shop_cod)";
		Query q1 = getSession().createSQLQuery(sql);
		q1.setString("shop_cod", shop_cod);
		q1.list();
	}

	public void deleteByShop(Shop shop) {
		String sql = "delete from RestCur r where r.shop=:shop";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("shop", shop);
		q1.executeUpdate();
	}

	public ArrayList<ShopSqRest> getByShop() {
		ArrayList<ShopSqRest> ret = new ArrayList<ShopSqRest>();
		String sql = "select r.shop.name,sum(r.cenain) from RestCur r where r.shop.worked=:worked and r.typeStock.worked=:main group by r.shop.name";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("worked", true);
		q1.setParameter("main", true);
		Iterator iter = q1.list().iterator();
		while (iter.hasNext()) {
			Object[] row = (Object[]) iter.next();
			ShopSqRest rest = new ShopSqRest();
			Shop shop = (Shop) getShopProvider().getByCritery(
					new CommonCritery((String) row[0])).get(0);
			if (shop != null) {
				rest.setShop(shop);
				rest.setRestfact((BigDecimal) row[1]);
				ret.add(rest);
			}
		}
		return ret;
	}

	public HashMap<Shop, HashMap> getByShopStock() {
		HashMap<Shop, HashMap> hashshop = new HashMap<Shop, HashMap>();

		String sql = "select r.shop.name,r.typeStock.n,sum(r.cenain) from RestCur r where r.shop.worked=:worked and r.typeStock.worked=:main group by r.shop.name,r.typeStock.n";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("worked", true);
		q1.setParameter("main", true);
		Iterator iter = q1.list().iterator();
		while (iter.hasNext()) {
			Object[] row = (Object[]) iter.next();
			Shop shop = (Shop) getShopProvider().getByCritery(
					new CommonCritery((String) row[0])).get(0);
			int h = shop.hashCode();
			if (!hashshop.containsKey(shop)) {
				HashMap<TypeStock, HashMap> hashtypestock = new HashMap<TypeStock, HashMap>();
				hashshop.put(shop, hashtypestock);
			}
			TypeStock typestock = (TypeStock) getTypeStockProvider().read(
					(Long) row[1]);
			HashMap<TypeStock, BigDecimal> hashtypestock = hashshop.get(shop);
			BigDecimal summa = (BigDecimal) row[2];
			hashtypestock.put(typestock, summa);
		}
		return hashshop;
	}

	public SummaInOut getSummaInOut(Shop shop, List<GroupTovar> groups) {
		// sum(m.qty*m.tovar.cenaRegion)
		String sql = "select new ru.perm.v.el59.office.db.SummaInOut(sum(m.cenain),0.00) " + "  from RestCur m where ";
		if (groups != null) {
			if (groups.size() > 0) {
				String setgroupscod = "";
				for (GroupTovar groupTovar : groups) {
					setgroupscod = setgroupscod + "'" + groupTovar.getCod()
							+ "',";
				}
				setgroupscod = setgroupscod + "''";
				sql = sql + " m.tovar.group.cod in (" + setgroupscod + ") and ";
			}
		}
		SetTypeStock settypestock = (SetTypeStock) getSetTypeStockProvider()
				.getByCritery(new CommonCritery("Без клиентской")).get(0);
		sql = sql + " m.typeStock.n in (";
		for (TypeStock ts : settypestock.getTypeStocks())
			sql = sql + ts.getN() + ",";
		sql = sql + "0) and ";

		sql = sql + " m.shop=:shop ";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("shop", shop);
		SummaInOut ret = (SummaInOut) q1.uniqueResult();
		return ret;
	}

	@Override
	public List<Integer> getDistinctListNnum() {
		String sql = "select distinct tovar.nnum from RestCur";
		Query q1 = getSession().createQuery(sql);
		List q1list = q1.list();
		return q1list;
	}

	/*	*//**
	 * Загрузка текущих остатов из списка полученного из xls файла
	 */
	/*
	 * @Override public String loadFromXls(List<RestXls> listRestXls) { // Перед
	 * загрузкой почистить остатки String sql =
	 * "delete from RestCur r where r.shop.cod=:cod"; Query q1 =
	 * getSession().createQuery(sql);
	 * q1.setParameter("cod",getShopForLoadRestXls()); q1.executeUpdate(); //
	 * Загрузка TypeStock typestock = (TypeStock)
	 * getTypeStockprovider().initialize(getTypeStockForLoadRestXls()); Shop
	 * shop=(Shop) getShopprovider().initialize(getShopForLoadRestXls());
	 * if(shop==null) { return
	 * "Магазин с кодом "+getShopForLoadRestXls()+" не найден"; } String ret="";
	 * for(RestXls r :listRestXls) { if(r.getQty()>0) { RestCur rest=new
	 * RestCur(); rest.setQty(r.getQty()); rest.setFreeqty(r.getQty());
	 * rest.setCenain(r.getCenain()); rest.setShop(shop);
	 * rest.setTypeStock(typestock); Integer c = new Integer(0); try { c =
	 * Integer.parseInt(r.getCategory()); } catch (Exception e) { return
	 * "Файл не загружен.Нет категории у товара "+r.getNnum(); } // if(c<13) {
	 * try { Integer nnum=Integer.parseInt(r.getNnum()); Tovar tovar=(Tovar)
	 * getTovarProvider().initialize(nnum); if(tovar!=null) {
	 * rest.setTovar(tovar); create(rest); } else { if(ret.equals("")) {
	 * ret="Не все товары сохранены. В ном. справочнике нет след.товаров:"; }
	 * ret=ret+nnum+";"; } } catch (Exception e) { return
	 * "Ошибка при сохранении товара "+r.getNnum(); } // } } } // TODO
	 * Auto-generated method stub return ret; }
	 */@Override
	public void execproc(String proc) {
		String sql = "select " + proc;
		Query q1 = getSession().createSQLQuery(sql);
		q1.list();
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public ISetTypeStockProvider getSetTypeStockProvider() {
		return setTypeStockProvider;
	}

	public void setSetTypeStockProvider(
			ISetTypeStockProvider setTypeStockProvider) {
		this.setTypeStockProvider = setTypeStockProvider;
	}

	public IPriceProvider getPriceProvider() {
		return priceProvider;
	}

	public void setPriceProvider(IPriceProvider priceProvider) {
		this.priceProvider = priceProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public ITypeStockProvider getTypeStockProvider() {
		return typeStockProvider;
	}

	public void setTypeStockProvider(ITypeStockProvider typeStockProvider) {
		this.typeStockProvider = typeStockProvider;
	}
}
