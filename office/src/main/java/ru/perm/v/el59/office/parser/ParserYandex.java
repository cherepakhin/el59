package ru.perm.v.el59.office.parser;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParserYandex extends AParserSite implements IParserSite {
	// Кодировка сайта
	private static String CHARSET_SITE = "UTF-8";
	// Метка кратких характеристик
	private static final String XPATH_SHORT_FEATURES = "//li[@class='n-product-spec-list__item']";
	// Метка полных характеристик
	private static String XPATH_FULL_FEATURES = "//span[@class='n-product-spec__name-inner']/text() | //span[@class='n-product-spec__value-inner']/text() | //div[@class='n-product-spec-wrap__body']/h2/text() ";
	// Метка файла картинки
	private static String XPATH_PHOTO_SRC = "//li[@class='n-gallery__item']//img ";
	private static String XPATH_PHOTO_SRC1 = "//div[@class='n-gallery-popup__item']//img";
	private static final String PHOTO_ATTR_SRC = "src";
	private static final String PHOTO_ATTR_SRC1 = "src";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "=";

	public ParserYandex() {
		super();
	}

	/**
	 * Формирование информации о товаре с сайта Яндекс-Маркет
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
	 * (tovar == null) return null; if (parseProperty) { tovarInfo =
	 * fillInfo(tovarInfo, nnum); } if (parsePicture) { tovarInfo =
	 * fillPhoto(tovarInfo, nnum, manager); } tovarInfo.setLink(getUrl());
	 * tovarInfo.setManager(manager); setOld(tovarInfo);
	 * getTovarInfoProvider().update(tovarInfo);
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
	@Override
	protected TovarInfo fillInfo() throws IOException, XPathExpressionException {
		// Корректировка url. На иВход Игорь загоняет строку
		// https://market.yandex.ru/product/7275466
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_SHORT_FEATURES);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		String info = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			String s = nodes.item(i).getNodeValue();
			if (s != null) {
				info = info + nodes.item(i).getNodeValue().trim();
				s = s.substring(0, 1).toUpperCase() + s.substring(1);
				info = info + s + ".";
			}
			if (nodes.item(i).hasChildNodes()) {
				NodeList child = nodes.item(i).getChildNodes();
				for (int j = 0; j < child.getLength(); j++) {
					if (child.item(j).getNodeValue() != null) {
						s = child.item(j).getNodeValue().trim();
						s = s.substring(0, 1).toUpperCase() + s.substring(1);
						info = info + s + ". ";
					}
				}
			}
		}
		tovarInfo.setInfo(clearHTML(info));

		// Полные характеристики
		// Характеристики находятся
		// https://market.yandex.ru/product/7275466/spec
		// Document doc = getDoc(getUrl() + "/spec");
		Document doc = getDoc(getUrl());
		expr = xpath.compile(XPATH_FULL_FEATURES);
		result = expr.evaluate(doc, XPathConstants.NODESET);
		nodes = (NodeList) result;
		boolean isname = true;
		boolean isgroup = false;
		String nameGroup = "";
		Feature f = new Feature();
		for (int i = 0; i < nodes.getLength(); i++) {
			String valNode = nodes.item(i).getNodeValue();
			org.w3c.dom.Node n = nodes.item(i);
			NamedNodeMap attr = n.getParentNode().getAttributes();
			String parentTag = n.getParentNode().getNodeName();
			// Группа
			org.w3c.dom.Node a = attr.getNamedItem("class");
			if (parentTag.equals("h2")) {
				isgroup = true;
				nameGroup = n.getNodeValue();
			}
			if (!isgroup) {
				if (a.getNodeValue().equals("n-product-spec__value-inner")) {
					String s = valNode.trim();
					String delim = "";
					if (!f.getVal().equals("")) {
						delim = ",";
					}
					f.setVal(clearHTML(f.getVal() + delim + s));
					isname = false;
				} else {
					isname = true;
				}
				if (isname) {
					isname = false;

					if (f != null) {
						if (!f.getName().equals("") && !f.getVal().equals("")) {
							if (!isLoadedDimension(f)) {
								tovarInfo.addFeature(f);
							}
						}
					}
					f = new Feature();
					f.setGrp(nameGroup.trim());
					f.setName(valNode.trim());
				}
			} else {
				isgroup = false;
				String s = nameGroup.trim();
				if (!s.equals("")) {
					f.setGrp(nameGroup.trim());
				}
			}
		}
		for (Feature feature : tovarInfo.getListFeature()) {
			Logger.getLogger(this.getClass().getName()).info(
					String.format("Хар-ка: %s:%s:%s", feature.getGrp(),
							feature.getName(), feature.getVal()));
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
	 * @throws Exception
	 */
	@Override
	protected TovarInfo fillPhoto() throws Exception {
		// Основная картинка
		List<Photo> photos = findPhoto();
		tovarInfo.getListPhoto().addAll(photos);
		return tovarInfo;
	}

	private List<Photo> findPhoto() throws Exception {
		String filename = "";
		List<Photo> ret = new ArrayList<Photo>();
		ArrayList<String> imgPaths = new ArrayList<String>();
		if(getUrl().startsWith("http")) {
			imgPaths = getImgPathFromHTTP();
		} else {
			// если в по http url нет картинок, то возможно в url содержимое html-страницы
			imgPaths = getImgPathFromText();
		}
		int i=0;
		for (String imgPath : imgPaths) {
			// System.out.println(imgpath);
			filename = loadPhoto(imgPath, getNnum(), null, null,
					i);
			i++;
			// System.out.println(filename);
			Photo photo = new Photo();
			photo.setManager(manager);
			photo.setPath(filename.replace(getBaseDirForPhoto(), ""));
			String info = "";
			photo.setName(info);
			ret.add(photo);
		}
		return ret;
	}

	private ArrayList<String> getImgPathFromText() {
		int startPos = getUrl().indexOf("image&quot;:{&quot;url&quot;:&quot;//avatars.mds.yandex.net/get-mpic/");
		int endPos = getUrl().indexOf("&quot;}},&quot;", startPos);
		String imgPath = getUrl().substring(startPos, endPos);
		imgPath=imgPath.replace("image&quot;:{&quot;url&quot;:&quot;//", "http://");
		imgPath=imgPath.substring(0, imgPath.length()-1)+"orig";
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(imgPath);
		return ret;
	}

	private ArrayList<String> getImgPathFromHTTP()
			throws XPathExpressionException {
		ArrayList<String> ret = new ArrayList<String>();
		XPath xpath = getXPath();
		// Поиск картинок
		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		Document doc = getDoc();
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		String src = PHOTO_ATTR_SRC;
		if (nodes.getLength() == 0) {
			// Если картинка только одна
			expr = xpath.compile(XPATH_PHOTO_SRC1);
			result = expr.evaluate(doc, XPathConstants.NODESET);
			nodes = (NodeList) result;
			src = PHOTO_ATTR_SRC1;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = attr.getNamedItem(src);
			if (imgnode != null) {
				String imgpath = imgnode.getNodeValue();
				if (imgpath.startsWith("//")) {
					imgpath = imgpath.replace("//", "http://");
				}
				if (imgpath.contains("size")) {
					int pos = imgpath.indexOf("&amp;size");
					if (pos > 0) {
						imgpath = imgpath.substring(0, pos);
					}
				}
				ret.add(imgpath);
				// System.out.println(imgpath);
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
		return "https://market.yandex.ru";
	}

	@Override
	protected String loadPhoto(String link, Integer nnum, String delimeter,
			String endDelimeter, int i) throws Exception {
		String ret = null;
		String ext = ".jpg";
		ret = getFullFileName(getDirForPhoto(nnum), nnum + "_" + i + ext);
		if (link.length() < 40) {
			Logger.getLogger(this.getClass().getName()).info(
					String.format("%s %d %s", link, i, ret));
		}
		FileUtils.copyURLToFile(new URL(link), new File(ret));
		// FileUtils.copyURLToFile(new URL(link),new
		// File("/home/vasi/"+nnum+"_"+i+ext));
		return ret;
	}

}
