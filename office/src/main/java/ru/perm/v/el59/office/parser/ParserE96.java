package ru.perm.v.el59.office.parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.perm.v.el59.office.db.Feature;
import ru.perm.v.el59.office.db.Photo;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.web.IParserSite;

public class ParserE96 extends AParserSite implements IParserSite {
	private static final String MAINPROPERTIES = "Основные характеристики";
	// Кодировка сайта
	private static String CHARSET_SITE = "UTF-8";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	public ParserE96() {
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
	 * manager, Boolean parseProperty, Boolean parsePicture) throws Exception {
	 * Tovar tovar = setNnumUrl(nnum, _url,parseProperty,parsePicture); if
	 * (tovar == null) { return null; } if (parseProperty) { fillInfo(); } if
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
	// Метка полных характеристик
	private static String XPATH_INFO = ""
			+ "//section[@class='description']/p/text()";
	// Метка полных характеристик
	private static String XPATH_FULL_FEATURES = "" + "//h5/text() | "
			+ "//div[@class='feature_item']//span[@class='txt']/s/text() |"
			+ "//div[@class='feature_item']//span[@class='value']/text() " + "";

	@Override
	public TovarInfo fillInfo() throws IOException, XPathExpressionException {

		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_INFO);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		// Object result = expr.evaluate(doc);
		NodeList nodes = (NodeList) result;
		String info = "";
		for (int i = 0; i < nodes.getLength(); i++) {
			info = info + nodes.item(i).getNodeValue().trim() + " ";
		}
		System.out.println(info);
		tovarInfo.setInfo(clearHTML(info.trim()));

		// Полные характеристики
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
			String tagParent = parent.getNodeName();

			if (tagParent.equals("h5")) {
				nameGroup = valNode;
			}

			if (tagParent.equals("s")) {
				nameFeature = valNode;
				flagNameFeature = true;
			}

			if (tagParent.equals("span")) {
				String v = parent.getAttributes().getNamedItem("class")
						.getNodeValue();
				if (v != null && v.equals("value") && flagNameFeature) {
					valFeature = valNode;
					flagValFeature = true;
				}
			}

			if (valNode.equals("Товар сертифицирован")) {
				flagNameFeature = false;
				flagValFeature = false;
			}

			if (flagNameFeature && flagValFeature) {
				Feature f = new Feature();
				f.setGrp(nameGroup.trim());
				f.setName(nameFeature.trim());
				f.setVal(clearHTML(valFeature.trim()));
				if (f != null) {
					if (!f.getName().equals("") && !f.getVal().equals("")) {
						if (!isLoadedDimension(f)) {
							tovarInfo.addFeature(f);
						}
					}
				}
				flagNameFeature = false;
				flagValFeature = false;
				valFeature = "";
				nameFeature = "";
				Logger.getLogger(this.getClass()).info(
						String.format("%s|%s|%s", f.getGrp(), f.getName(),
								f.getVal()));
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
	// <img
	// src="http://cdn.e96.ru/assets/images/catalog/audio_video_dvd/audio_racks/36991/Mystery_MMK_615U_22174.jpg"
	// alt="Микросистема Mystery MMK-615U" style="visibility: visible;">
	// private final static String XPATH_PHOTO_SRC =
	// "//div[@class='imglisting-list']//a";
	private final static String XPATH_PHOTO_SRC = "//li[@class='image']//a[@data-href]";
	// Метка основной картинки. Нужна если картинка только одна
	private final static String XPATH_PHOTO_MAIN_SRC = "//div[@id='imglisting']/img";
	private static final String PHOTO_ATTR_SRC = "src";
	private static final String PHOTO_ATTR_HREF = "data-href";

	@Override
	public TovarInfo fillPhoto() throws Exception {
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
		XPath xpath = getXPath();
		XPathExpression expr = xpath.compile(XPATH_PHOTO_SRC);
		Object result = expr.evaluate(getDoc(), XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int num_img = 0; num_img < nodes.getLength(); num_img++) {
			Node n = nodes.item(num_img);
			NamedNodeMap attr = n.getAttributes();
			Node imgnode = attr.getNamedItem(PHOTO_ATTR_HREF);
			if (imgnode != null) {
				Photo photo = new Photo();
				photo.setManager(manager);
//				String imgpath = cleaningScale(imgnode.getNodeValue());
				String imgpath = imgnode.getNodeValue();
				filename = loadPhoto(imgpath, getNnum(), DELIMETER_NAMEFILE,
						null, num_img);
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
					// String imgpath = cleaningScale(imgnode.getNodeValue());
					String imgpath = imgnode.getNodeValue();
					filename = loadPhoto(imgpath, getNnum(),
							DELIMETER_NAMEFILE, null, num_img);
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

	private String cleaningScale(String nodeValue) {
		int posBeginNameFile = nodeValue.lastIndexOf("/");
		if (posBeginNameFile > 0) {
			String nameFileImg = nodeValue.substring(posBeginNameFile,
					nodeValue.length());
			String s = nodeValue.substring(0, posBeginNameFile);
			int lastPosDelimeter = s.lastIndexOf("/");
			if (lastPosDelimeter > 0) {
				s = s.substring(0, lastPosDelimeter) + nameFileImg;
				s = s.replace("thumbs/", "");
				return s;
			}
		}
		return "";
	}

	@Override
	public String getCHARSET_SITE() {
		return CHARSET_SITE;
	}

	@Override
	public String getSampleLink() {
		// TODO Auto-generated method stub
		return "http://e96.ru/";
	}

}