package ru.perm.v.el59.office.dao.impl.shopmodel;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.critery.BonusCardCritery;
import ru.el59.office.db.Shop;
import ru.el59.office.iproviders.shopmodel.IBonusCardProvider;
import ru.el59.office.shopmodel.BonusCard;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

public class BonusCardProvider extends GenericDaoMessageImpl<BonusCard, Long>
		implements IBonusCardProvider {

	public BonusCardProvider(Class<BonusCard> type) {
		super(type);
	}

	@Override
	public List getByCritery(Object critery) {
		BonusCardCritery c = (BonusCardCritery) critery;
		Criteria bonusCardCriteria = getSession().createCriteria(
				BonusCard.class);
		if (c.n != null) {
			bonusCardCriteria.add(Restrictions.eq("n", c.n));
		}
		if (c.fromdate != null && c.todate != null) {
			bonusCardCriteria.add(Restrictions.ge("ddate", c.fromdate));
			bonusCardCriteria.add(Restrictions.le("ddate", c.todate));
		}
		/*
		 * if(c.nameContragent!=null) { Criteria contragentCriteria =
		 * bonusCardCriteria.createCriteria("contragent");
		 * contragentCriteria.add(Restrictions.like("name", c.nameContragent,
		 * MatchMode.ANYWHERE).ignoreCase()); } if(c.docDetail!=null) {
		 * bonusCardCriteria.add(Restrictions.eq("docDetail",c.docDetail)); }
		 */
		if (c.shop != null) {
			bonusCardCriteria.add(Restrictions.eq("shop", c.shop));
		}
		if (c.stroke != null) {
			bonusCardCriteria.add(Restrictions.eq("stroke", c.stroke));
		}
		if (c.name != null) {
			bonusCardCriteria.add(Restrictions.like("name", c.name.trim(),
					MatchMode.ANYWHERE));
		}
		List list = bonusCardCriteria.list();
		return list;
	}

	@Override
	public void create(Shop shop, Date ddate, Integer qty,
					   String prefixBonusCard) throws Exception {
		DecimalFormat df = new DecimalFormat("0000,0000,0000");
		String shortShop = shop.getCod().substring(3);
		Long max = getMaxNumber(shortShop, prefixBonusCard);
		Calendar cal = Calendar.getInstance();
		cal.setTime(ddate);
		String sDate = String.format("%1$ty%1$tm%1$td", ddate);
		String stroke = shop.getCod() + sDate;
		for (Long l = max; l < max + qty; l++) {
			BonusCard b = new BonusCard();
			b.setShop(shop);
			b.setDdate(ddate);
			String ss = stroke + String.format("%07d", l);
			b.setStroke(ss);
			String sss = df.format(l);
			String c = sss.substring(4, 5);
			sss = sss.replace(c, "-");
			b.setName(prefixBonusCard + shortShop + "-" + sss);
			create(b);
		}
	}

	private Long getMaxNumber(String shortShop, String prefixBonusCard) {
		Long ret = 1L;
		String s = (String) getSession().createQuery(
				"select max(name) from BonusCard where name like \'"
						+ prefixBonusCard + shortShop + "%\'").uniqueResult();
		if (s == null)
			return ret;
		s = s.substring(5);
		s = s.replace("-", "");
		ret = Long.getLong(s);
		return ret + 1;
	}

	@Override
	public BonusCard getByStroke(String stroke) {
		Pattern pattern = Pattern.compile(".{1}(\\d+).(\\d+).{1}");
		Matcher matcher = pattern.matcher(stroke);
		if (matcher.matches()) {
			String code = matcher.group(1);
			String s = matcher.group(2);
			BonusCardCritery c = new BonusCardCritery();
			c.stroke = s;
			List list = getByCritery(c);
			if (list.size() == 1)
				return (BonusCard) list.get(0);
		}
		return null;
	}

	@Override
	public void update(BonusCard o) throws Exception {
		if (getCommander() != null) {
			BonusCard bonuscard = (BonusCard) o;
			super.update(o, bonuscard.getShop().getCod());
		}
	}

	@Override
	public Long create(BonusCard o) throws Exception {
		BonusCard bonuscard = (BonusCard) o;
		return super.create(o, bonuscard.getShop().getCod());
	}
}
