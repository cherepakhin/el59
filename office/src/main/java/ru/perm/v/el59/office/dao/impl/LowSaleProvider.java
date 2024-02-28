package ru.perm.v.el59.office.dao.impl;

import ru.perm.v.el59.dao.CommonCritery;
import ru.perm.v.el59.dao.IGenericDao;
import ru.perm.v.el59.office.critery.MoveCritery;
import ru.perm.v.el59.office.critery.RestCritery;
import ru.perm.v.el59.office.critery.ShopCritery;
import ru.perm.v.el59.office.db.MoveSummary;
import ru.perm.v.el59.office.db.OpGroup;
import ru.perm.v.el59.office.db.Rest;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.ILowSaleProvider;
import ru.perm.v.el59.office.iproviders.IMoveProvider;
import ru.perm.v.el59.office.iproviders.IRestProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.report.LowSale;

import java.math.BigDecimal;
import java.util.*;

public class LowSaleProvider implements ILowSaleProvider {

	public LowSaleProvider() {
		super();
	}

	private ArrayList<Rest> rests = new ArrayList<Rest>();
	private ArrayList<MoveSummary> movies = new ArrayList<MoveSummary>();
	private IRestProvider restprovider;
	private IGenericDao opgroupprovider;
	private IMoveProvider moveprovider;
	private IGenericDao shopprovider;
	private ITovarProvider tovarprovider;

	// private int qtydays=30;

	public HashMap<String, LowSale> build(MoveCritery critery) {
		RestCritery criteryRest = new RestCritery();
		criteryRest.tovarCritery.clone(critery);
		Calendar cal = Calendar.getInstance();
		cal.setTime(critery.fromDate);
		cal.roll(Calendar.DAY_OF_YEAR, -1);
		criteryRest.ddate = cal.getTime();
		criteryRest.tovarCritery.arrshops.addAll(critery.arrshops);
		criteryRest.arrtypestock.addAll(critery.arrtypestock);
		System.out.println("rest");
		rests = getRestprovider().getOnDate(criteryRest);
		System.out.println("ok");
		// CriteryMove criteryMove= new CriteryMove();
		HashMap<String, LowSale> hashLowSale = new HashMap<String, LowSale>();
		Iterator iter = rests.iterator();
		String key;
		while (iter.hasNext()) {
			Rest r = (Rest) iter.next();
			key = r.getTovar().getNnum().toString() + r.getShop().getName();
			if (hashLowSale.containsKey(key)) {
				Rest r0 = hashLowSale.get(key).getRest0();
				r0.setQty(r0.getQty().add(r.getQty()));
				r0.setCenain(r0.getCenain().add(r.getCenain()));
				r = r0;
			}
			LowSale lowsale = new LowSale();
			lowsale.setRest0(r);
			hashLowSale.put(key, lowsale);
		}
		critery.arrOpgroup = new ArrayList<OpGroup>();
		critery.arrOpgroup = (ArrayList<OpGroup>) getOpgroupprovider()
				.getByCritery(new CommonCritery("Приход"));
		System.out.println("prihod");
		movies = getMoveprovider().getSumByCritery(critery);
		System.out.println("ok");
		iter = movies.iterator();
		while (iter.hasNext()) {
			MoveSummary m = (MoveSummary) iter.next();
			key = m.getTovar().getNnum().toString() + m.getShopname();
			if (hashLowSale.containsKey(key)) {
				hashLowSale.get(key).setPrihod(m);
			} else {
				LowSale l = createLowsaleByMove(m);
				l.setPrihod(m);
				hashLowSale.put(key, l);
			}
		}
		critery.arrOpgroup = (ArrayList<OpGroup>) getOpgroupprovider()
				.getByCritery(new CommonCritery("Расход"));
		// critery.opgroup="Расход";
		System.out.println("rashod");
		movies = getMoveprovider().getSumByCritery(critery);
		System.out.println("ok");
		iter = movies.iterator();
		while (iter.hasNext()) {
			MoveSummary m = (MoveSummary) iter.next();
			key = m.getTovar().getNnum().toString() + m.getShopname();
			if (hashLowSale.containsKey(key)) {
				hashLowSale.get(key).setRashod(m);
			} else {
				LowSale l = createLowsaleByMove(m);
				l.setRashod(m);
				hashLowSale.put(key, l);
			}
		}
		critery.arrOpgroup = (ArrayList<OpGroup>) getOpgroupprovider()
				.getByCritery(new CommonCritery("Возвраты"));
		movies = getMoveprovider().getSumByCritery(critery);
		System.out.println("ok");
		iter = movies.iterator();
		while (iter.hasNext()) {
			MoveSummary m = (MoveSummary) iter.next();
			key = m.getTovar().getNnum().toString() + m.getShopname();
			if (hashLowSale.containsKey(key)) {
				hashLowSale.get(key).setVozvrat(m);
			} else {
				LowSale l = createLowsaleByMove(m);
				l.setVozvrat(m);
				hashLowSale.put(key, l);
			}
		}
		System.out.println("real");
		critery.arrOpgroup = (ArrayList<OpGroup>) getOpgroupprovider()
				.getByCritery(new CommonCritery("Реализация"));
		movies = getMoveprovider().getSumByCritery(critery);
		System.out.println("ok");
		iter = movies.iterator();
		while (iter.hasNext()) {
			MoveSummary m = (MoveSummary) iter.next();
			key = m.getTovar().getNnum().toString() + m.getShopname();
			if (hashLowSale.containsKey(key)) {
				hashLowSale.get(key).setSale(m);
			} else {
				LowSale l = createLowsaleByMove(m);
				l.setSale(m);
				hashLowSale.put(key, l);
			}
		}
		criteryRest.ddate = critery.toDate;
		criteryRest.tovarCritery.arrshops.addAll(critery.arrshops);
		System.out.println("restonend");
		rests = getRestprovider().getOnDate(criteryRest);
		System.out.println("ok");
		// CriteryMove criteryMove= new CriteryMove();
		iter = rests.iterator();
		while (iter.hasNext()) {
			Rest r = (Rest) iter.next();
			key = r.getTovar().getNnum().toString() + r.getShop().getName();
			if (hashLowSale.containsKey(key)) {
				Rest rend = hashLowSale.get(key).getRestend();
				rend.setQty(rend.getQty().add(r.getQty()));
				rend.setCenain(rend.getCenain().add(r.getCenain()));
				hashLowSale.get(key).setRestend(rend);
			} else {
				LowSale lowsale = new LowSale();
				lowsale.setRestend(r);
				Rest r0 = new Rest();
				r0.setTovar(r.getTovar());
				r0.setShop(r.getShop());
				lowsale.setRest0(r0);
				hashLowSale.put(key, lowsale);
			}
		}
		// Заполнение null
		iter = hashLowSale.keySet().iterator();
		while (iter.hasNext()) {
			LowSale l = hashLowSale.get((String) iter.next());
			if (l.getPrihod() == null)
				l.setPrihod(new MoveSummary());
			if (l.getSale() == null)
				l.setSale(new MoveSummary());
			if (l.getRashod() == null)
				l.setRashod(new MoveSummary());
			if (l.getVozvrat() == null)
				l.setVozvrat(new MoveSummary());
			if (l.getRestend() == null)
				l.setRestend(new Rest());
		}
		for (String k : hashLowSale.keySet()) {
			LowSale l = hashLowSale.get(k);

			MoveSummary restCalc = new MoveSummary();
/*			BigDecimal q = l.getRest0().getQty() + l.getPrihod().getSumQty()
					+ l.getVozvrat().getSumQty() - l.getRashod().getSumQty()
					- l.getSale().getSumQty();
*/			
			BigDecimal q = l.getRest0().getQty().add(l.getPrihod().getSumQty()).add(l.getVozvrat().getSumQty()).subtract(l.getRashod().getSumQty()).subtract(l.getSale().getSumQty()); 
			restCalc.setSumQty(q);
/*			BigDecimal s = l.getRest0().getCenain() + l.getPrihod().getSumSummain()
					+ l.getVozvrat().getSumSummain()
					- l.getRashod().getSumSummain()
					- l.getSale().getSumSummain();
*/
			BigDecimal s = l.getRest0().getCenain().add(l.getPrihod().getSumSummain()).add(l.getVozvrat().getSumSummain()).subtract(l.getRashod().getSumSummain()).subtract(l.getSale().getSumSummain());

			restCalc.setSumSummain(s);
			l.setRestEndCalc(restCalc);

			MoveSummary diff = new MoveSummary();
			diff.setSumQty(l.getRestEndCalc().getSumQty().subtract(l.getRestend().getQty()));
			diff.setSumSummain(l.getRestEndCalc().getSumSummain().subtract(l.getRestend().getCenain()));
			l.setDiff(diff);
		}

		return hashLowSale;
	}

	private LowSale createLowsaleByMove(MoveSummary m) {
		Rest r = new Rest();
		r.setTovar(m.getTovar());
		ShopCritery cr = new ShopCritery();
		cr.setName(m.getShopname());
		List shoplist = getShopprovider().getByCritery(cr);
		r.setShop((Shop) shoplist.get(0));
		LowSale l = new LowSale();
		l.setRest0(r);
		return l;
	}

	public IRestProvider getRestprovider() {
		return restprovider;
	}

	public void setRestprovider(IRestProvider restprovider) {
		this.restprovider = restprovider;
	}

	public IGenericDao getOpgroupprovider() {
		return opgroupprovider;
	}

	public void setOpgroupprovider(IGenericDao opgroupprovider) {
		this.opgroupprovider = opgroupprovider;
	}

	public IMoveProvider getMoveprovider() {
		return moveprovider;
	}

	public void setMoveprovider(IMoveProvider moveprovider) {
		this.moveprovider = moveprovider;
	}

	public IGenericDao getShopprovider() {
		return shopprovider;
	}

	public void setShopprovider(IGenericDao shopprovider) {
		this.shopprovider = shopprovider;
	}

	public ITovarProvider getTovarprovider() {
		return tovarprovider;
	}

	public void setTovarprovider(ITovarProvider tovarprovider) {
		this.tovarprovider = tovarprovider;
	}
}
