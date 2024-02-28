package ru.perm.v.el59.office.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.critery.HistoryTagCritery;
import ru.perm.v.el59.office.db.HistoryTag;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.iproviders.IHistoryTagProvider;
import ru.perm.v.el59.office.util.Helper;

/**
 * @see iprovider.IHistoryTagProvider
 * @author vasi
 * 
 */
public class HistoryTagProvider extends
		GenericDaoHibernateImpl<HistoryTag, Long> implements
		IHistoryTagProvider {

	private static Logger LOGGER = Logger.getLogger(HistoryTagProvider.class);
	// Дата внедрения истории ценников
	private static Date startDate;
	
	public HistoryTagProvider(Class<HistoryTag> type) {
		super(type);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		startDate=cal.getTime();
	}

	@Override
	public List<HistoryTag> getByCritery(Object critery) {
		HistoryTagCritery c = (HistoryTagCritery) critery;
		Criteria historyTagQuery = getSession().createCriteria(
				HistoryTag.class);
		Criteria tovarQuery = historyTagQuery.createCriteria("tovar");
		if (c.tovarCritery != null) {
			if (c.tovarCritery.groups.size() > 0) {
				Criteria groupCritery = tovarQuery.createCriteria("group");
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
				tovarQuery.add(Restrictions.eq("typetovar",
						c.tovarCritery.typetovar));
			if (c.tovarCritery.comment != null)
				tovarQuery.add(Restrictions.eq("comment",
						c.tovarCritery.comment));
			if (!c.tovarCritery.name.equals(""))
				tovarQuery.add(Restrictions.like("name", c.tovarCritery.name,
						MatchMode.ANYWHERE).ignoreCase());
			if (c.tovarCritery.nnum.size() > 0) {
				tovarQuery.add(Restrictions.in("nnum", c.tovarCritery.nnum));
			}
			if(c.tovarCritery.fromDateInsert!=null && c.tovarCritery.toDateInsert!=null) {
				historyTagQuery.add(Restrictions.ge("ddate", c.tovarCritery.fromDateInsert));
				historyTagQuery.add(Restrictions.le("ddate", c.tovarCritery.toDateInsert));
			}
		}
		if(c.shops.size()>0) {
			historyTagQuery.add(Restrictions.in("shop", c.shops));
		}
		if(c.fromDate!=null && c.toDate!=null) {
			historyTagQuery.add(Restrictions.ge("ddate", c.fromDate));
			historyTagQuery.add(Restrictions.le("ddate", c.toDate));
		} else {
			historyTagQuery.add(Restrictions.ge("ddate", startDate));
		}
		historyTagQuery.addOrder(Order.asc("n"));
		List<HistoryTag> list= historyTagQuery.list();
		return list;
	}

	@Override
	public HistoryTag getByTovarShopDate(Tovar tovar, Shop shop, Date ddate) {
		return getByNnumShopCodDate(tovar.getNnum(), shop.getCod(), ddate);
	}

	@Override
	public HistoryTag getByNnumShopCodDate(Integer nnum, String shopCod,
			Date ddate) {
		Criteria historyTagQuery = getSession().createCriteria(
				HistoryTag.class);
		Criteria tovarQuery = historyTagQuery.createCriteria("tovar");
		tovarQuery.add(Restrictions.eq("nnum", nnum));
		Criteria shopQuery = historyTagQuery.createCriteria("shop");
		shopQuery.add(Restrictions.eq("cod", shopCod));
		historyTagQuery.add(Restrictions.le("ddate", ddate));
		
		historyTagQuery.addOrder(Order.desc("ddate"));
		historyTagQuery.addOrder(Order.asc("n"));
		historyTagQuery.setFirstResult(0);
		historyTagQuery.setMaxResults(1);
		List<HistoryTag> list = historyTagQuery.list();
		if(list.size()>0) {
			return list.get(0);
		}
		LOGGER.error(String.format("Not found history for nnum=%d,shopCod=%s,ddate=%tF",nnum,shopCod,ddate));
		return null;
	}

}
