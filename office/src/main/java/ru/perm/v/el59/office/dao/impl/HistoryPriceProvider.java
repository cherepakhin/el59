package ru.perm.v.el59.office.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ru.perm.v.el59.office.critery.TovarCritery;
import ru.perm.v.el59.office.db.HistoryPrice;
import ru.perm.v.el59.office.iproviders.IHistoryPriceProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.util.Helper;

/**
 * История изменения цен. Само отслеживание сделано через триггер
 * 
 * <pre>
 * CREATE TRIGGER onchange_price
 * AFTER INSERT OR UPDATE ON db.price FOR EACH ROW EXECUTE PROCEDURE db.price_change();
 * 
 * CREATE OR REPLACE FUNCTION db.price_change() RETURNS TRIGGER AS $$
 * BEGIN
 * 	if(TG_OP !='INSERT' and  OLD.cena!=NEW.CENA) then
 * 		insert into db.historyprice(n,tovar_nnum,ddate,ddatechange,cena,pricetype_n,manager_n) 
 * 		values(nextval('db.historyprice_n_seq'),OLD.tovar_nnum,OLD.ddate,CURRENT_DATE,OLD.cena,OLD.pricetype_n,OLD.manager_n);
 * 	end if;
 *   RETURN NEW;
 *   
 * END;
 * $$ LANGUAGE plpgsql;
 * </pre>
 */
public class HistoryPriceProvider extends
		GenericDaoHibernateImpl<HistoryPrice, Long> implements
		IHistoryPriceProvider {

	private IShopProvider shopProvider;
	private SimpleDateFormat sdf;

	public HistoryPriceProvider(Class<HistoryPrice> type) {
		super(type);
	}

	@Override
	public List<HistoryPrice> getByCritery(Object critery) {
		TovarCritery tovarCritery = (TovarCritery) critery;
		Criteria historyPriceQuery = getSession().createCriteria(
				HistoryPrice.class);
		Criteria tovarQuery = historyPriceQuery.createCriteria("tovar");
		if (tovarCritery.groups.size() > 0) {
			Criteria groupCritery = tovarQuery.createCriteria("group");
			Disjunction gc = Restrictions.disjunction();
			for (int i = 0; i < tovarCritery.groups.size(); i++) {
				String s = tovarCritery.groups.get(i).getCod();
				s = Helper.clear00(s);
				gc.add(Restrictions.like("cod", s, MatchMode.START)
						.ignoreCase());
			}
			groupCritery.add(gc);
		}
		if (!tovarCritery.name.equals("")) {
			tovarQuery.add(Restrictions.like("name", tovarCritery.name,
					MatchMode.ANYWHERE).ignoreCase());
		}
		if (tovarCritery.nnum.size() > 0) {
			tovarQuery.add(Restrictions.in("nnum", tovarCritery.nnum));
		}
		if(tovarCritery.fromDateInsert!=null && tovarCritery.toDateInsert!=null) {
			historyPriceQuery.add(Restrictions.ge("ddatechange", tovarCritery.fromDateInsert));
			historyPriceQuery.add(Restrictions.le("ddatechange", tovarCritery.toDateInsert));
		}

		tovarQuery.addOrder(Order.asc("nnum"));
		historyPriceQuery.addOrder(Order.asc("ddatechange"));
		ArrayList<HistoryPrice> list = (ArrayList<HistoryPrice>) historyPriceQuery
				.list();
		return list;
	}

	@Override
	public HistoryPrice getByTovarShopDate(Integer nnum, String shopCod,
			Date ddate) throws Exception {
		if(nnum.equals(97699971)) {
			System.out.println("stop");
		}
		String sql = "from HistoryPrice " +
				"where tovar.nnum=:nnum and " +
				"priceType.shop.cod in (:shopCods) and " +
				"ddate <= :fromDate " +
				"order by ddate desc";
		Query q1 = getSession().createQuery(sql );
		q1.setInteger("nnum", nnum);
		q1.setDate("fromDate", ddate);
		q1.setParameterList("shopCods", new String[] {shopCod,getShopProvider().getNullShop().getCod()});
		q1.setFirstResult(0);
		q1.setMaxResults(1);		
		List<HistoryPrice> list = q1.list();
		
/*		Criteria historyPriceQuery = getSession().createCriteria(
				HistoryPrice.class);
		Criteria tovarQuery = historyPriceQuery.createCriteria("tovar");
		Criteria priceTypeQuery = historyPriceQuery.createCriteria("priceType");
		Criteria shopQuery = priceTypeQuery.createCriteria("shop");

		historyPriceQuery.add(Restrictions.le("ddate", ddate));
		tovarQuery.add(Restrictions.eq("nnum", nnum));
		Disjunction gc = Restrictions.disjunction();
		gc.add(Restrictions.eq("cod", getShopProvider().getNullShop().getCod()));
		gc.add(Restrictions.eq("cod", shopCod));
		shopQuery.add(gc);
		historyPriceQuery.setFirstResult(1);
		historyPriceQuery.addOrder(Order.asc("n"));
		List<HistoryPrice> list = historyPriceQuery.list();
*/		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}
}
