package ru.perm.v.el59.office.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Manager;
import ru.perm.v.el59.office.db.Photo;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.web.IParserSite;

public class ParserSoftMarket extends AParserSite implements IParserSite {
	// Кодировка сайта
	private static String CHARSET_SITE = "UTF-8";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	public ParserSoftMarket() {
		super();
	}

	/**
	 * Формирование информации о товаре с сайта
	 * 
	 * @param nnum
	 *            - н.номер
	 * @return - заполненный данными с сайта объект TovarInfo
	 * @throws Exception
	 */
	/*
	 * @Override public Tovar parseTovar(Integer nnum, String _url, Manager
	 * manager,Boolean parseProperty,Boolean parsePicture) throws Exception {
	 * Tovar tovar = setNnumUrl(nnum, _url,parseProperty,parsePicture); if
	 * (tovar == null) return null; if (parseProperty) { fillInfo(); } if
	 * (parsePicture) { fillPhoto(manager); } tovarInfo.setManager(manager);
	 * setOld(tovarInfo); getTovarInfoProvider().update(tovarInfo);
	 * getSubsFeatureProvider().processTovarInfo(tovarInfo);
	 * getMainFeatureProvider().substituteMainFeature(tovarInfo); return
	 * tovarInfo.getTovar(); }
	 */
	/**
	 * Заполнение описания товара и характеристик
	 * 
	 * @param tovar
	 *            - TovarInfo для заполнения
	 * @param nnum
	 *            - н.номер
	 * @return - заполненный характеристиками и описанием объект
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	// p[@class="b-goods-specifications-title"]
	private final static String XPATH_FULL_FEATURES = "//div[@class='b-goods-specifications-cell']//text() | //p[@class='b-goods-specifications-title']//text()";
	// Метка группы
	private final static String LABEL_GROUP_TITLE = "b-goods-specifications-title";
	// Метка описания
	private final static String XPATH_INFO = "//p[@itemprop='description']/text()";
	

	@Override
	protected TovarInfo fillInfo() throws IOException, XPathExpressionException {
		XPath xpath = getXPath();
		org.w3c.dom.Node n;

		// Описание
		XPathExpression expr = xpath.compile(XPATH_INFO);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		String info ="";
		for (int i = 0; i < nodes.getLength(); i++) {
			n = nodes.item(i);
			String valNode = n.getNodeValue().trim();
			System.out.println(valNode);
			if(!valNode.isEmpty()) {
				info=info+valNode;
			}
		}
		
		Logger.getLogger(this.getClass()).info(info);
		tovarInfo.setInfo(info);
		// Полные характеристики
		expr = xpath.compile(XPATH_FULL_FEATURES);
		result = expr.evaluate(getDoc(), XPathConstants.NODESET);

		nodes = (NodeList) result;
		String nameGroup = "";
		String nameFeature = "";
		String valFeature = "";
		boolean flagNameFeature = false;
		boolean flagValFeature = false;
		NamedNodeMap attrName;
		String valNode="";
		for (int i = 0; i < nodes.getLength(); i++) {
			n = nodes.item(i);
			// Проверка на принадлежность к группе
			attrName = n.getParentNode().getAttributes();
			if (attrName != null) {
				org.w3c.dom.Node aNameClass = attrName.getNamedItem("class");
				valNode = n.getNodeValue().trim();
				// System.out.println(valNode);
				// Проверка на принадлежность к группе
				if (aNameClass != null
						&& aNameClass.getNodeValue().equals(LABEL_GROUP_TITLE)) {
					// System.out.println("Group "+valNode);
					nameGroup = valNode;
					flagNameFeature = false;
					flagValFeature = false;
					continue;
				}
			}

			// Проверка на принадлежность к названию
			if (!flagNameFeature && !valNode.isEmpty()) {
				nameFeature = valNode;
				flagNameFeature = true;
				continue;
			}

			// Проверка на принадлежность к значению хар-ки
			if (flagNameFeature && !valNode.isEmpty()) {
				valFeature = valNode;
				flagValFeature = true;
			}

			if (flagNameFeature && flagValFeature) {
				Feature f = new Feature();
				f.setGrp(clearHTML(nameGroup.trim()));
				f.setName(clearHTML(nameFeature.trim()));
				f.setVal(clearHTML(valFeature.trim()));
				if (f != null) {
					if (!f.getName().equals("") && !f.getVal().equals("")) {
						if (!isLoadedDimension(f)) {
							tovarInfo.addFeature(f);
						}
					}
				}
				Logger.getLogger(this.getClass()).info(
						String.format("%s:%s:%s", f.getGrp(), f.getName(),
								f.getVal()));
				flagNameFeature = false;
				flagValFeature = false;
				valFeature = "";
				nameFeature = "";
				// System.out.println(String.format("%s|%s|%s",
				// f.getGrp(),f.getName(), f.getVal()));
			}
		}
		return tovarInfo;
	}

	/**
	 * Загрузка фотографий
	 * 
	 * @param tovar
	 *            - TovarInfo для заполнения
	 * @param nnum
	 *            - н.номер
	 * @return - заполненный TovarInfo
	 * @throws XPathExpressionException
	 * @throws IOException
	 */
	// Метка файла картинки
	private static String XPATH_PHOTO_SRC = "//div[@id='stm-gallery']//img";
	private static final String PHOTO_ATTR_SRC = "src";

	@Override
	protected TovarInfo fillPhoto() throws Exception {
		List<Photo> photos = findPhoto(manager);
		tovarInfo.getListPhoto().addAll(photos);
		return tovarInfo;
	}

	private List<Photo> findPhoto(Manager manager) throws Exception {
		String filename = "";
		List<Photo> ret = new ArrayList<Photo>();
		XPath xpath = getXPath();
		// Поиск самой картинки
		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = attr.getNamedItem(PHOTO_ATTR_SRC);
			if (imgnode != null) {
				String imgpath = imgnode.getNodeValue();
				imgpath=imgpath.replace("65x65", "1200x1200");
				filename = loadPhoto(imgpath, getNnum(), DELIMETER_NAMEFILE,
						null, i);
				System.out.println(filename);
				Photo photo = new Photo();
				photo.setManager(manager);
				photo.setPath(filename.replace(getBaseDirForPhoto(), ""));
				String info = "";
				photo.setName(info);
				ret.add(photo);
			}
		}
		return ret;
	}

	public String getSampleLink() {
		return "http://www.sotmarket.ru/product/";
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

}
