package ru.perm.v.el59.office.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Photo;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.web.IParserSite;

public class ParserMailRu extends AParserSite implements IParserSite {
	// Кодировка сайта
	private static String CHARSET_SITE = "Windows-1251";
	// Метка кратких характеристик
	private static final String XPATH_SHORT_FEATURES = "//div[@class='goods_card__general_information__box_second clearfix']//li";
	// Метка полных характеристик
	private static String XPATH_FULL_FEATURES = "//div[@class='good__charectistic__full js-model_params']/h3/text() | "
			+ "//div[@class='good__charectistic__full js-model_params']/table//tr/th/span/b/text() | "
			+ "//div[@class='good__charectistic__full js-model_params']/table//tr/td/text()";
	// Метка файла картинки
	private static String XPATH_PHOTO_SRC = "//a[@class='js-image_preview goods_image_listing2__preview pseudo-link']";
	private static final String PHOTO_ATTR_SRC = "data-image-url";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "src=";

	public ParserMailRu() {
		super();
	}

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
		Document doc = getDoc();
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_SHORT_FEATURES);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		// Object result = expr.evaluate(doc);
		NodeList nodes = (NodeList) result;
		String info = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			// info = info + nodes.item(i).getNodeValue();
			System.out.println(info);
			if (nodes.item(i).hasChildNodes()) {
				NodeList child = nodes.item(i).getChildNodes();
				for (int j = 0; j < child.getLength(); j++) {
					String s = child.item(j).getNodeValue();
					System.out.println(s);
					s = s.substring(0, 1).toUpperCase() + s.substring(1);
					info = info + s + ". ";
				}
			}
		}
		tovarInfo.setInfo(clearHTML(info));

		// Полные характеристики
		// doc = getDoc(getUrl().replace("model.xml", "model-spec.xml"));
		expr = xpath.compile(XPATH_FULL_FEATURES);
		result = expr.evaluate(doc, XPathConstants.NODESET);
		nodes = (NodeList) result;
		boolean isname = true;
		String nameGroup = "";
		Feature f = new Feature();
		for (int i = 0; i < nodes.getLength(); i++) {
			String valNode = nodes.item(i).getNodeValue();
			org.w3c.dom.Node n = nodes.item(i);
			// NamedNodeMap attr = n.getParentNode().getAttributes();
			String parentTag = n.getParentNode().getNodeName();
			// Группа
			// org.w3c.dom.Node a = attr.getNamedItem("class");
			if (parentTag.equals("h3")) {
				nameGroup = n.getNodeValue();
			} else {
				String s = valNode.trim();
				if (parentTag.equals("td")) {
					f.setVal(s);
					isname = false;
				} else {
					if(!s.isEmpty()) {
						f.setName(s);
						isname = true;
					}
				}
				if (!isname) {
					isname = false;
					f.setGrp(nameGroup);
					f.setGrp(clearHTML(f.getGrp()).trim());
					f.setName(clearHTML(f.getName()).trim());
					f.setVal(clearHTML(f.getVal()).trim());
					if (f != null) {
						if (!f.getName().equals("") && !f.getVal().equals("")) {
							if(!isLoadedDimension(f)) {
								tovarInfo.addFeature(f);
							}
						}
					}
//					System.out.println(String.format("%s \t %s \t %s", f.getGrp(),f.getName(),f.getVal()));
					f = new Feature();
				}
			}
		}
/*		for (Feature feature : tovar.getListFeature()) {
			System.out.println(String.format("%s \t %s \t %s", feature.getGrp(),feature.getName(),feature.getVal()));
		}
*/		return tovarInfo;
	}

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
		// Основная картинка
		List<Photo> photos = findPhoto();
		tovarInfo.getListPhoto().addAll(photos);
		return tovarInfo;
	}

	private List<Photo> findPhoto() throws Exception {
		String filename = "";
		List<Photo> ret= new ArrayList<Photo>();
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
					imgpath = imgpath.replace("//", "https://");
				}

				filename = loadPhoto(imgpath, getNnum(), DELIMETER_NAMEFILE, "&amp;version", i);
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
		return "https://torg.mail.ru/";
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

}
