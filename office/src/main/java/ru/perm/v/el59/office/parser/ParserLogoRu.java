package ru.perm.v.el59.office.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Photo;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.web.IParserSite;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserLogoRu extends AParserSite implements IParserSite {
	// Кодировка сайта
	private static String CHARSET_SITE = "UTF-8";
	// Метка полных характеристик
	private static String XPATH_FULL_FEATURES = "//div[@class='param']/div[@class='name']/span/text() | //div[@class='param']/div[@class='value']/text()";
	// Метка файла картинки
	private static String XPATH_PHOTO_SRC = "//div[@class='item']/img";
	private static String XPATH_PHOTO_SRC1 = "//a[@name='obzor']//img";
	private static final String PHOTO_ATTR_SRC1 = "src";
	private static final String PHOTO_ATTR_SRC = "data-itemid";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";
	// Метка краткого описания
	private static final String XPATH_SHORT_FEATURES = "//div[@class='item_short_info']//text()";

	public ParserLogoRu() {
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
/*	@Override
	public Tovar parseTovar(Integer nnum, String _url, Manager manager,
			Boolean parseProperty, Boolean parsePicture) throws Exception {
		Tovar tovar = setNnumUrl(nnum, _url,parseProperty,parsePicture);
		if (tovar == null)
			return null;
		if (parseProperty) {
			tovarInfo = fillInfo(tovarInfo, nnum);
		}
		if (parsePicture) {
			tovarInfo = fillPhoto(tovarInfo, nnum, manager);
		}
		tovarInfo.setLink(getUrl());
		tovarInfo.setManager(manager);
		setOld(tovarInfo);
		getTovarInfoProvider().update(tovarInfo);
		getSubsFeatureProvider().processTovarInfo(tovarInfo);
		getMainFeatureProvider().substituteMainFeature(tovarInfo);
		return tovarInfo.getTovar();
	}
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
	@Override
	protected TovarInfo fillInfo()
			throws IOException, XPathExpressionException {
		Document _doc = getDoc();
		XPath xpath = getXPath();
		// Инфо
		XPathExpression expr = xpath.compile(XPATH_SHORT_FEATURES);
		Object result = expr.evaluate(_doc, XPathConstants.NODESET);
		// Object result = expr.evaluate(doc);
		NodeList nodes = (NodeList) result;
		String info = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			info = info + nodes.item(i).getNodeValue().trim();
		}
		System.out.println(info);
		tovarInfo.setInfo(clearHTML(info));

		// Полные характеристики
		// doc = getDoc(getUrl().replace("model.xml", "model-spec.xml"));
		expr = xpath.compile(XPATH_FULL_FEATURES);
		result = expr.evaluate(_doc, XPathConstants.NODESET);
		nodes = (NodeList) result;
		Feature f = new Feature();
		for (int i = 0; i < nodes.getLength(); i++) {
			String valNode = nodes.item(i).getNodeValue();
			org.w3c.dom.Node n = nodes.item(i);
			// NamedNodeMap attr = n.getParentNode().getAttributes();
			String parentTag = n.getParentNode().getNodeName();
			// Группа
			// org.w3c.dom.Node a = attr.getNamedItem("class");
			String s = valNode.trim();
			if (parentTag.equals("span")) {
				f.setName(s);
			} else {
				f.setVal(clearHTML(s));
				System.out.println(String.format("%s \t %s \t %s", f.getGrp(),
						f.getName(), f.getVal()));
				if (f != null) {
					if (!f.getName().equals("") && !f.getVal().equals("")) {
						if(!isLoadedDimension(f)) {
							tovarInfo.addFeature(f);
						}
					}
				}
				f = new Feature();
			}
		}
		return tovarInfo;
	}

	/**
	 * Загрузка фотографий
	 * 
	 * @param tovar - TovarInfj для заполнения
	 * @param nnum
	 *            - н.номер
	 * @return - заполненный TovarInfo
	 * @throws Exception
	 */
	@Override
	protected TovarInfo fillPhoto()
			throws Exception {
		// Основная картинка
		List<Photo> photos = findPhoto();
		tovarInfo.getListPhoto().addAll(photos);
		return tovarInfo;
	}

	private List<Photo> findPhoto()
			throws Exception {
		String filename = "";
		XPath xpath = getXPath();
		List<Photo> ret = new ArrayList<Photo>();
		// Поиск самой картинки
		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		// Если нет списка картинок, то значит картинка одна и ее линк
		// XPATH_PHOTO_SRC1
		String srcImg = PHOTO_ATTR_SRC;
		if (nodes.getLength() == 0) {
			expr = xpath.compile(XPATH_PHOTO_SRC1);
			result = expr.evaluate(getDoc(), XPathConstants.NODESET);
			nodes = (NodeList) result;
			srcImg=PHOTO_ATTR_SRC1;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = attr.getNamedItem(srcImg);
			if (imgnode != null) {
				String imgpath = imgnode.getNodeValue();
				if (imgpath.startsWith("//")) {
					imgpath = imgpath.replace("//", "http://");
				}
/*				imgpath = getSampleLink() + imgpath;
				imgpath = imgpath.replace("ss_", "");
*/
				filename = loadPhoto(imgpath, getNnum(), DELIMETER_NAMEFILE, null, i);
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
		return "https://www.logo.ru";
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

}
