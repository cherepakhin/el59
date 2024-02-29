package ru.perm.v.el59.office.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Manager;
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

public class ParserTeplov extends AParserSite implements IParserSite {
	private static final String MAINPROPERTIES = "Основные характеристики";
	// Кодировка сайта
	private static String CHARSET_SITE = "windows-1251";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	public ParserTeplov() {
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
			+ "//div[@class='bx_item_detail']//div[@class='item_info_section']//table//tr/td/strong/text() |"
			+ "//div[@class='bx_item_detail']//div[@class='item_info_section']//table//tr/td/text() " + "";

	private static final String XPATH_SHORT_FEATURES = "" +
			"//div[@class='item_info_section']/text() | " +
			"//div[@class='item_info_section']/p/text()" ;

	@Override
	protected TovarInfo fillInfo() throws IOException, XPathExpressionException {

		XPath xpath = getXPath();

		Document doc = getDoc();
		
		XPathExpression expr = xpath.compile(XPATH_SHORT_FEATURES);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		String info = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			info = info + nodes.item(i).getNodeValue();
		}
		getTovarInfo().setInfo(clearHTML(info));

		// характеристики
		expr = xpath.compile(XPATH_FULL_FEATURES);
		result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		nodes = (NodeList) result;
		String nameGroup = "";
		String nameFeature = "";
		String valFeature = "";
		boolean flagNameFeature = false;
		boolean flagValFeature = false;
		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Node n = nodes.item(i);
			String valNode = n.getNodeValue().trim();
			Node parent = n.getParentNode();
			String tagParent=parent.getNodeName();

			if(tagParent.equals("strong")) {
				nameFeature = valNode;
				flagNameFeature = true;
			} else {
				valFeature = valNode;
				flagValFeature = true;
			}

			if(nameFeature.equals("Статус для заказа у поставщиков")) { 
				flagNameFeature=false;
				flagValFeature=false; 
			}

			if(nameFeature.equals("Базовая единица")) { 
				flagNameFeature=false;
				flagValFeature=false; 
			}

			if(nameFeature.equals("Реквизиты")) { 
				flagNameFeature=false;
				flagValFeature=false; 
			}
			if(nameFeature.equals("Ставки налогов")) { 
				flagNameFeature=false;
				flagValFeature=false; 
			}

			if (flagNameFeature && flagValFeature) {
				Feature f = new Feature();
				f.setGrp(nameGroup.trim());
				f.setName(nameFeature.trim());
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
	// Метка основной картинки. Нужна если картинка только одна
	private final static String XPATH_PHOTO_MAIN_SRC = "//div[@class='bx_bigimages_imgcontainer']/img";
	private static final String PHOTO_ATTR_SRC = "src";
	private static final String BASE_URL = "http://pechi.teplov.ru";

	@Override
	protected TovarInfo fillPhoto() throws Exception {
		// Основная картинка
		ArrayList<Photo> listPhoto = findPhoto(manager);
		if (listPhoto.size() > 0) {
			getTovarInfo().getListPhoto().addAll(listPhoto);
		}
		return tovarInfo;
	}

	private ArrayList<Photo> findPhoto(Manager manager) throws Exception {
		ArrayList<Photo> ret = new ArrayList<Photo>();
		String filename = "";
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_PHOTO_MAIN_SRC);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int num_img = 0; num_img < nodes.getLength(); num_img++) {
			Node n = nodes.item(num_img);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = attr.getNamedItem(PHOTO_ATTR_SRC);
			if (imgnode != null) {
				Photo photo = new Photo();
				photo.setManager(manager);
				String imgpath = BASE_URL + imgnode.getNodeValue();
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
		// если картинка только одна
		if (ret.size() == 0) {
			expr = xpath.compile(XPATH_PHOTO_MAIN_SRC);
			result = expr.evaluate(getDoc(), XPathConstants.NODESET);
			nodes = (NodeList) result;
			for (int num_img = 0; num_img < nodes.getLength(); num_img++) {
				Node n = nodes.item(num_img);
				NamedNodeMap attr = n.getAttributes();
				Node imgnode = attr.getNamedItem(PHOTO_ATTR_SRC);
				if (imgnode != null) {
					Photo photo = new Photo();
//					String imgpath = cleaningScale(imgnode.getNodeValue());
					String imgpath = imgnode.getNodeValue();
					filename = loadPhoto(imgpath, getNnum(),null,
							DELIMETER_NAMEFILE, num_img);
					Logger.getLogger(this.getClass()).info(
							String.format("Photo %d %s", getNnum(), filename));
					photo.setPath(filename.replace(getBaseDirForPhoto(), ""));
					String info = "";
					photo.setName(info);
					ret.add(photo);
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
		return "http://pechi.teplov.ru";
	}

}