package ru.perm.v.el59.office.dao.impl.shopmodel;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import ru.el59.office.db.Shop;
import ru.el59.office.shopmodel.PodCard;
import ru.perm.v.el59.dto.office.critery.PodCardCritery;
import ru.perm.v.el59.office.iproviders.IPodCardProvider;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PodCardProvider extends GenericDaoMessageImpl<PodCard, Long>
		implements IPodCardProvider {

	public PodCardProvider(Class<PodCard> type) {
		super(type);
	}

	@Override
	public List<PodCard> getByCritery(Object critery) {
		PodCardCritery c = (PodCardCritery) critery;
		Criteria podCardCriteria = getSession().createCriteria(PodCard.class);
		if (c.n != null) {
			podCardCriteria.add(Restrictions.eq("n", c.n));
		}
		if (c.fromdate != null && c.todate != null) {
			podCardCriteria.add(Restrictions.ge("ddate", c.fromdate));
			podCardCriteria.add(Restrictions.le("ddate", c.todate));
		}
		/*
		 * if(c.nameContragent!=null) { Criteria contragentCriteria =
		 * podCardCriteria.createCriteria("contragent");
		 * contragentCriteria.add(Restrictions.like("name", c.nameContragent,
		 * MatchMode.ANYWHERE).ignoreCase()); } if(c.docDetail!=null) {
		 * podCardCriteria.add(Restrictions.eq("docDetail",c.docDetail)); }
		 */if (c.stroke != null) {
			podCardCriteria.add(Restrictions.eq("stroke", c.stroke));
		}
		if (c.name != null) {
			podCardCriteria.add(Restrictions.like("name", c.name.trim(),
					MatchMode.ANYWHERE));
		}
		List<PodCard> list = podCardCriteria.list();
		return list;
	}

	@Override
	public void create(Shop shop, Date ddate, Integer qty, String prefixPodCard) throws Exception {
		DecimalFormat df = new DecimalFormat("0000,0000,0000");
		String shortShop = shop.getCod().substring(3);
		Long max = getMaxNumber(shortShop, prefixPodCard);
		Calendar cal = Calendar.getInstance();
		cal.setTime(ddate);
		String sDate = String.format("%1$ty%1$tm%1$td", ddate);
		String stroke = shop.getCod() + sDate;
		for (Long l = max; l < max + qty; l++) {
			PodCard b = new PodCard();
			b.setShop(shop);
			b.setDdate(ddate);
			String ss = stroke + String.format("%07d", l);
			b.setStroke(ss);
			String sss = df.format(l);
			String c = sss.substring(4, 5);
			sss = sss.replace(c, "-");
			b.setName(prefixPodCard + shortShop + "-" + sss);
			create(b);
		}
	}

	@Override
	public PodCard getByStroke(String stroke) {
		Pattern pattern = Pattern.compile(".{1}(\\d+).(\\d+).{1}");
		Matcher matcher = pattern.matcher(stroke);
		if (matcher.matches()) {
			String s = matcher.group(2);
			PodCardCritery c = new PodCardCritery();
			c.stroke = s;
			List<PodCard> list = getByCritery(c);
			if (list.size() == 1)
				return (PodCard) list.get(0);
		}
		return null;
	}

	private Long getMaxNumber(String shortShop, String prefixPodCard) {
		Long ret = 1L;
		String s = (String) getSession().createQuery(
				"select max(name) from PodCard where name like \'"
						+ prefixPodCard + shortShop + "%\'").uniqueResult();
		if (s == null)
			return ret;
		s = s.substring(5);
		s = s.replace("-", "");
		ret = Long.decode(s);
		return ret + 1;
	}
}
