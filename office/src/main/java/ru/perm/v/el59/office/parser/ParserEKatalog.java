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
import java.util.List;
import java.util.logging.Logger;

public class ParserEKatalog extends AParserSite implements IParserSite {
	// Кодировка сайта
	private static String CHARSET_SITE = "UTF-8";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	public ParserEKatalog() {
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
		if (tovar == null) {
			return null;
		}
		if (parseProperty) {
			fillInfo();
		}
		if (parsePicture) {
			fillPhoto(manager);
		}
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
	// Метка полных характеристик
	private static String XPATH_FULL_FEATURES = ""
			+ "//td[@class='op1']//text() | //td[@class='op3']//text()";

	private static String XPATH_INFO = "//div[@itemprop='description']//text()";
	private static String XPATH_SHORT_FEATURES = "//td[@class='prop']//text() | //td[@class='val']//text()";
//	XPATH_SHORT_FEATURES

	@Override
	protected TovarInfo fillInfo() throws IOException, XPathExpressionException {
		XPath xpath = getXPath();

		// Основная ссылка должна быть вида
		// http://www.e-katalog.ru/GORENJE-EC-55-CL.htm
		// Если есть полные характеристики,
		// то ссылка должна быть вида
		// http://www.e-katalog.ru/ek-item.php?resolved_name_=GORENJE-EC-55-CL&view_=tbl

		XPathExpression expr = xpath.compile(XPATH_INFO);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		// Object result = expr.evaluate(doc);
		NodeList nodes = (NodeList) result;
		String info = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			info = info + nodes.item(i).getNodeValue().trim();
		}
		System.out.println(info);
		tovarInfo.setInfo(clearHTML(info));

		expr = xpath.compile(XPATH_FULL_FEATURES);

		// Полные характеристики
		// Сначала пробую прямую ссылку, для товаров у ктр. хар-ки прямо на
		// главной странице
		result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		nodes = (NodeList) result;
		if (nodes.getLength() == 0) {
			// Если не найдено, тогда иду на вкладку "Характеристики"
			// Преобразование из базовой ссылки в ссылку с полными хар-ми
			String urlFullProps = getUrl().replace(getSampleLink(), "");
			urlFullProps = urlFullProps.replace(".htm", "");
			urlFullProps = getSampleLink() + "ek-item.php?resolved_name_="
					+ urlFullProps + "&view_=tbl";
			Document docFullProps = getDoc(urlFullProps);
			result = expr.evaluate(docFullProps, XPathConstants.NODESET);
			nodes = (NodeList) result;
		}
		String nameGroup = "";
		String nameFeature = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node n = nodes.item(i);
			String valNode = n.getNodeValue().trim();
			int c = 0;
			// какой то странный символ с кодом 160
			/*
			 * if (valNode.length() == 1) { c = (int) valNode.charAt(0); }
			 */
			if (!valNode.isEmpty() && c != 160) {
				NamedNodeMap attrGrp = n.getParentNode().getParentNode()
						.getAttributes();
				// Группа
				org.w3c.dom.Node aGrp = attrGrp.getNamedItem("class");
				if (aGrp != null && aGrp.getNodeValue().equals("op1-title")) {
					nameGroup = valNode;
					nameFeature = "";
					continue;
				} else {
					NamedNodeMap attrName = n.getParentNode().getParentNode()
							.getAttributes();
					org.w3c.dom.Node aNameClass = attrName
							.getNamedItem("class");
					if (aNameClass != null
							&& (aNameClass.getNodeValue().equals("op1"))) {
						nameFeature = valNode;
						continue;
					}
					attrName = n.getParentNode().getParentNode()
							.getParentNode().getAttributes();
					aNameClass = attrName.getNamedItem("class");
					if (aNameClass != null
							&& (aNameClass.getNodeValue().equals("op1"))) {
						nameFeature = nameFeature + " " + valNode;
						continue;
					}
					if (!valNode.equals(nameFeature)
							&& !valNode.equals(nameGroup) && !valNode.isEmpty()) {
						Feature f;
						if (nameFeature.isEmpty()) {
							f = getTovarInfo().getListFeature().get(
									getTovarInfo().getListFeature().size() - 1);
							f.setVal(f.getVal() + ";" + valNode.trim());
						} else {
							f = new Feature();
							f.setGrp(clearHTML(nameGroup.trim()));
							f.setName(clearHTML(nameFeature.trim()));
							f.setVal(clearHTML(valNode.trim()));
							if (f != null) {
								if (!f.getName().equals("") && !f.getVal().equals("")) {
									if(!isLoadedDimension(f)) {
										tovarInfo.addFeature(f);
									}
								}
							}
						}
						nameFeature = "";
					}
				}
			}
		}

		// Если не найдена вкладка Полных характеристик, то взять упрощенные
		// хар-ки
		if (getTovarInfo().getListFeature().size() == 0) {
			expr = xpath.compile(XPATH_SHORT_FEATURES);
			result = expr.evaluate(getDoc(), XPathConstants.NODESET);
			nodes = (NodeList) result;
			nameGroup = "";
			nameFeature = "";
			String valFeature = "";
			Feature f = null;
			for (int i = 0; i < nodes.getLength(); i++) {
				org.w3c.dom.Node n = nodes.item(i);
				String valNode = n.getNodeValue().trim();
				// Название хар-ки
				if (isValInAtributeInParent(n, "class", "prop", 4) || isValInAtributeInParent(n, "class", "prop", 3)) {
					if (isValInAtributeInParent(n, "class", "nobr ib", 1) || isValInAtributeInParent(n, "class", "prop", 2)) {
/*						nameGroup=nameGroup+" "+valNode;
					} else {
*/						addFeature(getTovarInfo(), f, nameGroup, nameFeature,
								valFeature);
						f = new Feature();
						nameFeature = valNode;
						valFeature = "";
					}
				}
				// Значение хар-ки
				if (isValInAtributeInParent(n, "class", "val", 4)) {
					valFeature = valFeature + valNode + " ";
				}
			}
			addFeature(getTovarInfo(), f, nameGroup, nameFeature, valFeature);
		}
		return tovarInfo;
	}

	private void addFeature(TovarInfo tovarInfo, Feature f, String nameGroup,
			String nameFeature, String valFeature) {
		if (f != null) {
			f.setGrp(clearHTML(nameGroup.trim()));
			f.setName(clearHTML(nameFeature.trim()));
			f.setVal(clearHTML(valFeature.trim()));
			List<Feature> listFeature = recognizeDimension(f);
			if (listFeature != null) {
				getTovarInfo().getListFeature().addAll(listFeature);
			} else {
				getTovarInfo().getListFeature().add(f);
			}
		}
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
	private static String XPATH_PHOTO_SRC = "//div[@class='img200 h']";
	private static final String PHOTO_ATTR_SRC = "onclick";
	private static final String BASE_URL = "http://www.e-katalog.ru";

	@Override
	protected TovarInfo fillPhoto() throws Exception {
		// Основная картинка
		Photo photo = findPhoto();
		photo.setManager(manager);
		if (photo.getPath() != null && !photo.getPath().equals("")) {
			getTovarInfo().getListPhoto().add(photo);
		}
		return tovarInfo;
	}

	private Photo findPhoto() throws Exception {
		String filename = "";
		Photo photo = new Photo();
		Document doc = getDoc();
		XPath xpath = getXPath();
		// Поиск самой картинки
		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = attr.getNamedItem(PHOTO_ATTR_SRC);
			if (imgnode != null) {
				String _val = imgnode.getNodeValue();
				if (_val.contains("return $.prettyPhoto.open_pg('")) {
					int pos = _val.indexOf("'");
					_val = _val.substring(pos + 1);
					pos = _val.indexOf("'");
					_val = _val.substring(0, pos);

					String imgpath = BASE_URL + _val;
					filename = loadPhoto(imgpath, getNnum(),
							DELIMETER_NAMEFILE, null, 1);
					Logger.getLogger(this.getClass().getName()).info(
							String.format("Photo %d %s", getNnum(), filename));
					photo.setPath(filename.replace(getBaseDirForPhoto(), ""));
					String info = "Общий вид";
					photo.setName(info);
				}
			}
		}
		return photo;
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

	@Override
	public String getSampleLink() {
		return "http://www.e-katalog.ru/";
	}

}
