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

public class ParserMVideo extends AParserSite implements IParserSite {
	// Кодировка сайта
	private static String CHARSET_SITE = "UTF-8";

	public ParserMVideo() {
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
		Tovar tovar = setNnumUrl(nnum, _url, parseProperty, parsePicture);
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
	// Метка кратких характеристик
	private static final String XPATH_SHORT_FEATURES = "//div[@class='pds-top-description']//text()";
	// Метка полных характеристик
	private static String XPATH_FULL_FEATURES = "//div[@class='product-details-tables-holder']//text()";

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
	protected TovarInfo fillInfo() throws IOException, XPathExpressionException {
		Document doc = getDoc();
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_SHORT_FEATURES);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
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
		result = expr.evaluate(doc, XPathConstants.NODESET);
		nodes = (NodeList) result;
		String nameGroup = "";
		String nameFeature = "";
		boolean nameIsSet = false;
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node n = nodes.item(i);
			String valNode = n.getNodeValue();
			Node parent = n.getParentNode();
			if(!valNode.trim().isEmpty()) {
				System.out.println(valNode);
			}
			// Группа
			Node attr = parent.getAttributes().getNamedItem("class");
			if (n.getNodeName().equals("h3")) {
				nameGroup = valNode;
				// System.out.println(nameGroup);
				continue;
			}
			if (!nameIsSet) {
				if (parent.getNodeName().equals("div")
						&& parent.getParentNode().getNodeName().equals("li")) {
					nameFeature = valNode;
					nameIsSet = true;
					continue;
				}
			}
			if (attr != null && attr.getNodeValue().equals("js-pxl-tooltip")) {
				Feature f = new Feature();
				f.setGrp(clearHTML(nameGroup).trim());
				f.setName(clearHTML(nameFeature).trim());
				f.setVal(clearHTML(valNode).trim());
				System.out.println(String.format("%s \t %s \t %s", f.getGrp(),
						f.getName(), f.getVal()));

				if (f != null) {
					if (!f.getName().equals("") && !f.getVal().equals("")) {
						if(!isLoadedDimension(f)) {
							tovarInfo.addFeature(f);
						}
					}
				}
				nameIsSet = false;
				continue;
			}
		}
		return tovarInfo;
	}

	// Метка файла картинки
	private static String XPATH_PHOTO_SRC = "//a[@class='fancybox-thumb js-elevate-zoom'] | //a[@class='fancybox-thumb js-elevate-zoom  select']";
	private static final String PHOTO_ATTR_SRC = "data-zoom-image";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	/**
	 * Загрузка фотографий
	 * 
	 * @param tovar
	 *            - TovarInfj для заполнения
	 * @param nnum
	 *            - н.номер
	 * @return - заполненный TovarInfo
	 * @throws Exception
	 */
	@Override
	protected TovarInfo fillPhoto()
			throws Exception {
		List<Photo> photos = findPhoto();
		tovarInfo.getListPhoto().addAll(photos);
		return tovarInfo;
	}

	private List<Photo> findPhoto()
			throws Exception {
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
				if (imgpath.startsWith("//")) {
					imgpath = imgpath.replace("//", "http://");
				}

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
		return "http://www.mvideo.ru/";
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

}
