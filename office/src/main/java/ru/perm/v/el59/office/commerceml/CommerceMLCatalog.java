package ru.perm.v.el59.office.commerceml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.util.logging.Logger; 
import ru.perm.v.el59.office.commerceml.model.CommerceInfo;
import ru.perm.v.el59.office.commerceml.model.Good;
import ru.perm.v.el59.office.commerceml.model.Property;
import ru.perm.v.el59.office.commerceml.model.PropertyName;
import ru.perm.v.el59.office.db.Tovar;
import ru.perm.v.el59.office.db.TovarInfo;
import ru.perm.v.el59.office.db.Vars;
import ru.perm.v.el59.office.iproviders.ICommerceMLCatalog;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.iproviders.ITovarProvider;
import ru.perm.v.el59.office.iproviders.IVarProvider;
import ru.perm.v.el59.office.iproviders.web.ISubsFeatureProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommerceMLCatalog implements ICommerceMLCatalog {
	/**
	 * Каталог для хранения информации (import.xml+картинки)
	 */
	private IVarProvider varProvider;
	private ITovarInfoProvider tovarInfoProvider;
	private ITovarProvider tovarProvider;
	private ISubsFeatureProvider subsFeatureProvider;
	private String rootDirName;
	private HashMap<String, PropertyName> hashPropertyName;
	private List<Good> listGood;
	private IFillerFeatures fillerFeatures;

	@Override
	public void emptyDestDirectory() throws IOException {
		if (getRootDirName() != null && !getRootDirName().isEmpty()) {
			File root = new File(getRootDirName());
			FileUtils.deleteDirectory(root);
		}
	}

	@Override
	public boolean uploadFile(byte[] data, String path) throws IOException {
		path = normalize(path);
		String filename = getRootDirName() + path;
		FileUtils.writeByteArrayToFile(new File(filename), data);
		return false;
	}

	private String normalize(String path) {
		return FilenameUtils.normalize(path);
	}

	public String getRootDirName() {
		if (rootDirName == null) {
			setRootDirName((String) varProvider.getValue(Vars.DirForCommerceML));
		}
		return rootDirName;
	}

	public void setRootDirName(String rootDirName) {
		this.rootDirName = FilenameUtils.normalizeNoEndSeparator(rootDirName);
		File root = new File(rootDirName);
		if (!root.exists()) {
			root.mkdir();
		}
	}

	public IVarProvider getVarProvider() {
		return varProvider;
	}

	public void setVarProvider(IVarProvider varProvider) {
		this.varProvider = varProvider;
	}

	public List<String> findNameGood(String pattern) {
		ArrayList<String> ret = new ArrayList<String>();
		List<Good> list = getListGood();
		pattern = pattern.toLowerCase();
		for (Good good : list) {
			if (good.getName().toLowerCase().contains(pattern)) {
				ret.add(good.getName());
			}
		}
		return ret;
	}

	private Good getGood(String name) {
		List<Good> _listGood = getListGood();
		for (Good good : _listGood) {
			if (good.getName().equals(name)) {
				return good;
			}
		}
		return null;
	}

	public boolean setTovarInfo(Tovar tovar, String goodName) throws Exception {
		Integer nnum = tovar.getNnum();
		TovarInfo tovarInfo = (TovarInfo) getTovarInfoProvider().initialize(
				tovar.getNnum());
		if (tovarInfo == null) {
			tovarInfo = new TovarInfo();
			tovar = (Tovar) getTovarProvider().initialize(tovar.getNnum());
			if (tovar != null) {
				tovarInfo.setTovar(tovar);
			} else {
				Logger.getLogger(this.getClass().getName()).error(
						"Товар не найден " + nnum);
				return false;
			}
		}
		Good good = getGood(goodName);
		if (good == null) {
			Logger.getLogger(this.getClass().getName()).error(
					"Товар в каталоге не найден " + goodName);
			return false;
		}
		tovarInfo = getFillerFeatures().fillInfo(tovarInfo, good);
		try {
			tovarInfo = getFillerFeatures().fillPhoto(getRootDirName(),
					tovarInfo, good);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).error(
					"Ошибка при подключения картинки " + goodName);
			e.printStackTrace();
		}
		getTovarInfoProvider().update(tovarInfo);
		getSubsFeatureProvider().processTovarInfo(tovarInfo);
		return true;
	}

	/*
	 * private TovarInfo fillInfo(TovarInfo tovarInfo, Good good) { String
	 * nameGroup=""; for (PropertyVal propertyVal : good.getListPropertyVal()) {
	 * Feature f = new Feature(); f.setGrp(nameGroup);
	 * if(hashPropertyName.containsKey(propertyVal.getId())) { String
	 * nameFeature=hashPropertyName.get(propertyVal.getId()).getName();
	 * f.setName(nameFeature); String valNode=propertyVal.getVal();
	 * f.setVal(valNode); tovarInfo.addFeature(f);
	 * Logger.getLogger(this.getClass
	 * ()).info(String.format("Для товара добавлена %s хар-ка  %s:%s:%s",
	 * good.getName(),nameGroup,nameFeature,valNode)); } }
	 * 
	 * return tovarInfo; }
	 */
	public HashMap<String, PropertyName> getHashPropertyName() {
		if (hashPropertyName == null) {
			getListGood();
		}
		return hashPropertyName;
	}

	public List<Good> getListGood() {
		if (listGood == null || listGood.size() == 0) {
			loadCatalog();
		}
		return listGood;
	}

	private void loadCatalog() {
		listGood = new ArrayList<Good>();
		hashPropertyName = new HashMap<String, PropertyName>();
		try {
			String importxmlfile = FilenameUtils.concat(getRootDirName(),
					"import.xml");
			String xml = FileUtils.readFileToString(new File(importxmlfile),
					"UTF-8");
			int pos = xml.indexOf("<");
			xml = xml.substring(pos);
			XStream xstream = new XStream(new DomDriver());
			xstream.autodetectAnnotations(true);
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias("КоммерческаяИнформация", CommerceInfo.class);
			CommerceInfo commerceInfo = new CommerceInfo();
			commerceInfo = (CommerceInfo) xstream.fromXML(xml);
			listGood = commerceInfo.getCatalog().getGoods();
			for (PropertyName propertyName : commerceInfo.getClassificator()
					.getListPropertyName()) {
				hashPropertyName.put(propertyName.getId(), propertyName);
			}
			for (Good good : listGood) {
				for (Property property : good.getListProperty()) {
					if (hashPropertyName.containsKey(property.getId())) {
						String nameProperty = hashPropertyName.get(
								property.getId()).getName();
						property.setName(nameProperty);
					} else {
						Logger.getLogger(this.getClass().getName()).error(
								String.format(
										"Для товара %s не найдена хар-ка %s",
										good.getName(), property.getId()));
					}
				}
			}
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).error("Каталог не загружен.");
			Logger.getLogger(this.getClass().getName()).error(e);
			e.printStackTrace();
		}
	}

	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

	public ITovarProvider getTovarProvider() {
		return tovarProvider;
	}

	public void setTovarProvider(ITovarProvider tovarProvider) {
		this.tovarProvider = tovarProvider;
	}

	public ISubsFeatureProvider getSubsFeatureProvider() {
		return subsFeatureProvider;
	}

	public void setSubsFeatureProvider(ISubsFeatureProvider subsFeatureProvider) {
		this.subsFeatureProvider = subsFeatureProvider;
	}

	public IFillerFeatures getFillerFeatures() {
		return fillerFeatures;
	}

	public void setFillerFeatures(IFillerFeatures fillerFeatures) {
		this.fillerFeatures = fillerFeatures;
	}

}
