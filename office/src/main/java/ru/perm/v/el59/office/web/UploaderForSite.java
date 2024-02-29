package ru.perm.v.el59.office.web;

import org.apache.camel.Exchange;
import ru.perm.v.el59.office.db.web.RestWeb;
import ru.perm.v.el59.office.db.web.TypeSite;
import ru.perm.v.el59.office.iproviders.ITovarInfoProvider;
import ru.perm.v.el59.office.iproviders.web.IRestWebProvider;
import ru.perm.v.el59.office.iproviders.web.IUploaderForSite;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Загрузчик сайтов
 * @author vasi
 *
 */

public class UploaderForSite implements IUploaderForSite {
	private ITovarInfoProvider tovarInfoProvider;
	private IRestWebProvider restWebProvider;
	private String codesetXml;
	private String delimCsv;
	private DecimalFormat df = new DecimalFormat("0");
	private String xmlFile="";
	private String rest0File="";
	private String rest1File="";
	private String script;

	private Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}

	public byte[] getCatalog(Exchange exchange) throws Exception {
		getLogger().info("Выгрузка каталога. Начало");
		String xml = getTovarInfoProvider().getXmlCommerceByTovarCritery();
		getLogger().info("Выгрузка каталога. Конец");
		return xml.getBytes(codesetXml);
	}

	public String getRest(Exchange exchange)
			throws UnsupportedEncodingException {
		return getRest(TypeSite.INNER);
	}

	private String getRest(TypeSite typeSite) {
		getLogger().info("Выгрузка остатков. Начало");
		List<RestWeb> _listRest = getRestWebProvider().getListForSite(
				typeSite);

		StringBuilder builder = new StringBuilder();
		for (RestWeb r : _listRest) {
			String rest = df.format(r.getQty());
			// Если товар со склада Эльдорадо, то цена с остатков
			// ЕСЛИ НЕТ ЦЕНЫ, ТО ТОВАР НЕ ПОПАДАЕТ НА САЙТ
			if (r.getCenaSupplier() != null) {
//				builder.append(r.getShop().getCod());
//				builder.append(delimCsv);
				builder.append(r.getTovar().getNnum());
				builder.append(delimCsv);
				builder.append(df.format(r.getQty()));
				builder.append(delimCsv);
				builder.append(df.format(r.getCenaOut()));
				builder.append(delimCsv);
				builder.append(r.getContragent().getN());
				builder.append(delimCsv);
				builder.append(r.getContragent().getName());
				builder.append("\n");
			}
		}
		getLogger().info("Выгрузка остатков. Конец");
		return builder.toString();
	}
	
	public ITovarInfoProvider getTovarInfoProvider() {
		return tovarInfoProvider;
	}

	public void setTovarInfoProvider(ITovarInfoProvider tovarInfoProvider) {
		this.tovarInfoProvider = tovarInfoProvider;
	}

	public IRestWebProvider getRestWebProvider() {
		return restWebProvider;
	}

	public void setRestWebProvider(IRestWebProvider restWebProvider) {
		this.restWebProvider = restWebProvider;
	}

	public String getCodesetXml() {
		return codesetXml;
	}

	public void setCodesetXml(String codesetXml) {
		this.codesetXml = codesetXml;
	}

	public String getDelimCsv() {
		return delimCsv;
	}

	public void setDelimCsv(String delimCsv) {
		this.delimCsv = delimCsv;
	}

	@Override
	public void upload() throws IOException {
		ExecutorService exec = Executors.newFixedThreadPool(3);
		ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
//		results.add(exec.submit(new CatalogThread(getTovarInfoProvider(),getXmlFile(),getCodesetXml())));
//		results.add(exec.submit(new RestLoadThread(getRestWebProvider(),TypeSite.INNER ,getRest0File(),getDelimCsv())));
//		results.add(exec.submit(new RestLoadThread(getRestWebProvider(),TypeSite.EL59,getRest1File(),getDelimCsv())));
//		Integer ret=0;
//		for (Future<Integer> future : results) {
//			try {
//				Logger.getLogger(this.getClass()).severe("ret="+ret);
//				ret=ret+future.get();
//			} catch (InterruptedException e) {
//				Logger.getLogger(this.getClass()).severe(e.getMessage());
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				Logger.getLogger(this.getClass()).severe(e.getMessage());
//				e.printStackTrace();
//			} finally {
//				exec.shutdown();
//			}
//		}
//		if(ret!=3) {
//			// Не все задачи выполнились
//			Logger.getLogger(this.getClass()).severe("Не все задачи выполнились");
//		} else {
//			runScript();
//		}
	}

	@Override
	public void runScript() throws IOException {
		Logger.getLogger(this.getClass()).info("Запуск скрипта загрузки сайта.");
		Process proc = Runtime.getRuntime().exec(getScript());
/*		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Logger.getLogger(this.getClass()).severe(e);
		}
*/		Logger.getLogger(this.getClass()).info("Cкрипт загрузки сайта отработал.");
	}
	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getRest0File() {
		return rest0File;
	}

	public void setRest0File(String rest0File) {
		this.rest0File = rest0File;
	}

	public String getRest1File() {
		return rest1File;
	}

	public void setRest1File(String rest1File) {
		this.rest1File = rest1File;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
