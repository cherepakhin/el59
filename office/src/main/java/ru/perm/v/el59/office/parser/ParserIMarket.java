package ru.perm.v.el59.office.parser;

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

public class ParserIMarket extends AParserSite implements IParserSite {
	private static final String MAINPROPERTIES = "Основные характеристики";
	// Кодировка сайта
	private static String CHARSET_SITE = "UTF-8";
	// Символ, после которого будет название файла
	private static String DELIMETER_NAMEFILE = "/";

	public ParserIMarket() {
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
			+ "//div[@class='features']/div[@class='myprhar']/div[@class='pseudo_h3']/text() | "
			+ "//div[@class='features']/div[@class='myprhar']/div[@class='tab-holder']//td/text() | " +
			"//div[@class='features']/div[@class='myprhar']/div[@class='tab-holder']//td/img";

/*
<div class="features">
	<div class="myprhar">
		<div class="pseudo_h3">Основные характеристики</div>
			<div class="tab-holder">
				<table>
					<tbody>
						<tr class="dark">
							<td>Тип</td>
							<td>ЭЛТ-телевизор с плоским экраном</td>
						</tr>
						<tr class="">
							<td>Экранное меню на русском</td>
							<td>
								<img src="../../../../images/img-plus.gif" alt="+"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>	 
*/
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
			Node parent = n.getParentNode();
			String tagParent=parent.getNodeName();

			Node attrs = parent.getAttributes().getNamedItem("class");
			if (attrs!=null && attrs.getNodeValue().equals("pseudo_h3") ) {
				nameGroup=valNode;
			}

			if(tagParent.equals("td") || tagParent.equals("img")) {
				if(!flagNameFeature) {
					nameFeature = valNode;
					flagNameFeature = true;
				} else {
					if(n.getNodeName().equals("img")) {
						Node _plusAttr = n.getAttributes().getNamedItem("alt");
						if(_plusAttr!=null && _plusAttr.getNodeValue().equals("+")) {
							valFeature = "Да";
						} else {
							valFeature = "Нет";
						}
					} else {
						valFeature = valNode;
					}
					flagValFeature = true;
				}
			}

			if (flagNameFeature && flagValFeature) {
				Feature f = new Feature();
				f.setGrp(clearHTML(nameGroup).trim());
				f.setName(clearHTML(nameFeature).trim());
				f.setVal(clearHTML(valFeature).trim());
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
				// Logger.getLogger(this.getClass().getName()).info(String.format("%s:%s:%s",
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
	/*
	<div class="thumbnails">
		<div class="thumbnailimage">
			<div class="thumb_container">
				<div class="large_thumb" style="margin-left: 0px; margin-top: 0px;">
					<div class="large_thumb_border"/>
					<div class="large_thumb_shine" style="background-position: -99px 0px;">
						<img src="http://imarket59.ru/sites/default/files/imagecache/54x54/104522.jpg" alt="" title="" width="54" height="54" class="imagecache imagecache-54x54"/>
					</div>
					<div class="large_thumb_none" id="mythimg0">
						<a href="http://imarket59.ru/sites/default/files/104522.jpg" title="Apple MacBook Air 11 Mid 2013 MD711 " class="colorbox imagefield imagefield-imagelink imagefield-field_image_cache initColorbox-processed cboxElement" rel="gallery-10621">
							<img src="http://imarket59.ru/sites/default/files/imagecache/390x290/104522.jpg" alt="" title="Apple MacBook Air 11 Mid 2013 MD711 " width="390" height="208" class="imagecache imagecache-390x290"/>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="thumbnailimage">
	 */
	
	// Метка файла картинки
	// private static String XPATH_PHOTO_SRC =
	// "//div[@class='ad-image' and @style]";
	//<img src="http://cdn.e96.ru/assets/images/catalog/audio_video_dvd/audio_racks/36991/Mystery_MMK_615U_22174.jpg" alt="Микросистема Mystery MMK-615U" style="visibility: visible;">
	private final static String XPATH_PHOTO_SRC = "//div[@class='thumbnails']" +
			"/div[@class='thumbnailimage']" +
			"/div[@class='thumb_container']" +
			"//div[@class='large_thumb_none']/a";
	// Метка основной картинки. Нужна если картинка только одна
	private static final String PHOTO_ATTR_HREF = "href";

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
				String imgpath = imgnode.getNodeValue();
				filename = loadPhoto(imgpath, getNnum(), DELIMETER_NAMEFILE,null,
						num_img);
				Logger.getLogger(this.getClass().getName()).info(
						String.format("Photo %d %s", getNnum(), filename));
				photo.setPath(filename.replace(getBaseDirForPhoto(), ""));
				String info = "";
				photo.setName(info);
				ret.add(photo);
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
		// TODO Auto-generated method stub
		return "http://imarket59.ru/shop/";
	}

}