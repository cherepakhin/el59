package ru.perm.v.el59.office.dao.impl.web;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.perm.v.el59.office.db.web.RestWeb;
import ru.perm.v.el59.office.db.web.TypeSite;
import ru.perm.v.el59.office.iproviders.web.IRestWebProvider;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.Callable;

public class RestLoadThread implements Callable<Integer>{

	
	private IRestWebProvider restWebProvider;
	private TypeSite typeSite;
	private String file;
	private String delimCsv;
	private DecimalFormat df = new DecimalFormat("0");
	
	public RestLoadThread(IRestWebProvider restWebProvider, TypeSite typeSite,String file,String delimCsv) {
		this.restWebProvider=restWebProvider;
		this.typeSite=typeSite;
		this.file=file;
		this.delimCsv=delimCsv;
	}
	@Override
	public Integer call() throws Exception {
		Logger.getLogger(this.getClass().getName()).info("Выгрузка остатков. Начало");
		List<RestWeb> _listRest = restWebProvider.getListForSite(
				typeSite);

		String ret = "";
		Logger.getLogger(this.getClass().getName()).info("Начало формирования содержимого файла "+file);
		StringBuilder builder = new StringBuilder();
		for (RestWeb r : _listRest) {
			String rest = df.format(r.getQty());
			// Если товар со склада Эльдорадо, то цена с остатков
			// ЕСЛИ НЕТ ЦЕНЫ, ТО ТОВАР НЕ ПОПАДАЕТ НА САЙТ
			if (r.getCenaSupplier() != null) {
/*				builder.append(r.getShop().getCod());
				builder.append(delimCsv);*/
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
		Logger.getLogger(this.getClass().getName()).info("Конец формирования содержимого файла "+file);
		FileUtils.writeStringToFile(new File(file), builder.toString());
		Logger.getLogger(this.getClass().getName()).info("Выгрузка остатков. Конец");
		return 1;
	}

}
