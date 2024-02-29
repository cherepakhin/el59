package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.office.db.dto.DocItemWithPrice;
import ru.perm.v.el59.office.db.dto.TTovar;
import ru.perm.v.el59.office.iproviders.IDocItemProvider;
import ru.perm.v.el59.office.iproviders.IDocProvider;
import ru.perm.v.el59.office.iproviders.IPriceProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.iproviders.critery.DocItemCritery;
import ru.perm.v.el59.office.iproviders.critery.PriceCritery;
import ru.perm.v.el59.office.iproviders.critery.TovarCritery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class DocItemProvider extends GenericDaoHibernateImpl<DocItem, Long>
		implements IDocItemProvider {

	private IDocProvider docProvider;
	private IPriceProvider priceProvider;
	private ITovarProvider tovarProvider;
	private DocItem nullDocItem;
	private Logger LOGGER = Logger.getLogger(this.getClass());

	public DocItemProvider(Class<DocItem> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocItem> getByCritery(Object critery) {
		DocItemCritery c = (DocItemCritery) critery;
		Criteria criteria = getSession().createCriteria(DocItem.class, "d");
		if (c.doc != null) {
			criteria.add(Restrictions.eq("doc", c.doc));
		}
		if (c.nnum != null) {
			Criteria tovarCritery = criteria.createCriteria("tovar");
			tovarCritery.add(Restrictions.eq("nnum", c.nnum));
		}
		ArrayList<DocItem> list = (ArrayList<DocItem>) criteria.list();

		return list;
	}

	@Override
	public void deleteByDoc(Doc doc) {
		String sql = "delete from DocItem where doc=:doc";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("doc", doc);
		q1.executeUpdate();
	}

	@Override
	public Long create(DocItem o) throws Exception {
		Long n = super.create(o);
		getDocProvider().recalc(o.getDoc());
		return n;
	}

	@Override
	public void delete(DocItem docItem) throws Exception {
		String sql = "update DocWItem set docItem=:nullDocItem where docItem_n=:docItem";
		Query q1 = getSession().createQuery(sql);
		q1.setParameter("docItem", docItem);
		q1.setParameter("nullDocItem", getNullDocItem());
		q1.executeUpdate();
		super.delete(docItem);
		getDocProvider().recalc(docItem.getDoc());
	}

	@Override
	public void update(DocItem o) {
		try {
			super.update(o);
			getDocProvider().recalc(o.getDoc());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public IDocProvider getDocProvider() {
		return docProvider;
	}

	public void setDocProvider(IDocProvider docProvider) {
		this.docProvider = docProvider;
	}

	public IPriceProvider getPriceProvider() {
		return priceProvider;
	}

	public void setPriceProvider(IPriceProvider priceProvider) {
		this.priceProvider = priceProvider;
	}

	@Override
	public List<DocItemWithPrice> getDocItemForPrice(Doc doc,
													 PriceType selectedPriceType) throws Exception {
		DocItemCritery docItemCritery = new DocItemCritery();
		docItemCritery.doc = doc;
		List<DocItem> listDocItem = getByCritery(docItemCritery);
		List<DocItemWithPrice> listDocItemWithPrices = new ArrayList<DocItemWithPrice>();
		if (listDocItem.size() > 0) {
			PriceCritery priceCritery = new PriceCritery();
			priceCritery.namePriceType = selectedPriceType.getName();
			TovarCritery tc = new TovarCritery();
			for (DocItem docItem : listDocItem) {
				tc.nnum.add(docItem.getTovar().getNnum());
			}
			priceCritery.tovarCritery = tc;
			List<Price> listPrice = getPriceProvider().getByCritery(
					priceCritery);
			HashMap<Tovar, Price> hashTovarPrice = new HashMap<Tovar, Price>();
			for (Price price : listPrice) {
				hashTovarPrice.put(price.getTovar(), price);
			}
			for (DocItem docItem : listDocItem) {
				DocItemWithPrice dwp = new DocItemWithPrice();
				dwp.setDocItem(docItem);
				if (hashTovarPrice.containsKey(docItem.getTovar())) {
					dwp.setPrice(hashTovarPrice.get(docItem.getTovar()));
				} else {
					Price p = new Price();
					p.setTovar(docItem.getTovar());
					p.setPriceType(selectedPriceType);
					Long n = getPriceProvider().create(p);
					p.setN(n);
					dwp.setPrice(p);
				}
				listDocItemWithPrices.add(dwp);
			}
		}
		return listDocItemWithPrices;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	@Override
	public DocItem getNullDocItem() {
		if (nullDocItem == null) {
			nullDocItem = read(0L);
		}
		return nullDocItem;
	}
	
	@Override
	public void createByListTTovar(Doc doc,List<TTovar> listTTovar) throws Exception {
		for (TTovar tTovar : listTTovar) {
			DocItem di = new DocItem();
			di.setDoc(doc);
			di.setCena(tTovar.getItemDTO().getPrice());
			di.setQty(tTovar.getItemDTO().getQty());
			di.setAnalog(tTovar.getItemDTO().getDescription());
			if (tTovar.getSelected()!=null && tTovar.getSelected().getNnum() > 0) {
				di.setTovar(tTovar.getSelected());
				di.setAnalog(tTovar.getItemDTO().getDescription());
			} else {
				di.setTovar(getNullDocItem().getTovar());
			}
			super.create(di);
		}
		getDocProvider().recalc(doc);
	}

	@Override
	public void delete(List<DocItem> listDocItem) throws Exception {
		if(listDocItem!=null) {
			Doc doc= listDocItem.get(0).getDoc();
			for (DocItem docItem : listDocItem) {
				DocItem di = read(docItem.getN());
				super.delete(di);
			}
			getDocProvider().recalc(doc);
		}
	}
}
