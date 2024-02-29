package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.*;
import ru.el59.office.db.web.RestWeb;
import ru.el59.office.db.web.SummarySite;
import ru.perm.v.el59.office.iproviders.critery.PriceCritery;
import ru.perm.v.el59.office.iproviders.critery.RestSupplierCritery;
import ru.perm.v.el59.office.iproviders.exception.MessageException;
import ru.perm.v.el59.office.iproviders.web.IRestWebProvider;
import ru.perm.v.el59.office.util.Helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;


public class RestSupplierProvider extends
        GenericDaoHibernateImpl<RestSupplier, Long> implements
        IRestSupplierProvider {
    private ITovarProvider tovarProvider;
    private IContragentProvider contragentProvider;
    private IAnalogProvider analogProvider;
    private IRestWebProvider restWebProvider;
    private IShopProvider shopProvider;
    private HashMap<String, Tovar> hashAnalogTovar;
    private IPriceProvider priceProvider;
    private IRestCurProvider restCurProvider;

    public RestSupplierProvider(Class<RestSupplier> type) {
        super(type);
    }

    public List<RestSupplier> getByCritery(Object o) {
        RestSupplierCritery critery = (RestSupplierCritery) o;
        Criteria restCritery = getSession().createCriteria(RestSupplier.class);
        Criteria tovarCritery = restCritery.createCriteria("tovar");
        if (critery.groups != null && critery.groups.size() > 0) {
            Criteria groupCritery = tovarCritery.createCriteria("group");
            Disjunction gc = Restrictions.disjunction();
            for (int i = 0; i < critery.groups.size(); i++) {
                String s = critery.groups.get(i).getCod();
                s = Helper.clear00(s);
                gc.add(Restrictions.like("cod", s, MatchMode.START)
                        .ignoreCase());
            }
            groupCritery.add(gc);
        }
        if (!critery.name.isEmpty()) {
            tovarCritery.add(Restrictions.ilike("name", critery.name,
                    MatchMode.ANYWHERE));
        }
        if (critery.nnum.size() > 0) {
            tovarCritery.add(Restrictions.in("nnum", critery.nnum));
        }
        if (critery.listContragent.size() > 0) {
            restCritery.add(Restrictions.in("contragent",
                    critery.listContragent));
        }

        restCritery.addOrder(Order.asc("tovar"));
        List<RestSupplier> list = restCritery.list();
        // Добавить остатки Техномира
        /*
         * if (critery.nnum.size() > 0) { List<Contragent> listContragent =
         * getContragentProvider().getByLikeName("техномир");
         * if(listContragent.size()>0) { RestCritery critery2 = new
         * RestCritery(); critery2.tovarCritery.nnum.addAll(critery.nnum); Shop
         * tm = getShopProvider().read("07863");
         * critery2.tovarCritery.arrshops.add(tm); List<RestCur> listTM =
         * getRestCurProvider().getByCritery(critery2); if(listTM.size()>0) {
         * for (RestCur restCur : listTM) { RestSupplier r = new RestSupplier();
         * r
         * .setCena(restCur.getCenain().divide(restCur.getQty(),RoundingMode.HALF_UP
         * ).setScale(2, RoundingMode.HALF_UP));
         * r.setContragent(listContragent.get(0));
         * r.setTovar(restCur.getTovar()); r.setQty(restCur.getQty());
         * r.setDdate(new Date()); list.add(r); } } } }
         */

        /*
         * try { convertToXml(list); } catch (IOException | TemplateException e)
         * { // TODO Auto-generated catch block e.printStackTrace(); }
         */
        return list;
    }

    private void convertToXml(List<RestSupplier> list) throws IOException {
// Закомментил при переносе. Изначально работала конвертация в xml
//		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
//		cfg.setDirectoryForTemplateLoading(new File(this.getClass()
//				.getResource("/restsupplier").getFile()));
//		cfg.setDefaultEncoding("UTF-8");
//		cfg.setLogTemplateExceptions(false);
//		Template template = cfg.getTemplate("restsupplier.ftl");
//		Writer out = new StringWriter();
//		Map<String, Object> beans = new HashMap<>();
//		beans.put("rests", list);
//		template.process(beans, out);
//		out.flush();
//		out.close();
//		File xmlFile = File.createTempFile("out", ".xml");
//		FileUtils.writeStringToFile(xmlFile, out.toString());
    }

    public void deleteBySupplier(Contragent supplier) {
        String sql = "delete from RestSupplier r where r.contragent=:supplier";
        Query q1 = getSession().createQuery(sql);
        q1.setParameter("shop", supplier);
        q1.executeUpdate();
    }

    @Override
    public void clearAllRest() {
        String sql = "delete from RestSupplier r ";
        Query q1 = getSession().createQuery(sql);
        q1.executeUpdate();
        sql = "delete from RestWeb r ";
        q1 = getSession().createQuery(sql);
        q1.executeUpdate();
    }

    /*	*/

    /**
     * Загрузка текущих остатов из списка полученного из xls файла
     */
    /*
     * @Override public String loadFromXls(List<RestXls> listRestXls) { // Перед
     * загрузкой почистить остатки String sql =
     * "delete from RestSupplier r where r.shop.cod=:cod"; Query q1 =
     * getSession().createQuery(sql);
     * q1.setParameter("cod",getShopForLoadRestXls()); q1.executeUpdate(); //
     * Загрузка TypeStock typestock = (TypeStock)
     * getTypeStockprovider().initialize(getTypeStockForLoadRestXls()); Shop
     * shop=(Shop) getShopprovider().initialize(getShopForLoadRestXls());
     * if(shop==null) { return
     * "Магазин с кодом "+getShopForLoadRestXls()+" не найден"; } String ret="";
     * for(RestXls r :listRestXls) { if(r.getQty()>0) { RestSupplier rest=new
     * RestSupplier(); rest.setQty(r.getQty()); rest.setFreeqty(r.getQty());
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
     */
    public ITovarProvider getTovarProvider() {
        return tovarProvider;
    }

    public void setTovarProvider(ITovarProvider tovarProvider) {
        this.tovarProvider = tovarProvider;
    }

    @Override
    public List<Integer> getDistinctListNnum() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Загрузка текущих остатов из списка, полученного из xls файла
     *
     * @throws Exception
     */
    @Override
    public List<RestXls> loadFromXls(List<RestXls> listRestXls,
                                     Contragent supplier) throws Exception {

        Logger.getLogger(this.getClass().getName()).info(
                "RestXls:" + new Date() + " size=" + listRestXls.size());
        // Формирование списка товаров у которых есть прайс w магазина
        PriceCritery priceCritery = new PriceCritery();
        priceCritery.namePriceType = getPriceProvider().getNamePriceW();
        List<Price> listPrice = getPriceProvider().getByCritery(priceCritery);
        ArrayList<Tovar> listTovarPrice = new ArrayList<Tovar>();
        for (Price price : listPrice) {
            listTovarPrice.add(price.getTovar());
        }
        // Перед загрузкой почистить остатки
        // Загрузка
        Contragent supplier1 = getContragentProvider().initialize(
                supplier.getN());
        if (supplier1 == null) {
            throw new MessageException("Поставщик с кодом " + supplier.getN()
                    + " не найден");
        }

        String sql = "delete from RestSupplier r where r.contragent.n=:n";
        Query q1 = getSession().createQuery(sql);
        q1.setParameter("n", supplier1.getN());
        q1.executeUpdate();

        System.out.println("RestXls:" + new Date());
        createHashAnalogTovar();

        ArrayList<RestSupplier> listRestSupplier = new ArrayList<RestSupplier>();
        List<RestXls> ret = new ArrayList<RestXls>();
        // Просто счетчик
        int i = 0;
        int step = listRestXls.size() / 10;
        int cur_percent = 0;
        for (RestXls r : listRestXls) {
            i++;
            if (i == step) {
                i = 0;
                cur_percent++;
                Logger.getLogger(this.getClass().getName()).info(
                        "Прайс для сайта:" + cur_percent * 10 + "%");
            }
            if (r.getQty().compareTo(BigDecimal.ZERO) > 0) {
                RestSupplier rest = new RestSupplier();
                rest.setQty(r.getQty());
                rest.setCena(r.getCena());
                rest.setContragent(supplier1);
                Tovar tovar = getTovar(r);
                if (tovar != null) {
                    // Если товар распознан (озеленен)
                    rest.setTovar(tovar);
                    create(rest);
                    listRestSupplier.add(rest);
                    // Если нет цены в прайсе, то создаем =0
                    if (!listTovarPrice.contains(tovar)) {
                        // Logger.getLogger(this.getClass().getName()).info("Прайс для сайта:"+tovar.getNnum());
                        getPriceProvider().update(
                                getPriceProvider().getNamePriceW(),
                                tovar.getNnum(), new BigDecimal("0.00"));
                    }
                    getTovarProvider().setCenaSupplier(tovar, r.getCena(),
                            new Date());
                } else {
                    ret.add(r);
                }
            }
        }
        Logger.getLogger(this.getClass().getName()).info(
                "Обновление остатков для инетмагазина:" + new Date());
        // Обновление остатков для инетмагазина
        sql = "delete from RestWeb r where r.contragent=:supplier";
        q1 = getSession().createQuery(sql);
        q1.setParameter("supplier", supplier1);
        q1.executeUpdate();
        Shop shop = getShopProvider().getNullShop();
        for (RestSupplier r : listRestSupplier) {
            if (r.getQty().compareTo(BigDecimal.ZERO) > 0) {
                RestWeb rest = new RestWeb();
                rest.setQty(r.getQty());
                rest.setCenaSupplier(r.getCena());
                rest.setContragent(supplier1);
                rest.setShop(shop);
                rest.setTovar(r.getTovar());
                getRestWebProvider().create(rest);
            }
        }
        Logger.getLogger(this.getClass().getName()).info("End:" + new Date());
        // String proc = "select db.insertrestsupplier()";
        // Query qproc = getSession().createSQLQuery(proc);
        // qproc.list();
        hashAnalogTovar.clear();
        return ret;
    }

    /**
     * Распознавание товара
     *
     * @param r
     * @return
     */
    private Tovar getTovar(RestXls r) {
        // System.out.println(r.getName());
        if (r.getNnum() != null) {
            Integer nnum = Integer.parseInt(r.getNnum());
            Tovar tovar = (Tovar) getTovarProvider().initialize(nnum);
            if (tovar != null) {
                // Проверка на двойник
                if (tovar.getParentnnum() != null) {
                    tovar = (Tovar) getTovarProvider().initialize(
                            tovar.getParentnnum());
                }
                return tovar;
            }
        }
        Tovar tovar = getTovarFromAnalogHash(r.getName().trim());
        // Проверка на двойник
        if (tovar != null && tovar.getParentnnum() != null) {
            tovar = (Tovar) getTovarProvider()
                    .initialize(tovar.getParentnnum());
        }
        return tovar;
    }

    private Tovar getTovarFromAnalogHash(String nameAnalog) {
        if (hashAnalogTovar.containsKey(nameAnalog.toLowerCase())) {
            return hashAnalogTovar.get(nameAnalog.toLowerCase());
        }
        return null;
    }

    private HashMap<String, Tovar> createHashAnalogTovar() {
        hashAnalogTovar = new HashMap<String, Tovar>();
        List<Analog> list = getAnalogProvider().getByLikeName("");
        for (Analog analog : list) {
            hashAnalogTovar.put(analog.getName().toLowerCase(),
                    analog.getTovar());
        }
        return hashAnalogTovar;
    }

    public IContragentProvider getContragentProvider() {
        return contragentProvider;
    }

    public void setContragentProvider(IContragentProvider contragentProvider) {
        this.contragentProvider = contragentProvider;
    }

    public IAnalogProvider getAnalogProvider() {
        return analogProvider;
    }

    public void setAnalogProvider(IAnalogProvider analogProvider) {
        this.analogProvider = analogProvider;
    }

    public IRestWebProvider getRestWebProvider() {
        return restWebProvider;
    }

    public void setRestWebProvider(IRestWebProvider restWebProvider) {
        this.restWebProvider = restWebProvider;
    }

    @Override
    public Long create(RestSupplier o) {
        Long n = (Long) getSession().save(o);
        return n;
    }

    @Override
    public List<SummarySite> getSummarySite() {
        String sql = "select r.contragent.name,count(r.tovar.nnum) from RestSupplier r group by r.contragent.name";
        Query q1 = getSession().createQuery(sql);
        List list = q1.list();
        Iterator iter = q1.list().iterator();
        Object[] row;
        ArrayList<SummarySite> ret = new ArrayList<SummarySite>();
        Long sum = 0L;
        while (iter.hasNext()) {
            row = (Object[]) iter.next();
            SummarySite s = new SummarySite();
            s.setName(" " + (String) row[0]);
            s.setQty((Long) row[1]);
            sum = sum + s.getQty();
            ret.add(s);
        }
        SummarySite s = new SummarySite();
        s.setName("Итого:");
        s.setQty(sum);
        ret.add(s);
        return ret;
    }

    @Override
    public List<Contragent> getCurrentSuppliers() {
        String sql = "select distinct contragent from RestSupplier";
        Query q1 = getSession().createQuery(sql);
        List suppliers = q1.list();
        return suppliers;
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

    @Override
    public List<Price> changeCenaIn(List<Price> list) {
        String sql = "select r.tovar.nnum,min(cena) from RestSupplier r group by r.tovar.nnum";
        Query q1 = getSession().createQuery(sql);
        List listMinCena = q1.list();
        Iterator iter = q1.list().iterator();
        Object[] row;
        HashMap<Integer, BigDecimal> hashMinCena = new HashMap<Integer, BigDecimal>();
        while (iter.hasNext()) {
            row = (Object[]) iter.next();
            hashMinCena.put((Integer) row[0], (BigDecimal) row[1]);
        }
        for (Price p : list) {
            if (hashMinCena.containsKey(p.getTovar().getNnum())) {
                /*
                 * p.getTovar().setCenainrub(
                 * hashMinCena.get(p.getTovar().getNnum()));
                 */
                p.getTovar().setCena0(hashMinCena.get(p.getTovar().getNnum()));
            } else {
                p.getTovar().setCena0(BigDecimal.ZERO);
            }

        }
        return list;
    }

    public IRestCurProvider getRestCurProvider() {
        return restCurProvider;
    }

    public void setRestCurProvider(IRestCurProvider restCurProvider) {
        this.restCurProvider = restCurProvider;
    }
}
