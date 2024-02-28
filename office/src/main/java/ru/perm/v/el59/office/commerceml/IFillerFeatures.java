package ru.perm.v.el59.office.commerceml;

import java.io.IOException;

import ru.perm.v.el59.office.commerceml.model.Good;
import ru.perm.v.el59.office.db.TovarInfo;

public interface IFillerFeatures {
	public TovarInfo fillInfo(TovarInfo tovarInfo, Good good);

	/**
	 * Заполнение фотографиями tovarInfo.
	 * 
	 * @param pathRootDirCommerceML
	 *            - каталог где расположен файл import.xml и картинки
	 * @param tovarInfo
	 * @param good
	 *            - товар из каталога
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public TovarInfo fillPhoto(String pathRootDirCommerceML,
			TovarInfo tovarInfo, Good good) throws IOException, Exception;
}
