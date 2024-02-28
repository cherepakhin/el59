package ru.perm.v.el59.office.test.commerceml;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import ru.el59.office.commerceml.Catalog;
import ru.el59.office.commerceml.Classificator;
import ru.el59.office.commerceml.TovarInfoXml;
import ru.el59.office.db.Feature;
import ru.el59.office.db.GroupT;
import ru.el59.office.db.GroupTovar;
import ru.el59.office.db.Photo;
import ru.el59.office.db.Tovar;
import ru.el59.office.db.TovarInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreemaker {

	@Test
	public void convertorGroupTXml() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(GroupT.class, "/");
		try {
			Template groupTemplate = configuration
					.getTemplate("tovarinfoxml.ftl");
			StringWriter writer = new StringWriter();
			Map<String, Object> map = new HashMap<String, Object>();

/*			Classificator c = new Classificator();
			c.setName("Каталог");
			c.setId("0");
*/			
			GroupT root = new GroupT();
			root.setCod("0");
			root.setName("-");

			
			GroupT groupt = new GroupT();
			groupt.setCod("000000");
			groupt.setName("name0");
/*			GroupTXml grouptXML = new GroupTXml(groupt);
			grouptXML.setOrd(0);*/

			GroupT child = new GroupT();
			child.setCod("010000");
			child.setName("name01");
			groupt.addChild(child);
/*			GroupTXml childGrouptXML = new GroupTXml(child);
			childGrouptXML.setOrd(0);*/
/*			grouptXML.getChilds().add(childGrouptXML);*/
			
//			c.getGroups().add(grouptXML);
			
			root.addChild(groupt);
			
			groupt = new GroupT();
			groupt.setCod("000001");
			groupt.setName("name1");
/*			grouptXML = new GroupTXml(groupt);
			grouptXML.setOrd(1);
			c.getGroups().add(grouptXML);*/

			root.addChild(groupt);

			ArrayList<TovarInfo> listTovarInfo = new ArrayList<TovarInfo>();
			TovarInfo tovarInfo = new TovarInfo();
			tovarInfo.setNnum(1111);
			tovarInfo.setInfo("info1");
			Feature f = new Feature();
			f.setName("nameFeature1");
			f.setVal("featureVal1");
			f.setGrp("Grp1");
			f.setTovarInfo(tovarInfo);
			tovarInfo.getListFeature().add(f);
			
			f = new Feature();
			f.setGrp("Grp2");
			f.setName("nameFeature2");
			f.setVal("featureVal2");
			f.setTovarInfo(tovarInfo);
			tovarInfo.getListFeature().add(f);

			Photo p = new Photo();
			p.setName("photo1");
			p.setPath("pathPhoto1");
			p.setOrd(0);
			p.setTovarInfo(tovarInfo);
			tovarInfo.addPhoto(p);
			
			p = new Photo();
			p.setName("photo2");
			p.setPath("pathPhoto2");
			p.setOrd(0);
			p.setTovarInfo(tovarInfo);
			tovarInfo.addPhoto(p);

			GroupTovar groupTovar = new GroupTovar();
			groupTovar.setCod("0000");
			groupTovar.setGroupT(groupt);
			
			Tovar tovar = new Tovar();
			tovar.setGroup(groupTovar);
			tovar.setNnum(123456789);
			tovar.setName("tovar");
			tovar.setBrand("Brand");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			tovar.setDateChanged(cal.getTime());
			tovarInfo.setTovar(tovar);

			listTovarInfo.add(tovarInfo);
			
			ArrayList<Integer> liders = new ArrayList<Integer>();
			liders.add(123456789);
			
			map.put("groupt", root);
			map.put("listTovarInfo", listTovarInfo);
			map.put("liders", liders);
			groupTemplate.process(map, writer);

			System.out.println(writer.toString());

		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	@Test
	@Ignore
	public void convertorTovarInfoXml() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

		configuration.setClassForTemplateLoading(Classificator.class, "/");
		try {
			Template catalogTemplate = configuration
					.getTemplate("tovarinfoxml.ftl");
			StringWriter writer = new StringWriter();
			Map<String, Object> map = new HashMap<String, Object>();

			Catalog c = new Catalog();
			c.setName("Каталог товаров");
			c.setId("0");
			
			GroupT groupt = new GroupT();
			groupt.setCod("000000");
			groupt.setName("name0");

			groupt = new GroupT();
			groupt.setCod("000001");
			groupt.setName("name1");

			TovarInfo tovarInfo = new TovarInfo();
			tovarInfo.setNnum(1111);
			tovarInfo.setInfo("info1");
			Feature f = new Feature();
			f.setName("nameFeature1");
			f.setVal("featureVal1");
			f.setGrp("Grp1");
			f.setTovarInfo(tovarInfo);
			tovarInfo.getListFeature().add(f);
			
			f = new Feature();
			f.setGrp("Grp2");
			f.setName("nameFeature2");
			f.setVal("featureVal2");
			f.setTovarInfo(tovarInfo);
			tovarInfo.getListFeature().add(f);

			Photo p = new Photo();
			p.setName("photo1");
			p.setPath("pathPhoto1");
			p.setOrd(0);
			p.setTovarInfo(tovarInfo);
			tovarInfo.addPhoto(p);
			
			p = new Photo();
			p.setName("photo2");
			p.setPath("pathPhoto2");
			p.setOrd(0);
			p.setTovarInfo(tovarInfo);
			tovarInfo.addPhoto(p);

			GroupTovar groupTovar = new GroupTovar();
			groupTovar.setCod("0000");
			groupTovar.setGroupT(groupt);
			
			Tovar tovar = new Tovar();
			tovar.setGroup(groupTovar);
			tovar.setNnum(123456789);
			tovar.setName("tovar");
			tovar.setBrand("Brand");
			tovarInfo.setTovar(tovar);
			TovarInfoXml tovarInfoXml = new TovarInfoXml(tovarInfo);
			c.getListTovarInfoXml().add(tovarInfoXml);
			
			map.put("catalog", c);
			catalogTemplate.process(map, writer);

			System.out.println(writer.toString());

		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
}
