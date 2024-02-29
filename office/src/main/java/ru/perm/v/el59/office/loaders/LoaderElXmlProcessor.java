package ru.perm.v.el59.office.loaders;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;

/**
 * Подгузка ХЬД Эльдорадо
 * 
 * @author vasi
 * 
 */
public class LoaderElXmlProcessor {
	private ITovarInfoProvider tovarInfoProvider;
	private String path;
	private Boolean onlyNew;
	private Boolean busy = false;
	private String filename;

	public void process(Exchange exchange) throws Exception {
		if (!busy) {
			busy = true;
			Logger.getLogger(this.getClass().getName()).info("Загрузка XML.Начало.");
			LoaderElXml loader = new LoaderElXml();
			loader.load(path, onlyNew, tovarInfoProvider);
			Logger.getLogger(this.getClass().getName()).info("Загрузка XML.Конец.");
			busy = false;
		} else {
			Logger.getLogger(this.getClass().getName()).info("Загрузка XML.Занято");
		}
	}

	public void processByListFile(Exchange exchange) throws Exception {
		if (!busy) {
			busy = true;
			Logger.getLogger(this.getClass().getName()).info("Загрузка XML.Начало.");
			LoaderElXml loader = new LoaderElXml();
			loader.load(path, onlyNew, tovarInfoProvider, filename);
			Logger.getLogger(this.getClass().getName()).info("Загрузка XML.Конец.");
			busy = false;
		} else {
			Logger.getLogger(this.getClass().getName()).info("Загрузка XML.Занято");
		}
	}

	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean getOnlyNew() {
		return onlyNew;
	}

	public void setOnlyNew(Boolean onlyNew) {
		this.onlyNew = onlyNew;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
