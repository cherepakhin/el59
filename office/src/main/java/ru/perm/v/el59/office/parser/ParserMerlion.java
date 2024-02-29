package ru.perm.v.el59.office.parser;

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
import java.util.logging.Logger;

public class ParserMerlion extends AParserSite implements IParserSite {
	// Кодировка сайта
	private static String CHARSET_SITE = "Windows-1251";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	public ParserMerlion() {
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
	public Tovar parseTovar(Integer nnum, String _url, Manager manager,Boolean parseProperty,Boolean parsePicture)
			throws Exception {
		Tovar tovar = setNnumUrl(nnum, _url,parseProperty,parsePicture);
		if (tovar == null)
			return null;
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
			+ "//table[@class='properties']//tr[@class]/td/text() | "
			+ "//table[@class='properties']//tr[@class]/td/div/text()  " + "";

	// Тесты на
	// http://www.merlion.ru/catalog/product/625409/#img-0
	// http://www.merlion.ru/catalog/product/706793/#img-0
	@Override
	protected TovarInfo fillInfo() throws IOException, XPathExpressionException {
		XPath xpath = getXPath();

		// Полные характеристики
		XPathExpression expr = xpath.compile(XPATH_FULL_FEATURES);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		String nameGroup = "";
		String nameFeature = "";
		String valFeature = "";
		boolean flagNameFeature = false;
		boolean flagValFeature = false;
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node n = nodes.item(i);
			String valNode = n.getNodeValue().trim();
			if (flagNameFeature && !valNode.isEmpty()) {
				valFeature = valFeature + " " + valNode;
				flagValFeature = true;
			}

			if (i > 0 && i < (nodes.getLength() - 1)) {
				if (nodes.item(i + 1).getParentNode() == n.getParentNode()) {
					flagValFeature = false;
				}
			}

			// Node parent = n.getParentNode();
			// System.out.println(parent);

			if (!flagNameFeature) {
				nameFeature = valNode;
				flagNameFeature = true;
			}
			if (valNode.equals("Сайт производителя")) {
				flagNameFeature = false;
				flagValFeature = false;
			}
			if (valNode.equals("Тип поставки")) {
				flagNameFeature = false;
				flagValFeature = false;
			}
			if (flagNameFeature && flagValFeature) {
				Feature f = new Feature();
				f.setGrp(nameGroup.trim());
				f.setName(clearHTML(nameFeature.trim()));
				f.setVal(clearHTML(valFeature.trim()));
				if (f != null) {
					if (!f.getName().equals("") && !f.getVal().equals("")) {
						if(!isLoadedDimension(f)) {
							tovarInfo.addFeature(f);
						}
					}
				}
				flagNameFeature = false;
				flagValFeature = false;
				valFeature = "";
				nameFeature = "";
				System.out.println(String.format("%s|%s|%s", f.getGrp(),
						f.getName(), f.getVal()));
				// Logger.getLogger(this.getClass()).info(String.format("%s:%s:%s",
				// nameGroup,nameFeature,valFeature));
			}
		}
		return tovarInfo;
	}

	/**
	 * Загрузка фотографий
	 * 
	 * @param tovar
	 *            - TovarInfj для заполнения
	 * @param nnum
	 *            - н.номер
	 * @return - заполненный TovarInfo
	 * @throws XPathExpressionException
	 * @throws IOException
	 */
	// Метка файла картинки
	// private static String XPATH_PHOTO_SRC =
	// "//div[@class='ad-image' and @style]";
	private static String XPATH_PHOTO_SRC = "//div[@class='ad-thumbs']//a";
	private static final String PHOTO_ATTR_SRC = "href";

	@Override
	protected TovarInfo fillPhoto() throws Exception {
		// Основная картинка
		ArrayList<Photo> listPhoto = findPhoto();
		if (listPhoto.size() > 0) {
			getTovarInfo().getListPhoto().addAll(listPhoto);
		}
		return tovarInfo;
	}

	private ArrayList<Photo> findPhoto() throws Exception {
		ArrayList<Photo> ret = new ArrayList<Photo>();
		String filename = "";
//		XPath xpath = getXPath();
//		String _url = getUrl();
//		_url = _url.replace("#img-0", "photolist");
		
//		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
//		Object result = expr.evaluate(getDoc(_url), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;

		for (int num_img = 0; num_img < nodes.getLength(); num_img++) {
			Node n = nodes.item(num_img);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = attr.getNamedItem(PHOTO_ATTR_SRC);
			if (imgnode != null) {
				Photo photo = new Photo();
				photo.setManager(manager);
				String imgpath = imgnode.getNodeValue();
				filename = loadPhoto(imgpath, getNnum(), DELIMETER_NAMEFILE,null,
						num_img);
				Logger.getLogger(this.getClass()).info(
						String.format("Photo %d %s", getNnum(), filename));
				photo.setPath(filename.replace(getBaseDirForPhoto(), ""));
				String info = "";
				photo.setName(info);
				ret.add(photo);
			}
		}
		return ret;
	}

	public String getSampleLink() {
		return "http://merlion.com/catalog/product/";
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

}
