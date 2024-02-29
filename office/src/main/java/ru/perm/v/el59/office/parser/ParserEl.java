package ru.perm.v.el59.office.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.perm.v.el59.office.db.*;
import ru.perm.v.el59.office.iproviders.web.IParserEl;
import ru.perm.v.el59.office.iproviders.web.IParserSite;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ParserEl extends AParserSite implements IParserEl, IParserSite {
	private static String CHARSET_SITE = "Windows-1251";
	// URL сайта Эльдорадо
	private static String URL_SITE = "https://www.eldorado.ru";
	// Заголовок страницы (нужен для анализа содержимого). Если товара нет, то в
	// нем Ошибка 404
	private static String XPATH_HEAD = "//html/head/title/text()";
	// URL страницы с описанем и характеристиками
	private static String URL_CATALOG = URL_SITE + "/cat/detail/";
	// Метка характеристик
	// private static String XPATH_FULL_FEATURES =
	// "//div[@class='specificationTextTable q-item-full-specs-table']//td/text() | "
	// +
	// "//div[@class='specificationTextTable q-item-full-specs-table']//tr/td/div/text()";
	private static String XPATH_FULL_FEATURES = "//div[@class='specificationTextTable q-item-full-specs-table']//tr";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	/**
	 * Формирование информации о товаре с сайта Эльдорадо
	 * 
	 * @param nnum
	 *            - н.номер
	 * @return - заполненный данными с сайта объект TovarInfo. NULL если
	 *         информации о товаре нет
	 * @throws Exception
	 */
	public TovarInfo parseTovar(Integer nnum, Manager manager) throws Exception {
		String snnum = nnum.toString();
		if (snnum.length() < 8)
			snnum = '0' + snnum;
		Tovar tovar = setNnumUrl(nnum, URL_CATALOG + snnum + '/', true, true);
		if (tovar == null)
			return null;
		parseTovar(nnum, URL_CATALOG + snnum, manager, true, true);
		return tovarInfo;
	}

	/**
	 * Заполнение описания товара и характеристик
	 * 
	 * @return
	 * @return - заполненный характеристиками и описанием объект
	 * @throws Exception
	 */
	@Override
	protected TovarInfo fillInfo() throws Exception {
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_HEAD);
		Logger.getLogger(this.getClass().getName()).info(getUrl());
		Document doc = getDoc(getUrl() + '/');
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		String info = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			info = info + nodes.item(i).getNodeValue();
			if (nodes.item(i).hasChildNodes()) {
				NodeList child = nodes.item(i).getChildNodes();
				for (int j = 0; j < child.getLength(); j++) {
					info = info + child.item(j).getNodeValue();
				}
			}
		}
		info = clearHTML(info);
		Logger.getLogger(this.getClass().getName()).info(info);
		// Нет Ошибки 404. Удаляю старые характеристики
		tovarInfo = getTovarInfoProvider().clearFeaturesWeb(tovarInfo);
		// tovarInfo.setInfo(info);
		expr = xpath.compile(XPATH_FULL_FEATURES);
		result = expr.evaluate(doc, XPathConstants.NODESET);
		nodes = (NodeList) result;
		String nameGroup = "";
		String nameFeature = "";
		String valFeature = "";

		for (int i = 0; i < nodes.getLength(); i++) {
			// org.w3c.dom.Node n = nodes.item(i);
			if (nodes.item(i).hasChildNodes()) {
				NodeList childs = nodes.item(i).getChildNodes();
				// System.out.println("childs.getLength()" +
				// childs.getLength());
				if (childs.getLength() == 1) {
					// Группа
					NodeList subChilds = childs.item(0).getChildNodes(); // td
					nameGroup = clearHTML(subChilds.item(0).getNodeValue());
					continue;
				}
				if (childs.getLength() == 2) {
					NodeList subChilds = childs.item(0).getChildNodes(); // td
					nameFeature = clearHTML(subChilds.item(0).getNodeValue());
					Node nodeForVal = childs.item(1);
					valFeature = clearHTML(getTextFromNode(nodeForVal));
				}
				/*
				 * for (int c = 0; c < childs.getLength(); c++) {
				 * System.out.println
				 * ("subChilds.getLength()"+subChilds.getLength()); for (int s =
				 * 0; s < subChilds.getLength(); s++) { String txt
				 * =subChilds.item(s).getNodeValue(); if(txt!=null) {
				 * System.out.println(txt); } } }
				 */
				Feature f = new Feature();
				f.setGrp(nameGroup.trim());
				f.setName(nameFeature.trim());
				f.setVal(valFeature.trim());
				Logger.getLogger(this.getClass().getName()).info(
						String.format("Хар-ка: %s:%s:%s", nameGroup,
								nameFeature, valFeature));
				if (f != null) {
					if (!f.getName().equals("") && !f.getVal().equals("")) {
						if (!isLoadedDimension(f)) {
							tovarInfo.addFeature(f);
						}
					}
				}
			}
		}

		return tovarInfo;
	}

	private String getTextFromNode(Node nodeForVal) {
		String txt = nodeForVal.getNodeValue();
		if (txt == null) {
			txt = "";
		}
		if (nodeForVal.hasChildNodes()) {
			for (int i = 0; i < nodeForVal.getChildNodes().getLength(); i++) {
				// Отфильтровываю контекстную помощь для значения хар-ки
				NamedNodeMap attrName = nodeForVal.getChildNodes().item(i)
						.getAttributes();
				if (attrName != null) {
					org.w3c.dom.Node aNameClass = attrName
							.getNamedItem("class");

					if (aNameClass != null
							&& aNameClass.getNodeValue().equals(
									"popup default_popup help_popup")) {
						continue;
					}
				}

				String val = getTextFromNode(nodeForVal.getChildNodes().item(i))
						.trim();
				// какие-то странный символ встречается -62,-96
				byte[] b = val.getBytes();
				if (b.length == 1) {
					if (b[0] == -96) {
						continue;
					}
				}

				if (val.length() == 0) {
					continue;
				}
				// На нектр. хар-х есть ссылки на "Другие товары"
				if (val.contains("Другие товары")) {
					continue;
				}
				// Удаление всякой херни типа перевода строки
				val = val.replaceAll("\\p{Cntrl}", " ");
				val = clearHTML(val);
				if (txt.length() == 0) {
					txt = val;
				} else {
					txt = txt + val;
				}
			}
		}
		return txt;
	}

	/**
	 * Загрузка фотографий
	 * 
	 * @return - заполненный TovarInfo
	 * @throws Exception
	 */
	@Override
	protected TovarInfo fillPhoto() throws Exception {
		// Основная картинка
		ArrayList<Photo> listPhoto = findPhoto();
		if (listPhoto.size() > 0) {
			getTovarInfo().getListPhoto().addAll(listPhoto);
		}
		return tovarInfo;
	}

	// Метка файла картинки
	private static String XPATH_PHOTO_SRC = ""
			+ "//div[@class='fotorama q-main-fotorama']//a[@class='productMainImg noBorder']";
	// +
	// "//div[@class='fotorama__stage__frame fotorama__loaded fotorama__loaded--img fotorama__active']//img";
	// Название аттрибута файла картинки
	private static final String PHOTO_ATTR_SRC = "href";

	// private static final String PHOTO_ATTR_HREF = "href";

	private ArrayList<Photo> findPhoto() throws Exception {
		ArrayList<Photo> ret = new ArrayList<Photo>();
		String filename = "";
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;

		for (int num_img = 0; num_img < nodes.getLength(); num_img++) {
			Node n = nodes.item(num_img);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = null;
			Node _imgnode = attr.getNamedItem(PHOTO_ATTR_SRC);
			if (_imgnode != null) {
				imgnode = _imgnode;
			}
			_imgnode = attr.getNamedItem(PHOTO_ATTR_SRC);
			if (_imgnode != null) {
				imgnode = _imgnode;
			}
			if (imgnode != null) {
				String imgpath = imgnode.getNodeValue();
				// Ссылка на картинки след.вида
				// http://static.eldorado.ru/photos/71/new_71186080_l_1468238967.jpeg/resize/500x375/
				// все от /resize... надо убрать
				int pos = imgpath.indexOf("/resize");
				imgpath = imgpath.substring(0, pos);
				if (imgpath != null && !imgpath.isEmpty()) {
					Photo photo = new Photo();
					// filename = loadPhoto(imgpath, getNnum(),
					filename = loadPhoto(imgpath, getNnum(),
							DELIMETER_NAMEFILE, null, num_img);
					if (filename != null) {
						Logger.getLogger(this.getClass().getName()).info(
								String.format("Photo %d %s", getNnum(),
										filename));
						photo.setPath(filename
								.replace(getBaseDirForPhoto(), ""));
						String info = "";
						photo.setName(info);
						ret.add(photo);
					}
				}
			}
		}

		return ret;
	}

	@Override
	public List<Tovar> parseListTovar(List<Integer> listNnum, Manager manager)
			throws Exception {
		List<Tovar> ret = new ArrayList<Tovar>();
		for (Integer nnum : listNnum) {
			System.out.println(nnum);
			// if(t==null) {
			TovarInfo tovarInfo = parseTovar(nnum, manager);
			if (tovarInfo != null) {
				Tovar tovar = (Tovar) getTovarProvider().initialize(nnum);
				ret.add(tovar);
				if (tovar != null) {
					tovarInfo.setTovar(tovar);
					getTovarInfoProvider().update(tovarInfo);
				} else {
					System.out.println("Товар не найден " + nnum);
				}
			}
		}
		return ret;
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

	@Override
	public String getSampleLink() {
		return "https://www.eldorado.ru";
	}

}
