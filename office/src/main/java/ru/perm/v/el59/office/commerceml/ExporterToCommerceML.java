package ru.perm.v.el59.office.commerceml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ru.perm.v.el59.office.db.FeaturePrice;
import ru.perm.v.el59.office.db.GroupT;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.iproviders.IGroupTProvider;
import ru.perm.v.el59.office.iproviders.IGroupTovarProvider;
import ru.perm.v.el59.office.iproviders.IThingProvider;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.util.Helper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Экспортер в форме E-Commerce
 * 
 * @author vasi
 * 
 */
public class ExporterToCommerceML implements IExporterToCommerceML {

	private static Logger LOGGER = Logger.getLogger(ExporterToCommerceML.class);
	private IGroupTovarProvider groupTovarProvider;
	private IGroupTProvider groupTProvider;
	private ITovarInfoProvider tovarInfoProvider;
	private IThingProvider thingProvider;
	private String nameXmlFile;
	private String nameZipFile;
	private List<Integer> listLider;
	// Файл шаблона Freemaker справочника товаров, для выгрузки на сайт
	private String templateCatalog = "";

	public ExporterToCommerceML() {
		super();
	}

	/*
	 * public class UnitXmlConverter extends AbstractSingleValueConverter {
	 * 
	 * public boolean canConvert(Class clazz) { return
	 * clazz.equals(UnitXml.class); }
	 * 
	 * public Object fromString(String str) { UnitXml unit = new UnitXml(); //
	 * unit.setName(str); return unit; }
	 * 
	 * @Override public String toString(Object obj) { UnitXml unit = new
	 * UnitXml(); return unit.getVal(); }
	 * 
	 * } public class PhotoXmlConverter extends AbstractSingleValueConverter {
	 * 
	 * public boolean canConvert(Class clazz) { return
	 * clazz.equals(PhotoXml.class); }
	 * 
	 * public Object fromString(String str) { PhotoXml p = new PhotoXml(str);
	 * return p; }
	 * 
	 * @Override public String toString(Object obj) { PhotoXml p = (PhotoXml)
	 * obj; return p.getPath(); }
	 * 
	 * }
	 */@Override
	public String getXmlCommerceML(List<TovarInfo> listTovarInfo,
			List<Integer> listLider) throws IOException, TemplateException {
		this.listLider = listLider;
		LOGGER.info("Start export catalog to xml");
		GroupT root = new GroupT();
		root.setCod("");
		LOGGER.info("getGroupTProvider().getTree(root)");
		root = getGroupTProvider().getTree(root);
		System.gc();
		LOGGER.info("getGroupTProvider().initChilds(root)");
		root = getGroupTProvider().initChilds(root);
		System.gc();
		LOGGER.info("getXmlGroup(root)");
		StringBuffer xmlAll = new StringBuffer();
		Configuration configuration = new Configuration(
				Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(GroupT.class, "/");
		Template groupTemplate = configuration
				.getTemplate(getTemplateCatalog());
		StringWriter writer = new StringWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		// Замена короткого классификатора в названии товара полным.
		for (TovarInfo ti : listTovarInfo) {
			ti.getTovar().setNameFull(
					getThingProvider().createFullDescription(
							ti.getTovar().getName()));
			updateInfoIfEmpty(ti);
		}
		map.put("groupt", root);
		map.put("listTovarInfo", listTovarInfo);
		map.put("liders", listLider);
		LOGGER.info("start freemarker");
		groupTemplate.process(map, writer);
		LOGGER.info("end freemarker");
		System.gc();
		root = null;
		listTovarInfo = null;
		System.gc();
		xmlAll.append(writer);
		System.gc();
		LOGGER.info("End export catalog to xml");
		return xmlAll.toString();
	}

	/**
	 * Заполнение презентации из ценников, если не заполнено.
	 * 
	 * @param ti
	 *            -Описание товара
	 */
	private void updateInfoIfEmpty(TovarInfo ti) {
		if (ti.getInfo().isEmpty()) {
			String info = "";
			for (FeaturePrice f : ti.getListFeaturePrice()) {
				if (f.getVal() != null && !f.getVal().isEmpty()
						&& f.getName() != null
						&& f.getName().toLowerCase().contains("основная")
						&& f.getVal().length() > 1) {
					info = info + f.getVal().substring(0, 1).toUpperCase()
							+ f.getVal().substring(1) + ". ";
					
				}
			}
			for (FeaturePrice f : ti.getListFeaturePrice()) {
				if (f.getVal() != null && !f.getVal().isEmpty()
						&& f.getName() != null
						&& f.getName().toLowerCase().contains("аннотация")
						&& f.getVal().length() > 1) {
					info = info + f.getVal().substring(0, 1).toUpperCase()
							+ f.getVal().substring(1) + ". ";
				}
			}
			ti.setInfo(info);
		}

	}

	public String getXmlGroup(GroupT root) throws IOException,
			TemplateException {
		Classificator cf = new Classificator();
		cf.setId("1");
		cf.setName("Классификатор (Каталог товаров)");
		GroupTXml groupTxml = convertGroupTtoGroupTXml(root, 0);
		cf.setGroups(groupTxml.getChilds());
		return "";
	}

	private GroupTXml convertGroupTtoGroupTXml(GroupT groupt, Integer ord) {
		GroupTXml g = new GroupTXml(groupt);
		g.setOrd(ord);
		for (int i = 0; i < groupt.getChilds().size(); i++) {
			GroupT c = groupt.getChilds().get(i);
			GroupTXml child = convertGroupTtoGroupTXml(c, i);
			child.setCodParent(g.getCod());
			g.getChilds().add(child);

		}
		return g;
	}

	public String getCatalogXml(List<TovarInfo> list) throws IOException,
			TemplateException {
		Catalog catalog = new Catalog();
		catalog.setId("1");
		catalog.setName("Каталог товаров");
		LOGGER.info("Create list TovarInfoXml.Size=" + list.size());
		for (TovarInfo tovarInfo : list) {
			try {
				TovarInfoXml ti = new TovarInfoXml(tovarInfo);
				String newName = getThingProvider().createFullDescription(
						ti.getName());
				ti.setName(newName);
				if (isLider(ti.getNnum())) {
					ti.setLider("Да");
					ti.setDateChanged(Helper.getDateFornmatter().format(
							new Date()));
				} else {
					ti.setLider("Нет");
				}
				catalog.getListTovarInfoXml().add(ti);
			} catch (Exception e) {
				System.out.println(tovarInfo.getNnum());
				e.printStackTrace();
				LOGGER.error("Ошибка", e);
			}
		}
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(Catalog.class, "/");
		Template catalogTemplate = configuration
				.getTemplate(getTemplateCatalog());
		StringWriter writer = new StringWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("catalog", catalog);
		catalogTemplate.process(map, writer);
		return writer.toString();
	}

	/**
	 * Проверка на лидера продаж
	 * 
	 * @param nnum
	 * @return
	 */
	private boolean isLider(Integer nnum) {
		if (listLider != null && listLider.size() > 0) {
			return listLider.contains(nnum);
		}
		return false;
	}

	public IGroupTovarProvider getGroupTovarProvider() {
		return groupTovarProvider;
	}

	public void setGroupTovarProvider(IGroupTovarProvider groupTovarProvider) {
		this.groupTovarProvider = groupTovarProvider;
	}

	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

	public String getNameXmlFile() {
		return nameXmlFile;
	}

	public void setNameXmlFile(String nameXmlFile) {
		this.nameXmlFile = nameXmlFile;
	}

	public String getNameZipFile() {
		return nameZipFile;
	}

	public void setNameZipFile(String nameZipFile) {
		this.nameZipFile = nameZipFile;
	}

	public IGroupTProvider getGroupTProvider() {
		return groupTProvider;
	}

	public void setGroupTProvider(IGroupTProvider groupTProvider) {
		this.groupTProvider = groupTProvider;
	}

	public IThingProvider getThingProvider() {
		return thingProvider;
	}

	public void setThingProvider(IThingProvider thingProvider) {
		this.thingProvider = thingProvider;
	}

	public String getTemplateCatalog() {
		return templateCatalog;
	}

	public void setTemplateCatalog(String templateCatalog) {
		this.templateCatalog = templateCatalog;
	}
}
