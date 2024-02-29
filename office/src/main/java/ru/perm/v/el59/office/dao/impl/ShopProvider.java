package ru.perm.v.el59.office.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.critery.ShopCritery;
import ru.perm.v.el59.office.wscommand.impl.GenericDaoMessageImpl;

import java.util.*;

public class ShopProvider extends GenericDaoMessageImpl<Shop, String> implements
		IShopProvider {

	private String nullShopCod;
	private Shop nullShop;
	private Map<String, Shop> hashShop = new HashMap<String, Shop>();

	public ShopProvider(Class<Shop> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shop> getByCritery(Object critery) {
		ShopCritery shop = (ShopCritery) critery;
		Criteria shopCriteria = getSession().createCriteria(Shop.class);
		if ((shop.getCod() != null) && (!shop.getCod().equals(""))) {
			shopCriteria.add(Restrictions.eq("cod", shop.getCod()));
		}
		if (shop.getWorked() != null) {
			shopCriteria.add(Restrictions.eq("worked", shop.getWorked()));
		}
		if ((shop.getName() != null) && (!shop.getName().equals(""))) {
			if (shop.getName().contains(";")) {
				String[] arrShopName = shop.getName().split(";");
				shopCriteria.add(Restrictions.in("name",
						Arrays.asList(arrShopName)));
			} else {
				shopCriteria.add(Restrictions.like("name", shop.getName(),
						MatchMode.ANYWHERE).ignoreCase());
			}
		}
		shopCriteria.addOrder(Order.asc("name"));
		ArrayList<Shop> list = (ArrayList<Shop>) shopCriteria.list();
		return list;
	}

	// nullShop - пустой магазин. Заглушка.
	@Override
	public Shop getNullShop() throws Exception {
		if (nullShop == null) {
			ShopCritery shopCritery = new ShopCritery();
			shopCritery.setCod(getNullShopCod());
			List<Shop> list = getByCritery(shopCritery);
			if (list.size() == 0) {
				new Exception("Не найден магазин по умолчанию с кодом "+getNullShopCod());
			} else {
				nullShop = list.get(0);
			}
		} 
		return nullShop;
	}

	public String getNullShopCod() {
		return nullShopCod;
	}

	public void setNullShopCod(String nullShopCod) {
		this.nullShopCod = nullShopCod;
	}

	@Override
	public List<Shop> getWorkedShop() {
		ShopCritery c = new ShopCritery();
		c.setWorked(true);
		return getByCritery(c);
	}

	@Override
	public List<Shop> getManagedShop(Manager manager) {
		if (manager == null || manager.getShop() == null
				|| manager.getShop().equals("")) {
			return getWorkedShop();
		} else {
			ShopCritery shopCritery = new ShopCritery();
			shopCritery.setName(manager.getShop());
			return getByCritery(shopCritery);
		}
	}

	@Override
	public String create(Shop shop) throws Exception {
		String cod = super.create(shop);
		if (hashShop.containsKey(cod)) {
			Shop oldShop = hashShop.get(cod);
			hashShop.remove(oldShop);
		}
		hashShop.put(cod, shop);
		return cod;
	}

	@Override
	public void delete(Shop shop) throws Exception {
		super.delete(shop);
		hashShop.remove(shop);
	}

	@Override
	public void update(Shop shop) throws Exception {
		super.update(shop);
		hashShop.remove(shop);
		hashShop.put(shop.getCod(), shop);
	}

	@Override
	public Shop read(String id) {
		if (hashShop.keySet().contains(id)) {
			return hashShop.get(id);
		} else {
			Shop shop = super.read(id);
			hashShop.put(id, shop);
			return shop;
		}
	}

}
