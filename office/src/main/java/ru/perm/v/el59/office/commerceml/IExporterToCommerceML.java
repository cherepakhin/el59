package ru.perm.v.el59.office.commerceml;

import java.io.IOException;
import java.util.List;

import freemarker.template.TemplateException;

import ru.perm.v.el59.office.db.TovarInfo;

public interface IExporterToCommerceML {

	/**
	 * Получение xml в формате CommerceML
	 * @param listTovarInfo - список описаний товаров
	 * @param listLider - список лидеров продаж
	 * @return - текст xml
	 * @throws IOException
	 * @throws TemplateException
	 */
	String getXmlCommerceML(List<TovarInfo> listTovarInfo,
			List<Integer> listLider) throws IOException, TemplateException;

}
