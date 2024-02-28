package ru.perm.v.el59.office.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;

import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.web.DocWInfo;
import ru.perm.v.el59.office.emailer.IEmailer;
import ru.perm.v.el59.office.iproviders.IPriceProvider;
import ru.perm.v.el59.office.iproviders.IShopProvider;
import ru.perm.v.el59.office.iproviders.web.IDocWProvider;

public class UtilProcessor {
	private IPriceProvider priceProvider;
	private IDocWProvider docWProvider;
	private IShopProvider shopProvider;
	private IEmailer emailer;
	private String emailManager;

	public void setToZeroOldLocalPrice(Exchange exchange) throws Exception {
		Logger.getLogger(this.getClass()).info(
				"Обнуление устареших локальных прайсов.Начало.");
		Integer c = getPriceProvider().setToZeroOldLocalPrice();
		Logger.getLogger(this.getClass()).info(
				"Обнуление устареших локальных прайсов.Конец.Обнулено:" + c);
	}

	public void sendMessageOpenDocw(Exchange exchange) throws Exception {
		List<Shop> shops = getShopProvider().getWorkedShop();
		HashMap<Shop, Integer> mapShopQtyOpenDocW = new HashMap<Shop, Integer>();
		for (Shop shop : shops) {
			ArrayList<Shop> _listShop = new ArrayList<Shop>();
			_listShop.add(shop);
			List<DocWInfo> listDocW = getDocWProvider().getOnlyOpen(_listShop);
			Integer count = 0;
			for (DocWInfo docWInfo : listDocW) {
				if (docWInfo.getQtyOrder() > docWInfo.getQtyOrderSupplier()) {
					count++;
				}
			}
			mapShopQtyOpenDocW.put(shop, count);
		}
		String message = "";
		for (Shop shop : mapShopQtyOpenDocW.keySet()) {
			if (mapShopQtyOpenDocW.get(shop) > 0) {
				String s = "Магазин " + shop.getName() + " не отработано "
						+ mapShopQtyOpenDocW.get(shop) + " web-выписок\n";
				getEmailer().send(null, shop.getEmail(), s,
						"Неотработанные выписки", null,true);
				message = message + s;
			}
		}
		getEmailer().send(null, emailManager, message,
				"Неотработанные выписки", null,true);
	}

	public IPriceProvider getPriceProvider() {
		return priceProvider;
	}

	public void setPriceProvider(IPriceProvider priceProvider) {
		this.priceProvider = priceProvider;
	}

	public IDocWProvider getDocWProvider() {
		return docWProvider;
	}

	public void setDocWProvider(IDocWProvider docWProvider) {
		this.docWProvider = docWProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public IEmailer getEmailer() {
		return emailer;
	}

	public void setEmailer(IEmailer emailer) {
		this.emailer = emailer;
	}

	public String getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(String emailManager) {
		this.emailManager = emailManager;
	}
}
