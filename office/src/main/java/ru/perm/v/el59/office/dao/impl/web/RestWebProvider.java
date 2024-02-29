package ru.perm.v.el59.office.dao.impl.web;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.critery.PriceCritery;
import ru.perm.v.el59.office.critery.RestCritery;
import ru.perm.v.el59.office.critery.TovarCritery;
import ru.perm.v.el59.office.dao.impl.GenericDaoHibernateImpl;
import ru.perm.v.el59.office.db.Price;
import ru.perm.v.el59.office.db.SetTovar;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.web.RestWeb;
import ru.perm.v.el59.office.db.web.TypeSite;
import ru.perm.v.el59.office.iproviders.web.IRestWebProvider;
import ru.perm.v.el59.office.util.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RestWebProvider extends GenericDaoHibernateImpl<RestWeb, Long>
        implements IRestWebProvider {
    private ITovarProvider tovarProvider;
    private IShopProvider shopProvider;
    private ISetTovarProvider setTovarProvider;
    private IPriceProvider priceProvider;
    private IPriceTypeProvider pricetypeProvider;
    private List<String> listBlackWord = new ArrayList<String>();
    private String nameSetForDeleteFromSite;
    private Integer maxResultLeaderSale;
    private String codeOperSale;
    private List<String> categoryLeaderSale;

    // private HashMap<Integer, BigDecimal> hashPrice = new HashMap<Integer,
    // BigDecimal>();
    private SetTovar setTovar;
    /**
     * Цена в прайсе
     */
    private HashMap<Tovar, BigDecimal> hashTovarPriceShop;
//	private HashMap<Tovar, BigDecimal> hashTovarPriceWeb;

    public RestWebProvider(Class<RestWeb> type) {
        super(type);
    }

    public ArrayList<RestWeb> getByCritery(Object o) {
        RestCritery critery = (RestCritery) o;
        Criteria restCritery = getSession().createCriteria(RestWeb.class);
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
        if (!critery.tovarCritery.name.isEmpty())
            tovarCritery.add(Restrictions.ilike("name",
                    critery.tovarCritery.name, MatchMode.ANYWHERE));
        if (critery.tovarCritery.nnum.size() > 0)
            tovarCritery.add(Restrictions.in("nnum", critery.tovarCritery.nnum));
        if (critery.tovarCritery.arrshops.size() > 0) {
            restCritery.add(Restrictions
                    .in("shop", critery.tovarCritery.arrshops));
        }
        restCritery.addOrder(Order.asc("shop"));
        ArrayList<RestWeb> list = (ArrayList<RestWeb>) restCritery.list();

        return list;
    }

    private ArrayList<RestWeb> getMinPriceForShop(Shop shop) {
        Criteria restCritery = getSession().createCriteria(RestWeb.class);
        restCritery.add(Restrictions.eq("shop", shop));
        restCritery.addOrder(Order.asc("tovar"));
        ArrayList<RestWeb> list = (ArrayList<RestWeb>) restCritery.list();
        return list;
    }

    @Override
    public List<RestWeb> getListForSite(TypeSite typeSite) {
        /*
		 * String sql = "select *  from db.insertrestsupplier() "; Query
		 * q1=getSession().createQuery(sql); q1.list();
		 */
        // Загрузка текущего розничного прайса hashTovarCena=new HashMap<Tovar,
        // BigDecimal>();
        Logger.getLogger(this.getClass().getName()).info("Старт загрузки прайсов " + typeSite.toString());
        loadPrice();
        Logger.getLogger(this.getClass().getName()).info("Конец загрузки прайсов " + typeSite.toString());

        List<RestWeb> ret = new ArrayList<RestWeb>();

/*
		ShopCritery shopCritery = new ShopCritery();
		shopCritery.setWorked(true);
		List<Shop> listShop = getShopProvider().getByCritery(shopCritery);

		for (Shop shop : listShop) {
*/
        Shop shop = null;
        try {
            shop = getShopProvider().getNullShop();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
        }
        Logger.getLogger(this.getClass().getName()).info(String.format("Старт для сайта %s", typeSite.toString()));
        HashMap<Integer, RestWeb> nnums = new HashMap<Integer, RestWeb>();
        ArrayList<RestWeb> listRestShop = getMinPriceForShop(shop);
        for (RestWeb r : listRestShop) {
            if (nnums.containsKey(r.getTovar().getNnum())) {
                if (nnums.get(r.getTovar().getNnum()).getCenaSupplier().compareTo(r.getCenaSupplier()) > 0) {
                    nnums.remove(r.getTovar().getNnum());
                    nnums.put(r.getTovar().getNnum(), r);
                }
            } else {
                nnums.put(r.getTovar().getNnum(), r);
            }
        }
        Logger.getLogger(this.getClass().getName()).info(String.format("Расценка %s для сайта %s", shop.getName(), typeSite.toString()));
        // Расценка
        for (RestWeb restWeb : nnums.values()) {
            RestWeb r = new RestWeb();
            r.setShop(restWeb.getShop());
            r.setContragent(restWeb.getContragent());
            r.setCenaSupplier(restWeb.getCenaSupplier());
            r.setCenaOut(getCena(restWeb, typeSite));
            r.setTovar(restWeb.getTovar());
            if (restWeb.getQtyDayDelivery() > 0) {
                r.setQty(new BigDecimal("0.00"));
            } else {
                r.setQty(restWeb.getQty());
            }
            ret.add(r);
        }

        // Дополнение сопутствующими товарами
/*				String sql = "select distinct relationTovar from GroupT ";
				Query q1 = getSession().createQuery(sql);
				List<Tovar> listRelationTovar = q1.list();
				for (Tovar tovar : listRelationTovar) {
					if (!nnums.containsKey(tovar.getNnum())) {
						RestWeb radd = new RestWeb();
						radd.setShop(shop);
						radd.setTovar(tovar);
						radd.setQty(new BigDecimal(1));
						PriceCritery p = new PriceCritery();
						p.namePriceType = getPriceProvider()
								.getNameDefaultPrice();
						p.tovarCritery = new TovarCritery();
						p.tovarCritery.nnum.add(tovar.getNnum());
						List<Price> list = getPriceProvider().getByCritery(p);
						if (list.size() > 0) {
							radd.setCenaSupplier(list.get(0).getCena());
							ret.add(radd);
						}
					}
				}
*/
//			}
//		}

        List<RestWeb> retWithPrice = new ArrayList<RestWeb>();

        Logger.getLogger(this.getClass().getName()).info("Отбор " + typeSite.toString());
        boolean checkOk = true;
        for (RestWeb r : ret) {
            checkOk = true;
            // Товары с меткой "НЕ ИСПОЛЬЗОВАТЬ" не грузить на сайт
            for (String word : getListBlackWord()) {
                if (r.getTovar().getName().toLowerCase()
                        .startsWith(word.toLowerCase())) {
                    checkOk = false;
                    Logger.getLogger(this.getClass().getName()).info(
                            "Товар " + r.getTovar().getNnum()
                                    + ". не будет загружен. Метка НЕ ИСПОЛЬЗОВАТЬ.");
                }
            }
            if (r.getTovar().getGroup().getGroupT() == null || r.getTovar().getGroup().getGroupT().getCod().equals("0000000000")) {
                checkOk = false;
                Logger.getLogger(this.getClass().getName()).info(
                        "Товар " + r.getTovar().getNnum()
                                + " не предназначен для сайта. Не назначена группа для сайта.");
            }
            // Эти товары не грузить на сайт
            if (checkOk) {
                if (getSetTovar().getTovars().contains(r.getTovar())) {
                    checkOk = false;
                }
            }
            if (r.getTovar().getParentnnum() != null) {
                checkOk = false;
            }
            if (r.getCenaOut() != null && checkOk) {
                if (r.getCenaOut().compareTo(BigDecimal.ZERO) > 0) {
                    retWithPrice.add(r);
                } else {
                    Logger.getLogger(this.getClass().getName()).info(
                            "Цена товара " + r.getTovar().getNnum() + " "
                                    + r.getTovar().getName() + " =0");
                }
            } else {
                Logger.getLogger(this.getClass().getName()).info(
                        "Цена товара " + r.getTovar().getNnum()
                                + " null или не грузить на сайт");
            }

        }
        Logger.getLogger(this.getClass().getName()).info("Конец выгрузки остатков " + typeSite.toString());
        return retWithPrice;
    }

    private BigDecimal getCena(RestWeb r, TypeSite typeSite) {

        BigDecimal cena = new BigDecimal("0.00");
        if (r.getCenaSupplier().compareTo(BigDecimal.ZERO) > 0) {
            // System.out.println("Cena:"+r.getCena());
            // System.out.println("Nnum:"+r.getTovar().getNnum());
            // System.out.println("Group:"+r.getTovar().getGroup().getName());

            // для внутреннего сайта расчетная цена не должна быть равна
            // прайсовой (прайс Розничный)
            // коэф-ты для расчета новой цены для внутреннего и внешнего сайта
            // разные
            if (typeSite == TypeSite.INNER) {
                cena = r.getCenaSupplier().multiply(r.getTovar().getGroup().getFactor());
                cena = checkMaxFromDefaultPrice(r, cena);
                // Приведение к девяткам
                cena = round(cena);
            }
            if (typeSite == TypeSite.EL59) {
                if (r == null) {
                    Logger.getLogger(this.getClass().getName()).error("r = null");
                    return BigDecimal.ZERO;
                }
                if (r.getTovar() == null) {
                    Logger.getLogger(this.getClass().getName()).error("r.getTovar() = null");
                    return BigDecimal.ZERO;
                }
				/*	УБРАЛ. Никто не следит за W-прайсом
				 				if(hashTovarPriceWeb.get(r.getTovar())==null) {
					Logger.getLogger(this.getClass().getName()).error("hashTovarPriceWeb.get(r.getTovar()) = null");
					return BigDecimal.ZERO;
				}
			if (hashTovarPriceWeb.containsKey(r.getTovar())
						&& hashTovarPriceWeb.get(r.getTovar()).compareTo(BigDecimal.ZERO) > 0) {
					cena = hashTovarPriceWeb.get(r.getTovar());
				} else {
*/
                cena = r.getCenaSupplier().multiply(r.getTovar().getGroup().getNacenkaForSite());
                // Приведение к девяткам
                cena = round(cena);
            }
        }
        return cena;
    }

    private void loadPrice() {
        // Загрузка Розничного прайса
        PriceCritery priceCritery = new PriceCritery();
        priceCritery.namePriceType = getPriceProvider().getNameDefaultPrice();
        TovarCritery tovarCritery = new TovarCritery();
        tovarCritery.isRest = true;
        priceCritery.tovarCritery = tovarCritery;
        List<Price> list = getPriceProvider().getByCritery(priceCritery);
        hashTovarPriceShop = new HashMap<Tovar, BigDecimal>();
        for (Price price : list) {
            hashTovarPriceShop.put(price.getTovar(), price.getCena());
        }
        // Загрузка w-прайса
/*		УБРАЛ. Никто не следит за W-прайсом
		priceCritery = new PriceCritery();
		priceCritery.namePriceType = getPriceProvider().getNamePriceW();
		list = getPriceProvider().getByCritery(priceCritery);
		hashTovarPriceWeb = new HashMap<Tovar, BigDecimal>();
		for (Price price : list) {
			hashTovarPriceWeb.put(price.getTovar(), price.getCena());
		}
*/
    }

    /**
     * Сверка с основным прайсом. Цена на сайте не должна быть выше розничного
     * прайса
     *
     * @param r
     * @param cena - расчетная цена
     * @return
     */
    private BigDecimal checkMaxFromDefaultPrice(RestWeb r, BigDecimal cena) {
        if (hashTovarPriceShop.containsKey(r.getTovar())
                && (hashTovarPriceShop.get(r.getTovar()).compareTo(cena) > 0)) {
            return hashTovarPriceShop.get(r.getTovar());
        }
        return cena;
    }

    // Приведение к девяткам
    private BigDecimal round(BigDecimal cena) {
        if (cena.compareTo(new BigDecimal("10.00")) <= 0) {
            return new BigDecimal(cena.intValue());
        }
        if (cena.compareTo(new BigDecimal("1000.00")) < 0) {
            BigDecimal c = cena.divide(new BigDecimal(10)).setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(10)).subtract(new BigDecimal(1));
            return c;

        } else {
            BigDecimal c = cena.divide(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).subtract(new BigDecimal(10));
            return c;
        }
    }

    @Override
    public List<Tovar> getListTovarForDelete() {
        ArrayList<Tovar> ret = new ArrayList<Tovar>();
        ret.addAll(getSetTovar().getTovars());
        return ret;
    }

    private SetTovar getSetTovar() {
        if (setTovar == null) {
            setTovar = getSetTovarProvider().getByEqName(
                    getNameSetForDeleteFromSite());
            setTovar = getSetTovarProvider().initialize(setTovar.getN());
        }
        if (setTovar == null) {
            Logger.getLogger(this.getClass().getName()).error(
                    "Не назначена группа товаров. Имя группы "
                            + getNameSetForDeleteFromSite());
        }
        return setTovar;
    }

    @Override
    public void addTovarForDelete(Tovar tovar) throws Exception {
        getSetTovarProvider().addTovar(getSetTovar(), tovar);
        setTovar = getSetTovarProvider().initialize(setTovar.getN());
    }

    @Override
    public void removeTovarForDelete(Tovar tovar) throws Exception {
        getSetTovarProvider().removeTovar(getSetTovar(), tovar);
        setTovar = getSetTovarProvider().initialize(setTovar.getN());
    }

    @Override
    public void addTovarForDelete(List<Tovar> listTovar) throws Exception {
        for (Tovar tovar : listTovar) {
            addTovarForDelete(tovar);
        }

    }

    @Override
    public void removeTovarForDelete(List<Tovar> listTovar) throws Exception {
        for (Tovar tovar : listTovar) {
            removeTovarForDelete(tovar);
        }
    }

    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }

    public IShopProvider getShopProvider() {
        return shopProvider;
    }

    public void setShopProvider(IShopProvider shopProvider) {
        this.shopProvider = shopProvider;
    }

    public IPriceProvider getPriceProvider() {
        return priceProvider;
    }

    public void setPriceProvider(IPriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    public IPriceTypeProvider getPriceTypeProvider() {
        return pricetypeProvider;
    }

    public void setPriceTypeProvider(IPriceTypeProvider pricetypeProvider) {
        this.pricetypeProvider = pricetypeProvider;
    }

    public List<String> getListBlackWord() {
        return listBlackWord;
    }

    public void setListBlackWord(List<String> listBlackWord) {
        this.listBlackWord = listBlackWord;
    }

    public String getNameSetForDeleteFromSite() {
        return nameSetForDeleteFromSite;
    }

    public void setNameSetForDeleteFromSite(String nameSetForDeleteFromSite) {
        this.nameSetForDeleteFromSite = nameSetForDeleteFromSite;
    }

    public ISetTovarProvider getSetTovarProvider() {
        return setTovarProvider;
    }

    public void setSetTovarProvider(ISetTovarProvider setTovarProvider) {
        this.setTovarProvider = setTovarProvider;
    }

    @Override
    public Long create(RestWeb o) {
        Long n = (Long) getSession().save(o);
        return n;
    }

    @Override
    public List<Integer> getListLiderSaleForSite() {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2012);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        String sql = "select m.tovar.nnum,sum(m.qty) " + "from Move m "
                + "where m.tovar in (select tovar from RestWeb) and "
                + "m.tovar.name like :name and  "
                + "m.ddate>:ddate and m.codeoper=:codeoper "
                + "group by m.tovar.nnum order by 2 desc";
        Query q1 = getSession().createQuery(sql);
        for (String nameTovar : getCategoryLeaderSale()) {
            q1.setParameter("name", nameTovar);
            q1.setParameter("ddate", cal.getTime());
            q1.setParameter("codeoper", getCodeOperSale());
            q1.setMaxResults(getMaxResultLeaderSale());
            List list = q1.list();
            for (Object r : list) {
                Object[] rr = (Object[]) r;
                ret.add((Integer) rr[0]);
            }
        }
        return ret;
    }

    public Integer getMaxResultLeaderSale() {
        return maxResultLeaderSale;
    }

    public void setMaxResultLeaderSale(Integer maxResultLeaderSale) {
        this.maxResultLeaderSale = maxResultLeaderSale;
    }

    public String getCodeOperSale() {
        return codeOperSale;
    }

    public void setCodeOperSale(String codeOperSale) {
        this.codeOperSale = codeOperSale;
    }

    public List<String> getCategoryLeaderSale() {
        return categoryLeaderSale;
    }

    public void setCategoryLeaderSale(List<String> categoryLeaderSale) {
        this.categoryLeaderSale = categoryLeaderSale;
    }

}
