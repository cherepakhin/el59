package ru.perm.v.el59.office.dao.impl.web;

import org.apache.commons.io.FileUtils;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.util.Zip;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class CatalogThread implements Callable<Integer>{

	private ITovarInfoProvider tovarInfoProvider;
	private String xmlFile ;
	private String codesetXml;
	
	public CatalogThread(ITovarInfoProvider tovarInfoProvider, String xmlFile,String codesetXml) {
		this.tovarInfoProvider=tovarInfoProvider;
		this.xmlFile =xmlFile;
		this.codesetXml=codesetXml;
	}
	@Override
	public Integer call() throws Exception {
		Logger.getLogger(this.getClass()).info("Выгрузка каталога. Начало");
		String xml = tovarInfoProvider.getXmlCommerceByTovarCritery();
		File f = new File(xmlFile);
		FileUtils.writeStringToFile(f, xml, codesetXml);
		String zipName = xmlFile.replace(".xml", ".zip");
		Zip.zipFile(xmlFile, zipName);
		Logger.getLogger(this.getClass()).info("Выгрузка каталога. Конец");
		return 1;
	}

}
