package ru.perm.v.el59.office.dao.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import org.jboss.logging.Logger;
import ru.el59.office.critery.PriceCritery;
import ru.el59.office.db.*;
import ru.el59.office.db.dto.PriceDbf;
import ru.el59.office.iproviders.*;
import ru.perm.v.el59.office.util.Helper;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;
import bsh.EvalError;
import bsh.Interpreter;

public class PriceProvider extends GenericDaoMessageImpl<Price, Long> implements
		IPriceProvider {

	private static final long serialVersionUID = 2987793255204684477L;
	private Logger LOGGER = Logger.getLogger(PriceProvider.class);
	private ITovarProvider tovarProvider;
	private IPriceTypeProvider pricetypeProvider;
	private IRestSupplierProvider restSupplierProvider;
	private IFormulaProvider formulaProvider;
	private IShopProvider shopProvider;
	private IManagerProvider managerProvider;
	private String nameDefaultPrice = "";
	private String namePriceW = "";
	private String defaultFormulaName = "";
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyMMdd");

	public PriceProvider(Class<Price> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Price> getByCritery(Object critery) {
		boolean isLocalPrice = false;
		PriceType localPriceType = null;
		PriceCritery c = (PriceCritery) critery;
		if (c.namePriceType != null) {
			localPriceType = getPriceTypeProvider()
					.getByEqName(c.namePriceType);
			if (localPriceType != null) {
				if (!localPriceType.getIsBase()) {
					isLocalPrice = true;
					c.namePriceType = localPriceType.getMainPriceType()
							.getName();
				}
			}
		}
		Criteria priceCritery = getSession().createCriteria(Price.class);
		Criteria tovarCritery = priceCritery.createCriteria("tovar");
		if (c.tovarCritery != null) {
			if (c.tovarCritery.groups.size() > 0) {
				Criteria groupCritery = tovarCritery.createCriteria("group");
				Disjunction gc = Restrictions.disjunction();
				for (int i = 0; i < c.tovarCritery.groups.size(); i++) {
					String s = c.tovarCritery.groups.get(i).getCod();
					s = Helper.clear00(s);
					gc.add(Restrictions.like("cod", s, MatchMode.START)
							.ignoreCase());
				}
				groupCritery.add(gc);
			}
			if (c.tovarCritery.typetovar != null)
				tovarCritery.add(Restrictions.eq("typetovar",
						c.tovarCritery.typetovar));
			if (c.tovarCritery.comment != null)
				tovarCritery.add(Restrictions.eq("comment",
						c.tovarCritery.comment));
			if (!c.tovarCritery.name.equals(""))
				tovarCritery.add(Restrictions.like("name", c.tovarCritery.name,
						MatchMode.ANYWHERE).ignoreCase());
			if (c.tovarCritery.nnum.size() > 0) {
				tovarCritery.add(Restrictions.in("nnum", c.tovarCritery.nnum));
			}
			if (c.tovarCritery.isRest) {
				// Для w-магазина
				if (c.namePriceType.equals(getNamePriceW())) {
					if (!c.supplier.equals("")) {
						tovarCritery
								.add(Restrictions
										.sqlRestriction("nnum in (select distinct tovar_nnum from db.restsupplier r,db.contragent s where r.contragent_n=s.n and lower(s.name) like '%"
												+ c.supplier.toLowerCase()
												+ "%' )"));
					} else {
						tovarCritery
								.add(Restrictions
										.sqlRestriction("nnum in (select distinct tovar_nnum from db.restsupplier)"));
					}
				} else {
					if (c.tovarCritery.arrshops.size() > 0) {
						String shops = "";
						for (Shop shop : c.tovarCritery.arrshops) {
							shops = shops + "'" + shop.getCod() + "',";
						}
						shops = shops + "''";
						tovarCritery
								.add(Restrictions
										.sqlRestriction("nnum in (select distinct tovar_nnum from db.restcur r where r.qty>0.00 and r.shop_cod in ("
												+ shops + "))"));
					} else {
						tovarCritery
								.add(Restrictions
										.sqlRestriction("nnum in (select distinct tovar_nnum from db.restcur r where r.qty>0.00)"));
					}
				}
			}

		}
		if (c.namePriceType != null) {
			Criteria pricetypeCriteria = priceCritery
					.createCriteria("priceType");
			pricetypeCriteria.add(Restrictions.eq("name", c.namePriceType));
		}
		if ((c.fromDdateChange != null) && (c.toDdateChange != null)) {
			priceCritery.add(Restrictions.between("ddate", c.fromDdateChange,
					c.toDdateChange));
		}
		priceCritery.addOrder(Order.asc("tovar.nnum"));
		List<Price> list = priceCritery.list();
		// Для w-прайса нужно подставить вместо сс мин.цену поставщика
		// if (c.namePriceType.equals(getNamePriceW())) {
		list = getRestSupplierProvider().changeCenaIn(list);
		// }
		// Добить товарами которых нет в прайсе
		if (c.tovarCritery != null && c.tovarCritery.nnum.size() > 0
				&& !c.tovarCritery.isRest) {
			if (list.size() < c.tovarCritery.nnum.size()) {
				for (Integer nnum : c.tovarCritery.nnum) {
					boolean found = false;
					for (Price price : list) {
						if (price.getTovar().getNnum().equals(nnum)) {
							found = true;
							break;
						}
					}
					if (!found) {
						try {
							Price p = update(c.namePriceType, nnum,
									new BigDecimal(0));
							list.add(p);
						} catch (Exception e) {
//							LOGGER..info(e.getLocalizedMessage());
							e.printStackTrace();
						}
					}
				}
			}
		}
		// Расчет остатков
		String sql = "select r.tovar.nnum as nnum,sum(r.qty) as qty from RestCur r where r.shop in (:shops) group by r.tovar.nnum";
		Query q1 = getSession().createQuery(sql);
		if (c.tovarCritery != null && c.tovarCritery.arrshops.size() > 0) {
			q1.setParameterList("shops", c.tovarCritery.arrshops);
		} else {
			q1.setParameterList("shops", getShopProvider().getWorkedShop());
		}
		HashMap<Integer, BigDecimal> hash = new HashMap<Integer, BigDecimal>();
		List l = q1.list();
		for (Object r : l) {
			Object[] rr = (Object[]) r;
			hash.put((Integer) rr[0], (BigDecimal) rr[1]);
		}
		for (Price p : list) {
			// Для основных прайсов в поле cenaTemp должна быть Мин.цена
			// поставщика
			p.getTovar().setCena0(p.getTovar().getCena0());
			if (p != null) {
				if (hash.containsKey(p.getTovar().getNnum())) {
					p.getTovar().setQtyRest(hash.get(p.getTovar().getNnum()));
				}
			}
		}
		// Обработка локального прайса
		if (isLocalPrice) {
			HashMap<Tovar, Price> mapTovarPrice = new HashMap<Tovar, Price>();
			for (Price p : list) {
				if (p != null) {
					// Для Локадьных прайсов в поле cenaTemp д.б. цена основного
					// прайса
					p.getTovar().setCena0(p.getCena());
					mapTovarPrice.put(p.getTovar(), p);
				}
			}
			priceCritery = getSession().createCriteria(Price.class);
			priceCritery.add(Restrictions.in("tovar", mapTovarPrice.keySet()));
			priceCritery.add(Restrictions.eq("priceType", localPriceType));
			List<Price> listPriceLocal = priceCritery.list();
			ArrayList<Tovar> listTovarLocalPrice = new ArrayList<Tovar>();
			for (Price priceLocal : listPriceLocal) {
				listTovarLocalPrice.add(priceLocal.getTovar());
				if (mapTovarPrice.keySet().contains(priceLocal.getTovar())) {
					/*
					 * BigDecimal cenaMainPrice = mapTovarPrice
					 * .get(priceLocal.getTovar()).getTovar().getCena0();
					 * priceLocal.getTovar().setCenaSupplier(cenaMainPrice);
					 */
					BigDecimal qtyRest = mapTovarPrice
							.get(priceLocal.getTovar()).getTovar().getQtyRest();
					priceLocal.getTovar().setQtyRest(qtyRest);
					mapTovarPrice.put(priceLocal.getTovar(), priceLocal);
				}
			}
			// Заполнение нулями локальных прайсов для товаров у ктр. лок. прайс
			// еще не заведен
			for (Tovar t : mapTovarPrice.keySet()) {
				if (!listTovarLocalPrice.contains(t)) {
					try {
						Price p = update(localPriceType.getName(), t.getNnum(),
								new BigDecimal("0.00"));
						// BigDecimal cenaMainPrice = t.getCena0();
						mapTovarPrice.put(t, p);

					} catch (Exception e) {
//						LOGGER.info(e.getLocalizedMessage());
						e.printStackTrace();
					}
				}
			}
			ArrayList<Price> retListLocalPrice = new ArrayList<Price>();
			retListLocalPrice.addAll(mapTovarPrice.values());
			// Полем cena в PriceCritery определяю что ныжны сены >0
			if (c.cena != null && c.cena.compareTo(BigDecimal.ZERO) > 0) {
				ArrayList<Price> _ret = new ArrayList<Price>();
				for (Price price : retListLocalPrice) {
					if (price.getCena().compareTo(BigDecimal.ZERO) > 0) {
						_ret.add(price);
					}
				}
				return _ret;
			}
			return retListLocalPrice;
		}
		return list;
	}

	@Override
	public void update(Price p, BigDecimal cenain) throws Exception {
		Tovar tovar = getTovarProvider().read(p.getTovar().getNnum());
		tovar.setCenainrub(cenain);
		tovar.setCena0(cenain);
		getTovarProvider().update(tovar);
		update(p);
	}

	@Override
	public void update(Price p) throws Exception {
		if (p.getLimitDate() == null) {
			p.setLimitDate(getLimitDate(p.getPriceType()));
		}

		if (p.getN() == null || p.getN() <= 0) {
			create(p);
		} else {
/*			Date now = new Date();
			now = Helper.getNullHour(now);
			Date pricedate = Helper.getNullHour(p.getDdate());
			if (pricedate.compareTo(now) == 0) {
				String fullStackTrace = ExceptionUtils
						.getStackTrace(new Exception(
								String.format(
										"Изменяется сегодняшняя дата цены nnum=%d, cena=%.2f ",
										p.getTovar().getNnum(), p.getCena())));
				LOGGER.error(fullStackTrace);
				LOGGER.info(String.format(
						"Изменяется сегодняшняя дата цены nnum=%d, cena=%.2f ",
						p.getTovar().getNnum(), p.getCena()));
			}
			String sql = "select cena from Price where n=:n";
			Query q1 = getSession().createQuery(sql);
			q1.setParameter("n", p.getN());
			BigDecimal cenaDB = (BigDecimal) q1.uniqueResult();
			if (cenaDB.compareTo(p.getCena()) == 0) {
				String fullStackTrace = ExceptionUtils
						.getStackTrace(new Exception(
								String.format(
										"Попытка обновления, но цена не изменилась nnum=%d, cena=%.2f ",
										p.getTovar().getNnum(), p.getCena())));
				LOGGER.error(fullStackTrace);
			}
			LOGGER.info(String.format(
					"Изменяется сегодняшняя дата цены nnum=%d, cena=%.2f, ddate=%s ",
					p.getTovar().getNnum(), p.getCena(),Helper.getDateFornmatter().format(p.getDdate())));
*/			super.update(p);
		}
	}

	@Override
	public Price update(String namePriceType, Integer nnum, BigDecimal cena)
			throws Exception {
		PriceType pricetype = getPriceTypeProvider().getByEqName(namePriceType);
		if (pricetype == null) {
//			LOGGER.error("Не распознан тип прайса " + namePriceType);
			throw new Exception("Не распознан тип прайса " + namePriceType);
		}
		return update(pricetype, nnum, cena);
	}

	@Override
	public Price update(PriceType pricetype, Integer nnum, BigDecimal cena)
			throws Exception {
		Criteria priceCriteria = getSession().createCriteria(Price.class);
		Criteria tovarCriteria = priceCriteria.createCriteria("tovar");
		tovarCriteria.add(Restrictions.eq("nnum", nnum));
		priceCriteria.add(Restrictions.eq("priceType", pricetype));

		/*
		 * PriceCritery priceCritery = new PriceCritery(); TovarCritery
		 * tovarCritery = new TovarCritery(); tovarCritery.nnum.add(nnum);
		 * priceCritery.tovarCritery=tovarCritery;
		 * priceCritery.namePriceType=pricetype.getName();
		 */
		List<Price> listPrice = priceCriteria.list();
		if (listPrice.size() > 0) {
			Price price = listPrice.get(0);
			if (price.getCena().compareTo(cena) != 0) {
				price.setCena(cena);
				price.setDdate(new Date());
				update(price);
			} /*else {
				String fullStackTrace = ExceptionUtils
						.getStackTrace(new Exception(
								String.format(
										"Попытка обновить цену такой же ценой nnum=%d, cena=%.2f ",
										price.getTovar().getNnum(), cena)));
				LOGGER.error(fullStackTrace);
			}*/
			return price;
		} else {
			Tovar t = (Tovar) getTovarProvider().read(nnum);
			if (t != null) {
				Price price = new Price();
				price.setTovar(t);
				price.setPriceType(pricetype);
				price.setCena(cena);
				price.setManager(getManagerProvider().getNullManager());
				price.setDdate(new Date());
				create(price);
				return price;
			}
		}
		return null;
	}

	private Date getLimitDate(PriceType pricetype) {
		if (pricetype != null && pricetype.getQtyDays() > 0) {
			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.add(Calendar.DAY_OF_YEAR, pricetype.getQtyDays());
			return cal.getTime();
		} else {
			return null;
		}
	}

	@Override
	public Long create(Price p) throws Exception {
		p.setDdate(new Date());
		return super.create(p);
	}

	@Override
	public void save(List<Price> listPrice) throws Exception {
		for (Price price : listPrice) {
			if (price.getChanged()) {
				// сохранение комментариев
				// в пизду. Обновление идет по всему списку
				getTovarProvider().update(price.getTovar());
			}
			if (price.getNewcena() != null && price.getChanged()) {
				price.save();
				update(price);
			}
		}
	}

	@Override
	public List<Price> getPrice(PriceType pricetype, Date fromDate, Date toDate) {
		Criteria priceCriteria = getSession().createCriteria(Price.class);
		priceCriteria.add(Restrictions.eq("priceType", pricetype));
		priceCriteria.add(Restrictions.gt("cena", new BigDecimal("0.00")));
		priceCriteria.addOrder((Order.asc("tovar.nnum")));
		if (fromDate != null && toDate != null) {
			priceCriteria.add(Restrictions.between("ddate", fromDate, toDate));
		}
		List<Price> ret = priceCriteria.list();
//		for (Price price : ret) {
//			price.setComment(price.getTovar().getCategory());
//		}
		return ret;
	}

	@Override
	public List<Price> getPrice(Shop shop, Date fromDate, Date toDate) {
		Criteria pricetypeCriteria = getSession().createCriteria(
				PriceType.class);
		pricetypeCriteria.add(Restrictions.eq("shop", shop));
		pricetypeCriteria.addOrder((Order.asc("priority")));
		List<PriceType> listPriceType = pricetypeCriteria.list();
		HashMap<Tovar, Price> mapTovarPrice = new HashMap<Tovar, Price>();
		PriceType mainPriceType = null;
		Integer priority0 = 1;
		for (PriceType pricetype : listPriceType) {
			if (pricetype.getPriority().equals(priority0)) {
				mainPriceType = pricetype.getMainPriceType();
			}
			List<Price> listPrice = getPrice(pricetype, fromDate, toDate);
			for (Price price : listPrice) {
				mapTovarPrice.put(price.getTovar(), price);
			}
		}
		if (mainPriceType != null) {
			List<Price> listPrice = getPrice(mainPriceType, fromDate, toDate);
			for (Price price : listPrice) {
				if (!mapTovarPrice.keySet().contains(price.getTovar())) {
					mapTovarPrice.put(price.getTovar(), price);
				}
			}
		}
		// Расчет остатков
		String sql = "select r.tovar.nnum as nnum,sum(r.qty),min(r.category) as qty from RestCur r where shop=:shop group by r.tovar.nnum";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("shop", shop);
		HashMap<Integer, Integer> hashCategoryRest = new HashMap<Integer, Integer>();
		HashMap<Integer, BigDecimal> hashQtyRest = new HashMap<Integer, BigDecimal>();
		List l = q1.list();
		for (Object r : l) {
			Object[] rr = (Object[]) r;
			hashQtyRest.put((Integer) rr[0], (BigDecimal) rr[1]);
			hashCategoryRest.put((Integer) rr[0], (Integer) rr[2]);
		}
		for (Tovar t : mapTovarPrice.keySet()) {
			if (hashQtyRest.containsKey(t.getNnum())) {
				mapTovarPrice.get(t).getTovar()
						.setQtyRest(hashQtyRest.get(t.getNnum()));
				// Используется для категории товара в конкретном магазине
				mapTovarPrice.get(t).setComment(String.valueOf(hashCategoryRest.get(t.getNnum())));
			}
		}
		ArrayList<Price> ret = new ArrayList<Price>();
		ret.addAll(mapTovarPrice.values());
		Collections.sort(ret, new Comparator<Price>() {

			@Override
			public int compare(Price o1, Price o2) {
				return o1.getTovar().getNnum()
						.compareTo(o2.getTovar().getNnum());
			}

		});
		// Расчет типа ценников
		if (!getDefaultFormulaName().isEmpty()) {
			Formula formula = getFormulaProvider().getByEqName(
					getDefaultFormulaName());
			Map mapNnumCena1 = new HashMap();
			if (formula != null) {
				Interpreter i = new Interpreter();
				try {
					i.set("listPrice", ret);
					mapNnumCena1 = (Map) i.eval(formula.getContent());
					for (Price price : ret) {
						// Метку ценника запихиваю в kop.
						if (mapNnumCena1
								.containsKey(price.getTovar().getNnum())) {
							// В setNewcena устанавливается сегодняшняя дата
							// Дату менять не нужно, поэтому сохраняю ... 
							Date tempDate = price.getDdate();
							price.setNewcena((BigDecimal) mapNnumCena1
									.get(price.getTovar().getNnum()));
							// ... и восстанавливаю 
							price.setDdate(tempDate);
						}
					}
				} catch (EvalError e) {
					e.printStackTrace();
					LOGGER.error(e);
				}
			}

		}
		return ret;
	}

	@Override
	public Integer setToZeroOldLocalPrice() {
		String sql = "update Price set cena=0 where limitdate<=:limitdate and cena>0";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("limitdate", new Date());
		int c = q1.executeUpdate();
		return c;
	}

	@Override
	public List<PriceDbf> getPriceDbf(PriceType priceType, Shop shop,
			Date fromDate, Date toDate, Formula formula) throws EvalError {
		if (formula != null) {
			formula = getFormulaProvider().initialize(formula.getN());
		}
		LOGGER.info("Выгрузка прайса.Начало.");
		ArrayList<PriceDbf> ret = new ArrayList<PriceDbf>();
		DecimalFormat nnumFormat = new DecimalFormat("00000000");
		DecimalFormat grupFormat = new DecimalFormat("00000");

		List<Price> _listPrice = new ArrayList<Price>();
		if (shop == null) {
			_listPrice = getPrice(priceType, fromDate, toDate);
		} else {
			_listPrice = getPrice(shop, fromDate, toDate);
		}
		Map mapNnumCena1 = new HashMap();
		if (formula != null) {
			Interpreter i = new Interpreter();
			i.set("listPrice", _listPrice);
			mapNnumCena1 = (Map) i.eval(formula.getContent());
		}
		LOGGER.info(String.format("Size list price=%d", _listPrice.size()));

		// Вывод прайсов во временный файл
		String nameLogFile = "";
		if (shop != null) {
			nameLogFile = String.format("/home/vasi/temp/dbf/pr-%s-%s.txt",
					shop.getCod(), dateFormatter.format(new Date()));
		} else {
			nameLogFile = "/home/vasi/temp/dbf/pr.txt";
		}
		File txtFile = new File(nameLogFile);
		if (txtFile.exists()) {
			txtFile.delete();
		}
		try {
			txtFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (Price price : _listPrice) {
			PriceDbf p = new PriceDbf();
			p.setGrup(grupFormat.format(price.getTovar().getGroup().getBest()));
			p.setNnum(nnumFormat.format(price.getTovar().getNnum()));
			p.setName(price.getTovar().getName());
			p.setCena(price.getCena());
			BigDecimal cena1 = price.getCena();
			if (mapNnumCena1.containsKey(price.getTovar().getNnum())) {
				cena1 = (BigDecimal) mapNnumCena1.get(price.getTovar()
						.getNnum());
				p.setCena1(cena1);
			}
//			p.se.setPriceName(price.getPriceType().getName());
			ret.add(p);

			try {
				BigDecimal k = new BigDecimal("-1.00");
				if (price.getTovar().getCenainrub().compareTo(BigDecimal.ZERO) != 0) {
					k = price.getCena().divide(price.getTovar().getCenainrub(),
							2, RoundingMode.HALF_DOWN);
				}
				FileUtils
						.writeStringToFile(
								txtFile,
								String.format(
										"%-8s;%-30s;%-30s;%-20s;%-40s;%2d;%1.2f;%1.2f;%1.2f;%1.0f;%s%n",
										nnumFormat.format(price.getTovar()
												.getNnum()), price.getTovar()
												.getGroup().getName(),
										price.getTovar().getGroup().getRoot()
												.getName().replace(";", ""),
										price.getPriceType().getName()
												.toLowerCase(), price
												.getTovar().getName(), price,
										price.getCena(), price.getTovar()
												.getCenainrub(), k, cena1,
										price.getTovar().getGroup().getBonusk()
												.getName()), true);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		LOGGER.info("Выгрузка прайса.Конец.");
		return ret;
	}

	public IManagerProvider getManagerProvider() {
		return managerProvider;
	}

	public void setManagerProvider(IManagerProvider managerProvider) {
		this.managerProvider = managerProvider;
	}

	public IFormulaProvider getFormulaProvider() {
		return formulaProvider;
	}

	public void setFormulaProvider(IFormulaProvider formulaProvider) {
		this.formulaProvider = formulaProvider;
	}

	public String getDefaultFormulaName() {
		return defaultFormulaName;
	}

	public void setDefaultFormulaName(String defaultFormulaName) {
		this.defaultFormulaName = defaultFormulaName;
	}

	@Override
	public String getNameDefaultPrice() {
		return nameDefaultPrice;
	}

	public void setNameDefaultPrice(String nameDefaultPrice) {
		this.nameDefaultPrice = nameDefaultPrice;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public IPriceTypeProvider getPriceTypeProvider() {
		return pricetypeProvider;
	}

	public void setPriceTypeProvider(IPriceTypeProvider pricetypeProvider) {
		this.pricetypeProvider = pricetypeProvider;
	}

	@Override
	public String getNamePriceW() {
		return namePriceW;
	}

	public void setNamePriceW(String namePriceW) {
		this.namePriceW = namePriceW;
	}

	public IRestSupplierProvider getRestSupplierProvider() {
		return restSupplierProvider;
	}

	public void setRestSupplierProvider(
			IRestSupplierProvider restSupplierProvider) {
		this.restSupplierProvider = restSupplierProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

}
