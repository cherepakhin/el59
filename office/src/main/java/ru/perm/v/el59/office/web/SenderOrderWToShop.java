package ru.perm.v.el59.office.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Body;

import ru.perm.v.el59.office.db.Shop;
import ru.perm.v.el59.office.db.dto.FileAttach;
import ru.perm.v.el59.office.emailer.IEmailer;
import ru.perm.v.el59.office.iproviders.IShopProvider;

import com.sun.istack.logging.Logger;

/**
 * Отправка выписок сайта по почте в магазины
 * 
 * @author vasi
 * 
 */
public class SenderOrderWToShop {

	private IEmailer emailer;
	private IShopProvider shopProvider;

	public void send(@Body Object body) throws Error {
		OrderDTO orderDTO = (OrderDTO) body;
		Logger.getLogger(this.getClass()).info(
				"Отправка по почте выписки сайта №" + orderDTO.getNumber()
						+ ".Начало");
		List<Shop> listShop = getShopProvider().getWorkedShop();
		for (Shop shop : listShop) {
			if (shop.getCod().equals(orderDTO.getShopDTO().getCod())) {

				List<FileAttach> listFileAttach = new ArrayList<FileAttach>();
				FileAttach fa = new FileAttach();
				fa.name = "Выписка " + orderDTO.getNumber() + ".xml";
				fa.body = orderDTO.getXml().getBytes();
				listFileAttach.add(fa);
				getEmailer().send(null, shop.getEmail(), orderDTO.getXml(),
						"Выписка с сайта " + orderDTO.getNumber(),
						listFileAttach,true);
			}
		}
		Logger.getLogger(this.getClass()).info(
				"Отправка по почте выписки сайта №" + orderDTO.getNumber()
						+ ".Конец");
	}

	public IEmailer getEmailer() {
		return emailer;
	}

	public void setEmailer(IEmailer emailer) {
		this.emailer = emailer;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

}
