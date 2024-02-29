package ru.perm.v.el59.office.loaders;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import org.apache.commons.io.FileUtils;
import java.util.logging.Logger; 
import ru.perm.v.el59.office.db.dto.elxml.*;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoaderElXml {

	public LoaderElXml() {
		super();
	}

	public String load(String dir, Boolean only_new,
			ITovarInfoProvider tovarInfoProvider) throws Exception {
		File directory = new File(dir);
		if (directory.isDirectory()) {
			XmlFilenameFilter ext = new XmlFilenameFilter();
			String[] arrfiles = directory.list(ext);
			List<String> listFiles = Arrays.asList(arrfiles);
			if (only_new) {
				listFiles = tovarInfoProvider.getNotLoadedFiles(listFiles);
			}
			Collections.sort(listFiles);
			for (String filename : listFiles) {
				loadFile(dir, filename, tovarInfoProvider);
			}
			String ret = "";
			for (String filename : listFiles) {
				ret = ret + "\n" + filename;
			}
			return ret;
		} else {
			return dir + "-не каталог";
		}
	}

	public void loadFile(String dir, String filename,
			ITovarInfoProvider tovarInfoProvider) throws Exception {
		Logger.getLogger(this.getClass()).info(
				"Сервер.Загрузка XML Эльдорадо. Каталог " + dir + ";Файл "
						+ filename);
		List<Good> listGood = getGoods(dir + File.separator + filename);
		tovarInfoProvider.createByListGood(listGood, filename);
		Logger.getLogger(this.getClass()).info("End");
	}

	public List<Good> getGoods(String filename) {
		Logger.getLogger(this.getClass()).info("Start receive "+filename);
		XStream xstream = new XStream(new Dom4JDriver());

		xstream.alias(Goods.class.getSimpleName(), Goods.class);
		xstream.alias(Good.class.getSimpleName(), Good.class);
		xstream.alias(Units.class.getSimpleName(), Units.class);
		xstream.alias("Features", FeaturePrice.class);
		xstream.alias(BarCodes.class.getSimpleName(), BarCodes.class);
		xstream.alias(Werks.class.getSimpleName(), Werks.class);
		xstream.addImplicitCollection(Goods.class, "goods", Good.class);
		xstream.addImplicitCollection(Good.class, "units", Units.class);
		xstream.addImplicitCollection(Good.class, "features",
				FeaturePrice.class);
		xstream.addImplicitCollection(Good.class, "werks", Werks.class);
		xstream.addImplicitCollection(Units.class, "barcodes", BarCodes.class);
		List<Good> listGood = new ArrayList<Good>();
		try {
			// Распознаю кодировку
			List<String> lines = FileUtils.readLines(new File(filename));
			String encoding="UTF-8";
			if(lines.size()>0) {
				String firstString = lines.get(0);
				if(firstString.contains("1251")) {
					encoding="Windows-1251";
				}
			}
			InputStream inputStream= new FileInputStream(filename);
			Reader reader = new InputStreamReader(inputStream,encoding);
			
			ObjectInputStream in = xstream.createObjectInputStream(reader);
			try {
				while (true) {
					Good good = (Good) in.readObject();
					listGood.add(good);
				}
			} catch (EOFException e) {
				Logger.getLogger(this.getClass()).info("Everythings readed!");
			}
			Logger.getLogger(this.getClass()).info("End receive "+filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listGood;
	}

	public void load(String path, Boolean onlyNew,
			ITovarInfoProvider tovarInfoProvider, String filename)
			throws Exception {
		try {
			List<String> listFile = FileUtils.readLines(new File(filename));
			for (String file : listFile) {
				loadFile(path, file, tovarInfoProvider);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
