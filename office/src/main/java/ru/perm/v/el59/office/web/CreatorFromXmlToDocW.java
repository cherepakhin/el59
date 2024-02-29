package ru.perm.v.el59.office.web;

import com.sun.istack.logging.Logger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.camel.Body;
import ru.perm.v.el59.office.db.Contragent;
import ru.perm.v.el59.office.db.TypeDoc;
import ru.perm.v.el59.office.db.web.DocW;
import ru.perm.v.el59.office.db.web.DocWItem;
import ru.perm.v.el59.office.iproviders.*;
import ru.perm.v.el59.office.iproviders.critery.PriceCritery;
import ru.perm.v.el59.office.iproviders.critery.ShopCritery;
import ru.perm.v.el59.office.iproviders.critery.TovarCritery;
import ru.perm.v.el59.office.iproviders.web.IDocWItemProvider;
import ru.perm.v.el59.office.iproviders.web.IDocWProvider;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class CreatorFromXmlToDocW {
	private XStream xstream;

	private String nameTypeDocDocw;
	private Integer nnumDostavka;
	private String nameDostavka;
	private BigDecimal priceDostavka;

	private IDocWProvider docWProvider;
	private ITovarProvider tovarProvider;
	private IDocWItemProvider docWItemProvider;
	private IShopProvider shopProvider;
	private IContragentProvider contragentProvider;
	private ITypeDocProvider typeDocProvider;
	private IPriceProvider priceProvider;
	private IDocItemProvider docItemProvider;

	public OrderDTO toOrderDTO(@Body Object body) {
		String xml = (String) body;
		OrderDTO orderDTO = getDTO(xml);
		return orderDTO;
	}

	public void buildDocW(@Body Object body) throws Error, Exception {
		OrderDTO orderDTO = (OrderDTO) body;
		Logger.getLogger(this.getClass()).info(
				"Создание выниски сайта №" + orderDTO.getNumber() + ".Начало");
		DocW docw = new DocW();
//		TypeDoc typedoc = getTypeDocProvider()
//				.getByName(getNameTypeDocDocw());
		TypeDoc typedoc = new TypeDoc();
		if (typedoc == null) {
			throw new Error("Не найден тип документа " + getNameTypeDocDocw());
		}

		docw.setTypeDoc(typedoc);
		docw.setNumdoc(orderDTO.getNumber());
		docw.setDdate(orderDTO.getDdate());
		docw.setSumma(orderDTO.getSum());
		docw.setComment(orderDTO.getComment() + " " + orderDTO.getTerms());
		docw.setSource(orderDTO.getSource());
		docw.setShippingAddress(orderDTO.getShippingAddress());

		ShopCritery shopCritery = new ShopCritery();
		shopCritery.setCod(orderDTO.getShopDTO().getCod());
//		List<Shop> listShop = getShopProvider().getByCritery(shopCritery);
//		if (listShop.size() == 0) {
//			throw new Error("Не найден магазин с кодом "
//					+ orderDTO.getShopDTO().getCod());
//		}
//		docw.setShop(listShop.get(0));

		/*
		 * Contragent contragent =
		 * getContragentProvider().getByEqName(orderDTO.getContragentDTO
		 * ().getName()); if(contragent==null) {
		 */
		Contragent c = new Contragent();
		ContragentDTO contragentDTO = orderDTO.getContragentDTO();
		c.setName(contragentDTO.getName());
		c.setAddress(contragentDTO.getAddress());
		c.setPhone(contragentDTO.getPhone());
		c.setEmail(contragentDTO.getEmail());
//		c.setShop(listShop.get(0));
//		Long contragent_n = getContragentProvider().create(c);
//		c.setN(contragent_n);
		docw.setContragent(c);
		/*
		 * } else { docw.setContragent(contragent); }
		 */
//		Long n = (Long) getDocWProvider().create(docw);
//		docw.setN(n);
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (Exception e) {
      Logger.getLogger(this.getClass()).info(e.getLocalizedMessage());
    }

		for (TovarDTO t : orderDTO.getListTovarDTO()) {
			DocWItem item = new DocWItem();
//			Tovar tovar = getTovarProvider().(t.getNnum());
//			item.setTovar(tovar);
			item.setDocw(docw);
			item.setQty(t.getQty());
			item.setCena(t.getPrice());
//			item.setDocItem(getDocItemProvider().getNullDocItem());
			item.setSumma(t.getPrice().multiply(t.getQty()));
//			getDocWItemProvider().create(item);
		}
		// Оформление доставки
		/*
		 * if(orderDTO.getTerms().toLowerCase().contains(getNameDostavka().
		 * toLowerCase())) { DocWItem item = new DocWItem(); Tovar tovar =
		 * getTovarProvider().read(getNnumDostavka()); item.setTovar(tovar);
		 * item.setDocw(docw); item.setQty(new BigDecimal(1));
		 * item.setCena(getPriceDostavka()); item.setSumma(getPriceDostavka());
		 * getDocWItemProvider().create(item); }
		 */Logger.getLogger(this.getClass()).info(
				"Создание выниски сайта №" + orderDTO.getNumber() + ".Конец");
	}

	private BigDecimal getPriceDostavka() {
		if (priceDostavka == null) {
			PriceCritery priceCritery = new PriceCritery();
//			priceCritery.namePriceType = getPriceProvider().getNameDefaultPrice();
			priceCritery.tovarCritery = new TovarCritery();
			priceCritery.tovarCritery.nnum.add(getNnumDostavka());
//			List<Price> list = getPriceProvider().getByCritery(priceCritery);
//			if (list.size() > 0) {
//				priceDostavka = list.get(0).getCena();
//			} else {
//				return new BigDecimal("0.00");
//			}
		}
		return priceDostavka;
	}

	private OrderDTO getDTO(String xml) {
		Logger.getLogger(this.getClass()).info("Подгрузка xml выписки");
		XStream xstream = getXStream();
		xstream.alias("Заявка", OrderDTO.class);
		xstream.aliasField("Номер", OrderDTO.class, "number");
		xstream.aliasField("Дата", OrderDTO.class, "ddate");
		xstream.aliasField("Сумма", OrderDTO.class, "sum");
		xstream.aliasField("Комментарий", OrderDTO.class, "comment");
		xstream.aliasField("УсловияДоставки", OrderDTO.class, "terms");
		xstream.aliasField("Источник", OrderDTO.class, "source");
		xstream.aliasField("АдресДоставки", OrderDTO.class, "shippingAddress");

		xstream.aliasField("Поставщик", OrderDTO.class, "shopDTO");
		xstream.alias("Поставщик", ShopDTO.class);
		xstream.aliasField("Ид", ShopDTO.class, "cod");
		xstream.omitField(ShopDTO.class, "Название");

		xstream.aliasField("Покупатель", OrderDTO.class, "contragentDTO");
		xstream.alias("Покупатель", ContragentDTO.class);
		xstream.aliasField("Имя", ContragentDTO.class, "name");
		xstream.aliasField("Адрес", ContragentDTO.class, "address");
		xstream.aliasField("Телефон", ContragentDTO.class, "phone");
		xstream.aliasField("E-mail", ContragentDTO.class, "email");

		xstream.aliasField("Товары", OrderDTO.class, "listTovarDTO");
		xstream.alias("Товар", TovarDTO.class);
		xstream.aliasField("Ид", TovarDTO.class, "nnum");
		xstream.aliasField("Название", TovarDTO.class, "name");
		xstream.aliasField("Количество", TovarDTO.class, "qty");
		xstream.aliasField("Цена", TovarDTO.class, "price");

		xstream.omitField(OrderDTO.class, "Сумма");
		OrderDTO o = (OrderDTO) xstream.fromXML(xml);
		o.setXml(xml);
		Logger.getLogger(this.getClass()).info(
				"Подгрузка xml выписки " + o.getNumber() + ".Конец");
		return o;
	}

	public XStream getXStream() {
		if (xstream == null) {
			xstream = new XStream(new DomDriver());
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.registerConverter(new DateConverter("yyyy-MM-dd",
					new String[] { "yyyy-MM-dd" }));
		}
		return xstream;
	}

	public IDocWProvider getDocWProvider() {
		return docWProvider;
	}

	public void setDocWProvider(IDocWProvider docWProvider) {
		this.docWProvider = docWProvider;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public IDocWItemProvider getDocWItemProvider() {
		return docWItemProvider;
	}

	public void setDocWItemProvider(IDocWItemProvider docWItemProvider) {
		this.docWItemProvider = docWItemProvider;
	}

	public IShopProvider getShopProvider() {
		return shopProvider;
	}

	public void setShopProvider(IShopProvider shopProvider) {
		this.shopProvider = shopProvider;
	}

	public IContragentProvider getContragentProvider() {
		return contragentProvider;
	}

	public void setContragentProvider(IContragentProvider contragentProvider) {
		this.contragentProvider = contragentProvider;
	}

	public ITypeDocProvider getTypeDocProvider() {
		return typeDocProvider;
	}

	public void setTypeDocProvider(ITypeDocProvider typeDocProvider) {
		this.typeDocProvider = typeDocProvider;
	}

	public String getNameTypeDocDocw() {
		return nameTypeDocDocw;
	}

	public void setNameTypeDocDocw(String nameTypeDocDocw) {
		this.nameTypeDocDocw = nameTypeDocDocw;
	}

	public Integer getNnumDostavka() {
		return nnumDostavka;
	}

	public void setNnumDostavka(Integer nnumDostavka) {
		this.nnumDostavka = nnumDostavka;
	}

	public String getNameDostavka() {
		return nameDostavka;
	}

	public void setNameDostavka(String nameDostavka) {
		this.nameDostavka = nameDostavka;
	}

	public IPriceProvider getPriceProvider() {
		return priceProvider;
	}

	public void setPriceProvider(IPriceProvider priceProvider) {
		this.priceProvider = priceProvider;
	}

	public IDocItemProvider getDocItemProvider() {
		return docItemProvider;
	}

	public void setDocItemProvider(IDocItemProvider docItemProvider) {
		this.docItemProvider = docItemProvider;
	}
}
